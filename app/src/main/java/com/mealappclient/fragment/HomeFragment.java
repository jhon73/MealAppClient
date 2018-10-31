package com.mealappclient.fragment;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mealappclient.MainActivity;
import com.mealappclient.R;
import com.mealappclient.adapter.HomeAdapter;
import com.mealappclient.baseclass.BaseFragment;
import com.mealappclient.databinding.FragmentHomeBinding;
import com.mealappclient.helper.APIService;
import com.mealappclient.helper.APIUtils;
import com.mealappclient.helper.ConstantData;
import com.mealappclient.retrofit.model.RestaurentListResp;
import com.mealappclient.utility.Utility;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends BaseFragment implements HomeAdapter.RestautarantHomeInterface {
    private FragmentHomeBinding mBinding;
    private LinearLayoutManager linearLayoutManager;
    private HomeAdapter mAdapter;
    private APIService apiService;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ArrayList<RestaurentListResp> arrayListRestaurent;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).setActionBarTitle("Dashboard");
        ((MainActivity) getActivity()).setMenuActionBar("HomeMenu");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        ButterKnife.bind(this, mBinding.getRoot());
        apiService = APIUtils.getAPIService();
        sharedPreferences = getActivity().getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        arrayListRestaurent = new ArrayList<>();

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mBinding.frHomeRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mAdapter = new HomeAdapter(getActivity(), arrayListRestaurent, this);
        mBinding.frHomeRv.setAdapter(mAdapter);
        getRestaurent();
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
                                    mBinding.txtTotalResstaurant.setText(response.body().size() + " RESTAURANTS");
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
    public void onRestaurantClick(int pos) {
        RestaurantsDetailsFragment nextFrag = new RestaurantsDetailsFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.ll_frame, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }
}
