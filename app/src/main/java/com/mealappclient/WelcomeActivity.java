package com.mealappclient;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mealappclient.adapter.IntroAdapter;
import com.mealappclient.baseclass.BaseAppCompactActivity;
import com.mealappclient.databinding.ActivityWelcomeBinding;
import com.mealappclient.utility.IntroPageTransformer;
import com.mealappclient.utility.Utility;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

public class WelcomeActivity extends BaseAppCompactActivity {
    private ActivityWelcomeBinding mBinding;

    private static final Integer[] DASHBOARD= {R.drawable.slider_one,R.drawable.slider_two, R.drawable.slider_three};
    private static int currentPage = 0;
    private Runnable Update;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);
        ButterKnife.bind(this);

        mBinding.viewpager.setAdapter(new IntroAdapter(getSupportFragmentManager()));

        // Set a PageTransformer
        IntroPageTransformer introPageTransformer = new IntroPageTransformer(this);
        mBinding.viewpager.setPageTransformer(false, new IntroPageTransformer());


        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mBinding.viewpager);

        // Auto start of viewpager
       handler = new Handler();
        Update = new Runnable() {
            public void run() {
                if (currentPage == DASHBOARD.length) {
                    currentPage = 0;
                }
                mBinding.viewpager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask()
        {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2500, 2500);

    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(Update);
        super.onDestroy();

    }

    @OnClick(R.id.btn_get_started)
    public void onClickGetStarted(){
        Utility.navigationIntent(this,LoginActivity.class);
    }
}
