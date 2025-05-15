package com.mikehans.d308vacationplanner;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mikehans.d308vacationplanner.data.VacationDatabase;
import com.mikehans.d308vacationplanner.models.Excursion;
import com.mikehans.d308vacationplanner.models.Vacation;
import com.mikehans.d308vacationplanner.utils.ValidationUtils;

import java.util.Calendar;
import java.util.Locale;

public class ExcursionActivity extends AppCompatActivity {

    // TODO: Replace manual date input with DatePickerDialog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion);

        EditText titleInput = findViewById(R.id.editTextExcursionTitle);
        EditText dateInput = findViewById(R.id.editTextExcursionDate);
        Button addButton = findViewById(R.id.buttonAddExcursion);
        Button backButton = findViewById(R.id.buttonBack);
        TextView vacationInfo = findViewById(R.id.textViewVacationInfo);

        final Calendar calendar = Calendar.getInstance();

        dateInput.setOnClickListener(v -> {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    ExcursionActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String formattedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                        dateInput.setText(formattedDate);
                    },
                    year, month, day
            );

            datePickerDialog.show();
        });


        int vacationId = getIntent().getIntExtra("vacationId", -1);
        if (vacationId == -1) {
            Toast.makeText(this, "Invalid vacation ID. Cannot load screen.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        VacationDatabase db = VacationDatabase.getInstance(this);
        Vacation associatedVacation = db.vacationDao().getVacationById(vacationId);

        String vacationTitle = associatedVacation.getTitle();
        String vacationStart = associatedVacation.getStartDate();
        String vacationEnd = associatedVacation.getEndDate();

        String infoText = "Vacation: " + vacationTitle + "\nDates: " + vacationStart + " to " + vacationEnd;
        vacationInfo.setText(infoText);
        vacationInfo.setTextSize(18);

        addButton.setOnClickListener(v -> {
            String title = titleInput.getText().toString().trim();
            String date = dateInput.getText().toString().trim();

            if (title.isEmpty()) {
                Toast.makeText(this, "Please enter an excursion title.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!ValidationUtils.isValidDate(date)) {
                Toast.makeText(this, "Please enter the date in YYYY-MM-DD format.",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (ValidationUtils.isBefore(date, vacationStart) || ValidationUtils.isAfter(date, vacationEnd)) {
                Toast.makeText(this, "Excursion date must be within vacation range (" +
                        vacationStart + " to " + vacationEnd + ").", Toast.LENGTH_LONG).show();
                return;
            }

            Excursion excursion = new Excursion(title, date, vacationId);
            db.excursionDao().insert(excursion);
            Log.d("Vacation_DB", "Excursion saved: " + excursion);
            Toast.makeText(this, "Excursion added!", Toast.LENGTH_SHORT).show();
            finish();
        });

        backButton.setOnClickListener(v -> finish());
    }
}
