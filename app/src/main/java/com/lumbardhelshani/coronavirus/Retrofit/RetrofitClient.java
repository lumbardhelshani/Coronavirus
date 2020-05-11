package com.lumbardhelshani.coronavirus.Retrofit;
/* user: lumba
   date: 5/11/2020
   time: 15:22
*/
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    private RetrofitClient(){    }

    public static Retrofit getRetrofitInstance(){
        if(retrofit == null){
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://corona.lmao.ninja/v2/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
