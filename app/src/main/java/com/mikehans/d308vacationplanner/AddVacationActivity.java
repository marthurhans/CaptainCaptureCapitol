package com.mikehans.d308vacationplanner;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mikehans.d308vacationplanner.data.VacationDatabase;
import com.mikehans.d308vacationplanner.models.Vacation;
import com.mikehans.d308vacationplanner.utils.ValidationUtils;

// TODO: Replace manual date input with DatePickerDialog
public class AddVacationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vacation);

        EditText editTitle = findViewById(R.id.editTextVacationTitle);
        EditText editHotel = findViewById(R.id.editTextHotel);
        EditText editStartDate = findViewById(R.id.editTextStartDate);
        EditText editEndDate = findViewById(R.id.editTextEndDate);
        Button saveButton = findViewById(R.id.buttonSaveVacation);
        Button backButton = findViewById(R.id.buttonBack);

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

