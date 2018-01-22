package xyz.ravitripathi.interncart.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import xyz.ravitripathi.interncart.R;
import xyz.ravitripathi.interncart.activity.MainActivity;
import xyz.ravitripathi.interncart.activity.ProductDetailActivity;
import xyz.ravitripathi.interncart.pojo.CartPOJO;
import xyz.ravitripathi.interncart.pojo.ProductPOJO;

/**
 * Created by ravi on 15/01/18.
 */


public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.VH> {
    private static Context context;
    private List<ProductPOJO> prodList;
    String userid = "1";

    public ProductRecyclerAdapter(Context context, List<ProductPOJO> prodList) {
        this.prodList = prodList;
        this.context = context;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
        VH holder = new VH(view);
        context = holder.itemView.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.setData(prodList.get(position));
    }

    @Override
    public int getItemCount() {
        return prodList.size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        TextView name, price, brand, unit;
        Button removeItem;
        ImageView image;

        public VH(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.prodImage);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            brand = itemView.findViewById(R.id.brand);
            unit = itemView.findViewById(R.id.unit);
            removeItem = itemView.findViewById(R.id.remove);
        }

        public void setData(final ProductPOJO data) {
            name.setText(data.getpName());
            price.setText(String.valueOf(data.getpPrice()));
            brand.setText(data.getpBrand());

            if (context instanceof MainActivity) {
                unit.setText("Available Units : ".concat(String.valueOf(data.getpUnit())));
//                removeItem.setVisibility(View.GONE);
            } else {
                unit.setText("Added Items: ".concat(String.valueOf(data.getpUnit())));
//                removeItem.setVisibility(View.VISIBLE);
            }

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

//            removeItem.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    CartPOJO cartPOJO = new CartPOJO("1",data.getProductId(),String.valueOf(data.getpUnit()));
//                    clearItem(cartPOJO);
//                }
//            });
        }

    }


}
