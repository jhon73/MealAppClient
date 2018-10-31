package com.mealappclient.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mealappclient.MainActivity;
import com.mealappclient.R;
import com.mealappclient.baseclass.BaseFragment;
import com.mealappclient.databinding.FragmentManageAddressBinding;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManageAddressFragment extends BaseFragment {
    private FragmentManageAddressBinding mBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity)getActivity()).setActionBarTitle("Menu");
        ((MainActivity)getActivity()).setMenuActionBar("HomeMenu");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_manage_address, container, false);
        ButterKnife.bind(this, mBinding.getRoot());
        return mBinding.getRoot();
    }

    @OnClick(R.id.btn_add_new_address)
    public void onClickNewAddress(){
        DeliveryAddressFragment nextFrag= new DeliveryAddressFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.ll_frame, nextFrag,"findThisFragment")
                .addToBackStack(null)
                .commit();
    }
}
