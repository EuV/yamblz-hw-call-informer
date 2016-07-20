package ru.yandex.yamblz.euv.informer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static android.telephony.TelephonyManager.ACTION_PHONE_STATE_CHANGED;
import static android.telephony.TelephonyManager.EXTRA_INCOMING_NUMBER;
import static android.telephony.TelephonyManager.EXTRA_STATE;
import static android.telephony.TelephonyManager.EXTRA_STATE_RINGING;

public class CallReceiver extends BroadcastReceiver {
    private static String TAG = CallReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!intent.getAction().equals(ACTION_PHONE_STATE_CHANGED)) return;
        if (!intent.getStringExtra(EXTRA_STATE).equals(EXTRA_STATE_RINGING)) return;

        String phoneNumber = intent.getStringExtra(EXTRA_INCOMING_NUMBER);
        Log.e(TAG, "Incoming call: " + phoneNumber);
    }
}
