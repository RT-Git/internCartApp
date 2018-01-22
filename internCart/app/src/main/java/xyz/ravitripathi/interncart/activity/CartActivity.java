package xyz.ravitripathi.interncart.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.ravitripathi.interncart.R;
import xyz.ravitripathi.interncart.adapters.ProductRecyclerAdapter;
import xyz.ravitripathi.interncart.networking.CheckoutAPI;
import xyz.ravitripathi.interncart.networking.GetCartAPI;
import xyz.ravitripathi.interncart.pojo.CartContentsResponse;
import xyz.ravitripathi.interncart.pojo.OrderPOJO;
import xyz.ravitripathi.interncart.pojo.PlaceOrderPOJO;
import xyz.ravitripathi.interncart.pojo.ProductInfoPOJO;
import xyz.ravitripathi.interncart.pojo.ProductPOJO;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String userID;
    private Context c;
    private ProductRecyclerAdapter productRecyclerAdapter;
    private CartContentsResponse cartContentsResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        c = this;
        bindViews();
        SharedPreferences sharedPref = getApplicationContext()
                .getSharedPreferences("shared", 0);
        String uidFromStorage = sharedPref.getString("uid", "0");
        if ("0".equals(uidFromStorage)) {

        } else
            fetchCart(uidFromStorage);
    }

    private void bindViews() {
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(c, 2));
    }


    private void fetchCart(String uid) {
        Toast.makeText(this,uid,Toast.LENGTH_SHORT).show();
        final GetCartAPI search = GetCartAPI.retrofit.create(GetCartAPI.class);
        Call<CartContentsResponse> call = search.getUserCart(uid);
        call.enqueue(new Callback<CartContentsResponse>() {
            @Override
            public void onResponse(Call<CartContentsResponse> call, Response<CartContentsResponse> response) {
                try {

                    if (response.body() != null) {
                        cartContentsResponse = response.body();
                        productRecyclerAdapter = new ProductRecyclerAdapter(CartActivity.this, response.body().getProductDTOList());
                        recyclerView.setAdapter(productRecyclerAdapter);
                    } else {
                        Toast.makeText(CartActivity.this, "Response is null", Toast.LENGTH_SHORT).show();

                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CartContentsResponse> call, Throwable t) {
                Toast.makeText(CartActivity.this, "FAILED", Toast.LENGTH_SHORT).show();
                Log.d("RESULT", "fetchCart FAILED");
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.check:
                checkOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void checkOut() {

        final CheckoutAPI out = CheckoutAPI.retrofit.create(CheckoutAPI.class);


        Map<String, ProductInfoPOJO> productInfoPOJOMap = new HashMap<>();

        for (ProductPOJO i : cartContentsResponse.getProductDTOList()) {
            ProductInfoPOJO p = new ProductInfoPOJO(String.valueOf(i.getpUnit()), String.valueOf(i.getpPrice()));
            productInfoPOJOMap.put(i.getProductId(), p);
        }

        Call<OrderPOJO> call = out.checkout(new PlaceOrderPOJO(cartContentsResponse.getUserId(), productInfoPOJOMap));
        Gson gson = new Gson();
        String json = gson.toJson(new PlaceOrderPOJO(cartContentsResponse.getUserId(), productInfoPOJOMap));
        Log.d("SENTJSON: ", json);
        call.enqueue(new Callback<OrderPOJO>() {
            @Override
            public void onResponse(Call<OrderPOJO> call, Response<OrderPOJO> response) {

                if (response.isSuccessful()) {
                    Log.e("Success", String.valueOf(response.code()));
                    try {
                        Toast.makeText(CartActivity.this, "Checkout done", Toast.LENGTH_SHORT).show();
                        new FancyGifDialog.Builder(CartActivity.this)
                                .setTitle("Done!")
                                .setMessage("Checkout complete!")
                                .setPositiveBtnBackground("#FF4081")
                                .setPositiveBtnText("Go back to Home")
                                .setGifResource(R.drawable.checkout_done)   //Pass your Gif here
                                .isCancellable(true)
                                .OnPositiveClicked(new FancyGifDialogListener() {
                                    @Override
                                    public void OnClick() {
                                        CartActivity.this.onBackPressed();
                                    }
                                })
                                .build();

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (response.code() == 401) {
                        Log.e("Not success", String.valueOf(response.code()));
                        Toast.makeText(CartActivity.this, "Sorry, this user does not exits", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("Not success", String.valueOf(response.code()));
                        Toast.makeText(CartActivity.this, "response in not successful", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<OrderPOJO> call, Throwable t) {
                Log.e("FAIL", "checkout FAIL");
                Toast.makeText(CartActivity.this, "Call Failure", Toast.LENGTH_SHORT);
            }
        });
    }

//    public void clearCart() {
//        final ClearCartAPI out = ClearCartAPI.retrofit.create(ClearCartAPI.class);
//        CartPOJO cartPOJO = new CartPOJO(cartContentsResponse.getUserId(), "","");
//        Call<String> call = out.clear(cartPOJO);
//        Gson gson = new Gson();
//        String json = gson.toJson(cartPOJO);
//        Log.d("SENTJSONclear: ", json);
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if (response.isSuccessful())
//                    Toast.makeText(CartActivity.this, "Cart Cleared", Toast.LENGTH_LONG).show();
////                else
////                    Toast.makeText(CartActivity.this, response.code(), Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Log.e("ClearAPI", "clearCart failed");
//            }
//        });
//    }
}
