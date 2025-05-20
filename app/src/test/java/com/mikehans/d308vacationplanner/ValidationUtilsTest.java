package com.mikehans.d308vacationplanner;

import static org.junit.Assert.assertTrue;

import com.mikehans.d308vacationplanner.utils.ValidationUtils;

import org.junit.Test;

public class ValidationUtilsTest {

    @Test
    public void testValidDateFormat() {
        assertTrue(ValidationUtils.isValidDate("2025-12-01"));
    }

    @Test
    public void testDateIsBeforeRange() {
        assertTrue(ValidationUtils.isBefore("2025-01-01", "2025-06-01"));
    }

}
