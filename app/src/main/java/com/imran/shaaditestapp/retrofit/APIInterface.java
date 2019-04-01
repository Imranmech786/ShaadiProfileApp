package com.imran.shaaditestapp.retrofit;


import com.imran.shaaditestapp.model.JsonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    //api/?results=10
    @GET("api/")
    Call<JsonResponse> getList(
            @Query("results") int id
    );

}
