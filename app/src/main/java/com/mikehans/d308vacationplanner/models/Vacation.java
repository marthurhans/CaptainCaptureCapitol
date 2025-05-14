package com.mikehans.d308vacationplanner.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "vacation_table")
public class Vacation implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String hotel;
    private String startDate;
    private String endDate;

    public Vacation (String title, String hotel, String startDate, String endDate) {
        this.title = title;
        this.hotel = hotel;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getHotel() { return hotel; }
    public void setHotel(String hotel) { this.hotel = hotel; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    // Used for debugging and UI display of vacation summary
    @Override
    public String toString() {
        return "Vacation{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", hotel='" + hotel + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}

