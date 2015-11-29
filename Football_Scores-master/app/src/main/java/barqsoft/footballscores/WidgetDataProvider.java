package barqsoft.footballscores;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint("NewApi")
public class WidgetDataProvider implements RemoteViewsFactory {

    public static final String TAG = "WidgetDataProvider";

    List<String> mCollections = new ArrayList<String>();

    Context mContext = null;

    private boolean mLoading = true;
    private ArrayList<DataHoldingClass> mDataHoldingClassArrayList = null;

    public WidgetDataProvider(Context context, Intent intent) {
        mContext = context;
        new DummyWait().execute();
    }

    @Override
    public int getCount() {
        return mCollections.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews mView = new RemoteViews(mContext.getPackageName(), R.layout.scores_list_item_widget);

        Log.d(TAG, "Inside getViewAt");
        if(mLoading) {
            mView.setViewVisibility(R.id.loading_text, View.VISIBLE);
        } else {

            Log.d(TAG, "getView");

            mView.setViewVisibility(R.id.loading_text, View.GONE);
            DataHoldingClass dataHoldingClass = mDataHoldingClassArrayList.get(position);
            mView.setTextViewText(R.id.home_name, dataHoldingClass.Home);
            mView.setImageViewResource(R.id.home_crest, Utilies.getTeamCrestByTeamName(dataHoldingClass.Home));
            mView.setTextViewText(R.id.away_name, dataHoldingClass.Away);
            mView.setImageViewResource(R.id.away_crest, Utilies.getTeamCrestByTeamName(dataHoldingClass.Away));
            mView.setTextViewText(R.id.score_textview, Utilies.getScores(Integer.parseInt(dataHoldingClass.Home_goals), Integer.parseInt(dataHoldingClass.Away_goals)));
            mView.setTextViewText(R.id.data_textview, dataHoldingClass.mTime);
        }

        final Intent fillInIntent = new Intent();
        fillInIntent.setAction(WidgetProvider.ACTION_APP);
        final Bundle bundle = new Bundle();
        bundle.putString(WidgetProvider.EXTRA_STRING,
                mCollections.get(position));
        fillInIntent.putExtras(bundle);

        mView.setOnClickFillInIntent(R.id.main_linear_layout, fillInIntent);
        return mView;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onCreate() {
        initData();
    }

    @Override
    public void onDataSetChanged() {
        if(mLoading) {
            Log.d(TAG,"Loading is true");
            initData();
        } else {
            Log.d(TAG, "Loading is false");
        }
    }

    private void initData() {
        mCollections.clear();
        mCollections.add("Loading data ...  (Check Internet Connection for faster loading)");
    }

    @Override
    public void onDestroy() {

    }

    public void dataLoadingComplete(ArrayList<DataHoldingClass> dataHoldingClassArrayList){
        Log.d(TAG, "dataLoadingComplete");
        mLoading = false;
        mDataHoldingClassArrayList = dataHoldingClassArrayList;
        mCollections.clear();
        for(int i = 0; i<mDataHoldingClassArrayList.size(); i++){
            mCollections.add("");
            Log.d(TAG, "Added item");
        }
        AppWidgetManager mAppWidgetManager = AppWidgetManager.getInstance(mContext);
        mAppWidgetManager.notifyAppWidgetViewDataChanged(mAppWidgetManager.getAppWidgetIds(new ComponentName(mContext,WidgetProvider.class.getName())), R.id.widgetCollectionList);
    }


    public static class DataHoldingClass{

        //Match data
        String League = null;
        String mDate = null;
        String mTime = null;
        String Home = null;
        String Away = null;
        String Home_goals = null;
        String Away_goals = null;
        String match_day = null;
    }


    private ArrayList<DataHoldingClass> processJSONdata (String JSONdata,Context mContext, boolean isReal)
    {
        final String BUNDESLIGA1 = mContext.getString(R.string.bundesliga_code);
        final String BUNDESLIGA2 = mContext.getString(R.string.bundesliga_two_code);
        final String PREMIER_LEAGUE = mContext.getString(R.string.premier_league);
        final String PRIMERA_DIVISION = mContext.getString(R.string.primera_division);
        final String SERIE_A = mContext.getString(R.string.serie_a);
        final String CHAMPIONS_LEAGUE = mContext.getString(R.string.champions_league_code);

        final String SEASON_LINK = mContext.getString(R.string.season_link);
        final String MATCH_LINK = mContext.getString(R.string.match_link);
        final String FIXTURES = mContext.getString(R.string.fixtures);
        final String LINKS = mContext.getString(R.string.links);
        final String SOCCER_SEASON = mContext.getString(R.string.soccerseason);
        final String SELF = mContext.getString(R.string.self);
        final String MATCH_DATE = mContext.getString(R.string.date);
        final String HOME_TEAM = mContext.getString(R.string.home_team_name);
        final String AWAY_TEAM = mContext.getString(R.string.away_team_name);
        final String RESULT = mContext.getString(R.string.result);
        final String HOME_GOALS = mContext.getString(R.string.goals_home_team);
        final String AWAY_GOALS = mContext.getString(R.string.goals_away_team);
        final String MATCH_DAY = mContext.getString(R.string.match_dat);

        //Match data
        String League = null;
        String mDate = null;
        String mTime = null;
        String match_id = null;


        try {
            JSONArray matches = new JSONObject(JSONdata).getJSONArray(FIXTURES);
            Log.d(TAG, String.valueOf(matches.length()));
            ArrayList<DataHoldingClass> values = new ArrayList <> (matches.length());
            Log.d(TAG, " Size : " + values.size());
            for(int i = 0;i < matches.length();i++)
            {
                JSONObject match_data = matches.getJSONObject(i);
                League = match_data.getJSONObject(LINKS).getJSONObject(SOCCER_SEASON).
                        getString("href");

                League = League.replace(SEASON_LINK,"");
                if(     League.equals(PREMIER_LEAGUE)      ||
                        League.equals(SERIE_A)             ||
                        League.equals(BUNDESLIGA1)         ||
                        League.equals(BUNDESLIGA2)         ||
                        League.equals(CHAMPIONS_LEAGUE)    ||
                        League.equals(PRIMERA_DIVISION)    )
                {
                    match_id = match_data.getJSONObject(LINKS).getJSONObject(SELF).
                            getString("href");
                    match_id = match_id.replace(MATCH_LINK, "");
                    if(!isReal){
                        match_id=match_id+Integer.toString(i);
                    }

                    mDate = match_data.getString(MATCH_DATE);
                    mTime = mDate.substring(mDate.indexOf('T') + 1, mDate.indexOf('Z'));
                    mDate = mDate.substring(0,mDate.indexOf('T'));
                    SimpleDateFormat match_date = new SimpleDateFormat(mContext.getString(R.string.match_date_format));
                    match_date.setTimeZone(TimeZone.getTimeZone("UTC"));
                    try {
                        Date parseddate = match_date.parse(mDate+mTime);
                        SimpleDateFormat new_date = new SimpleDateFormat(mContext.getString(R.string.another_match_date_format));
                        new_date.setTimeZone(TimeZone.getDefault());
                        mDate = new_date.format(parseddate);
                        mTime = mDate.substring(mDate.indexOf(':') + 1);
                        mDate = mDate.substring(0,mDate.indexOf(':'));

                        if(!isReal){
                            //This if statement changes the dummy data's date to match our current date range.
                            Date fragmentdate = new Date(System.currentTimeMillis()+((i-2)*86400000L));
                            SimpleDateFormat mformat = new SimpleDateFormat(mContext.getString(R.string.date_format_for_fragment));
                            mDate=mformat.format(fragmentdate);
                        }
                    }
                    catch (Exception e)
                    {
                        Log.d(TAG, "error here!");
                        Log.e(TAG, e.getMessage());
                    }

                    DataHoldingClass dataHoldingClass = new DataHoldingClass();
                    dataHoldingClass.Home = match_data.getString(HOME_TEAM);
                    dataHoldingClass.Away = match_data.getString(AWAY_TEAM);
                    dataHoldingClass.Home_goals = match_data.getJSONObject(RESULT).getString(HOME_GOALS);
                    dataHoldingClass.Away_goals = match_data.getJSONObject(RESULT).getString(AWAY_GOALS);
                    dataHoldingClass.match_day = match_data.getString(MATCH_DAY);
                    dataHoldingClass.mDate = mDate;
                    dataHoldingClass.mTime = mTime;
                    dataHoldingClass.League = League;

                    values.add(dataHoldingClass);
                }
            }
            Log.d(TAG, String.valueOf(values.size()));
            return values;
        }
        catch (JSONException e)
        {
            Log.d(TAG, "ERROR ");
            Log.e(TAG,e.getMessage());
        }

        return null;
    }


    class DummyWait extends AsyncTask<Void, Void, ArrayList<DataHoldingClass>> {

        protected ArrayList<DataHoldingClass> doInBackground(Void... params) {

                ArrayList<DataHoldingClass> savedData = null;

                //Creating fetch URL
                final String BASE_URL = mContext.getString(R.string.base_url); //Base URL
                final String QUERY_TIME_FRAME = mContext.getString(R.string.timeframe); //Time Frame parameter to determine days
                //final String QUERY_MATCH_DAY = "matchday";

                Uri fetch_build = Uri.parse(BASE_URL).buildUpon().
                        appendQueryParameter(QUERY_TIME_FRAME, "p1").build();
                //Log.v(LOG_TAG, "The url we are looking at is: "+fetch_build.toString()); //log spam
                HttpURLConnection m_connection = null;
                BufferedReader reader = null;
                String JSON_data = null;
                //Opening Connection
                try {
                    URL fetch = new URL(fetch_build.toString());
                    m_connection = (HttpURLConnection) fetch.openConnection();
                    m_connection.setRequestMethod(mContext.getString(R.string.get));
                    m_connection.addRequestProperty(mContext.getString(R.string.x_auth_token), mContext.getString(R.string.api_key));
                    m_connection.connect();

                    // Read the input stream into a String
                    InputStream inputStream = m_connection.getInputStream();
                    StringBuffer buffer = new StringBuffer();
                    if (inputStream == null) {
                        // Nothing to do.
                        return null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                        // But it does make debugging a *lot* easier if you print out the completed
                        // buffer for debugging.
                        buffer.append(line);
                        buffer.append("\n");
                    }
                    if (buffer.length() == 0) {
                        // Stream was empty.  No point in parsing.
                        return null;
                    }
                    JSON_data = buffer.toString();
                }
                catch (Exception e)
                {
                    Log.e(TAG, e.getMessage());
                }
                finally {
                    if(m_connection != null)
                    {
                        m_connection.disconnect();
                    }
                    if (reader != null)
                    {
                        try {
                            reader.close();
                        }
                        catch (IOException e)
                        {
                            Log.e(TAG,e.getMessage());
                        }
                    }
                }
                try {
                    if (JSON_data != null) {
                        //This bit is to check if the data contains any matches. If not, we call processJson on the dummy data
                        JSONArray matches = new JSONObject(JSON_data).getJSONArray("fixtures");
                        if (matches.length() == 0) {
                            savedData = null;
                        } else {
                            savedData = processJSONdata(JSON_data, mContext, true);
                        }
                    } else {
                        return null;
                    }
                }
                catch(Exception e)
                {
                    Log.e(TAG,e.getMessage());
                }

            fetch_build = Uri.parse(BASE_URL).buildUpon().
                    appendQueryParameter(QUERY_TIME_FRAME, "n1").build();

            m_connection = null;
            reader = null;
            JSON_data = null;
            //Opening Connection
            try {
                URL fetch = new URL(fetch_build.toString());
                m_connection = (HttpURLConnection) fetch.openConnection();
                m_connection.setRequestMethod(mContext.getString(R.string.get));
                m_connection.addRequestProperty(mContext.getString(R.string.x_auth_token), mContext.getString(R.string.api_key));
                m_connection.connect();

                // Read the input stream into a String
                InputStream inputStream = m_connection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line);
                    buffer.append("\n");
                }
                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                JSON_data = buffer.toString();
            }
            catch (Exception e)
            {
                Log.e(TAG, e.getMessage());
            }
            finally {
                if(m_connection != null)
                {
                    m_connection.disconnect();
                }
                if (reader != null)
                {
                    try {
                        reader.close();
                    }
                    catch (IOException e)
                    {
                        Log.e(TAG,e.getMessage());
                    }
                }
            }
            try {
                if (JSON_data != null) {
                    //This bit is to check if the data contains any matches. If not, we call processJson on the dummy data
                    JSONArray matches = new JSONObject(JSON_data).getJSONArray("fixtures");
                    if (matches.length() == 0 && savedData == null) {
                        return null;
                    } else if(matches.length() == 0){
                        return savedData;
                    }
                    savedData.addAll(processJSONdata(JSON_data, mContext, true));
                    return savedData;
                } else {
                    return null;
                }
            }
            catch(Exception e)
            {
                Log.e(TAG,e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<DataHoldingClass> params) {
            Log.d(TAG, "onPostExecute");
            if(params != null) {
                dataLoadingComplete(params);
            } else {
                // TODO : No Match Found ...
            }
        }
    }
}
