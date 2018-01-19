package xyz.ravitripathi.interncart.networking;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import xyz.ravitripathi.interncart.pojo.AuthResponse;
import xyz.ravitripathi.interncart.pojo.SignupPOJO;

import static xyz.ravitripathi.interncart.Constants.auth_and_user;

/**
 * Created by ravi on 17/01/18.
 */

public interface SignupAPI {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(auth_and_user)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @POST("/user/addorupdate")
    Call<AuthResponse> signupUser(@Body SignupPOJO body);

}
