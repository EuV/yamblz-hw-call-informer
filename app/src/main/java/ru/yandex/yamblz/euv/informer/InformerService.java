package ru.yandex.yamblz.euv.informer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class InformerService extends Service {
    public static final String EXTRA_PHONE_NUMBER = "phone_number";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String phoneNumber = intent.getStringExtra(EXTRA_PHONE_NUMBER);

        AlertWindow alertWindow = new AlertWindow(this, phoneNumber);
        alertWindow.show();

        new SearchAsyncTask(alertWindow).execute(phoneNumber);

        return START_NOT_STICKY;
    }
}
