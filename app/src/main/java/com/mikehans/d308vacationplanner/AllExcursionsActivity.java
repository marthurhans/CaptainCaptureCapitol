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

//    private void loadExcursions() {
//        ListView listView = findViewById(R.id.listViewAllVacations);
//        VacationDatabase db = VacationDatabase.getInstance(this);
//
//        List<Vacation> vacations = db.vacationDao().getAllVacations();
//        List<Excursion> allExcursions = new ArrayList<>();
//        List<Vacation> associatedVacations = new ArrayList<>();
//        List<String> displayList = new ArrayList<>();
//
//        for (Vacation vacation : vacations) {
//            List<Excursion> excursions = db.excursionDao().getExcursionsForVacation(vacation.getId());
//
//            for (Excursion excursion : excursions) {
//                allExcursions.add(excursion);
//                associatedVacations.add(vacation);
//                displayList.add(vacation.getTitle() + " - " + excursion.getTitle()
//                        + " (" + excursion.getDate() + ") \n++ Edit/Delete Excursion ++");
//            }
//
//            displayList.add(vacation.getTitle() + " \n++ Add Excursion ++");
//            allExcursions.add(null);
//            associatedVacations.add(vacation);
//        }
//
//        if (displayList.isEmpty()) {
//            displayList.add("No vacations or excursions found.");
//        }
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
//        listView.setAdapter(adapter);
//
//        listView.setOnItemClickListener((parent, view, position, id) -> {
//            Excursion selectedExcursion = allExcursions.get(position);
//            Vacation selectedVacation = associatedVacations.get(position);
//            String displayText = displayList.get(position);
//
//            Intent intent;
//
//            if (selectedExcursion == null && displayText.contains("Add Excursion")) {
//                intent = new Intent(AllExcursionsActivity.this, ExcursionActivity.class);
//                intent.putExtra("vacationId", selectedVacation.getId());
//                startActivity(intent);
//            } else if (selectedExcursion != null) {
//                intent = new Intent(AllExcursionsActivity.this, ExcursionDetailActivity.class);
//                intent.putExtra("excursion", selectedExcursion);
//                startActivity(intent);
//            }
//        });
//    }

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

