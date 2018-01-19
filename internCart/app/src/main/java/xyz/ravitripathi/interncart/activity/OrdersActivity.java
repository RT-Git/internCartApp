package xyz.ravitripathi.interncart.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.ravitripathi.interncart.R;
import xyz.ravitripathi.interncart.adapters.ProductRecyclerAdapter;
import xyz.ravitripathi.interncart.networking.OrdersAPI;
import xyz.ravitripathi.interncart.pojo.OrderPOJO;

public class OrdersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String userID;
    private Context c;
    private ProductRecyclerAdapter productRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        c = this;
        bindViews();
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String defaultValue = null;
        userID = sharedPref.getString("uid", defaultValue);
        fetchCart("4e6c7a99-a14a-407f-a7b9-cddfbd6190f9");
    }

    private void bindViews() {
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(c, 2));
    }


    private void fetchCart(String uid) {
        final OrdersAPI search = OrdersAPI.retrofit.create(OrdersAPI.class);
        Call<List<OrderPOJO>> call = search.getOrders(uid);
        call.enqueue(new Callback<List<OrderPOJO>>() {
            @Override
            public void onResponse(Call<List<OrderPOJO>> call, Response<List<OrderPOJO>> response) {
                try {

                    if (response.body()!=null) {
                        productRecyclerAdapter =
                                new ProductRecyclerAdapter(OrdersActivity.this, response.body().get(0).getProducts());
                        recyclerView.setAdapter(productRecyclerAdapter);
                    } else {
                        Toast.makeText(OrdersActivity.this,"Response is null", Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<OrderPOJO>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(OrdersActivity.this, "FAILED", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
