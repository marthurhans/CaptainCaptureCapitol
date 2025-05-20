package com.mikehans.d308vacationplanner;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

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
            CardView vacationCard = new CardView(this);
            vacationCard.setRadius(12f);
            vacationCard.setCardElevation(6f);
            vacationCard.setUseCompatPadding(true);

            LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            cardParams.setMargins(0, 16, 0, 16);
            vacationCard.setLayoutParams(cardParams);

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

            vacationCard.addView(innerLayout);
            vacationCard.setClickable(true);
            vacationCard.setFocusable(true);
            TypedValue typedValue = new TypedValue();
            getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
            Drawable rippleDrawable = ContextCompat.getDrawable(this, typedValue.resourceId);
            vacationCard.setForeground(rippleDrawable);

            vacationCard.setOnClickListener(v -> {
                Intent intent = new Intent(SelectVacationActivity.this, VacationDetailActivity.class);
                intent.putExtra("vacation", vacation);
                startActivity(intent);
            });

            layout.addView(vacationCard);
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

