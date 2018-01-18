package xyz.ravitripathi.interncart.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import xyz.ravitripathi.interncart.R;
import xyz.ravitripathi.interncart.activity.ProductDetailActivity;
import xyz.ravitripathi.interncart.pojo.ProductPOJO;

/**
 * Created by ravi on 15/01/18.
 */


public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.VH> {
    private static Context context;
    private List<ProductPOJO> prodList;

    public ProductRecyclerAdapter(Context context, List<ProductPOJO> prodList) {
        this.prodList = prodList;
        this.context = context;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
        VH holder = new VH(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        Log.d("Adapter", "Holder called");
        holder.setData(prodList.get(position));
    }

    @Override
    public int getItemCount() {
        return prodList.size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        TextView name, price, brand, unit;
        ImageView image;

        public VH(View itemView) {
            super(itemView);
            Log.d("Adapter", "VH called");
            image = itemView.findViewById(R.id.prodImage);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            brand = itemView.findViewById(R.id.brand);
            unit = itemView.findViewById(R.id.unit);
        }

        public void setData(final ProductPOJO data) {
            Log.d("Adapter", "Data set");
            name.setText(data.getpName());
            price.setText(String.valueOf(data.getpPrice()));
            brand.setText(data.getpBrand());
            unit.setText("Available Units : ".concat(String.valueOf(data.getpUnit())));

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.ic_log_out);


            Glide.with(context)
                    .load(data.getPimage())
                    .apply(options)
                    .into(image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, ProductDetailActivity.class);
                    i.putExtra("id", data.getProductId());
                    context.startActivity(i);
                }
            });

        }

    }


}
