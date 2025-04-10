package com.mikehans.d308vacationplanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class VacationAlertReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String alertType = intent.getStringExtra("alertType");
        String vacationTitle = intent.getStringExtra("vacationTitle");

        String message = "";
        if ("start".equals(alertType)) {
            message = "Today is your vacation start date! (" + vacationTitle + ")";
        } else if ("end".equals(alertType)) {
            message = "Alas, your vacation is on its final day. (" + vacationTitle + ")";
        }

        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}

