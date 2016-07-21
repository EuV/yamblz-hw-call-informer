package ru.yandex.yamblz.euv.informer;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import static android.graphics.PixelFormat.TRANSLUCENT;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
import static android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
import static android.view.WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

public class InformerService extends Service {
    public static final String EXTRA_PHONE_NUMBER = "phone_number";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showAlertWindow(getApplicationContext(), intent.getStringExtra(EXTRA_PHONE_NUMBER));
        return START_NOT_STICKY;
    }


    @SuppressLint("InflateParams")
    private void showAlertWindow(Context context, String phoneNumber) {
        final WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        final ViewGroup informerWindow = (ViewGroup) layoutInflater.inflate(R.layout.informer_window, null);

        TextView phoneHeader = (TextView) informerWindow.findViewById(R.id.text_phone_header);
        phoneHeader.setText(context.getString(R.string.format_incoming_call, phoneNumber));

        Button closeButton = (Button) informerWindow.findViewById(R.id.button_close);
        closeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                windowManager.removeView(informerWindow);
                stopSelf();
            }
        });

        LayoutParams windowParams = new LayoutParams(
                MATCH_PARENT,
                WRAP_CONTENT,
                TYPE_SYSTEM_ALERT,
                FLAG_NOT_FOCUSABLE | FLAG_NOT_TOUCH_MODAL,
                TRANSLUCENT
        );
        windowParams.gravity = Gravity.CENTER;

        windowManager.addView(informerWindow, windowParams);
    }
}
