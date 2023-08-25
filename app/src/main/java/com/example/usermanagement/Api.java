package com.example.usermanagement;

import com.example.usermanagement.ModelResponse.DeleteAccountResponse;
import com.example.usermanagement.ModelResponse.FetchUserResponse;
import com.example.usermanagement.ModelResponse.LoginResponse;
import com.example.usermanagement.ModelResponse.RegisterResponse;
import com.example.usermanagement.ModelResponse.UpdatePasswordResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("register.php")
    Call<RegisterResponse> register(
        @Field("username") String username,
        @Field("email") String email,
        @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("fetchusers.php")
    Call<FetchUserResponse> fetchAllUsers();

    @FormUrlEncoded
    @POST("updateuser.php")
    Call<LoginResponse> updateUserAccount(
            @Field("id") int userid,
            @Field("username") String username,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("updatepassword.php")
    Call<UpdatePasswordResponse> updateUserPassword(
            @Field("email") String email,
            @Field("current") String currentPassword,
            @Field("new") String newPassword
    );
    @FormUrlEncoded
    @POST("deleteaccount.php")
    Call<DeleteAccountResponse> deleteUserAccount(
           @Field("id") int userid
    );


}
