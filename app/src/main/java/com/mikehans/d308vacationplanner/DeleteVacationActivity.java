package com.mikehans.d308vacationplanner;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

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
            CardView card = new CardView(this);
            card.setRadius(12f);
            card.setCardElevation(6f);
            card.setUseCompatPadding(true);

            LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            cardParams.setMargins(0, 16, 0, 16);
            card.setLayoutParams(cardParams);

            LinearLayout innerLayout = new LinearLayout(this);
            innerLayout.setOrientation(LinearLayout.VERTICAL);
            innerLayout.setPadding(24, 24, 24, 24);

            TextView titleView = new TextView(this);
            titleView.setText(vacation.getTitle());
            titleView.setTextSize(18);
            titleView.setTypeface(null, Typeface.BOLD);

            TextView hotelView = new TextView(this);
            hotelView.setText(vacation.getHotel());
            hotelView.setTextSize(16);

            TextView datesView = new TextView(this);
            datesView.setText(vacation.getStartDate() + " to " + vacation.getEndDate());
            datesView.setTextSize(14);
            datesView.setTextColor(0xFF666666);

            innerLayout.addView(titleView);
            innerLayout.addView(hotelView);
            innerLayout.addView(datesView);

            List<Excursion> excursions = db.excursionDao().getExcursionsForVacation(vacation.getId());

            if (excursions.isEmpty()) {
                com.google.android.material.button.MaterialButton deleteButton = new com.google.android.material.button.MaterialButton(this);
                deleteButton.setText("Delete This Vacation");
                deleteButton.setTextColor(getColor(android.R.color.white));
                deleteButton.setBackgroundColor(getColor(android.R.color.holo_red_dark));
                deleteButton.setOnClickListener(v -> {
                    db.vacationDao().delete(vacation);
                    Toast.makeText(this, "Vacation deleted!", Toast.LENGTH_SHORT).show();
                    displayVacations();
                });
                innerLayout.addView(deleteButton);
            } else {
                TextView cannotDelete = new TextView(this);
                cannotDelete.setText("Cannot delete: excursions are attached. Tap to edit.");
                cannotDelete.setTextColor(getColor(android.R.color.holo_red_dark));
                cannotDelete.setTextSize(14);
                cannotDelete.setPadding(0, 12, 0, 0);
                cannotDelete.setClickable(true);
                cannotDelete.setFocusable(true);

                TypedValue rippleValue = new TypedValue();
                getTheme().resolveAttribute(android.R.attr.selectableItemBackground, rippleValue, true);
                cannotDelete.setForeground(ContextCompat.getDrawable(this, rippleValue.resourceId));

                cannotDelete.setOnClickListener(v2 -> {
                    Intent intent = new Intent(DeleteVacationActivity.this, VacationDetailActivity.class);
                    intent.putExtra("vacation", vacation);
                    startActivity(intent);
                });
                innerLayout.addView(cannotDelete);
            }

            card.addView(innerLayout);
            layout.addView(card);
        }

        com.google.android.material.button.MaterialButton backButton = new com.google.android.material.button.MaterialButton(this);
        backButton.setText("Back to Main Menu");

        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        buttonParams.setMargins(0, 24, 0, 24);
        backButton.setLayoutParams(buttonParams);

        backButton.setClickable(true);
        backButton.setFocusable(true);
        backButton.setOnClickListener(v -> finish());

        layout.addView(backButton);
    }
}


