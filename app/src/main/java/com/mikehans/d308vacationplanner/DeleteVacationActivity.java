package com.mikehans.d308vacationplanner;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mikehans.d308vacationplanner.data.VacationDatabase;
import com.mikehans.d308vacationplanner.models.Excursion;
import com.mikehans.d308vacationplanner.models.Vacation;

import java.util.List;

public class DeleteVacationActivity extends AppCompatActivity {

    private VacationDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_vacation);
        db = VacationDatabase.getInstance(this);
        displayVacations();
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayVacations();
    }

    private void displayVacations() {
        LinearLayout layout = findViewById(R.id.deleteVacationLayout);
        layout.removeAllViews();

        TextView header = new TextView(this);
        header.setText("Select a Vacation to Delete");
        header.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        header.setPadding(0, 0, 0, 20);
        layout.addView(header);

        List<Vacation> vacations = db.vacationDao().getAllVacations();

        for (Vacation vacation : vacations) {
            TextView vacationInfo = new TextView(this);
            vacationInfo.setText(
                    vacation.getTitle() + "\n" +
                            vacation.getHotel() + "\n" +
                            vacation.getStartDate() + " to " + vacation.getEndDate()
            );
            vacationInfo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            vacationInfo.setPadding(0, 20, 0, 8);
            layout.addView(vacationInfo);

            List<Excursion> excursions = db.excursionDao().getExcursionsForVacation(vacation.getId());

            if (excursions.isEmpty()) {
                Button deleteButton = new Button(this);
                deleteButton.setText("Delete This Vacation");
                deleteButton.setBackgroundColor(getColor(android.R.color.black));
                deleteButton.setTextColor(getColor(android.R.color.white));
                deleteButton.setOnClickListener(v -> {
                    db.vacationDao().delete(vacation);
                    Toast.makeText(this, "Vacation deleted!", Toast.LENGTH_SHORT).show();
                    displayVacations();
                });
                layout.addView(deleteButton);
            } else {
                TextView cannotDelete = new TextView(this);
                cannotDelete.setText("Cannot delete: excursions are attached. Tap to edit.");
                cannotDelete.setTextColor(getColor(android.R.color.holo_red_dark));
                cannotDelete.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                cannotDelete.setPadding(0, 4, 0, 20);
                cannotDelete.setClickable(true);
                cannotDelete.setOnClickListener(v2 -> {
                    Intent intent = new Intent(DeleteVacationActivity.this, VacationDetailActivity.class);
                    intent.putExtra("vacation", vacation);
                    startActivity(intent);
                });
                layout.addView(cannotDelete);
            }
        }

        Button backButton = new Button(this);
        backButton.setText("Back to Main Menu");
        backButton.setOnClickListener(v -> finish());
        layout.addView(backButton);
    }
}


