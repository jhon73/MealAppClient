package com.mealappclient.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mealappclient.R;
import com.mealappclient.databinding.RowCustomAdsViewBinding;
import com.mealappclient.databinding.RowHomeBinding;
import com.mealappclient.fragment.HomeFragment;

public class HomeAdapterMainTemp extends RecyclerView.Adapter<HomeAdapterMainTemp.ViewHolder> {
    private Context context;
    private RowHomeBinding mBinding;
    private RowCustomAdsViewBinding mBindingAds;
    private static final int LIST_AD_DELTA = 3;
    private static final int CONTENT = 0;
    private static final int AD = 1;

    public HomeAdapterMainTemp(FragmentActivity activity, HomeFragment homeFragment) {
        this.context = activity;
    }

    @Override
    public int getItemViewType(int position) {
        if (position > 0 && position % LIST_AD_DELTA == 0) {
            return AD;
        }
        return CONTENT;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == CONTENT){
            mBindingAds = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_custom_ads_view, parent, false);
            return new ViewHolder(mBindingAds,parent);
        }else {
            mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_home, parent, false);
//            return new ViewHolder(mBinding, parent);
        }
        return new ViewHolder(mBinding, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RowHomeBinding mBinding;
        private RowCustomAdsViewBinding mBindingAds;
        public ViewHolder(RowHomeBinding mBinding, View itemView) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }
        public ViewHolder(RowCustomAdsViewBinding mBindingAds, ViewGroup parent) {
            super(mBindingAds.getRoot());
            this.mBindingAds = mBindingAds;
        }
    }
}
