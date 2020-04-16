package com.automatodev.covids.controller.service;
import com.automatodev.covids.controller.callback.AllCallback;
import com.automatodev.covids.controller.callback.SingleCallback;
import com.automatodev.covids.model.api.CovidRequest;
import com.automatodev.covids.model.entity.Covid;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//####################
public class CovidService {
    private String baseUrl;
    private CovidRequest request;
    private Retrofit retrofit;
    //####################
    public CovidService() {
        baseUrl = "https://coronavirus-19-api.herokuapp.com/";
        retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        request = retrofit.create(CovidRequest.class);
    }
    //####################
    public void servceResultCountries(final AllCallback callback) {
        Call<List<Covid>> call = request.allCountries();
        call.enqueue(new Callback<List<Covid>>() {
            @Override
            public void onResponse(Call<List<Covid>> call, Response<List<Covid>> response) {
                List<Covid> covids = new ArrayList<>();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        covids = response.body();
                        callback.onResponse(covids);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Covid>> call, Throwable t) {
            }
        });
    }
    //####################
    public void serviceresultsGlobal(final SingleCallback callback){
        Call<Covid> call = request.allCases();
        call.enqueue(new Callback<Covid>() {
            @Override
            public void onResponse(Call<Covid> call, Response<Covid> response) {
                Covid covid = new Covid();
                if (response.isSuccessful()){
                    if (response.body()!= null){
                        covid= response.body();
                        callback.onResponse(covid);
                    }
                }
            }
            @Override
            public void onFailure(Call<Covid> call, Throwable t) {
            }
        });
    }
}
