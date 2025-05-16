package com.mikehans.d308vacationplanner.utils;


import android.content.Context;

import com.mikehans.d308vacationplanner.data.VacationDatabase;
import com.mikehans.d308vacationplanner.models.Excursion;
import com.mikehans.d308vacationplanner.models.Vacation;

import java.util.List;

public class ExportUtils {

    public static String generateCsvReport(Context context) {
        VacationDatabase db = VacationDatabase.getInstance(context);
        List<Vacation> vacations = db.vacationDao().getAllVacations();

        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("Vacation Title,Hotel,Start Date,End Date,Excursion Title,Excursion Date\n");

        for (Vacation vacation : vacations) {
            List<Excursion> excursions = db.excursionDao().getExcursionsForVacation(vacation.getId());

            if (excursions.isEmpty()) {
                appendCsvRow(csvBuilder, vacation, null);
            } else {
                for (Excursion excursion : excursions) {
                    appendCsvRow(csvBuilder, vacation, excursion);
                }
            }
        }
        return csvBuilder.toString();
    }

    private static void appendCsvRow(StringBuilder builder, Vacation vacation, Excursion excursion) {
        builder.append(String.format("%s,%s,%s,%s,%s,%s\n",
                formatCsv(vacation.getTitle()),
                formatCsv(vacation.getHotel()),
                vacation.getStartDate(),
                vacation.getEndDate(),
                excursion != null ? formatCsv(excursion.getTitle()) : "",
                excursion != null ? excursion.getDate() : ""));
    }

    private static String formatCsv(String value) {
        if (value == null) return "";
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }


}
