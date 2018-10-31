package com.mealappclient.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mealappclient.R;
import com.mealappclient.databinding.RowFilterMainBinding;
import com.mealappclient.fragment.FilterFragment;

public class FilterMainAdapter extends RecyclerView.Adapter<FilterMainAdapter.ViewHolder> {
    private Context context;
    private RowFilterMainBinding mBinding;

    public FilterMainAdapter(FragmentActivity activity, FilterFragment filterFragment) {
        this.context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_filter_main, parent, false);
        return new ViewHolder(mBinding, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(position);

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RowFilterMainBinding mBinding;
        private FilterChildAdapter mAdapter;
        public ViewHolder(RowFilterMainBinding mBinding, View itemView) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }

        public void bindData(int position) {
            mBinding.frFilterChildRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            mAdapter=new FilterChildAdapter(context);
            mBinding.frFilterChildRv.setAdapter(mAdapter);
        }
    }
}
