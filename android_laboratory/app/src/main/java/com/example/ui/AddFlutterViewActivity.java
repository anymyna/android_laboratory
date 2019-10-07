package com.example.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.orhanobut.logger.Logger;

import io.flutter.facade.Flutter;


public class AddFlutterViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flutter_view);

        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                View flutterView = Flutter.createView(
//                        AddFlutterViewActivity.this,
//                        getLifecycle(),
//                        "route1"
//                );
//                FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(600, 800);
//                layout.leftMargin = 100;
//                layout.topMargin = 200;
//                addContentView(flutterView, layout);
                FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
                tx.replace(R.id.container, Flutter.createFragment("route1"));
                tx.commit();
            }
        });
    }

}
