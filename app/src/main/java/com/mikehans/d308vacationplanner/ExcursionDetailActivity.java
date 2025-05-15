package com.mikehans.d308vacationplanner;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mikehans.d308vacationplanner.data.VacationDatabase;
import com.mikehans.d308vacationplanner.models.Excursion;
import com.mikehans.d308vacationplanner.models.Vacation;
import com.mikehans.d308vacationplanner.utils.ValidationUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Locale;

public class ExcursionDetailActivity extends AppCompatActivity {

    private EditText excursionTitleEditText;
    private EditText excursionDateEditText;
    private Button updateButton, deleteButton, alertButton;
    private TextView vacationInfoTextView;
    private Excursion currentExcursion;
    private VacationDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_detail);

        excursionTitleEditText = findViewById(R.id.excursion_title_edittext);
        excursionDateEditText = findViewById(R.id.editTextExcursionDate);
        updateButton = findViewById(R.id.update_excursion_button);
        deleteButton = findViewById(R.id.delete_excursion_button);
        alertButton = findViewById(R.id.buttonSetExcursionAlert);
        vacationInfoTextView = findViewById(R.id.textViewVacationInfo);

        final Calendar calendar = Calendar.getInstance();

        excursionDateEditText.setOnClickListener(v -> {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    ExcursionDetailActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String formattedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                        excursionDateEditText.setText(formattedDate);
                    },
                    year, month, day
            );

            datePickerDialog.show();
        });

        db = VacationDatabase.getInstance(this);

        currentExcursion = (Excursion) getIntent().getSerializableExtra("excursion");

        if (currentExcursion != null) {
            excursionTitleEditText.setText(currentExcursion.getTitle());
            excursionDateEditText.setText(currentExcursion.getDate());
            int vacationId = currentExcursion.getVacationId();
            Vacation vacation = db.vacationDao().getVacationById(vacationId);

            if (vacation != null) {
                String title = vacation.getTitle();
                String start = vacation.getStartDate();
                String end = vacation.getEndDate();

                String infoText = "Vacation: " + title + "\nDates: " + start + " to " + end;
                vacationInfoTextView.setText(infoText);
                vacationInfoTextView.setTextSize(18);
            }
        }

        updateButton.setOnClickListener(v -> updateExcursion());
        deleteButton.setOnClickListener(v -> deleteExcursion());
        alertButton.setOnClickListener(v -> {
            if (currentExcursion == null) return;

            String title = currentExcursion.getTitle();
            String dateStr = currentExcursion.getDate();

            try {
                LocalDate excursionDate = LocalDate.parse(dateStr);
                scheduleExcursionAlert(excursionDate, title);
                Toast.makeText(this, "Excursion alert scheduled!", Toast.LENGTH_SHORT).show();
            } catch (DateTimeParseException e) {
                Toast.makeText(this, "Invalid date format for alert.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateExcursion() {
        String newTitle = excursionTitleEditText.getText().toString().trim();
        String newDate = excursionDateEditText.getText().toString().trim();

        if (newTitle.isEmpty() || newDate.isEmpty()) {
            Toast.makeText(this, "All fields required", Toast.LENGTH_SHORT).show();
            return;
        }

        // 🔍 Pull in associated vacation and validate
        int vacationId = currentExcursion.getVacationId();
        Vacation associatedVacation = db.vacationDao().getVacationById(vacationId);
        if (associatedVacation == null) {
            Toast.makeText(this, "Associated vacation not found.", Toast.LENGTH_SHORT).show();
            return;
        }

        String vacationStart = associatedVacation.getStartDate();
        String vacationEnd = associatedVacation.getEndDate();

        // Do not allow excursion update unless in vacation range
        if (ValidationUtils.isBefore(newDate, vacationStart) || ValidationUtils.isAfter(newDate, vacationEnd)) {
            Toast.makeText(this, "Excursion date must be within vacation range (" +
                    vacationStart + " to " + vacationEnd + ").", Toast.LENGTH_LONG).show();
            return;
        }

        currentExcursion.setTitle(newTitle);
        currentExcursion.setDate(newDate);

        db.excursionDao().update(currentExcursion);
        Toast.makeText(this, "Excursion updated", Toast.LENGTH_SHORT).show();
        finish();
    }


    private void deleteExcursion() {
        db.excursionDao().delete(currentExcursion);
        Toast.makeText(this, "Excursion deleted", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void scheduleExcursionAlert(LocalDate date, String title) {
        Intent intent = new Intent(this, ExcursionAlertReceiver.class);
        intent.putExtra("excursionTitle", title);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                1,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // TEST CODE: toggle true to test alerts
        boolean testMode = false;
        long triggerAtMillis = testMode
                ? System.currentTimeMillis() + 5000
                : date.atTime(8, 0).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
        }
    }
}

