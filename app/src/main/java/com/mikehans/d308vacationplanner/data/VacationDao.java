package com.mikehans.d308vacationplanner.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Update;
import com.mikehans.d308vacationplanner.models.Vacation;
import java.util.List;

@Dao
public interface VacationDao {
    @Insert
    void insert(Vacation vacation);

    @Update
    void update(Vacation vacation);

    @Delete
    void delete(Vacation vacation);

    @Query("SELECT * FROM vacation_table")
    List<Vacation> getAllVacations();

    @Query("SELECT * FROM vacation_table WHERE id = :id LIMIT 1")
    Vacation getVacationById(int id);
}

