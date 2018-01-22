package xyz.ravitripathi.interncart.pojo;

/**
 * Created by ravi on 16/01/18.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductPOJO {

    @SerializedName("productId")
    @Expose
    private String productId;
    @SerializedName("productName")
    @Expose
    private String pName;
    @SerializedName("productPrice")
    @Expose
    private double pPrice;
    @SerializedName("productBrand")
    @Expose
    private String pBrand;
    @SerializedName("productCategory")
    @Expose
    private String pCategory;
    @SerializedName("productImage")
    @Expose
    private String pimage;
    @SerializedName("productUnit")
    @Expose
    private int pUnit;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public double getpPrice() {
        return pPrice;
    }

    public void setpPrice(double pPrice) {
        this.pPrice = pPrice;
    }

    public String getpBrand() {
        return pBrand;
    }

    public void setpBrand(String pBrand) {
        this.pBrand = pBrand;
    }

    public String getpCategory() {
        return pCategory;
    }

    public void setpCategory(String pCategory) {
        this.pCategory = pCategory;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }

    public int getpUnit() {
        return pUnit;
    }

    public void setpUnit(int pUnit) {
        this.pUnit = pUnit;
    }
}
