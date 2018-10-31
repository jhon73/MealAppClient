package com.mealappclient.container;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mealappclient.R;
import com.mealappclient.fragment.FavouriteFragment;
import com.mealappclient.fragment.OrderHistoryFragment;

public class OrderHistoryContainer extends BaseContainer {
    private OrderHistoryFragment orderHistoryFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_container, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (orderHistoryFragment == null)
            orderHistoryFragment = new OrderHistoryFragment();
        replaceFragment(orderHistoryFragment);
    }
}
