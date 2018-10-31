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
import com.mealappclient.databinding.RowHomeBinding;
import com.mealappclient.fragment.HomeFragment;
import com.mealappclient.retrofit.model.RestaurentListResp;
import com.mealappclient.utility.Utility;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private Context context;
    private RowHomeBinding mBinding;
    private ArrayList<RestaurentListResp> arrayList;
    private RestautarantHomeInterface restautarantHomeInterface;

    public
    HomeAdapter(FragmentActivity activity,ArrayList<RestaurentListResp> arrayList, RestautarantHomeInterface restautarantHomeInterface) {
        this.context = activity;
        this.arrayList = arrayList;
        this.restautarantHomeInterface = restautarantHomeInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_home, parent, false);
        return new ViewHolder(mBinding, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mBinding.txtHomeRestaurantName.setText(arrayList.get(position).getName());
        Utility.loadImage(arrayList.get(position).getImage(),holder.mBinding.imgHomeRestaurant,R.drawable.placeholder);
        holder.mBinding.fgHomeCard.setTag(position);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RowHomeBinding mBinding;

        public ViewHolder(RowHomeBinding mBinding, View itemView) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
            this.mBinding.fgHomeCard.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == mBinding.fgHomeCard) {
                if (restautarantHomeInterface != null) {
                    int pos = (int) v.getTag();
                    restautarantHomeInterface.onRestaurantClick(pos);
                }
            }
        }
    }
    public interface RestautarantHomeInterface{
        void onRestaurantClick(int pos);
    }

}
