package com.mikehans.d308vacationplanner;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.mikehans.d308vacationplanner.data.VacationDatabase;
import com.mikehans.d308vacationplanner.models.Vacation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class VacationDaoTest {

    private VacationDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, VacationDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void insertAndGetVacation() {
        Vacation vacation = new Vacation("Test Trip", "2025-07-01",
                "2025-07-10", "Test Hotel");
        db.vacationDao().insert(vacation);

        List<Vacation> allVacations = db.vacationDao().getAllVacations();

        assertEquals(1, allVacations.size());
        assertEquals("Test Trip", allVacations.get(0).getTitle());
    }
}
