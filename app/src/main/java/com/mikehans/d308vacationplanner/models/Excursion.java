package com.mikehans.d308vacationplanner.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// This sets up Room to delete all excursions if their parent vacation is deleted
@Entity(
        tableName = "excursions",
        foreignKeys = @ForeignKey(
                entity = Vacation.class,
                parentColumns = "id",
                childColumns = "vacationId",
                onDelete = ForeignKey.CASCADE
        )
)
public class Excursion implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String date;
    // Links this excursion to its parent vacation
    private int vacationId;

    public Excursion() {
    }

    public Excursion(String title, String date, int vacationId) {
        this.title = title;
        this.date = date;
        this.vacationId = vacationId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getVacationId() {
        return vacationId;
    }

    public void setVacationId(int vacationId) {
        this.vacationId = vacationId;
    }

    @Override
    public String toString() {
        try {
            if (date == null || date.isEmpty()) {
                return title + " (No Date)";
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
            LocalDate excursionDate = LocalDate.parse(date);
            return title + " (" + excursionDate.format(formatter) + ")";
        } catch (Exception e) {
            return title + " (" + date + ")";
        }
    }
}



