package it.jaschke.alexandria.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;

import com.google.zxing.integration.android.IntentIntegrator;

/**
 * This class is used as a wrapper over actual {@link IntentIntegrator}.
 * Created by pabhinav
 */
public class MyIntentIntegrator extends IntentIntegrator {

    private final Activity activity;
    private android.app.Fragment fragment;
    private android.support.v4.app.Fragment supportFragment;

    /**
     * @param activity {@link Activity} invoking the integration
     */
    public MyIntentIntegrator(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    /**
     * @param fragment {@link Fragment} invoking the integration.
     *                 {@link #startActivityForResult(Intent, int)} will be called on the {@link Fragment} instead
     *                 of an {@link Activity}
     */
    public static IntentIntegrator forSupportFragment(android.support.v4.app.Fragment fragment) {
        MyIntentIntegrator integrator = new MyIntentIntegrator(fragment.getActivity());
        integrator.supportFragment = fragment;
        return integrator;
    }

    /**
     * @param fragment {@link Fragment} invoking the integration.
     *                 {@link #startActivityForResult(Intent, int)} will be called on the {@link Fragment} instead
     *                 of an {@link Activity}
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static IntentIntegrator forFragment(Fragment fragment) {
        MyIntentIntegrator integrator = new MyIntentIntegrator(fragment.getActivity());
        integrator.fragment = fragment;
        return integrator;
    }

    @Override
    protected void startActivityForResult(Intent intent, int code) {
        if (fragment != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                fragment.startActivityForResult(intent, 0);
            }
        } else if (supportFragment != null) {
            supportFragment.startActivityForResult(intent, 0);
        } else {
            activity.startActivityForResult(intent, 0);
        }
    }
}
