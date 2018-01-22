package xyz.ravitripathi.interncart.networking;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import xyz.ravitripathi.interncart.pojo.ProductPOJO;

import static xyz.ravitripathi.interncart.Constants.catalogue;
import static xyz.ravitripathi.interncart.Constants.search_url;

/**
 * Created by ravi on 18/01/18.
 */

public interface LatestAPI {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(catalogue)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("/catalogue/latest")
    Call<List<ProductPOJO>> latest();
}
