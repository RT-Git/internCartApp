package xyz.ravitripathi.interncart.networking;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import xyz.ravitripathi.interncart.pojo.AuthPOJO;
import xyz.ravitripathi.interncart.pojo.AuthResponse;

import static xyz.ravitripathi.interncart.Constants.base_url;


/**
 * Created by ravi on 15/01/18.
 */

public interface AuthAPI {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @POST("/authenticate/login")
    Call<AuthResponse> postUser(@Body AuthPOJO body);

    //.baseUrl(App.getContext().getResources().getString(R.string.backend_url))

}



