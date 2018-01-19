package xyz.ravitripathi.interncart.networking;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import xyz.ravitripathi.interncart.pojo.OrderPOJO;
import xyz.ravitripathi.interncart.pojo.PlaceOrderPOJO;
import xyz.ravitripathi.interncart.pojo.ProductPOJO;

import static xyz.ravitripathi.interncart.Constants.order_url;
import static xyz.ravitripathi.interncart.Constants.search_url;

/**
 * Created by ravi on 19/01/18.
 */

public interface CheckoutAPI {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(order_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @POST("/order/add/")
    Call<OrderPOJO> checkout(@Body PlaceOrderPOJO obj);
}
