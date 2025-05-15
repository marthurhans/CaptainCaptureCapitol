package com.mikehans.d308vacationplanner;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.mikehans.d308vacationplanner.data.VacationDatabase;
import com.mikehans.d308vacationplanner.models.Vacation;
import com.mikehans.d308vacationplanner.utils.ValidationUtils;

import java.util.Calendar;
import java.util.Locale;

// TODO: Replace manual date input with DatePickerDialog
public class AddVacationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vacation);

        EditText editTitle = findViewById(R.id.editTextVacationTitle);
        EditText editHotel = findViewById(R.id.editTextHotel);
        EditText editStartDate = findViewById(R.id.editTextVacationStartDate);
        EditText editEndDate = findViewById(R.id.editTextVacationEndDate);
        Button saveButton = findViewById(R.id.buttonSaveVacation);
        Button backButton = findViewById(R.id.buttonBack);

        final Calendar calendar = Calendar.getInstance();

        editStartDate.setOnClickListener(v -> {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    AddVacationActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String formattedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                        editStartDate.setText(formattedDate);
                    },
                    year, month, day
            );

            datePickerDialog.show();
        });

        editEndDate.setOnClickListener(v -> {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    AddVacationActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String formattedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                        editEndDate.setText(formattedDate);
                    },
                    year, month, day
            );

            datePickerDialog.show();
        });


        VacationDatabase db = VacationDatabase.getInstance(this);

        saveButton.setOnClickListener(v -> {
            String title = editTitle.getText().toString().trim();
            String hotel = editHotel.getText().toString().trim();
            String startDate = editStartDate.getText().toString().trim();
            String endDate = editEndDate.getText().toString().trim();

            if (title.isEmpty() || hotel.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
            } else if (!ValidationUtils.isValidDate(startDate) || !ValidationUtils.isValidDate(endDate)) {
                Toast.makeText(this, "Please enter dates in YYYY-MM-DD format.", Toast.LENGTH_SHORT).show();
            } else if (!ValidationUtils.isDateRangeValid(startDate, endDate)) {
                Toast.makeText(this, "End date must be after start date.", Toast.LENGTH_SHORT).show();
            } else {
                Vacation vacation = new Vacation(title, hotel, startDate, endDate);
                db.vacationDao().insert(vacation);
                Toast.makeText(this, "Vacation Saved!", Toast.LENGTH_SHORT).show();

                finish();
            }
        });

        backButton.setOnClickListener(v -> finish());

    }
}

