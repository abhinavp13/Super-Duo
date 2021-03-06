package it.jaschke.alexandria;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import it.jaschke.alexandria.api.Callback;
import it.jaschke.alexandria.application.AlexApplication;
import it.jaschke.alexandria.services.BookService;
import it.jaschke.alexandria.util.AlexUitls;


public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks, Callback {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment navigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence title;
    private BroadcastReceiver messageReceiver;
    private final static String BOOK_DETAIL_FRAGMENT_ID = "Book Detail";
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    public static final String MESSAGE_EVENT = "MESSAGE_EVENT";
    public static final String MESSAGE_KEY = "MESSAGE_EXTRA";
    private boolean onceHiddenPlaceHolder = false;

    /**
     * Fragment Loaded.
     */
    Fragment nextFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Below line fixes the bug found in UI.
         * Bug : Whenever drawer is opened with
         * keyboard displaying on the screen,
         * the keyboard does not hide itself
         * keyboard stays there even with drawer
         * opened.
         */
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        if(isTablet()){
            setContentView(R.layout.activity_main_tablet);
        }else {
            setContentView(R.layout.activity_main);
        }

        /**
         * Bug fixed :
         * If tablet screen is displayed, and {@link BookDetail} is on top of UI,
         * then on orientation change, left container and right container both
         * displays same {@link BookDetail} fragments.
         * Fixed this problem. If nextFragment, which will be displayed in left
         * half of screen in landscape tablet mode, is {@link BookDetail}, then
         * nextFragment is changed to {@link ListOfBooks}.
         */
        if(findViewById(R.id.right_container) != null){
            if(nextFragment != null && nextFragment instanceof BookDetail){
                nextFragment = new ListOfBooks();
            }
        }

        messageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter(MESSAGE_EVENT);
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver,filter);

        navigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        /**
         * Bug fixed :
         * Title was becoming "Alexandria" when orientation was changed.
         */
        if(title == null) {
            title = getTitle();
        }

        // Set up the drawer.
        navigationDrawerFragment.setUp(R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        /**
         * Bug UI :
         * For Tablet,
         * We are able to see screen divided into 2 for all type of
         * fragments.
         * We need not divide for {@link AddBook} and {@link About}.
         */
        switch (position){
            default:
            case 0:
                nextFragment = new ListOfBooks();
                if(findViewById(R.id.right_container) != null){
                    findViewById(R.id.right_container).setVisibility(View.VISIBLE);
                }
                break;
            case 1:
                nextFragment = new AddBook();
                if(findViewById(R.id.right_container) != null){
                    findViewById(R.id.right_container).setVisibility(View.GONE);
                }
                break;
            case 2:
                nextFragment = new About();
                if(findViewById(R.id.right_container) != null){
                    findViewById(R.id.right_container).setVisibility(View.GONE);
                }
                break;
        }

        fragmentManager.beginTransaction()
                .replace(R.id.container, nextFragment)
                .addToBackStack((String) title)
                .commit();
    }

    public void setTitle(int titleId) {
        title = getString(titleId);
    }

    @SuppressWarnings("deprecation")
    private void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(title);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (!navigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }

        /** Need to hide if shown share menu item **/
        if(AlexApplication.getShareMenuItem() != null) {
            AlexApplication.getShareMenuItem().setVisible(false);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver);
        super.onDestroy();
    }

    @Override
    public void onItemSelected(String ean) {
        Bundle args = new Bundle();
        args.putString(BookDetail.EAN_KEY, ean);

        BookDetail fragment = new BookDetail();
        fragment.setArguments(args);

        int id = R.id.container;

        /**
         * Need to set place holder text visibility.
         */
        if(findViewById(R.id.right_container) != null){
            if(findViewById(R.id.place_holder_text_view_for_selected_book) != null){
                findViewById(R.id.place_holder_text_view_for_selected_book).setVisibility(View.INVISIBLE);
                onceHiddenPlaceHolder = true;
            }
            id = R.id.right_container;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(id, fragment)
                .addToBackStack(BOOK_DETAIL_FRAGMENT_ID)
                .commit();

    }

    private class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getStringExtra(MESSAGE_KEY)!=null){
                Toast.makeText(MainActivity.this, intent.getStringExtra(MESSAGE_KEY), Toast.LENGTH_LONG).show();
            }
        }
    }

    @SuppressWarnings("UnusedParameters")
    public void goBack(View view){
        getSupportFragmentManager().popBackStack();
    }

    private boolean isTablet() {
        return (getApplicationContext().getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    @Override
    public void onBackPressed() {
        /**
         * UI Bug :
         * Goes back even when navigation drawer is left opened.
         * Also, need not go back too much.
         * Just exit if navigation drawer is closed and back is pressed,
         * unless book detail ui is displayed.
         */

        if(navigationDrawerFragment!=null && navigationDrawerFragment.isDrawerOpen()){
                navigationDrawerFragment.closeDrawer();
                return;
        }

        /**
         * Check if the fragment currently shown is {@link BookDetail} fragment.
         * If it is {@link BookDetail}, then return to previous fragment,
         * else finish the activity.
         */
        if(getSupportFragmentManager() != null) {
            FragmentManager.BackStackEntry backEntry=getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount()-1);
            if(backEntry.getName().equals(BOOK_DETAIL_FRAGMENT_ID)){
                super.onBackPressed();
            } else {
                finish();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "nextFragment", nextFragment);
        outState.putBoolean("onceHiddenPlaceHolder", onceHiddenPlaceHolder);
    }

    @Override
    public void onRestoreInstanceState(Bundle inState){
        nextFragment = getSupportFragmentManager().getFragment(inState,"nextFragment");

        /**
         * If right container and next fragment exist,
         * sets its visibility.
         */
        if(findViewById(R.id.right_container) != null){
            if(nextFragment != null){

                /** If right container is present and book detail is the next Fragment, change it to list of books instance. **/
                if(nextFragment instanceof ListOfBooks){
                    onNavigationDrawerItemSelected(0);
                }

                if(nextFragment instanceof About || nextFragment instanceof  AddBook){
                    findViewById(R.id.right_container).setVisibility(View.GONE);
                } else if(nextFragment instanceof ListOfBooks){
                    findViewById(R.id.right_container).setVisibility(View.VISIBLE);
                }
            }
        }

        /**
         * Fetch the boolean variable which denotes whether place holder text view is hidden or not.
         */
        if(inState.getBoolean("onceHiddenPlaceHolder")){
            onceHiddenPlaceHolder = inState.getBoolean("onceHiddenPlaceHolder");
            if(findViewById(R.id.right_container) != null) {
                if (findViewById(R.id.place_holder_text_view_for_selected_book) != null) {
                    findViewById(R.id.place_holder_text_view_for_selected_book).setVisibility(View.INVISIBLE);
                    onceHiddenPlaceHolder = true;
                }
            }
        }
        super.onRestoreInstanceState(inState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("Request Code : ", String.valueOf(requestCode));
        String contents = null;
        String format = null;
        if(resultCode == RESULT_OK){
            contents = data.getStringExtra("SCAN_RESULT");
            format = data.getStringExtra("SCAN_RESULT_FORMAT");

            Log.d("MainActivity", "Scanned : " + contents);
            Toast.makeText(this, "Scanned : " + contents, Toast.LENGTH_SHORT).show();

        } else if (resultCode == RESULT_CANCELED) {
            Log.d("MainActivity", "Cancelled scan");
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            return;
        }
        if(format != null && contents != null && format.equals("EAN_13")){

            /** Just set the edit text value in the fragment, text watcher will take care of rest. **/
            if(nextFragment instanceof AddBook){
                ((AddBook)nextFragment).setEanEditText(contents);
            }
        } else {
            Log.d("MainActivity", "Not a book barcode scanned " + contents + " format : " + format);
            Toast.makeText(this, "Not a book barcode !", Toast.LENGTH_LONG).show();
        }
    }
}