package com.mikehans.d308vacationplanner;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.mikehans.d308vacationplanner.data.VacationDatabase;
import com.mikehans.d308vacationplanner.models.Excursion;
import com.mikehans.d308vacationplanner.models.Vacation;

import java.util.ArrayList;
import java.util.List;

public class AllExcursionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_excursions);

        loadVacations();

        Button backButton = findViewById(R.id.buttonBack);
        backButton.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadVacations();
    }

    private void loadVacations() {
        ListView listView = findViewById(R.id.listViewAllVacations);
        VacationDatabase db = VacationDatabase.getInstance(this);

        List<Vacation> vacationList = db.vacationDao().getAllVacations();
        List<VacationGroup> groups = new ArrayList<>();

        for (Vacation vacation : vacationList) {
            List<Excursion> excursions = db.excursionDao().getExcursionsForVacation(vacation.getId());
            groups.add(new VacationGroup(vacation, excursions));
        }

        VacationGroupAdapter adapter = new VacationGroupAdapter(this, groups);
        listView.setAdapter(adapter);
    }

}

