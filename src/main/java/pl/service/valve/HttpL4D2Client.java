package pl.service.valve;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
import pl.Control.Controller;

/**
 * Created by kaima_000 on 2016-09-02.
 */
@Component
public class HttpL4D2Client extends HttpValveClient implements HttpValveInterface{

    final static Logger logger = Logger.getLogger(HttpL4D2Client.class);
    private final static String API_URL_GET_STATISTICS_BY_ID = "https://api.steampowered.com/ISteamUserStats/GetUserStatsForGame/v0002/?appid=550&key=CEA96357A7E047331D22006B6D36D003&steamid=";

    public HttpL4D2Client() {
    }

    public JSONObject getJSONStatisticsOfPlayerById(String id) {
        JSONObject obj;
        try {
            obj = sendUrlAndGetJSON(API_URL_GET_STATISTICS_BY_ID + id);
            JSONObject playerstats = (JSONObject) obj.get("playerstats");
            JSONArray statsarray = (JSONArray) playerstats.get("stats");
            long wins = 0;
            long totalMatches = 0;

            JSONObject statsToReturn = new JSONObject();
            for (Object anArray : statsarray) {
                JSONObject onePositionOfArray = (JSONObject) anArray;
                if (onePositionOfArray.get("name").equals("Stat.InfectedKilled.Total")) statsToReturn.put("kills", onePositionOfArray.get("value"));
                else if (onePositionOfArray.get("name").equals("Stat.FinaleFinished.Total")) {
                    wins = (long) onePositionOfArray.get("value");
                    statsToReturn.put("wins", wins);
                } else if (onePositionOfArray.get("name").equals("Stat.GamesPlayed.Total")) totalMatches = (long) onePositionOfArray.get("value");
            }
            statsToReturn.put("losses",totalMatches - wins);

            return statsToReturn;
        } catch (Exception e) {
            Controller.controllerInstance.exceptionOccured(e);
        }
        return null;
    }

}
