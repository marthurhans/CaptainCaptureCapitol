package com.mikehans.d308vacationplanner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mikehans.d308vacationplanner.data.VacationDatabase;
import com.mikehans.d308vacationplanner.models.Excursion;
import com.mikehans.d308vacationplanner.models.Vacation;
import com.mikehans.d308vacationplanner.utils.ExportUtils;

import java.util.List;

// TODO: Add export button to generate CSV and TXT reports for all vacations and excursions
public class MainActivity extends AppCompatActivity {
    private VacationDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logAllExcursions();  // TEST CODE: activate method
        Log.d("EXPORT_TEST", ExportUtils.generateCsvReport(this)); //TEST CODE: LOG CSV


        db = VacationDatabase.getInstance(this);

        Button viewLastVacationButton = findViewById(R.id.buttonViewDetails);
        Button deleteVacationButton = findViewById(R.id.buttonDeleteVacation);
        Button addVacationButton = findViewById(R.id.buttonAddVacation);
        Button viewAllButton = findViewById(R.id.buttonViewAllVacations);
        Button editVacationButton = findViewById(R.id.buttonEditVacation);

        deleteVacationButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DeleteVacationActivity.class);
            startActivity(intent);
        });

        viewLastVacationButton.setOnClickListener(v -> {
            List<Vacation> vacations = db.vacationDao().getAllVacations();
            if (!vacations.isEmpty()) {
                Vacation vacationToSend = vacations.get(vacations.size() - 1);
                Intent intent = new Intent(MainActivity.this, VacationDetailActivity.class);
                intent.putExtra("vacation", vacationToSend);
                startActivity(intent);
            } else {
                Toast.makeText(this, "No vacation data to view.", Toast.LENGTH_SHORT).show();
            }
        });

        addVacationButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddVacationActivity.class);
            startActivity(intent);
        });


        viewAllButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AllVacationsActivity.class);
            startActivity(intent);
        });

        editVacationButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SelectVacationActivity.class);
            startActivity(intent);
        });

    }

    // TEST CODE: Logs excursions from vacation ID 999 to Logcat for manual debugging
    private void logAllExcursions() {
        VacationDatabase db = VacationDatabase.getInstance(this);

        List<Excursion> excursions = db.excursionDao().getExcursionsForVacation(999);

        for (Excursion e : excursions) {
            Log.d("Vacation_DB", "EXCURSION FOUND: " + e);
        }
    }
}


