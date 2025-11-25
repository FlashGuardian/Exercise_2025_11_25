package com.example.exercise_2025_11_25;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ToDoItemEditActivity extends AppCompatActivity {

    EditText titleEditText;
    EditText descriptionEditText;
    EditText deadlineDateEditText;
    EditText deadlineTimeEditText;
    CheckBox statusCheckBox;
    Button saveButton;
    Button cancelButton;
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

    public boolean isDate(String date) {
        if (date == null)
            return false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate ld = null;
        try {
            ld = LocalDate.parse(date, formatter);
            System.out.println(ld);
        } catch (DateTimeParseException e) {
            System.out.println("Date " + date + " is not a date.");
            return false;
        }
        return true;
    }

    public boolean isTime(String time) {
        if (time == null)
            return false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime ld = null;
        try {
            ld = LocalTime.parse(time, formatter);
            System.out.println(ld);
        } catch (DateTimeParseException e) {
            System.out.println("Date " + time + " is not a date.");
            return false;
        }
        return true;
    }

    public boolean isDateTime(String datetime) {
        if (datetime == null)
            return false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        LocalDateTime ld = null;
        try {
            ld = LocalDateTime.parse(datetime, formatter);
            System.out.println(ld);
        } catch (DateTimeParseException e) {
            System.out.println("Date " + datetime + " is not a date.");
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_to_do_item_edit);
        int itemID = -1;

        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        deadlineDateEditText = findViewById(R.id.deadlineDateEditText);
        deadlineTimeEditText = findViewById(R.id.deadlineTimeEditText);
        statusCheckBox = findViewById(R.id.statusCheckBox);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);

        Intent incomingIntent = getIntent();
        if (incomingIntent != null) {
            String incomingDeadline = incomingIntent.getStringExtra("itemDeadline");
            LocalDateTime deadline = LocalDateTime.now();
            if (isDateTime(incomingDeadline)) {
                deadline = LocalDateTime.parse(incomingDeadline, dateTimeFormatter);
            }
            titleEditText.setText(incomingIntent.getStringExtra("itemTitle"));
            descriptionEditText.setText(incomingIntent.getStringExtra("itemDescription"));
            deadlineDateEditText.setText(deadline.format(dateFormatter));
            deadlineTimeEditText.setText(deadline.format(timeFormatter));
            statusCheckBox.setChecked(incomingIntent.getBooleanExtra("itemStatus", false));
            itemID = incomingIntent.getIntExtra("itemID", -1);
        }

        deadlineDateEditText.setOnClickListener(v -> {
            String selectedDateString = deadlineDateEditText.getText().toString().trim();
            LocalDate selectedDate = LocalDate.now();
            if (isDate(selectedDateString)) {
                 selectedDate = LocalDate.parse(selectedDateString, dateFormatter);
            }
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    ToDoItemEditActivity.this,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        // on below line we are setting date to our edit text.
                        deadlineDateEditText.setText(LocalDate.of(year, monthOfYear + 1, dayOfMonth).format(dateFormatter));
                    },
                    selectedDate.getYear(), selectedDate.getMonthValue() - 1, selectedDate.getDayOfMonth());
            datePickerDialog.show();
        });

        deadlineTimeEditText.setOnClickListener(v -> {
            String selectedTimeString = deadlineTimeEditText.getText().toString().trim();
            LocalTime selectedTime = LocalTime.now();
            if (isTime(selectedTimeString)) {
                selectedTime = LocalTime.parse(selectedTimeString, timeFormatter);
            }
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    ToDoItemEditActivity.this,
                    (view, hourOfDay, minute) -> {
                        deadlineTimeEditText.setText(LocalTime.of(hourOfDay, minute).format(timeFormatter));
                    },
                    selectedTime.getHour(), selectedTime.getMinute(), true
            );
            timePickerDialog.show();
        });

        Intent intent = new Intent(ToDoItemEditActivity.this, MainActivity.class);

        final int finalItemID = itemID;
        saveButton.setOnClickListener(v -> {
            intent.putExtra("itemID", finalItemID);
            intent.putExtra("itemTitle", titleEditText.getText().toString().trim());
            intent.putExtra("itemDescription", descriptionEditText.getText().toString().trim());
            intent.putExtra("itemStatus", statusCheckBox.isChecked());
            String selectedDateString = deadlineDateEditText.getText().toString().trim();
            String selectedTimeString = deadlineTimeEditText.getText().toString().trim();
            intent.putExtra("itemDeadline", selectedTimeString + " " + selectedDateString);
            setResult(RESULT_OK, intent);
            finish();
        });

        cancelButton.setOnClickListener(v -> {
            setResult(RESULT_CANCELED, intent);
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}