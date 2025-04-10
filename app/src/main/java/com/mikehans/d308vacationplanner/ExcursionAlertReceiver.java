package com.mikehans.d308vacationplanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ExcursionAlertReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String excursionTitle = intent.getStringExtra("excursionTitle");

        if (excursionTitle != null) {
            Toast.makeText(context, "Excursion today: " + excursionTitle, Toast.LENGTH_LONG).show();
        }
    }
}
