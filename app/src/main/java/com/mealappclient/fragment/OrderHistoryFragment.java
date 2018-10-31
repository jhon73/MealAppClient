package com.mealappclient.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mealappclient.MainActivity;
import com.mealappclient.R;
import com.mealappclient.adapter.OrderHistoryAdapter;
import com.mealappclient.baseclass.BaseFragment;
import com.mealappclient.databinding.FragmentHomeBinding;
import com.mealappclient.databinding.FragmentOrderHistoryBinding;

import butterknife.ButterKnife;

public class OrderHistoryFragment extends BaseFragment {
    private FragmentOrderHistoryBinding mBinding;
    private LinearLayoutManager linearLayoutManager;
    private OrderHistoryAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity)getActivity()).setActionBarTitle("Order History");
        ((MainActivity)getActivity()).setIconBack();
        ((MainActivity)getActivity()).setMenuActionBar("ConfirmOrderMenu");
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_history, container, false);
        ButterKnife.bind(this, mBinding.getRoot());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ipokArrayList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mBinding.frOrderHistoryRv.setLayoutManager(linearLayoutManager);
        mAdapter = new OrderHistoryAdapter(getActivity(), this);
        mBinding.frOrderHistoryRv.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity)getActivity()).setActionBarTitle("Menu");
        ((MainActivity)getActivity()).setMenuActionBar("HomeMenu");
        ((MainActivity)getActivity()).setIconNavi();
    }
}
