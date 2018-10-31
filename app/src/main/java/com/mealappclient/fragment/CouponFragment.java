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
import com.mealappclient.adapter.CouponAdapter;
import com.mealappclient.adapter.FavouriteAdapter;
import com.mealappclient.baseclass.BaseFragment;
import com.mealappclient.databinding.FragmentCouponBinding;
import com.mealappclient.databinding.FragmentFavouriteBinding;

import butterknife.ButterKnife;

public class CouponFragment extends BaseFragment implements CouponAdapter.CouponInterface {

    private FragmentCouponBinding mBinding;
    private LinearLayoutManager linearLayoutManager;
    private CouponAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity)getActivity()).setActionBarTitle("Coupon");
        ((MainActivity)getActivity()).setIconBack();
        ((MainActivity)getActivity()).setMenuActionBar("CouponMenu");
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_coupon, container, false);
        ButterKnife.bind(this, mBinding.getRoot());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ipokArrayList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mBinding.frCouponRv.setLayoutManager(linearLayoutManager);
        mAdapter = new CouponAdapter(getActivity(), this);
        mBinding.frCouponRv.setAdapter(mAdapter);
    }

    @Override
    public void onCouponClick(int pos) {
        VerifyCodeFragment nextFrag= new VerifyCodeFragment();
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
