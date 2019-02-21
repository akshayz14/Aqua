package abc.akshay.com.aqua;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

/**
 * Created by akshay on 06/06/18
 */
public class Aqua extends MultiDexApplication {

    private static Context mContext;

    private static Application sInstance;

    public static Context getAppContext() {
        return mContext;
    }


    public static Application getInstance() {
        return sInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Aqua.mContext = getApplicationContext();
        sInstance = this;
    }



}
