package xyz.ravitripathi.interncart.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.ravitripathi.interncart.R;
import xyz.ravitripathi.interncart.networking.AddToCartAPI;
import xyz.ravitripathi.interncart.networking.ItemByIdAPI;
import xyz.ravitripathi.interncart.pojo.CartPOJO;
import xyz.ravitripathi.interncart.pojo.CartResponePOJO;
import xyz.ravitripathi.interncart.pojo.ProductPOJO;

public class ProductDetailActivity extends AppCompatActivity {

    TextView name, price, brand;
    Spinner itemSelect;
    Button b;
    ImageView image;
    String prodID, uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Intent i = getIntent();
        prodID = i.getStringExtra("id");
        SharedPreferences sharedPref = getApplicationContext()
                .getSharedPreferences("shared", 0);
        uid = sharedPref.getString("uid", "0");
        bindViews();
        if (prodID != null) {
            getItemDetails(prodID);
        }
    }

    private void bindViews() {
        name = findViewById(R.id.dname);
        name.setSelected(true);
        price = findViewById(R.id.dprice);
        brand = findViewById(R.id.dbrand);
        itemSelect = findViewById(R.id.itemSelect);
        image = findViewById(R.id.dprodImage);
        b = findViewById(R.id.dbuy);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uid.equals("0")) {
                    Toast.makeText(ProductDetailActivity.this, "Please Login to continue", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(ProductDetailActivity.this, LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(i);
                } else
                    addToCart(prodID, uid);
            }
        });
    }

    private void addToCart(String prodID, String uid) {

        //TODO:   NEXT LINE
        //uid = "1";
        String purchaseUnit = "1";
        final AddToCartAPI search = AddToCartAPI.retrofit.create(AddToCartAPI.class);
        try {
            purchaseUnit = itemSelect.getSelectedItem().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Call<CartResponePOJO> call = search.addtoCart(new CartPOJO(uid, prodID, purchaseUnit));
        call.enqueue(new Callback<CartResponePOJO>() {
            @Override
            public void onResponse(Call<CartResponePOJO> call, Response<CartResponePOJO> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(ProductDetailActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ProductDetailActivity.this, MainActivity.class));
                } else {
                    if (response != null) {

                        Toast.makeText(ProductDetailActivity.this, String.valueOf(response.code()), Toast.LENGTH_SHORT).show();

                        if (response.code() == 406) {
                            new FancyGifDialog.Builder(ProductDetailActivity.this)
                                    .setTitle("Cart is Full!")
                                    .setMessage("You've already filled 4 items in your cart")
                                    .setNegativeBtnText("Cancel")
                                    .setPositiveBtnBackground("#FF4081")
                                    .setPositiveBtnText("Go back to Home")
                                    .setNegativeBtnBackground("#FFA9A7A8")
                                    .setGifResource(R.drawable.cartfull)   //Pass your Gif here
                                    .isCancellable(true)
                                    .OnPositiveClicked(new FancyGifDialogListener() {
                                        @Override
                                        public void OnClick() {
                                            ProductDetailActivity.this.onBackPressed();
                                        }
                                    })
                                    .OnNegativeClicked(new FancyGifDialogListener() {
                                        @Override
                                        public void OnClick() {
                                            Toast.makeText(ProductDetailActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .build();

                        }
                        Toast.makeText(ProductDetailActivity.this, "You already have 4 items!", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(ProductDetailActivity.this, "Null response", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<CartResponePOJO> call, Throwable t) {
                Toast.makeText(ProductDetailActivity.this, "FAILED", Toast.LENGTH_SHORT).show();
                Log.d("RESULT", "FAILED");
            }
        });
    }

    private void setViews(Response<ProductPOJO> response) {
        name.setText(response.body().getpName());
        price.setText(String.valueOf(response.body().getpPrice()));
        brand.setText(response.body().getpBrand());
        int u = response.body().getpUnit();
        String units = String.valueOf(u);


        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_log_out);


        Glide.with(this)
                .load(response.body().getPimage())
                .apply(options)
                .into(image);


        ArrayList<String> values = new ArrayList<>();
        for (int i = 0; i < u; i++)
            values.add("" + (i + 1));


        try {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            itemSelect.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getItemDetails(String id) {
        final ItemByIdAPI get = ItemByIdAPI.retrofit.create(ItemByIdAPI.class);
        Call<ProductPOJO> call = get.searchById(id);
        call.enqueue(new Callback<ProductPOJO>() {
            @Override
            public void onResponse(Call<ProductPOJO> call, Response<ProductPOJO> response) {
                setViews(response);
            }

            @Override
            public void onFailure(Call<ProductPOJO> call, Throwable t) {
                Toast.makeText(ProductDetailActivity.this, "Failed to load item", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ProductDetailActivity.this, MainActivity.class));
            }
        });
    }


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
