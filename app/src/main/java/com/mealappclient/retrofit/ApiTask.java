package com.mealappclient.retrofit;

;


import com.google.gson.JsonObject;
import com.mealappclient.MealAppClient;
import com.mealappclient.retrofit.model.TokenResp;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;



public class ApiTask {
    private final APICall apiCall;

    public ApiTask() {
        Retrofit retrofit = MealAppClient.getRetrofitInstance();
        apiCall = retrofit.create(APICall.class);
    }


    public RequestBody toRequestBody(String value) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }

    public Call<?> login(String email, String password, APICallback onApiCallback) {


        Call<TokenResp> call = apiCall.login(email,password);
        call.enqueue(onApiCallback);
        return call;
    }

}
