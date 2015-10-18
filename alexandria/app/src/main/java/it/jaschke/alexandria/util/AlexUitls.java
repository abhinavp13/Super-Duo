package it.jaschke.alexandria.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * This class holds logic for static util functions used throughout application.
 *
 * Created by pabhinav
 */
public class AlexUitls {

    /** Overriding instantiation for this class **/
    private AlexUitls(){}

    /**
     * This function checks for internet connectivity given activity context
     *
     * @param context
     * @return
     */
    public static boolean checkConnectivity(Context context){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }
}
