package ru.yandex.yamblz.euv.informer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static android.telephony.TelephonyManager.ACTION_PHONE_STATE_CHANGED;
import static android.telephony.TelephonyManager.EXTRA_INCOMING_NUMBER;
import static android.telephony.TelephonyManager.EXTRA_STATE;
import static android.telephony.TelephonyManager.EXTRA_STATE_RINGING;
import static ru.yandex.yamblz.euv.informer.InformerService.EXTRA_PHONE_NUMBER;

public class CallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!intent.getAction().equals(ACTION_PHONE_STATE_CHANGED)) return;
        if (!intent.getStringExtra(EXTRA_STATE).equals(EXTRA_STATE_RINGING)) return;

        Intent informer = new Intent(context, InformerService.class);
        informer.putExtra(EXTRA_PHONE_NUMBER, intent.getStringExtra(EXTRA_INCOMING_NUMBER));
        context.startService(informer);
    }
}
