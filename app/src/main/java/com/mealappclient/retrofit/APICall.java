package com.mealappclient.retrofit;



import com.mealappclient.retrofit.model.TokenResp;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;



public interface APICall {
    int Token_RES_CODE = 401;
    int REGISTER_REQ_CODE = 1;
    int LOGIN_CODE = 2;


   @FormUrlEncoded
    @PUT(WebAPI.LOGIN)
    Call<TokenResp> login(
            @Field("username") String email,
            @Field("password") String password
            );

//    @FormUrlEncoded
//    @PUT(WebAPI.LOGIN)
//    Call<TokenResp> login(@Body RequestBody username,@Body RequestBody password);


}
