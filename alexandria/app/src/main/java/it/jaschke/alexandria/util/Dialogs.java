package it.jaschke.alexandria.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

/**
 * All types of dialogs can be registered here.
 *
 * Created by pabhinav
 */
public class Dialogs {

    /** Breaking default constructor **/
    private Dialogs(){ }

    /**
     * No Internet Connection is found.
     * This function creates alert dialog for such a situation.
     *
     * @param context
     * @param cancelable
     */
    public static void showNoInternetConnectionAlertDialog(final Context context, boolean cancelable){

        // Dialog for no internet connection :
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("No Internet Connection Found");
        alertDialog.setMessage("Please Check Your Internet Connection and then try again");
        alertDialog.setCancelable(cancelable);
        alertDialog.setButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                context.startActivity(intent);
                alertDialog.hide();
            }
        });
        alertDialog.show();
    }
}
