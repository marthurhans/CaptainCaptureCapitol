package com.mikehans.d308vacationplanner.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mikehans.d308vacationplanner.models.Excursion;

import java.util.List;

@Dao
public interface ExcursionDao {

    @Insert
    void insert(Excursion excursion);

    @Update
    void update(Excursion excursion);

    @Delete
    void delete(Excursion excursion);

    @Query("SELECT * FROM excursions WHERE vacationId = :vacationId")
    List<Excursion> getExcursionsForVacation(int vacationId);
}

