package xyz.ravitripathi.interncart.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by ravi on 15/01/18.
 */


public class productPOJO {

    @SerializedName("productId")
    @Expose
    private String productId;

    @SerializedName("pName")
    @Expose
    private String pName;

    @SerializedName("pPrice")
    @Expose
    private double pPrice;

    @SerializedName("pBrand")
    @Expose
    private String pBrand;

    @SerializedName("pCategory")
    @Expose
    private String pCategory;

    @SerializedName("pimage")
    @Expose
    private String pimage;

    @SerializedName("pUnit")
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