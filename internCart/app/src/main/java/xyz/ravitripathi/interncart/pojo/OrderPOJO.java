package xyz.ravitripathi.interncart.pojo;

/**
 * Created by ravi on 18/01/18.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderPOJO {

    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("uId")
    @Expose
    private String uId;
    @SerializedName("date")
    @Expose
    private long date;
    @SerializedName("products")
    @Expose
    private List<ProductPOJO> products;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUId() {
        return uId;
    }

    public void setUId(String uId) {
        this.uId = uId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public List<ProductPOJO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductPOJO> products) {
        this.products = products;
    }

}
