package com.mealappclient.fragment;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mealappclient.LoginActivity;
import com.mealappclient.MainActivity;
import com.mealappclient.R;
import com.mealappclient.adapter.OrderHistoryAdapter;
import com.mealappclient.adapter.RestaurantsAdapter;
import com.mealappclient.baseclass.BaseFragment;
import com.mealappclient.container.BaseContainer;
import com.mealappclient.databinding.FragmentRestaurantsBinding;
import com.mealappclient.helper.APIService;
import com.mealappclient.helper.APIUtils;
import com.mealappclient.helper.ConstantData;
import com.mealappclient.retrofit.model.RestaurentListResp;
import com.mealappclient.retrofit.model.TokenResp;
import com.mealappclient.utility.Utility;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class RestaurantsFragment extends BaseFragment implements RestaurantsAdapter.RestautarantInterface {
    private FragmentRestaurantsBinding mBinding;
    private LinearLayoutManager linearLayoutManager;
    private RestaurantsAdapter mAdapter;
    private APIService apiService;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ArrayList<RestaurentListResp> arrayListRestaurent;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity)getActivity()).setActionBarTitle("Restaurant");
        ((MainActivity)getActivity()).setIconBack();
        ((MainActivity)getActivity()).setMenuActionBar("RestaurantMenu");
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_restaurants, container, false);
        ButterKnife.bind(this, mBinding.getRoot());
        apiService = APIUtils.getAPIService();
        sharedPreferences = getActivity().getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        arrayListRestaurent = new ArrayList<>();

        return mBinding.getRoot();
    }

    private void getRestaurent() {
        mBinding.rotateloading.start();
        apiService.getRestaurentList(sharedPreferences.getString(ConstantData.TOKEN, ""))
                .enqueue(new Callback<List<RestaurentListResp>>() {
                    @Override
                    public void onResponse(Call<List<RestaurentListResp>> call, Response<List<RestaurentListResp>> response) {
                        if (response.isSuccessful()) {
                            if (response.code() == 200) {
                                if (response.body() != null) {
                                    mBinding.rotateloading.stop();
                                    arrayListRestaurent.addAll(response.body());
                                    mAdapter.notifyDataSetChanged();
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
                    public void onFailure(Call<List<RestaurentListResp>> call, Throwable t) {
                        mBinding.rotateloading.stop();
                        Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mBinding.frRestaurantRv.setLayoutManager(linearLayoutManager);
        mAdapter = new RestaurantsAdapter(getActivity(),arrayListRestaurent, this);
        mBinding.frRestaurantRv.setAdapter(mAdapter);
        getRestaurent();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity)getActivity()).setActionBarTitle("Menu");
        ((MainActivity)getActivity()).setMenuActionBar("HomeMenu");
        ((MainActivity)getActivity()).setIconNavi();
    }

    @Override
    public void onRestaurantClick(int pos) {
//        ((BaseContainer) getParentFragment()).replaceFragment(new RestaurantsDetailsFragment());
//        Log.i("TAG", "onRestaurantClick: ");
        RestaurantsDetailsFragment nextFrag = new RestaurantsDetailsFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.ll_frame, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }
}
