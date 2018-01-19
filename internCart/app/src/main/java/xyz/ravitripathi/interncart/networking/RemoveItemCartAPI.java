package xyz.ravitripathi.interncart.networking;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import xyz.ravitripathi.interncart.pojo.CartPOJO;

import static xyz.ravitripathi.interncart.Constants.cart_url;

/**
 * Created by ravi on 19/01/18.
 */

public interface RemoveItemCartAPI {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(cart_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @POST("/cart/remove")
    Call<String> clear(@Body CartPOJO cartPOJO);
}
