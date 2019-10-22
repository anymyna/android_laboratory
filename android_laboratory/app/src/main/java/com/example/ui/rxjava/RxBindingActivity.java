package com.example.ui.rxjava;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import com.example.ui.R;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

public class RxBindingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxbinding_layout);

        RxView.clicks( findViewById(R.id.text_camera))
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Toast.makeText(RxBindingActivity.this, "camera", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
