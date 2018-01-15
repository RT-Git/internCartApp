package xyz.ravitripathi.interncart.networking;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import xyz.ravitripathi.interncart.pojo.productPOJO;

/**
 * Created by ravi on 15/01/18.
 */

public class SearchAPI {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://www.google.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
//
//    @GET("/search")
//    Call<productPOJO> search(@Path());
}
