package com.mikehans.d308vacationplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mikehans.d308vacationplanner.models.Vacation;

import java.util.List;

public class VacationAdapter extends BaseAdapter {
    private final Context context;
    private final List<Vacation> vacationList;

    public VacationAdapter(Context context, List<Vacation> vacationList) {
        this.context = context;
        this.vacationList = vacationList;
    }

    @Override
    public int getCount() {
        return vacationList.size();
    }

    @Override
    public Object getItem(int position) {
        return vacationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return vacationList.get(position).getId(); // Adjust if needed
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.vacation_item, parent, false);
        }

        Vacation vacation = vacationList.get(position);

        TextView nameView = convertView.findViewById(R.id.textViewVacationName);
        TextView datesView = convertView.findViewById(R.id.textViewVacationDates);

        nameView.setText(vacation.getTitle());
        datesView.setText(vacation.getStartDate() + " – " + vacation.getEndDate());

        return convertView;
    }
}

