package xyz.ravitripathi.interncart;

import android.app.Application;
import android.content.Context;

/**
 * Created by ravi on 15/01/18.
 */

public class App extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}
