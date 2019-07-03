package com.farhan.myapplication.API;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BookAPI {
    @GET("apibooks")
    Call<ServerResponse> getBooks();

    @GET("apialltransaction")
    Call<TransactionResponse> getAllTransaction();

    @GET("apimytransaction")
    Call<TransactionResponse> getMyTransaction(
            @Header("Authorization") String api_token
    );

    @GET("apibooks/{id}")
    Call<DetailBookResponse> getBooksbyId(
            @Path("id") int id
    );

    @FormUrlEncoded
    @POST("apilogin")
    Call<UserResponse> loginRequest(
        @Field("email") String email,
        @Field("password") String password
    );

    @FormUrlEncoded
    @POST("apiregister")
    Call<UserResponse> registerRequest(
        @Field("name") String name,
        @Field("email") String email,
        @Field("password") String password
    );


    @FormUrlEncoded
    @POST("apiborrow")
    Call<BorrowResponse> borrowRequest(
            @Header("Authorization") String api_token,
            @Field("borrow_date") String borrowDate,
            @Field("return_date") String returnDate,
            @Field("total_price") Double totalPrice,
            @Field("id") int id
    );

    @POST("apilogout")
    Call<UserResponse> logoutRequest(
            @Header("Authorization") String api_token
    );

}
