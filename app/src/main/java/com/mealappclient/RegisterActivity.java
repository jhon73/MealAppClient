package com.mealappclient;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.mealappclient.baseclass.BaseAppCompactActivity;
import com.mealappclient.databinding.ActivityLoginBinding;
import com.mealappclient.databinding.ActivityRegisterBinding;
import com.mealappclient.helper.APIService;
import com.mealappclient.helper.APIUtils;
import com.mealappclient.retrofit.model.TokenResp;
import com.mealappclient.utility.Utility;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseAppCompactActivity {
    private ActivityRegisterBinding mBinding;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        ButterKnife.bind(this);
        apiService = APIUtils.getAPIService();

    }

    @OnClick(R.id.btn_register)
    public void onClickRegister() {

        if (mBinding.edtFullName.getText().length() == 0)
            mBinding.edtFullName.setError("Enter Full name");
        else if (mBinding.edtEmail.getText().length() == 0)
            mBinding.edtEmail.setError("Enter Email");
        else if (mBinding.edtPassword.getText().length() == 0)
            mBinding.edtPassword.setError("Enter Password");
        else if (mBinding.edtPassword.getText().length() < 6)
            mBinding.edtPassword.setError("password length minimum 6 character");
        else {

            Map<String, Object> requestBodyMap = new HashMap<>();
            requestBodyMap.put("activated", true);
            requestBodyMap.put("authorities", new String[]{"ROLE_USER"});
            requestBodyMap.put("email", mBinding.edtEmail.getText().toString().trim());
            requestBodyMap.put("firstName", mBinding.edtFullName.getText().toString().trim());
            requestBodyMap.put("login", mBinding.edtEmail.getText().toString().trim());
            requestBodyMap.put("password", mBinding.edtPassword.getText().toString().trim());
            apiService.getSignUpData(requestBodyMap)
                    .enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                if (response.code() == 201) {
                                    Utility.navigationIntent(RegisterActivity.this, LoginActivity.class);
                                } else if (response.code() == 401) {
                                    Utility.toast(RegisterActivity.this, "Unauthorized");
                                } else if (response.code() == 403) {
                                    Utility.toast(RegisterActivity.this, "Forbidden");
                                } else if (response.code() == 404) {
                                    Utility.toast(RegisterActivity.this, "Not Found");
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(RegisterActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @OnClick(R.id.txt_already_login)
    public void onClickAlreadyLogin() {
        Utility.navigationIntent(RegisterActivity.this,LoginActivity.class);
        finish();
    }

//        Utility.navigationIntent(this,LoginActivity.class);
//    }

}
