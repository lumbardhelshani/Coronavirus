package com.lumbardhelshani.coronavirus.Retrofit;
/* user: lumba
   date: 5/11/2020
   time: 15:32
*/

import com.lumbardhelshani.coronavirus.Models.CountryCovidData;
import com.lumbardhelshani.coronavirus.Models.WorldCovidData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CovidService {

    @GET("all")
    Call<WorldCovidData> getWorldCovidStatistics();

    @GET("countries")
    Call<List<CountryCovidData>> getCountriesStatistics();
}
