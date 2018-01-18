package xyz.ravitripathi.interncart.pojo;

/**
 * Created by ravi on 18/01/18.
 */

public class CartPOJO {
    final String userId;
    final String productId;
    final String purchaseUnit;


    public CartPOJO(String userId, String productId) {
        this.userId = userId;
        this.productId = productId;
        this.purchaseUnit = "1";
    }
}
