package com.mealappclient.fragment;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mealappclient.MainActivity;
import com.mealappclient.R;
import com.mealappclient.adapter.RestaurantRecommendedAdapter;
import com.mealappclient.adapter.RestaurantsMainAdapter;
import com.mealappclient.adapter.SearchAdapter;
import com.mealappclient.baseclass.BaseFragment;
import com.mealappclient.databinding.FragmentRestaurantDetailsBinding;
import com.mealappclient.databinding.FragmentSearchBinding;
import com.mealappclient.helper.APIService;
import com.mealappclient.helper.APIUtils;
import com.mealappclient.helper.ConstantData;
import com.mealappclient.retrofit.model.MenuItemResp;
import com.mealappclient.retrofit.model.RestaurentListResp;
import com.mealappclient.utility.Utility;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class RestaurantsDetailsFragment extends BaseFragment {

    private FragmentRestaurantDetailsBinding mBinding;
    private LinearLayoutManager linearLayoutManager;
    private RestaurantsMainAdapter mAdapter;
    private RestaurantRecommendedAdapter mAdapterRecommended;
    private ArrayList<MenuItemResp> arrayListRecommended;
    private MenuItemResp menuItemResp;
    private APIService apiService;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).setActionBarTitle("Dana Panee");
        ((MainActivity) getActivity()).setActionBarSubTitle("indian, chines");
        ((MainActivity) getActivity()).setIconBack();
        ((MainActivity) getActivity()).setMenuActionBar("RestaurantDetailMenu");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_restaurant_details, container, false);
        ButterKnife.bind(this, mBinding.getRoot());
        apiService = APIUtils.getAPIService();
        sharedPreferences = getActivity().getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        arrayListRecommended = new ArrayList<>();
        menuItemResp = new MenuItemResp();
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ipokArrayList = new ArrayList<>();

        mBinding.frRestaurantDetailRecommendedRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mAdapterRecommended = new RestaurantRecommendedAdapter(getActivity(),arrayListRecommended, this);
        mBinding.frRestaurantDetailRecommendedRv.setAdapter(mAdapterRecommended);

        getRecommendeList();

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mBinding.frRestaurantDetailMainRv.setLayoutManager(linearLayoutManager);
        mAdapter = new RestaurantsMainAdapter(getActivity(), this);
        mBinding.frRestaurantDetailMainRv.setAdapter(mAdapter);


    }

    private void getRecommendeList() {
        mBinding.rotateloading.start();
        apiService.getAllMenuItem(sharedPreferences.getString(ConstantData.TOKEN, ""))
                .enqueue(new Callback<List<MenuItemResp>>() {
                    @Override
                    public void onResponse(Call<List<MenuItemResp>> call, Response<List<MenuItemResp>> response) {
                        if (response.isSuccessful()) {
                            if (response.code() == 200) {
                                if (response.body() != null) {
//                                    menuItemResp.setDescription(response.body().getDescription());
//                                    menuItemResp.setId(response.body().getId());
//                                    menuItemResp.setImageurl(response.body().getImageurl());
//                                    menuItemResp.setImageurlContentType(response.body().getImageurlContentType());
//                                    menuItemResp.setItemname(response.body().getItemname());
//                                    menuItemResp.setPrice(response.body().getPrice());
                                    mBinding.rotateloading.stop();
                                    arrayListRecommended.addAll(response.body());
                                    mAdapterRecommended.notifyDataSetChanged();
                                }
                            } else if (response.code() == 401) {
                                mBinding.rotateloading.stop();
                                Utility.toast(getActivity(), "Unauthorized");
                            } else if (response.code() == 403) {
                                mBinding.rotateloading.stop();
                                Utility.toast(getActivity(), "Forbidden");
                            } else if (response.code() == 404) {
                                mBinding.rotateloading.stop();
                                Utility.toast(getActivity(), "Not Found");
                            }
                        }else{
                            mBinding.rotateloading.stop();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<MenuItemResp>> call, Throwable t) {
                        mBinding.rotateloading.stop();
                        Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @OnClick(R.id.ll_delivery_time)
    public void onclickDeliveryTime() {
            new OptionSetDeliveryTime(getActivity()).show(getChildFragmentManager(),"");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        ((MainActivity)getActivity()).setIconNavi();
    }


}
