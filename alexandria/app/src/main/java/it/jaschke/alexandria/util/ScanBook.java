package it.jaschke.alexandria.util;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;


/**
 * This class calls zxing library, which in turn helps app use camera
 * to scan barcode for book.
 *
 * Created by pabhinav
 */
public class ScanBook {

    /**
     * Logger for this class.
     */
    private final Logger logger = Logger.getInstance("ScanBook");

    /**
     * This could be a {@link Context} or {@link android.support.v4.app.Fragment}
     */
    private Object scanBindObject;

    /**
     * Activity linked with {@code scanBindObject}
     */
    private Activity activity;

    /**
     * Constructor for initiating scan based on context provided.
     * @param context
     */
    public ScanBook (Context context){
        scanBindObject = context;
    }

    /**
     * Constructor for initiating scan based on fragment provided.
     * @param fragment
     */
    public ScanBook (Fragment fragment){
        scanBindObject = fragment;
    }

    /**
     * This function initiate camera scan
     * using {@link Context} or {@link Fragment} object.
     *
     * @throws Exception
     */
    public void initiateScan() throws Exception {

        /** need to build activity **/
//        buildActivity();

        /** Start the intent for calling external scanning app **/
//        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
//        activity.startActivityForResult(intent, 0);

        /** Calling built-in zxing library for scanning books **/
        /** If binding object is null, throw exception **/
        if(scanBindObject == null){
            AppUnRecoverableException exception = new AppUnRecoverableException("Scan Bind Object is Null");
            logger.error("Cannot initiate Scan",exception);
            throw exception;
        }

        if(scanBindObject instanceof Fragment){
            logger.info("Will initiate camera scan for fragment");
            MyIntentIntegrator.forSupportFragment((Fragment)scanBindObject).initiateScan();
        } else if (scanBindObject instanceof Context){
            logger.info("Will Initiate camera scan for activity");
            new MyIntentIntegrator((Activity)scanBindObject).initiateScan();
        } else {
            AppUnRecoverableException exception = new AppUnRecoverableException("Cannot Initiate Scan. Scan Bind Object is neither instanceof Fragment nor an activity context");
            logger.error("Cannot initiate Scan",exception);
            throw exception;
        }

    }

    /**
     * This function builds activity linked with {@code scanBindObject}.
     */
    public void buildActivity() throws Exception{

        /** If binding object is null, throw exception **/
        if(scanBindObject == null){
            AppUnRecoverableException exception = new AppUnRecoverableException("Scan Bind Object is Null");
            logger.error("Cannot initiate Scan", exception);
            throw exception;
        }

        if(scanBindObject instanceof Fragment){
            logger.info("Will initiate camera scan for fragment");
            activity = ((Fragment) scanBindObject).getActivity();
        } else if (scanBindObject instanceof Context){
            logger.info("Will Initiate camera scan for activity");
            activity = (Activity)scanBindObject;
        } else {
            AppUnRecoverableException exception = new AppUnRecoverableException("Cannot Initiate Scan. Scan Bind Object is neither instanceof Fragment nor an activity context");
            logger.error("Cannot initiate Scan", exception);
            throw exception;
        }
    }
}
