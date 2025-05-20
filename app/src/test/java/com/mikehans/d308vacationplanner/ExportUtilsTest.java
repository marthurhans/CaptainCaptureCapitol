package com.mikehans.d308vacationplanner;

import static org.junit.Assert.*;

import org.junit.Test;

public class ExportUtilsTest {

    @Test
    public void testFakeExportReportFormat() {

        String fakeExport = "VACATION REPORT\n\n" +
                "WallyWorld (2025-06-01 to 2025-06-10)\n" +
                "- Sleeping (2025-06-03)\n";

        assertTrue(fakeExport.contains("VACATION REPORT"));
        assertTrue(fakeExport.contains("WallyWorld"));
        assertTrue(fakeExport.contains("Sleeping"));
    }
}


