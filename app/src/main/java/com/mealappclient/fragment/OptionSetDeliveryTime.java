package com.mealappclient.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yudiz.wheel.ArrayWheelAdapter;
import com.example.yudiz.wheel.OnWheelChangedListener;
import com.example.yudiz.wheel.WheelView;
import com.mealappclient.R;
import com.mealappclient.databinding.FragmentOptionBinding;
import com.mealappclient.helper.SlideAnimation;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("ValidFragment")
public class OptionSetDeliveryTime extends DialogFragment implements OnWheelChangedListener, View.OnTouchListener {
    private ArrayWheelAdapter mAdapter;
    private Context context;
    private FragmentOptionBinding mBinding;
    private boolean isExpand;
    private TextView textView;

    public OptionSetDeliveryTime(FragmentActivity activity) {
        this.context = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setStyle(STYLE_NO_TITLE, 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_option, container, false);
        ButterKnife.bind(this, mBinding.getRoot());
        mBinding.frgOptionLl.setOnTouchListener(this);
        mBinding.activitySignUpSv.setOnTouchListener(this);
        setUpWheel();
        return mBinding.getRoot();
    }

    private void setUpWheel() {
        List<String> age = new ArrayList<>();
        for (int i = 10; i < 100; i++) {
            age.add(i + "");
        }
        mAdapter = new ArrayWheelAdapter<>(context, R.layout.wheel_picker_row, R.id.wheel_picker_row_tv, new ArrayList<String>());
        mAdapter.setList(age);
        mBinding.wheelTime.setViewAdapter(mAdapter);
        mBinding.wheelTime.addChangingListener(this);
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
//        Toast.makeText(context, ""+newValue + "years", Toast.LENGTH_SHORT).show();
        mBinding.actSignUpTvAge.setText((newValue + 10) + " years");
    }

    @OnClick(R.id.btn_set_delivery_time)
    public void onClickSetDeliveryTime(){
        dismiss();
    }
//    @OnClick(R.id.act_sign_up_ll_age)
//    public void ageClick() {
//        if (mBinding.actSignUpTvAge.getText().toString().equals(""))
//            mBinding.actSignUpTvAge.setText(10 + " years");
//
//        Animation animation;
//        if (isExpand) {
//            animation = new SlideAnimation(mBinding.actSignUpRlAge, (int) getResources().getDimension(R.dimen._110sdp), 0);
//            mBinding.activitySignUpSv.setScrollingEnabled(true);
//        } else {
//            animation = new SlideAnimation(mBinding.actSignUpRlAge, 0, (int) getResources().getDimension(R.dimen._110sdp));
//            mBinding.activitySignUpSv.setScrollingEnabled(false);
//        }
//
////        isExpand = !isExpand;
//        animation.setInterpolator(new AccelerateInterpolator());
//        animation.setDuration(300);
//        mBinding.actSignUpRlAge.setAnimation(animation);
//        mBinding.actSignUpRlAge.startAnimation(animation);
//
//    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

//    @OnClick(R.id.act_sign_up_wheel_age_view)
//    public void wheelClose(){
//        Animation animation;
//        if (isExpand) {
//            animation = new SlideAnimation(mBinding.actSignUpRlAge, (int) getResources().getDimension(R.dimen._110sdp), 0);
//            mBinding.activitySignUpSv.setScrollingEnabled(true);
//        } else {
//            animation = new SlideAnimation(mBinding.actSignUpRlAge, 0, (int) getResources().getDimension(R.dimen._110sdp));
//            mBinding.activitySignUpSv.setScrollingEnabled(false);
//        }
//
////        isExpand = !isExpand;
//        animation.setInterpolator(new AccelerateInterpolator());
//        animation.setDuration(300);
//        mBinding.actSignUpRlAge.setAnimation(animation);
//        mBinding.actSignUpRlAge.startAnimation(animation);
//    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mBinding.activitySignUpSv.setScrollingEnabled(true);
        return false;
    }
}
