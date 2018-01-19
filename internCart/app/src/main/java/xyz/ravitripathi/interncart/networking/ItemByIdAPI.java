package xyz.ravitripathi.interncart.networking;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import xyz.ravitripathi.interncart.pojo.ProductPOJO;

import static xyz.ravitripathi.interncart.Constants.search_url;

/**
 * Created by ravi on 17/01/18.
 */

public interface ItemByIdAPI {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(search_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("/search/getOne/{prodID}")
    Call<ProductPOJO> searchById(@Path("prodID") String prodID);

}
