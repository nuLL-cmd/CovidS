package com.automatodev.covids.model.api;

import com.automatodev.covids.model.entity.Covid;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CovidRequest {

    @GET("all")
    Call<Covid> allCases();

    @GET("countries")
    Call<List<Covid>> allCountries();

}