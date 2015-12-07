package barqsoft.footballscores;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yehya khaled on 3/3/2015.
 */
public class Utilies
{
    public static final int SERIE_A = 401;
    public static final int PREMIER_LEGAUE = 398;
    public static final int CHAMPIONS_LEAGUE = 405;
    public static final int PRIMERA_DIVISION = 399;
    public static final int BUNDESLIGA = 394;

    public static final String LOG_TAG = "Utilities";

    public static String getLeague(int league_num)
    {
        /** Below method is called for fetching league code.
         * need to be run only once, then data can be saved above.
         */
        //getLeagueNameById(league_num);

        Log.d(LOG_TAG, "League Name : " + league_num);

        switch (league_num)
        {
            case SERIE_A : return "Seria A";
            case PREMIER_LEGAUE : return "Premier League";
            case CHAMPIONS_LEAGUE : return "UEFA Champions League";
            case PRIMERA_DIVISION : return "Primera Division";
            case BUNDESLIGA : return "Bundesliga";
            default: return "Not known League Please report";
        }
    }
    public static String getMatchDay(int match_day,int league_num)
    {
        if(league_num == CHAMPIONS_LEAGUE)
        {
            if (match_day <= 6)
            {
                return "Group Stages, Matchday : 6";
            }
            else if(match_day == 7 || match_day == 8)
            {
                return "First Knockout round";
            }
            else if(match_day == 9 || match_day == 10)
            {
                return "QuarterFinal";
            }
            else if(match_day == 11 || match_day == 12)
            {
                return "SemiFinal";
            }
            else
            {
                return "Final";
            }
        }
        else
        {
            return "Matchday : " + match_day;
        }
    }

    public static String getScores(int home_goals,int awaygoals)
    {
        if(home_goals < 0 || awaygoals < 0)
        {
            return " - ";
        }
        else
        {
            return home_goals + " - " + awaygoals;
        }
    }

    public static int getTeamCrestByTeamName (String teamname)
    {
        if (teamname==null){return R.drawable.no_icon;}
        switch (teamname)
        { //This is the set of icons that are currently in the app. Feel free to find and add more
            //as you go.
            case "Arsenal London FC" : return R.drawable.arsenal;
            case "Manchester_United FC" : return R.drawable.manchester_united;
            case "Swansea City" : return R.drawable.swansea_city_afc;
            case "Leicester City" : return R.drawable.leicester_city_fc_hd_logo;
            case "Everton FC" : return R.drawable.everton_fc_logo1;
            case "West Ham United FC" : return R.drawable.west_ham;
            case "Tottenham Hotspur FC" : return R.drawable.tottenham_hotspur;
            case "West Bromwich Albion" : return R.drawable.west_bromwich_albion_hd_logo;
            case "Sunderland AFC" : return R.drawable.sunderland;
            case "Stoke City FC" : return R.drawable.stoke_city;
        }

        /** All below lines are generated through a script
         * They are similar, wrote simple script to write them **/
        if(teamname.toLowerCase().contains("arsenal".toLowerCase()))
        {
            return R.drawable.arsenal;
        }

        if(teamname.toLowerCase().contains("bayern munchen".toLowerCase()))
        {
            return R.drawable.bayern_munchen;
        }

        if(teamname.toLowerCase().contains("manchester city".toLowerCase()))
        {
            return R.drawable.manchester_city;
        }

        if(teamname.toLowerCase().contains("manchester united".toLowerCase()))
        {
            return R.drawable.manchester_united;
        }

        if(teamname.toLowerCase().contains("Newcastle united".toLowerCase()))
        {
            return R.drawable.newcastle;
        }

        if(teamname.toLowerCase().contains("Queens Park Rangers".toLowerCase()))
        {
            return R.drawable.queens_park_rangers_hd_logo;
        }

        if(teamname.toLowerCase().contains("hull city".toLowerCase()))
        {
            return R.drawable.hull_city_afc_hd_logo;
        }

        if(teamname.toLowerCase().contains("schalke".toLowerCase()))
        {
            return R.drawable.schalke_04;
        }

        if(teamname.toLowerCase().contains("aston".toLowerCase()))
        {
            return R.drawable.aston;
        }

        if(teamname.toLowerCase().contains("atalanta".toLowerCase()))
        {
            return R.drawable.atalanta;
        }

        if(teamname.toLowerCase().contains("de madrid".toLowerCase()))
        {
            return R.drawable.atletico;
        }

        if(teamname.toLowerCase().contains("barcelona".toLowerCase()))
        {
            return R.drawable.barcelona;
        }

        if(teamname.toLowerCase().contains("betis".toLowerCase()))
        {
            return R.drawable.betis;
        }

        if(teamname.toLowerCase().contains("bilbao".toLowerCase()))
        {
            return R.drawable.bilbao;
        }

        if(teamname.toLowerCase().contains("birmingham".toLowerCase()))
        {
            return R.drawable.birmingham;
        }

        if(teamname.toLowerCase().contains("blackburn".toLowerCase()))
        {
            return R.drawable.blackburn;
        }

        if(teamname.toLowerCase().contains("bochum".toLowerCase()))
        {
            return R.drawable.bochum;
        }

        if(teamname.toLowerCase().contains("bologna".toLowerCase()))
        {
            return R.drawable.bologna;
        }

        if(teamname.toLowerCase().contains("bolton".toLowerCase()))
        {
            return R.drawable.bolton;
        }

        if(teamname.toLowerCase().contains("burnley".toLowerCase()))
        {
            return R.drawable.burnley;
        }

        if(teamname.toLowerCase().contains("cagliari".toLowerCase()))
        {
            return R.drawable.cagliari;
        }

        if(teamname.toLowerCase().contains("cardiff".toLowerCase()))
        {
            return R.drawable.cardiff;
        }

        if(teamname.toLowerCase().contains("celtic".toLowerCase()))
        {
            return R.drawable.celtic;
        }

        if(teamname.toLowerCase().contains("cesena".toLowerCase()))
        {
            return R.drawable.cesena;
        }

        if(teamname.toLowerCase().contains("chelsea".toLowerCase()))
        {
            return R.drawable.chelsea;
        }

        if(teamname.toLowerCase().contains("dortmund".toLowerCase()))
        {
            return R.drawable.dortmund;
        }

        if(teamname.toLowerCase().contains("empoli".toLowerCase()))
        {
            return R.drawable.empoli;
        }

        if(teamname.toLowerCase().contains("england".toLowerCase()))
        {
            return R.drawable.england;
        }

        if(teamname.toLowerCase().contains("espanyol".toLowerCase()))
        {
            return R.drawable.espanyol;
        }

        if(teamname.toLowerCase().contains("fiorentina".toLowerCase()))
        {
            return R.drawable.fiorentina;
        }

        if(teamname.toLowerCase().contains("fulham".toLowerCase()))
        {
            return R.drawable.fulham;
        }

        if(teamname.toLowerCase().contains("germany".toLowerCase()))
        {
            return R.drawable.germany;
        }

        if(teamname.toLowerCase().contains("getafe".toLowerCase()))
        {
            return R.drawable.getafe;
        }

        if(teamname.toLowerCase().contains("internazionale".toLowerCase()))
        {
            return R.drawable.internazionale;
        }

        if(teamname.toLowerCase().contains("ireland".toLowerCase()))
        {
            return R.drawable.ireland;
        }

        if(teamname.toLowerCase().contains("italy".toLowerCase()))
        {
            return R.drawable.italy;
        }

        if(teamname.toLowerCase().contains("juventus".toLowerCase()))
        {
            return R.drawable.juventus;
        }

        if(teamname.toLowerCase().contains("kaiserslautern".toLowerCase()))
        {
            return R.drawable.kaiserslautern;
        }

        if(teamname.toLowerCase().contains("koln".toLowerCase()))
        {
            return R.drawable.koln;
        }

        if(teamname.toLowerCase().contains("lazio".toLowerCase()))
        {
            return R.drawable.lazio;
        }

        if(teamname.toLowerCase().contains("leeds".toLowerCase()))
        {
            return R.drawable.leeds;
        }

        if(teamname.toLowerCase().contains("leverkusen".toLowerCase()))
        {
            return R.drawable.leverkusen;
        }

        if(teamname.toLowerCase().contains("liverpool".toLowerCase()))
        {
            return R.drawable.liverpool;
        }

        if(teamname.toLowerCase().contains("livorno".toLowerCase()))
        {
            return R.drawable.livorno;
        }

        if(teamname.toLowerCase().contains("real madrid".toLowerCase()))
        {
            return R.drawable.madrid;
        }

        if(teamname.toLowerCase().contains("malaga".toLowerCase()))
        {
            return R.drawable.malaga;
        }

        if(teamname.toLowerCase().contains("mallorca".toLowerCase()))
        {
            return R.drawable.mallorca;
        }

        if(teamname.toLowerCase().contains("middlesbrough".toLowerCase()))
        {
            return R.drawable.middlesbrough;
        }

        if(teamname.toLowerCase().contains("milan".toLowerCase()))
        {
            return R.drawable.milan;
        }

        if(teamname.toLowerCase().contains("Paris Saint-Germain".toLowerCase()))
        {
            return R.drawable.paris_saint_germain;
        }


        if(teamname.toLowerCase().contains("millwall".toLowerCase()))
        {
            return R.drawable.millwall;
        }

        if(teamname.toLowerCase().contains("mönchengladbach".toLowerCase()))
        {
            return R.drawable.monchengladbach;
        }

        if(teamname.toLowerCase().contains("napoli".toLowerCase()))
        {
            return R.drawable.napoli;
        }

        if(teamname.toLowerCase().contains("newcastle".toLowerCase()))
        {
            return R.drawable.newcastle;
        }

        if(teamname.toLowerCase().contains("norwich".toLowerCase()))
        {
            return R.drawable.norwich;
        }

        if(teamname.toLowerCase().contains("nottingham".toLowerCase()))
        {
            return R.drawable.nottingham;
        }

        if(teamname.toLowerCase().contains("nurnberg".toLowerCase()))
        {
            return R.drawable.nurnberg;
        }

        if(teamname.toLowerCase().contains("osasuna".toLowerCase()))
        {
            return R.drawable.osasuna;
        }

        if(teamname.toLowerCase().contains("oviedo".toLowerCase()))
        {
            return R.drawable.oviedo;
        }

        if(teamname.toLowerCase().contains("palermo".toLowerCase()))
        {
            return R.drawable.palermo;
        }

        if(teamname.toLowerCase().contains("parma".toLowerCase()))
        {
            return R.drawable.parma;
        }

        if(teamname.toLowerCase().contains("perugia".toLowerCase()))
        {
            return R.drawable.perugia;
        }

        if(teamname.toLowerCase().contains("portsmouth".toLowerCase()))
        {
            return R.drawable.portsmouth;
        }

        if(teamname.toLowerCase().contains("reading".toLowerCase()))
        {
            return R.drawable.reading;
        }

        if(teamname.toLowerCase().contains("roma".toLowerCase()))
        {
            return R.drawable.roma;
        }

        if(teamname.toLowerCase().contains("sampdoria".toLowerCase()))
        {
            return R.drawable.sampdoria;
        }

        if(teamname.toLowerCase().contains("scotland".toLowerCase()))
        {
            return R.drawable.scotland;
        }

        if(teamname.toLowerCase().contains("sevilla".toLowerCase()))
        {
            return R.drawable.sevilla;
        }

        if(teamname.toLowerCase().contains("sociedad".toLowerCase()))
        {
            return R.drawable.sociedad;
        }

        if(teamname.toLowerCase().contains("southampton".toLowerCase()))
        {
            return R.drawable.southampton;
        }

        if(teamname.toLowerCase().contains("spain".toLowerCase()))
        {
            return R.drawable.spain;
        }

        if(teamname.toLowerCase().contains("stoke".toLowerCase()))
        {
            return R.drawable.stoke;
        }

        if(teamname.toLowerCase().contains("swansea".toLowerCase()))
        {
            return R.drawable.swansea;
        }

        if(teamname.toLowerCase().contains("tenerife".toLowerCase()))
        {
            return R.drawable.tenerife;
        }

        if(teamname.toLowerCase().contains("torino".toLowerCase()))
        {
            return R.drawable.torino;
        }

        if(teamname.toLowerCase().contains("tottenham".toLowerCase()))
        {
            return R.drawable.tottenham;
        }

        if(teamname.toLowerCase().contains("udinese".toLowerCase()))
        {
            return R.drawable.udinese;
        }

        if(teamname.toLowerCase().contains("valencia".toLowerCase()))
        {
            return R.drawable.valencia;
        }

        if(teamname.toLowerCase().contains("valladolid".toLowerCase()))
        {
            return R.drawable.valladolid;
        }

        if(teamname.toLowerCase().contains("vicenza".toLowerCase()))
        {
            return R.drawable.vicenza;
        }

        if(teamname.toLowerCase().contains("villareal".toLowerCase()))
        {
            return R.drawable.villareal;
        }

        if(teamname.toLowerCase().contains("watford".toLowerCase()))
        {
            return R.drawable.watford;
        }

        if(teamname.toLowerCase().contains("wolfsburg".toLowerCase()))
        {
            return R.drawable.wolfsburg;
        }

        if(teamname.toLowerCase().contains("wolverhampton".toLowerCase()))
        {
            return R.drawable.wolverhampton;
        }

        if(teamname.toLowerCase().contains("wrexham".toLowerCase()))
        {
            return R.drawable.wrexham;
        }

        if(teamname.toLowerCase().contains("zaragoza".toLowerCase()))
        {
            return R.drawable.zaragoza;
        }


        /** No match found **/
        return R.drawable.no_icon;


    }

    private static void getLeagueNameById(int id)
    {
        //Creating fetch URL
        final String BASE_URL = "http://api.football-data.org/alpha/soccerseasons/"+String.valueOf(id); //Base URL
        final String QUERY_TIME_FRAME = "timeFrame"; //Time Frame parameter to determine days
        //final String QUERY_MATCH_DAY = "matchday";

        Uri fetch_build = Uri.parse(BASE_URL).buildUpon().build();
        //Log.v(LOG_TAG, "The url we are looking at is: "+fetch_build.toString()); //log spam
        HttpURLConnection m_connection = null;
        BufferedReader reader = null;
        String JSON_data = null;
        //Opening Connection
        try {
            URL fetch = new URL(fetch_build.toString());
            m_connection = (HttpURLConnection) fetch.openConnection();
            m_connection.setRequestMethod("GET");
            m_connection.connect();

            Log.d(LOG_TAG, "URL to fetch data from : " + fetch_build.toString());

            // Read the input stream into a String
            InputStream inputStream = m_connection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return;
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
                return;
            }
            JSON_data = buffer.toString();
            Log.d(LOG_TAG, " Received Msg : " + JSON_data);
        }
        catch (Exception e)
        {
            Log.e(LOG_TAG,"Exception here" + e.getMessage());
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
                    Log.e(LOG_TAG,"Error Closing Stream");
                }
            }
        }
    }
}
