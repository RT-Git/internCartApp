package xyz.ravitripathi.interncart.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import xyz.ravitripathi.interncart.networking.GetCartAPI;
import xyz.ravitripathi.interncart.pojo.ProductPOJO;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String userID;
    private Context c;
    private ProductRecyclerAdapter productRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        c = this;
        bindViews();
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String defaultValue = null;
        userID = sharedPref.getString("uid", defaultValue);
        fetchCart(userID);
    }

    private void bindViews() {
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(c, 2));
    }


    private void fetchCart(String uid) {
        final GetCartAPI search = GetCartAPI.retrofit.create(GetCartAPI.class);
        Call<List<ProductPOJO>> call = search.getUserCart(uid);
        call.enqueue(new Callback<List<ProductPOJO>>() {
            @Override
            public void onResponse(Call<List<ProductPOJO>> call, Response<List<ProductPOJO>> response) {
                try {
                    if (!response.body().isEmpty()) {
                        productRecyclerAdapter = new ProductRecyclerAdapter(CartActivity.this, response.body());
                        recyclerView.setAdapter(productRecyclerAdapter);
                    } else {

                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<ProductPOJO>> call, Throwable t) {
                Toast.makeText(CartActivity.this, "FAILED", Toast.LENGTH_SHORT).show();
                Log.d("RESULT", "FAILED");
            }
        });
    }
}
