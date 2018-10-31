package com.mealappclient.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mealappclient.MainActivity;
import com.mealappclient.R;
import com.mealappclient.adapter.FavouriteAdapter;
import com.mealappclient.adapter.OrderConfirmAdapter;
import com.mealappclient.baseclass.BaseFragment;
import com.mealappclient.databinding.FragmentOrderConfirmBinding;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderConfirmFragment extends BaseFragment {
    private FragmentOrderConfirmBinding mBinding;
    private int totalQunt = 1;
    private LinearLayoutManager linearLayoutManager;
    private OrderConfirmAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).setActionBarTitle("Confirm Order");
        ((MainActivity)getActivity()).setIconBack();
        ((MainActivity)getActivity()).setMenuActionBar("ConfirmOrderMenu");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_confirm, container, false);
        ButterKnife.bind(this, mBinding.getRoot());
        return mBinding.getRoot();
    }

//    @OnClick(R.id.btn_plush)
//    public void onClickPlush() {
//        totalQunt = Integer.parseInt(mBinding.txtTotalQuntity.getText().toString());
//        totalQunt += 1;
//        mBinding.txtTotalQuntity.setText("" + totalQunt);
//    }
//
//    @OnClick(R.id.btn_minus)
//    public void onClickMinus() {
//        if (totalQunt > 1) {
//            totalQunt -= 1;
//            mBinding.txtTotalQuntity.setText("" + totalQunt);
//        }
//    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ipokArrayList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mBinding.frOrderConfirmRv.setLayoutManager(linearLayoutManager);
        mAdapter = new OrderConfirmAdapter(getActivity(), this);
        mBinding.frOrderConfirmRv.setAdapter(mAdapter);
//        mBinding.frOrderConfirmRv.setNestedScrollingEnabled(false);
    }

    @OnClick(R.id.btn_proceed)
    public void onClickProceed(){
        DeliveryAddressFragment nextFrag= new DeliveryAddressFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.ll_frame, nextFrag,"findThisFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity)getActivity()).setActionBarTitle("Menu");
        ((MainActivity)getActivity()).setMenuActionBar("HomeMenu");
        ((MainActivity)getActivity()).setIconNavi();
    }
}
