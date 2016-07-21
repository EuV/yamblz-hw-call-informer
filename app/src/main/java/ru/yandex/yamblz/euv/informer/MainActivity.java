package ru.yandex.yamblz.euv.informer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import static ru.yandex.yamblz.euv.informer.InformerService.EXTRA_PHONE_NUMBER;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // For debug purposes only
        Button button = new Button(this);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent informer = new Intent(MainActivity.this, InformerService.class);
                informer.putExtra(EXTRA_PHONE_NUMBER, "+79999999999");
                startService(informer);
            }
        });
        setContentView(button);
    }
}
