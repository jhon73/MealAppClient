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
import com.mealappclient.databinding.RowRestaurantsBinding;
import com.mealappclient.fragment.RestaurantsFragment;
import com.mealappclient.retrofit.model.RestaurentListResp;
import com.mealappclient.utility.Utility;

import java.util.ArrayList;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.ViewHolder> {
    private RowRestaurantsBinding mBinding;
    private Context context;
    private RestautarantInterface restautarantInterface;
    private ArrayList<RestaurentListResp> arrayList;

    public RestaurantsAdapter(FragmentActivity activity,ArrayList<RestaurentListResp> arrayList, RestautarantInterface restautarantInterface) {
        this.context = context;
        this.arrayList = arrayList;
        this.restautarantInterface = restautarantInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_restaurants, parent, false);
        return new ViewHolder(mBinding, parent);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mBinding.txtNameRowRestaurant.setText(arrayList.get(position).getName());
        Utility.loadImage(arrayList.get(position).getImage(),holder.mBinding.imgRowRestaurant,R.drawable.placeholder);
        holder.mBinding.llRestaurant.setTag(position);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RowRestaurantsBinding mBinding;
        public ViewHolder(RowRestaurantsBinding mBinding, View itemView) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
            this.mBinding.llRestaurant.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == mBinding.llRestaurant) {
                if (restautarantInterface != null) {
                    int pos = (int) v.getTag();
                    restautarantInterface.onRestaurantClick(pos);
                }
            }
        }
    }

    public interface RestautarantInterface{
        void onRestaurantClick(int pos);
    }
}
