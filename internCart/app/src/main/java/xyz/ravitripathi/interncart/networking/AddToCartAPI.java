package xyz.ravitripathi.interncart.networking;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import xyz.ravitripathi.interncart.pojo.CartPOJO;
import xyz.ravitripathi.interncart.pojo.CartResponePOJO;

import static xyz.ravitripathi.interncart.Constants.base_url;

/**
 * Created by ravi on 18/01/18.
 */

public interface AddToCartAPI {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.177.7.121:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @POST("/cart/addToCart")
    Call<CartResponePOJO> addtoCart(@Body CartPOJO body);

}
