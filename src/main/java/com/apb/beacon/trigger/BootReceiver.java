package com.apb.beacon.trigger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.apb.beacon.alert.PanicAlert;
import com.apb.beacon.common.AppConstants;
import com.apb.beacon.common.ApplicationSettings;

import static android.content.Intent.ACTION_BOOT_COMPLETED;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(ACTION_BOOT_COMPLETED)) {
//        	new PanicAlert(context).deActivate();
        	if(ApplicationSettings.isAlertActive(context)){
                Log.e("BootReceiver", "Alarm is active");
	        	ApplicationSettings.setAlertActive(context, false);
	        	new PanicAlert(context).activate();
        	}

            int wizardState = ApplicationSettings.getWizardState(context.getApplicationContext());
            Log.e("BootReceiver", "wizardState = " + wizardState);
            if (wizardState == AppConstants.WIZARD_FLAG_HOME_READY) {
                Log.e("BootReceiver", "BootReceiver in Panic Button");
                context.startService(new Intent(context, HardwareTriggerService.class));
            }
        }
    }
}
