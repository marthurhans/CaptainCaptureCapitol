package com.mikehans.d308vacationplanner;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mikehans.d308vacationplanner.data.VacationDatabase;
import com.mikehans.d308vacationplanner.models.Excursion;
import com.mikehans.d308vacationplanner.models.Vacation;
import com.mikehans.d308vacationplanner.utils.ValidationUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class VacationDetailActivity extends AppCompatActivity {

    private Vacation vacation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_detail);

        vacation = (Vacation) getIntent().getSerializableExtra("vacation");

        if (vacation != null) {
            EditText titleInput = findViewById(R.id.editTextTitle);
            EditText hotelInput = findViewById(R.id.editTextHotel);
            EditText startInput = findViewById(R.id.editTextVacationStartDate);
            EditText endInput = findViewById(R.id.editTextVacationEndDate);
            Button saveButton = findViewById(R.id.buttonSaveChanges);
            Button setAlertsButton = findViewById(R.id.buttonSetAlerts);
            Button shareButton = findViewById(R.id.buttonShare);
            Button addExcursionButton = findViewById(R.id.buttonAddExcursion);
            Button backButton = findViewById(R.id.buttonBack);

            final Calendar calendar = Calendar.getInstance();

            startInput.setOnClickListener(v -> {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        VacationDetailActivity.this,
                        (view, selectedYear, selectedMonth, selectedDay) -> {
                            String formattedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                            startInput.setText(formattedDate);
                        },
                        year, month, day
                );

                datePickerDialog.show();
            });

            endInput.setOnClickListener(v -> {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        VacationDetailActivity.this,
                        (view, selectedYear, selectedMonth, selectedDay) -> {
                            String formattedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                            endInput.setText(formattedDate);
                        },
                        year, month, day
                );

                datePickerDialog.show();
            });


            titleInput.setText(vacation.getTitle());
            hotelInput.setText(vacation.getHotel());
            startInput.setText(vacation.getStartDate());
            endInput.setText(vacation.getEndDate());

            saveButton.setOnClickListener(v -> {
                String updatedTitle = titleInput.getText().toString().trim();
                String updatedHotel = hotelInput.getText().toString().trim();
                String updatedStart = startInput.getText().toString().trim();
                String updatedEnd = endInput.getText().toString().trim();

                if (!ValidationUtils.isValidDate(updatedStart) || !ValidationUtils.isValidDate(updatedEnd)) {
                    Toast.makeText(this, "Please enter dates in YYYY-MM-DD format.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!ValidationUtils.isDateRangeValid(updatedStart, updatedEnd)) {
                    Toast.makeText(this, "End date must be after start date.", Toast.LENGTH_SHORT).show();
                    return;
                }

                vacation.setTitle(updatedTitle);
                vacation.setHotel(updatedHotel);
                vacation.setStartDate(updatedStart);
                vacation.setEndDate(updatedEnd);

                VacationDatabase db = VacationDatabase.getInstance(this);
                db.vacationDao().update(vacation);

                Toast.makeText(this, "Vacation updated!", Toast.LENGTH_SHORT).show();
                finish();
            });

            setAlertsButton.setOnClickListener(v -> {
                String title = titleInput.getText().toString();
                String startDateStr = startInput.getText().toString();
                String endDateStr = endInput.getText().toString();

                try {
                    LocalDate startDate = LocalDate.parse(startDateStr);
                    LocalDate endDate = LocalDate.parse(endDateStr);

                    scheduleAlert(startDate, "start", title);
                    scheduleAlert(endDate, "end", title);

                    Toast.makeText(this, "Vacation alerts scheduled!", Toast.LENGTH_SHORT).show();
                } catch (DateTimeParseException e) {
                    Toast.makeText(this, "Invalid date format. Please use YYYY-MM-DD.", Toast.LENGTH_SHORT).show();
                }
            });

            shareButton.setOnClickListener(v -> {
                String title = titleInput.getText().toString().trim();
                String hotel = hotelInput.getText().toString().trim();
                String startDate = startInput.getText().toString().trim();
                String endDate = endInput.getText().toString().trim();

                String message = "Vacation: " + title +
                        "\nHotel: " + hotel +
                        "\nStart Date: " + startDate +
                        "\nEnd Date: " + endDate;

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, message);

                startActivity(Intent.createChooser(shareIntent, "Share vacation using"));
            });

            addExcursionButton.setOnClickListener(v -> {
                Intent intent = new Intent(VacationDetailActivity.this, ExcursionActivity.class);
                intent.putExtra("vacationId", vacation.getId());
                startActivity(intent);
            });

            backButton.setOnClickListener(v -> finish());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (vacation != null) {
            VacationDatabase db = VacationDatabase.getInstance(this);
            List<Excursion> excursions = db.excursionDao().getExcursionsForVacation(vacation.getId());

            TextView excursionsTitleTextView = findViewById(R.id.textViewExcursionsTitle);

            excursionsTitleTextView.setText("Excursions:\n(Tap any excursion to edit or delete)");

            ListView listView = findViewById(R.id.listViewExcursions);

            if (excursions.isEmpty()) {
                Toast.makeText(this, "No excursions yet.", Toast.LENGTH_SHORT).show();
                listView.setAdapter(null);
            } else {
                ArrayAdapter<Excursion> adapter = new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        excursions
                );
                listView.setAdapter(adapter);

                listView.setOnItemClickListener((parent, view, position, id) -> {
                    Excursion selectedExcursion = excursions.get(position);
                    Intent intent = new Intent(VacationDetailActivity.this, ExcursionDetailActivity.class);
                    intent.putExtra("excursion", selectedExcursion);
                    startActivity(intent);
                });
            }
        }
    }

    private void scheduleAlert(LocalDate date, String alertType, String vacationTitle) {
        Context context = getApplicationContext();
        Intent intent = new Intent(context, VacationAlertReceiver.class);
        intent.putExtra("alertType", alertType);
        intent.putExtra("vacationTitle", vacationTitle);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                getAlertRequestCode(alertType),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // TEST CODE: toggle true to test alerts
        boolean testMode = false;

        long triggerAtMillis;
        if (testMode) {
            triggerAtMillis = System.currentTimeMillis() + (alertType.equals("start") ? 5000 : 10000);
        } else {
            ZonedDateTime zonedDateTime = date.atTime(8, 0).atZone(ZoneId.systemDefault());
            triggerAtMillis = zonedDateTime.toInstant().toEpochMilli();
        }

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
        }
    }

    private int getAlertRequestCode(String alertType) {
        return "start".equals(alertType) ? 1 : 2;
    }
}
