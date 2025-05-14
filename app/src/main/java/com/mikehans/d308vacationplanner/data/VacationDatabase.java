package com.mikehans.d308vacationplanner.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.mikehans.d308vacationplanner.models.Excursion;
import com.mikehans.d308vacationplanner.models.Vacation;

@Database(entities = {Vacation.class, Excursion.class}, version = 4, exportSchema = false)
public abstract class VacationDatabase extends RoomDatabase {

    public abstract VacationDao vacationDao();
    public abstract ExcursionDao excursionDao();
    private static VacationDatabase instance;

    public static synchronized VacationDatabase getInstance(android.content.Context context) {
        if (instance == null) {
            instance = androidx.room.Room.databaseBuilder(context.getApplicationContext(),
                            VacationDatabase.class, "vacation_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}

