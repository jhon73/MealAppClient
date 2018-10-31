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
import com.mealappclient.databinding.RowOrderConfirmBinding;
import com.mealappclient.fragment.OrderConfirmFragment;

public class OrderConfirmAdapter extends RecyclerView.Adapter<OrderConfirmAdapter.ViewHolder> {
    private RowOrderConfirmBinding mBinding;
    private Context context;

    public OrderConfirmAdapter(FragmentActivity activity, OrderConfirmFragment orderConfirmFragment) {
        this.context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_order_confirm, parent, false);
        return new ViewHolder(mBinding, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RowOrderConfirmBinding mBinding;

        public ViewHolder(RowOrderConfirmBinding mBinding, View itemView) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
            this.mBinding.btnPlush.setOnClickListener(this);
            this.mBinding.btnMinus.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == mBinding.btnPlush){
                int temp = Integer.parseInt(mBinding.txtTotalParticularItem.getText().toString());
                temp+=1;
                mBinding.txtTotalParticularItem.setText(""+temp);
            }else if(v == mBinding.btnMinus){
                int temp = Integer.parseInt(mBinding.txtTotalParticularItem.getText().toString());
                if (temp != 0) {
                    temp -= 1;
                    mBinding.txtTotalParticularItem.setText("" + temp);
                }
            }
        }
    }
}
