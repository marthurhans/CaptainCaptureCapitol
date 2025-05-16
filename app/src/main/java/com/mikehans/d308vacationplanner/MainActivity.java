package com.mikehans.d308vacationplanner;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.mikehans.d308vacationplanner.data.VacationDatabase;
import com.mikehans.d308vacationplanner.models.Vacation;
import com.mikehans.d308vacationplanner.utils.ExportUtils;

import java.io.File;
import java.util.List;

// TODO: Add export button to generate CSV and TXT reports for all vacations and excursions
public class MainActivity extends AppCompatActivity {
    private VacationDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TEST CODE: activate method
        runDebugLogs(true);

        db = VacationDatabase.getInstance(this);

        Button viewLastVacationButton = findViewById(R.id.buttonViewDetails);
        Button deleteVacationButton = findViewById(R.id.buttonDeleteVacation);
        Button addVacationButton = findViewById(R.id.buttonAddVacation);
        Button viewAllButton = findViewById(R.id.buttonViewAllVacations);
        Button editVacationButton = findViewById(R.id.buttonEditVacation);
        Button exportButton = findViewById(R.id.buttonExportReport);

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

        exportButton.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Choose Report Format")
                    .setItems(new String[]{"CSV", "Plain Text"}, (dialog, which) -> {
                        String content;
                        String filename;

                        if (which == 0) {
                            content = ExportUtils.generateCsvReport(this);
                            filename = "vacation_report.csv";
                        } else {
                            content = ExportUtils.generatePlainTextReport(this);
                            filename = "vacation_report.txt";
                        }

                        File file = ExportUtils.saveReportToFile(this, content, filename);
                        if (file != null) {
                            Log.d("EXPORT_UI", "Report saved: " + file.getAbsolutePath());
                            Toast.makeText(this, "Report saved successfully!", Toast.LENGTH_SHORT).show();

                            Uri uri = FileProvider.getUriForFile(
                                    this,
                                    getPackageName() + ".fileprovider",
                                    file
                            );

                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("text/plain");
                            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                            startActivity(Intent.createChooser(shareIntent, "Share report via"));

                        } else {
                            Toast.makeText(this, "Failed to save report.", Toast.LENGTH_SHORT).show();
                        }

                    })
                    .show();
        });
    }

    private void runDebugLogs(boolean enabled) {
        if(!enabled) return;

        Log.d("EXPORT_TEST", ExportUtils.generateCsvReport(this)); //TEST CODE: LOG CSV
        Log.d("EXPORT_TEST_TXT", ExportUtils.generatePlainTextReport(this)); //TEST CODE: LOG TEXT
        File file = ExportUtils.saveReportToFile
                (this, ExportUtils.generatePlainTextReport(this), "test_report.txt");
        Log.d("EXPORT_FILE", "Saved file: " + file.getAbsolutePath()); //TEST CODE: SAVE REPORT + LOG PATH
    }
}


