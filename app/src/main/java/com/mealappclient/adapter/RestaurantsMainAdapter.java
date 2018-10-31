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
import com.mealappclient.databinding.RowRestaturantDetailMainBinding;
import com.mealappclient.fragment.RestaurantsDetailsFragment;
import com.mealappclient.retrofit.model.MenuItemResp;

public class RestaurantsMainAdapter extends RecyclerView.Adapter<RestaurantsMainAdapter.ViewHolder> {
    private Context context;
    private RowRestaturantDetailMainBinding mBinding;


    public RestaurantsMainAdapter(FragmentActivity activity, RestaurantsDetailsFragment restaurantsDetailsFragment) {
        this.context = activity;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_restaturant_detail_main, parent, false);
        return new ViewHolder(mBinding, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RowRestaturantDetailMainBinding mBinding;
        private RestaurantsChildAdapter mAdapter;
        public ViewHolder(RowRestaturantDetailMainBinding mBinding, View itemView) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }
        public void bindData(int position) {
            mBinding.frRestaurantDetailChildRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            mAdapter=new RestaurantsChildAdapter(context);
            mBinding.frRestaurantDetailChildRv.setAdapter(mAdapter);
        }
    }
}
