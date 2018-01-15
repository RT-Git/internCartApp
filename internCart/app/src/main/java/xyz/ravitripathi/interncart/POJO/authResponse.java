package xyz.ravitripathi.interncart.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ravi on 15/01/18.
 */

public class authResponse {
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @SerializedName("uid")
    @Expose
    private String uid;
}
