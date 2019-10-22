package com.example.ui.rxjava;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.ui.R;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.functions.Consumer;



public class RxPermissionsActivity extends AppCompatActivity {
    private String TAG = "RxPermissionsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxpermission_layout);


        RxPermissions permissions = new RxPermissions(this);
        permissions.setLogging(true);
        Log.d(TAG, "checkPermissionsIsGranted WRITE_EXTERNAL_STORAGE " + permissions.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE));


       findViewById(R.id.text_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxPermissions permissions = new RxPermissions(RxPermissionsActivity.this);
                permissions.setLogging(true);
                permissions.request(Manifest.permission.CAMERA)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                Log.d(TAG, "text_camera aBoolean " + aBoolean);
                            }
                        });
            }
        });

       //You can also observe a detailed result with requestEach or ensureEach :
        findViewById(R.id.text_sms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RxPermissions rxPermissions = new RxPermissions(RxPermissionsActivity.this);
                rxPermissions.requestEach(Manifest.permission.READ_SMS)
                        .subscribe(new Consumer<Permission>() {
                            @Override
                            public void accept(Permission permission) throws Exception {
                                if (permission.granted) {
                                    // 用户已经同意该权限
                                    Log.d(TAG, "text_sms granted" );
                                    //result.agree(permission);
                                } else if (permission.shouldShowRequestPermissionRationale) {
                                    // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                                    //result.refuse(permission);
                                    Log.d(TAG, "text_sms shouldShowRequestPermissionRationale" );
                                } else {
                                    // 用户拒绝了该权限，并且选中『不再询问』，提醒用户手动打开权限
                                    //result.noMoreQuestions(permission);
                                    Log.d(TAG, "text_sms " );
                                }
                            }
                        });
            }
        });

        findViewById(R.id.text_camera_sms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxPermissions permissions = new RxPermissions(RxPermissionsActivity.this);
                permissions.setLogging(true);
                permissions.request(Manifest.permission.CAMERA,Manifest.permission.READ_SMS)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                Log.d(TAG, "text_camera_sms aBoolean " + aBoolean);
                            }
                        });
            }
        });
    }

}
