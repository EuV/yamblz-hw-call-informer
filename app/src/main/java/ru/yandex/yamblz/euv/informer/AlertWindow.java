package ru.yandex.yamblz.euv.informer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.content.Context.WINDOW_SERVICE;
import static android.content.Intent.ACTION_VIEW;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.graphics.PixelFormat.TRANSLUCENT;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
import static android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
import static android.view.WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

public class AlertWindow {
    final InformerService service;
    final WindowManager windowManager;
    final ViewGroup windowLayout;
    final ProgressBar progressBar;
    final TextView textPreview;

    @SuppressLint("InflateParams")
    AlertWindow(final InformerService service, String phoneNumber) {
        this.service = service;
        windowManager = (WindowManager) service.getSystemService(WINDOW_SERVICE);

        LayoutInflater layoutInflater = (LayoutInflater) service.getSystemService(LAYOUT_INFLATER_SERVICE);
        windowLayout = (ViewGroup) layoutInflater.inflate(R.layout.informer_window, null);

        TextView phoneHeader = (TextView) windowLayout.findViewById(R.id.text_phone_header);
        phoneHeader.setText(service.getString(R.string.format_incoming_call, phoneNumber));

        Button closeButton = (Button) windowLayout.findViewById(R.id.button_close);
        closeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                destroy();
            }
        });

        progressBar = (ProgressBar) windowLayout.findViewById(R.id.progress_bar);
        textPreview = (TextView) windowLayout.findViewById(R.id.text_preview);
    }


    public void show() {
        LayoutParams windowParams = new LayoutParams(
                MATCH_PARENT,
                WRAP_CONTENT,
                TYPE_SYSTEM_ALERT,
                FLAG_NOT_FOCUSABLE | FLAG_NOT_TOUCH_MODAL,
                TRANSLUCENT
        );
        windowParams.gravity = Gravity.CENTER;
        windowManager.addView(windowLayout, windowParams);
    }


    public void setPreview(final SearchAsyncTask.Response response) {
        textPreview.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        if (response == null) {
            textPreview.setText(R.string.label_search_failed);
            return;
        }

        textPreview.setText(response.textPreview);
        textPreview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ACTION_VIEW, Uri.parse(response.link));
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                service.startActivity(intent);
                destroy();
            }
        });
    }


    private void destroy() {
        windowManager.removeView(windowLayout);
        service.stopSelf();
    }
}
