package xyz.ravitripathi.interncart.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;



import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import xyz.ravitripathi.interncart.R;
import xyz.ravitripathi.interncart.pojo.ProductPOJO;

/**
 * Created by ravi on 15/01/18.
 */


public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.VH> {
    private List<ProductPOJO> prodList;
    private static Context context;

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
        Log.d("Adapter","Holder called");
        holder.setData(prodList.get(position));
    }

    @Override
    public int getItemCount() {
        return prodList.size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        TextView name,price,brand,category,unit;
        ImageView image;

        public VH(View itemView) {
            super(itemView);
            Log.d("Adapter","VH called");
            image = itemView.findViewById(R.id.prodImage);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            brand = itemView.findViewById(R.id.brand);
            category = itemView.findViewById(R.id.cat);
            unit = itemView.findViewById(R.id.unit);
        }

        public void setData(final ProductPOJO data) {
            Log.d("Adapter","Data set");
            name.setText(data.getpName());
            price.setText(String.valueOf(data.getpPrice()));
            brand.setText(data.getpBrand());
            category.setText(data.getpCategory());
            unit.setText(String.valueOf(data.getpUnit()));

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.ic_log_out);

            Glide.with(context)
                    .load(data.getPimage())
                    .apply(options)
                    .into(image);

        }

    }


}
