package xyz.ravitripathi.interncart.pojo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ravi on 19/01/18.
 */

public class PlaceOrderPOJO {
    private String uId;
    private Map<String, ProductInfoPOJO> productInfos = new HashMap<>();


    public PlaceOrderPOJO(String uId,Map<String,ProductInfoPOJO> productInfos) {
        this.uId = uId;
        this.productInfos = productInfos;

         }
}
