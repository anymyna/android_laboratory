package com.example.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.alibaba.android.arouter.launcher.ARouter;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.photoview_layout,R.id.retrofit_post_layout,R.id.retrofit_get_layout,R.id.btn_tab_top,R.id.loopviewpager_layout,R.id.btn_greendao_layout,R.id.btn_dbflow_layout,R.id.btn_swipebacklayout,R.id.btn_parallaxback, R.id.btn_logger,R.id.btn_tab_recyclerview, R.id.easy_permissions, R.id.RxPermission_layout,R.id.btn_vector, R.id.btn_rxjava, R.id.btn_okhttp, R.id.btn_add_flutter_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_tab_top:
                startActivity(new Intent(this, TabLayoutTopActivity.class));
                break;
            case R.id.btn_tab_recyclerview:
                startActivity(new Intent(this, TablayoutRecyclerViewActivity.class));
                break;
            case R.id.btn_vector:
                //startActivity(new Intent(this, VectorDemoActivity.class));
                ARouter.getInstance().build("/test/activity").navigation();
                break;
            case R.id.btn_rxjava:
                startActivity(new Intent(this, RxJavaActivity.class));
                break;
            case R.id.btn_okhttp:
                startActivity(new Intent(this, OkhttpDemoActivity.class));
                break;
            case R.id.btn_add_flutter_view:
                startActivity(new Intent(this, AddFlutterViewActivity.class));
                break;
            case R.id.RxPermission_layout:
                startActivity(new Intent(this, RxPermissionActivity.class));
                break;
            case R.id.easy_permissions:
                startActivity(new Intent(this, EasyPermissionsActivity.class));
                break;
            case R.id.btn_logger:
                startActivity(new Intent(this, EasyPermissionsActivity.class));
                break;
            case R.id.btn_parallaxback:
                startActivity(new Intent(this, ParallaxBackLayoutActivity.class));
                break;
            case R.id.btn_swipebacklayout:
                startActivity(new Intent(this, SwipeBackLayoutActivity.class));
                break;
            case R.id.btn_dbflow_layout:
                startActivity(new Intent(this, DBFlowDemoActivity.class));
                break;
            case R.id.btn_greendao_layout:
                startActivity(new Intent(this, DBFlowDemoActivity.class));
                break;
            case R.id.loopviewpager_layout:
                startActivity(new Intent(this, LoopViewPagerActivity.class));
                break;
            case R.id.photoview_layout:
                startActivity(new Intent(this, PhotoViewActivity.class));
                break;
            case R.id.retrofit_get_layout:
                ARouter.getInstance().build("/retrofit/get").navigation();
                break;
            case R.id.retrofit_post_layout:
                ARouter.getInstance().build("/retrofit/post").navigation();
                break;



        }
    }


}
