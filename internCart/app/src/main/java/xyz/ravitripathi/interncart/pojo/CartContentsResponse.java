package xyz.ravitripathi.interncart.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ravi on 18/01/18.
 */

public class CartContentsResponse {
    @SerializedName("cartID")
    @Expose
    private String cartID;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("productId")
    @Expose
    private String productId;
    @SerializedName("purchaseUnit")
    @Expose
    private int purchaseUnit;
    @SerializedName("productDTOList")
    @Expose
    private List<ProductPOJO> productDTOList;

    public String getCartID() {
        return cartID;
    }

    public void setCartID(String cartID) {
        this.cartID = cartID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getPurchaseUnit() {
        return purchaseUnit;
    }

    public void setPurchaseUnit(int purchaseUnit) {
        this.purchaseUnit = purchaseUnit;
    }

    public List<ProductPOJO> getProductDTOList() {
        return productDTOList;
    }

    public void setProductDTOList(List<ProductPOJO> productDTOList) {
        this.productDTOList = productDTOList;
    }

}
