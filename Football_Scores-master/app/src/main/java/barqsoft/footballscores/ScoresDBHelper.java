package barqsoft.footballscores;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import barqsoft.footballscores.DatabaseContract.scores_table;

/**
 * Created by yehya khaled on 2/25/2015.
 */
public class ScoresDBHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "Scores.db";
    private static final int DATABASE_VERSION = 2;
    public ScoresDBHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        final String TEXT_NOT_NULL = " TEXT NOT NULL,";
        final String INTEGER_NOT_NULL = " INTEGER NOT NULL,";
        final String CreateScoresTable = "CREATE TABLE " + DatabaseContract.SCORES_TABLE + " ("
                + scores_table._ID + " INTEGER PRIMARY KEY,"
                + scores_table.DATE_COL + TEXT_NOT_NULL
                + scores_table.TIME_COL + INTEGER_NOT_NULL
                + scores_table.HOME_COL + TEXT_NOT_NULL
                + scores_table.AWAY_COL + TEXT_NOT_NULL
                + scores_table.LEAGUE_COL + INTEGER_NOT_NULL
                + scores_table.HOME_GOALS_COL + TEXT_NOT_NULL
                + scores_table.AWAY_GOALS_COL + TEXT_NOT_NULL
                + scores_table.MATCH_ID + INTEGER_NOT_NULL
                + scores_table.MATCH_DAY + INTEGER_NOT_NULL
                + " UNIQUE ("+scores_table.MATCH_ID+") ON CONFLICT REPLACE"
                + " );";
        db.execSQL(CreateScoresTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //Remove old values when upgrading.
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.SCORES_TABLE);
    }
}
