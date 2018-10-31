package com.mealappclient.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mealappclient.MainActivity;
import com.mealappclient.R;
import com.mealappclient.baseclass.BaseFragment;
import com.mealappclient.databinding.FragmentPaymentBinding;
import com.stripe.android.model.Card;
import com.stripe.android.view.CardInputListener;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaymentFragment extends BaseFragment {
    private FragmentPaymentBinding mBinding;
    private  Card cardToSave;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).setActionBarTitle("Checkout");
        ((MainActivity)getActivity()).setIconBack();
        ((MainActivity)getActivity()).setMenuActionBar("CheckoutMenu");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment, container, false);
        ButterKnife.bind(this, mBinding.getRoot());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardToSave = mBinding.cardInputWidget.getCard();
    }

    @OnClick(R.id.btn_place_order)
    public void onClickPlcaeOrder(){
        OrderFinishFragment nextFrag= new OrderFinishFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.ll_frame, nextFrag,"findThisFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBinding.cardInputWidget.setCardNumberTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBinding.card5.setCardNumber(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBinding.cardInputWidget.setExpiryDateTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBinding.card5.setCardExpiry(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBinding.cardInputWidget.setCvcNumberTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBinding.card5.setCVV(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBinding.cardHolderName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBinding.card5.setCardHolderName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
