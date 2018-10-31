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
import com.mealappclient.databinding.RowCouponBinding;
import com.mealappclient.fragment.CouponFragment;
import butterknife.OnClick;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.ViewHolder>{
    private RowCouponBinding mBinding;
    private Context context;
    private CouponInterface couponInterface;

    public CouponAdapter(FragmentActivity activity, CouponInterface couponInterface) {
        this.context = activity;
        this.couponInterface = couponInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_coupon, parent, false);
        return new ViewHolder(mBinding, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mBinding.llCoupon.setTag(position);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private RowCouponBinding mBinding;
        public ViewHolder(RowCouponBinding mBinding, View itemView) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
            this.mBinding.llCoupon.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == mBinding.llCoupon) {
                if (couponInterface != null) {
                    int pos = (int) v.getTag();
                    couponInterface.onCouponClick(pos);
                }
            }
        }
    }

    public interface CouponInterface{
        void onCouponClick(int pos);
    }
}

