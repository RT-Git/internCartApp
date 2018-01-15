package xyz.ravitripathi.interncart.networking;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import xyz.ravitripathi.interncart.POJO.authPOJO;
import xyz.ravitripathi.interncart.POJO.authResponse;


/**
 * Created by ravi on 15/01/18.
 */

public interface authAPI {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://www.google.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @POST("/jayson")
    Call<authResponse> postUser(@Body authPOJO body);

    //.baseUrl(App.getContext().getResources().getString(R.string.backend_url))

}



