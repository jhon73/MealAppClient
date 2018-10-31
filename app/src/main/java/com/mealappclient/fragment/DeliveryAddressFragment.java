package com.mealappclient.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.mealappclient.MainActivity;
import com.mealappclient.R;
import com.mealappclient.baseclass.BaseFragment;
import com.mealappclient.databinding.FragmentDeliveryAddressBinding;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeliveryAddressFragment extends BaseFragment {
    private FragmentDeliveryAddressBinding mBinding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).setActionBarTitle("Delivery Address");
        ((MainActivity)getActivity()).setIconBack();
        ((MainActivity)getActivity()).setMenuActionBar("DeliveryAddressMenu");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_delivery_address , container, false);
        ButterKnife.bind(this, mBinding.getRoot());
        return mBinding.getRoot();
    }

    @OnClick(R.id.btn_checkout)
    public void onClickCheckout(){
        PaymentFragment nextFrag= new PaymentFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.ll_frame, nextFrag,"findThisFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity) getActivity()).setActionBarTitle("Coupon");
        ((MainActivity)getActivity()).setMenuActionBar("else");
        ((MainActivity)getActivity()).setIconBack();
    }
}
