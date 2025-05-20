package com.mikehans.d308vacationplanner;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.mikehans.d308vacationplanner.models.Excursion;
import com.mikehans.d308vacationplanner.models.Vacation;

import java.util.List;

public class VacationGroupAdapter extends BaseAdapter {
    private final Context context;
    private final List<VacationGroup> vacationGroups;

    public VacationGroupAdapter(Context context, List<VacationGroup> vacationGroups) {
        this.context = context;
        this.vacationGroups = vacationGroups;
    }

    @Override
    public int getCount() {
        return vacationGroups.size();
    }

    @Override
    public Object getItem(int position) {
        return vacationGroups.get(position);
    }

    @Override
    public long getItemId(int position) {
        return vacationGroups.get(position).vacation.getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.vacation_group_item, parent, false);
        }

        VacationGroup group = vacationGroups.get(position);
        Vacation vacation = group.vacation;
        List<Excursion> excursions = group.excursions;

        TextView titleView = convertView.findViewById(R.id.textViewVacationTitle);
        TextView datesView = convertView.findViewById(R.id.textViewVacationDates);

        titleView.setText(vacation.getTitle());
        datesView.setText(vacation.getStartDate() + " – " + vacation.getEndDate());

        LinearLayout excursionContainer = convertView.findViewById(R.id.excursionContainer);
        excursionContainer.removeAllViews(); // prevent stacking on reuse

        for (Excursion excursion : excursions) {
            CardView excursionCard = new CardView(context);
            excursionCard.setRadius(12f);
            excursionCard.setCardElevation(4f);
            excursionCard.setUseCompatPadding(true);

            LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            cardParams.setMargins(0, 8, 0, 8);
            excursionCard.setLayoutParams(cardParams);

            TextView excursionText = new TextView(context);
            excursionText.setText(excursion.getTitle() + " (" + excursion.getDate() + ")");
            excursionText.setTextSize(14);
            excursionText.setPadding(24, 16, 24, 16);
            excursionText.setTextColor(0xFF000000);  // Black

            excursionCard.addView(excursionText);
            excursionCard.setClickable(true);
            excursionCard.setForeground(context.getResources().getDrawable(android.R.drawable.list_selector_background, null));

            excursionCard.setOnClickListener(v -> {
                Intent intent = new Intent(context, ExcursionDetailActivity.class);
                intent.putExtra("excursion", excursion);
                context.startActivity(intent);
            });

            excursionContainer.addView(excursionCard);
        }

        Button addButton = convertView.findViewById(R.id.buttonAddExcursion);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, ExcursionActivity.class);
            intent.putExtra("vacationId", vacation.getId());
            context.startActivity(intent);
        });

        return convertView;
    }

}


