package com.example.ui.rxjava;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ui.R;
import com.jakewharton.rxbinding2.support.v7.widget.RecyclerViewChildAttachStateChangeEvent;
import com.jakewharton.rxbinding2.support.v7.widget.RecyclerViewFlingEvent;
import com.jakewharton.rxbinding2.support.v7.widget.RecyclerViewScrollEvent;
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.orhanobut.logger.Logger;


import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class RxBindingActivity extends AppCompatActivity {

    public CompositeDisposable mCompositeDisposable;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private LinearLayoutManager mManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxbinding_layout);
        mCompositeDisposable = new CompositeDisposable();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new RecyclerAdapter();
        mManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.setAdapter(mAdapter);

        Button camera = findViewById(R.id.text_camera);

        //点击监听
        RxView.clicks(camera)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Toast.makeText(RxBindingActivity.this, "clicks", Toast.LENGTH_LONG).show();
                    }
                });

        //长按监听
        RxView.longClicks(camera)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Toast.makeText(RxBindingActivity.this, "longClicks", Toast.LENGTH_LONG).show();
                    }
                });

        //绘制监听
        RxView.draws(camera)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        //Toast.makeText(RxBindingActivity.this, "draws", Toast.LENGTH_SHORT).show();
                    }
                });


        // 延时操作
        Observable.timer(2, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Toast.makeText(RxBindingActivity.this, "timer", Toast.LENGTH_SHORT).show();
                    }
                });

        //TextView textChanges 监听
        TextView textView = findViewById(R.id.text);
        addDisposable(RxTextView.textChanges(textView).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence text) throws Exception {
                Toast.makeText(RxBindingActivity.this, "textChanges", Toast.LENGTH_LONG).show();
            }
        }));

       //checkedChanges选中状态改变事件
        Button sms = findViewById(R.id.btn_sms);
        sms.setEnabled(false);
        CheckBox checkBox = findViewById(R.id.checkbox);
        addDisposable(RxCompoundButton.checkedChanges(checkBox)
                .subscribe(aBoolean -> {
                    Toast.makeText(RxBindingActivity.this, "checkedChanges " + aBoolean, Toast.LENGTH_LONG).show();
                }));
        addDisposable(RxView.clicks(sms)
                //防抖2s
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(o -> Toast.makeText(RxBindingActivity.this, "sms",
                        Toast.LENGTH_SHORT).show()));


        //RecyclerView滚动事件
        RxRecyclerView.scrollEvents(mRecyclerView)
                .subscribe(new Consumer<RecyclerViewScrollEvent>() {
                    @Override
                    public void accept(RecyclerViewScrollEvent recyclerViewScrollEvent) throws Exception {
                        recyclerViewScrollEvent.dx();   //X方向滚动了dx
                        recyclerViewScrollEvent.dy();   //Y方向滚动了dy
                        recyclerViewScrollEvent.view(); //RecyclerView
                        Logger.e("recyclerViewScrollEvent.dx()"+recyclerViewScrollEvent.dx());
                    }
                });


        //RecyclerView滚动状态改变
        RxRecyclerView.scrollStateChanges(mRecyclerView)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Logger.e("scrollStateChanges "+integer);
                    }
                });

        //View的添加和移除事件
        RxRecyclerView.childAttachStateChangeEvents(mRecyclerView)
                .subscribe(new Consumer<RecyclerViewChildAttachStateChangeEvent>() {
                    @Override
                    public void accept(RecyclerViewChildAttachStateChangeEvent recyclerViewChildAttachStateChangeEvent) throws Exception {
                        //onNext
                        Logger.e("childAttachStateChangeEvents ");
                        recyclerViewChildAttachStateChangeEvent.child();
                        recyclerViewChildAttachStateChangeEvent.view();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //onError
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //onComplete
                    }
                }, new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        //onSubscribe
                    }
                });

        //手指离开屏幕的飞跃事件
        RxRecyclerView.flingEvents(mRecyclerView)
                .subscribe(new Consumer<RecyclerViewFlingEvent>() {
                    @Override
                    public void accept(RecyclerViewFlingEvent recyclerViewFlingEvent) throws Exception {
                        Logger.e("flingEvents velocityX(); "+recyclerViewFlingEvent.velocityX());
                        recyclerViewFlingEvent.velocityX(); //x方向速度
                        recyclerViewFlingEvent.velocityY(); //y方向速度
                        recyclerViewFlingEvent.view();
                    }
                });




    }





    /**
     * 添加订阅
     */
    public void addDisposable(Disposable mDisposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(mDisposable);
    }

    /**
     * 取消所有订阅
     */
    public void clearDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearDisposable();
    }




    public class RecyclerAdapter extends RecyclerView.Adapter {

        public static final int VIEW_TYPE_ITEM = 1;


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((ItemViewHolder) holder).setData(position);
        }

        @Override
        public int getItemCount() {
            return 15;
        }

        @Override
        public int getItemViewType(int position) {
            return VIEW_TYPE_ITEM;
        }



        class ItemViewHolder extends RecyclerView.ViewHolder {

            public TextView mTitle;

            public ItemViewHolder(View itemView) {
                super(itemView);
                mTitle = (TextView) itemView.findViewById(R.id.title);
            }

            public void setData(int position) {
                switch (position) {
                    default:
                        mTitle.setText("Item " + position);
                }
            }
        }
    }
}
