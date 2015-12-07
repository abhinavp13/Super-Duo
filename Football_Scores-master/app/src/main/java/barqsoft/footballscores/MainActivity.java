package barqsoft.footballscores;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends ActionBarActivity
{
    public static int selected_match_id;
    public static int current_fragment = 2;
    private PagerFragment my_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** Added Coach Mark, so that person can easily understand app **/
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        boolean needToShow = sharedPreferences.getBoolean("NeedToShow", true);
        if(needToShow){
            showCoachMark(savedInstanceState);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("NeedToShow",false);
            editor.commit();
        } else {
            if (savedInstanceState == null) {
                my_main = new PagerFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, my_main)
                        .commit();
            }
        }
    }


    public void showCoachMark(final Bundle savedInstanceState){

        View v = (View) findViewById(R.id.dialog_background_helper_view);
        v.setVisibility(View.VISIBLE);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);

                if (savedInstanceState == null) {
                    my_main = new PagerFragment();
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.container, my_main)
                            .commit();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about)
        {
            Intent start_about = new Intent(this,AboutActivity.class);
            startActivity(start_about);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        outState.putInt(getString(R.string.pager_current_key),my_main.mPagerHandler.getCurrentItem());
        outState.putInt(getString(R.string.selected_match_key),selected_match_id);
        getSupportFragmentManager().putFragment(outState,getString(R.string.fragment_key),my_main);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        current_fragment = savedInstanceState.getInt(getString(R.string.pager_current_key));
        selected_match_id = savedInstanceState.getInt(getString(R.string.selected_match_key));
        my_main = (PagerFragment) getSupportFragmentManager().getFragment(savedInstanceState,getString(R.string.fragment_key));
        super.onRestoreInstanceState(savedInstanceState);
    }
}
