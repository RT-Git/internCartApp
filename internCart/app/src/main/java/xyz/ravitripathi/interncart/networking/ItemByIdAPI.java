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
 * Created by ravi on 17/01/18.
 */

public interface ItemByIdAPI {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.177.7.117:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("/search/getOne/{prodID}")
    Call<ProductPOJO> searchById(@Path("prodID")String prodID);

}
