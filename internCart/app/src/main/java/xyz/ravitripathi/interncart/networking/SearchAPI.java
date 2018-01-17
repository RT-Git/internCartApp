package xyz.ravitripathi.interncart.networking;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import xyz.ravitripathi.interncart.pojo.ProductPOJO;

import static xyz.ravitripathi.interncart.Constants.base_url;

/**
 * Created by ravi on 15/01/18.
 */

public interface SearchAPI {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
//
    @GET("/search/get/{prodname}")
    Call<List<ProductPOJO>> search(@Path("prodname")String prodname);
}
