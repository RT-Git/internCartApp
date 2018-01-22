package xyz.ravitripathi.interncart.networking;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import xyz.ravitripathi.interncart.pojo.CartContentsResponse;

import static xyz.ravitripathi.interncart.Constants.cart_url;

/**
 * Created by ravi on 18/01/18.
 */

public interface GetCartAPI {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(cart_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("/cart/getByUid/{userId}")
    Call<CartContentsResponse> getUserCart(@Path("userId") String uid);

}
