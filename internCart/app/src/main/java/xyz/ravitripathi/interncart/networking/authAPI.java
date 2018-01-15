package xyz.ravitripathi.interncart.networking;

import android.content.res.Resources;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import xyz.ravitripathi.interncart.App;
import xyz.ravitripathi.interncart.POJO.authPOJO;
import xyz.ravitripathi.interncart.POJO.authResponse;
import xyz.ravitripathi.interncart.R;


/**
 * Created by ravi on 15/01/18.
 */

public interface authAPI {
        @POST("/jayson")
        Call<authResponse> postUser(@Body authPOJO body);


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://www.google.com")
            //.baseUrl(App.getContext().getResources().getString(R.string.backend_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    /*
    @POST("studio")
    Call<List<ResponsePOJO>> getSongs(Res);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://starlord.hackerearth.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}*/




