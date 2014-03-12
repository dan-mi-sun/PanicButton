package com.apb.beacon;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.apb.beacon.alert.AlertStatus;
import com.apb.beacon.alert.PanicAlert;
import com.apb.beacon.data.PBDatabase;
import com.apb.beacon.location.LocationFormatter;
import com.apb.beacon.model.Page;
import com.apb.beacon.model.SMSSettings;
import com.apb.beacon.sms.SetupContactsFragment;
import com.apb.beacon.sms.SetupMessageFragment;
import com.apb.beacon.wizard.LanguageSettingsFragment;
import com.apb.beacon.wizard.NewSimpleFragment;
import com.apb.beacon.wizard.SetupCodeFragment;
import com.apb.beacon.wizard.WizardModalActivity;

/**
 * Created by aoe on 2/15/14.
 */
public class MainActivity extends FragmentActivity {

    TextView tvToastMessage;

    Page currentPage;
    String pageId;
    String selectedLang;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.root_layout);

        tvToastMessage = (TextView) findViewById(R.id.tv_toast);
//        tvToastMessage.setVisibility(View.GONE);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.package.ACTION_LOGOUT");
        registerReceiver(activityFinishReceiver, intentFilter);

        pageId = getIntent().getExtras().getString("page_id");
        selectedLang = ApplicationSettings.getSelectedLanguage(this);

        PBDatabase dbInstance = new PBDatabase(this);
        dbInstance.open();
        currentPage = dbInstance.retrievePage(pageId, selectedLang);
        dbInstance.close();

        if (currentPage == null) {
            Log.e(">>>>>>", "page = null");
            Toast.makeText(this, "Still to be implemented.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            Fragment fragment = null;

            if (currentPage.getType().equals("simple")) {
                tvToastMessage.setVisibility(View.INVISIBLE);
                fragment = new NewSimpleFragment().newInstance(pageId, AppConstants.FROM_MAIN_ACTIVITY);
            } else if (currentPage.getType().equals("modal")){
                tvToastMessage.setVisibility(View.INVISIBLE);
                Intent i = new Intent(MainActivity.this, WizardModalActivity.class);
                i.putExtra("page_id", pageId);
                i.putExtra("parent_activity", AppConstants.FROM_MAIN_ACTIVITY);
                startActivity(i);
                finish();
                return;
            } else {
                if (currentPage.getComponent().equals("contacts"))
                    fragment = new SetupContactsFragment().newInstance(pageId, AppConstants.FROM_MAIN_ACTIVITY);
                else if (currentPage.getComponent().equals("message"))
                    fragment = new SetupMessageFragment().newInstance(pageId, AppConstants.FROM_MAIN_ACTIVITY);
                else if (currentPage.getComponent().equals("code"))
                    fragment = new SetupCodeFragment().newInstance(pageId, AppConstants.FROM_MAIN_ACTIVITY);
                else if (currentPage.getComponent().equals("language"))
                    fragment = new LanguageSettingsFragment().newInstance(pageId);
                else
                    fragment = new NewSimpleFragment().newInstance(pageId, AppConstants.FROM_MAIN_ACTIVITY);
            }
            fragmentTransaction.add(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(currentPage.getId().equals("settings") && AlertStatus.ACTIVE.equals(getPanicAlert().getAlertStatus())){
            Toast.makeText(MainActivity.this, "This is Settings.", Toast.LENGTH_SHORT).show();

            Location currentLocation = ApplicationSettings.getCurrentBestLocation(MainActivity.this);
            String locationString = new LocationFormatter(currentLocation).format();
            locationString = "location = " + (locationString.equals("") ? "can't be retrieved.\n" : locationString);

                    SMSSettings smsSettings = SMSSettings.retrieve(MainActivity.this);
            String msg = "Messages-" + smsSettings.trimmedMessage() + "\n";

            StringBuilder sb = new StringBuilder("Phone Number(s)-\n");
            for (String phoneNumber : smsSettings.validPhoneNumbers()) {
                sb.append(phoneNumber + "\n");
            }

            String dialogBody = locationString + msg + sb.toString();
            alert(dialogBody);
        }
    }

    PanicAlert getPanicAlert() {
        return new PanicAlert(this);
    }


    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(MainActivity.this);
        bld.setMessage(message);
        bld.setCancelable(false);
        bld.setNeutralButton("OK", null);
        bld.create().show();
    }


    @Override
    protected void onPause() {
        super.onPause();
//        unregisterReceiver(activityFinishReceiver);

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("com.package.ACTION_LOGOUT");
        sendBroadcast(broadcastIntent);

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(activityFinishReceiver);
    }

    BroadcastReceiver activityFinishReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("onReceive","Logout in progress");
            finish();
        }
    };


}
