package com.mealappclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.mealappclient.baseclass.BaseAppCompactActivity;
import com.mealappclient.databinding.ActivityLoginBinding;
import com.mealappclient.databinding.ActivityMainBinding;
//import com.mealappclient.helper.APIService;
//import com.mealappclient.helper.APIUtils;
import com.mealappclient.helper.APIService;
import com.mealappclient.helper.APIUtils;
import com.mealappclient.helper.ConstantData;
import com.mealappclient.retrofit.APICall;
import com.mealappclient.retrofit.APICallback;
import com.mealappclient.retrofit.OnApiResponseListner;
import com.mealappclient.retrofit.model.TokenResp;
import com.mealappclient.utility.Utility;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseAppCompactActivity  {
    private ActivityLoginBinding mBinding;
    private APIService apiService;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        ButterKnife.bind(this);
        apiService = APIUtils.getAPIService();
        sharedPreferences = getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    @OnClick(R.id.btn_sign_up_new_user)
    public void onClickeOpenRegistation(){
        Utility.navigationIntent(this,RegisterActivity.class);
    }

    @OnClick(R.id.btn_login)
    public void onClickRegister() {
        if (mBinding.txtActLoginEmail.getText().length() ==0 )
            mBinding.txtActLoginEmail.setError("Enter Email");
        else if (mBinding.txtActLoginPassword.getText().length() ==0)
            mBinding.txtActLoginPassword.setError("Enter Password");
        else {
            Map<String, String> requestBodyMap = new HashMap<>();
            requestBodyMap.put("username", mBinding.txtActLoginEmail.getText().toString().trim());
            requestBodyMap.put("password", mBinding.txtActLoginPassword.getText().toString().trim());
            apiService.getLoginData(requestBodyMap)
                    .enqueue(new Callback<TokenResp>() {
                        @Override
                        public void onResponse(Call<TokenResp> call, Response<TokenResp> response) {
                            if (response.isSuccessful()) {
                                if (response.code() == 200) {
                                    if (response.body().getIdToken() != null) {
                                        Utility.navigationIntent(LoginActivity.this, MainActivity.class);
                                        editor.putString(ConstantData.TOKEN, "Bearer "+response.body().getIdToken());
                                        editor.commit();
                                        String token  = sharedPreferences.getString(ConstantData.TOKEN, "");  //get Token data
                                    }
                                } else if (response.code() == 401) {
                                    Utility.toast(LoginActivity.this, "Unauthorized");
                                } else if (response.code() == 403) {
                                    Utility.toast(LoginActivity.this, "Forbidden");
                                } else if (response.code() == 404) {
                                    Utility.toast(LoginActivity.this, "Not Found");
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<TokenResp> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

//        if (Utility.haveNetworkConnection(this)) {
//            callLogin = ((MealAppClient) getApplication()).getApiTask().login(mBinding.txtActLoginEmail.getText().toString().trim(),
//                    mBinding.txtActLoginPassword.getText().toString().trim(),
//                    new APICallback(this, APICall.LOGIN_CODE, this));
//        } else {
//            Utility.toast(this, "No internet Connection");
//        }


//    @Override
//    public void onResponseComplete(Object clsGson, int requestCode) {
//        if (requestCode == APICall.LOGIN_CODE) {
//            if (clsGson instanceof TokenResp) {
//                TokenResp tokenResp = (TokenResp) clsGson;
//                Utility.toast(this, "Success");
//                Utility.log("" + tokenResp.getIdToken());
//            }
//        }
//    }
//
//    @Override
//    public void onResponseError(Object errorMessage, int requestCode) {
//        Utility.toast(this, "fail"+ errorMessage);
//    }
}

