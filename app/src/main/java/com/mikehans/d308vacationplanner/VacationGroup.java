package com.mikehans.d308vacationplanner;

import com.mikehans.d308vacationplanner.models.Excursion;
import com.mikehans.d308vacationplanner.models.Vacation;

import java.util.List;

public class VacationGroup {
    public Vacation vacation;
    public List<Excursion> excursions;

    public VacationGroup(Vacation vacation, List<Excursion> excursions) {
        this.vacation = vacation;
        this.excursions = excursions;
    }
}

