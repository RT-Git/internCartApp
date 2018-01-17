package xyz.ravitripathi.interncart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.ravitripathi.interncart.R;
import xyz.ravitripathi.interncart.networking.ItemByIdAPI;
import xyz.ravitripathi.interncart.pojo.ProductPOJO;

public class ProductDetailActivity extends AppCompatActivity {

    TextView name, price, brand, unit;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Intent i = getIntent();
        String prodID = i.getStringExtra("id");
        bindViews();
        if (prodID != null) {
            getItemDetails(prodID);
        }
    }

    private void bindViews() {
        name = findViewById(R.id.dname);
        price = findViewById(R.id.dprice);
        brand = findViewById(R.id.dbrand);
        unit = findViewById(R.id.dunit);
        image = findViewById(R.id.dprodImage);
    }


    private void setViews(Response<ProductPOJO> response) {
        name.setText(response.body().getpName());
        price.setText(String.valueOf(response.body().getpPrice()));
        brand.setText(response.body().getpBrand());
        unit.setText(String.valueOf(response.body().getpUnit()));

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_log_out);

        Glide.with(this)
                .load(response.body().getPimage())
                .apply(options)
                .into(image);

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


}
