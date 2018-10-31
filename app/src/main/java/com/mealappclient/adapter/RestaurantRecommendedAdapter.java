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
import com.mealappclient.databinding.RowRestaurantRecommendedBinding;
import com.mealappclient.fragment.RestaurantsDetailsFragment;
import com.mealappclient.retrofit.model.MenuItemResp;
import com.mealappclient.utility.Utility;

import java.util.ArrayList;

public class RestaurantRecommendedAdapter extends RecyclerView.Adapter<RestaurantRecommendedAdapter.ViewHolder> {
    private Context context;
    private RowRestaurantRecommendedBinding mBinding;
    private RestaurantDetailRecommendedInterface restaurantDetailRecommendedInterface;
    private ArrayList<MenuItemResp> arrayList;

    public RestaurantRecommendedAdapter(FragmentActivity activity, ArrayList<MenuItemResp> menuItemResp, RestaurantsDetailsFragment restaurantsDetailsFragment) {
        this.context = activity;
        this.arrayList = menuItemResp;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_restaurant_recommended, parent, false);
        return new ViewHolder(mBinding, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mBinding.txtRecommendedName.setText(arrayList.get(position).getItemname());
        Utility.loadImage(arrayList.get(position).getImageurl(), holder.mBinding.imgRecommendedRest, R.drawable.placeholder);
        holder.mBinding.txtRecommendedPrice.setText("$"+arrayList.get(position).getPrice());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RowRestaurantRecommendedBinding mBinding;

        public ViewHolder(RowRestaurantRecommendedBinding mBinding, View itemView) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }
    }
    public interface RestaurantDetailRecommendedInterface {
        void onClickRecommendedItem(int pos);
    }
}
