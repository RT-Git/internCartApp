package xyz.ravitripathi.interncart.networking;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import xyz.ravitripathi.interncart.pojo.OrderPOJO;

import static xyz.ravitripathi.interncart.Constants.order_url;

/**
 * Created by ravi on 18/01/18.
 */

public interface OrdersAPI {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(order_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("/order/history/{uid}")
    Call<List<OrderPOJO>> getOrders(@Path("uid")String uid);

}
