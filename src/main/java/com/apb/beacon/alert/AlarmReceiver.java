package com.apb.beacon.alert;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

import com.apb.beacon.ApplicationSettings;
import com.apb.beacon.common.Intents;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(">>>>>>", "AlarmReceiver receive");
        if(intent.getAction().equals(Intents.SEND_ALERT_ACTION)) {
            Location currentBestLocation = ApplicationSettings.getCurrentBestLocation(context);
            getPanicMessage(context).send(currentBestLocation);
        }
    }

    PanicMessage getPanicMessage(Context context) {
        return new PanicMessage(context);
    }
}
