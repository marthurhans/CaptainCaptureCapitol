package com.mikehans.d308vacationplanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.mikehans.d308vacationplanner.data.VacationDatabase;
import com.mikehans.d308vacationplanner.models.Vacation;

import java.util.List;

public class SelectVacationActivity extends AppCompatActivity {

    private VacationDatabase db;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_vacation);

        db = VacationDatabase.getInstance(this);
        layout = findViewById(R.id.vacationListLayout);
    }

    @Override
    protected void onResume() {
        super.onResume();

        layout.removeAllViews();
        List<Vacation> vacations = db.vacationDao().getAllVacations();

        for (Vacation vacation : vacations) {
            Button button = new Button(this);
            button.setText(
                    vacation.getTitle() + "\n" +
                            vacation.getHotel() + "\n" +
                            vacation.getStartDate() + " to " + vacation.getEndDate()
            );
            button.setOnClickListener(v -> {
                Intent intent = new Intent(SelectVacationActivity.this, VacationDetailActivity.class);
                intent.putExtra("vacation", vacation);
                startActivity(intent);
            });
            layout.addView(button);
        }

        Button backButton = new Button(this);
        backButton.setText("Back to Main Menu");
        backButton.setOnClickListener(v -> finish());
        layout.addView(backButton);
    }
}

