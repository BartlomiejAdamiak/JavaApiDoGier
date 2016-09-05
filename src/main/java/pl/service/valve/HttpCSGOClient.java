package pl.service.valve;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.Controller.Controller;
import pl.model.Player;

/**
 * Created by kaima_000 on 2016-09-02.
 */
@Component
public class HttpCSGOClient extends HttpValveClient implements HttpValveInterface{

    final static Logger logger = Logger.getLogger(HttpL4D2Client.class);
    private final static String API_URL_GET_STATISTICS_BY_ID = "https://api.steampowered.com/ISteamUserStats/GetUserStatsForGame/v0002/?appid=730&key=CEA96357A7E047331D22006B6D36D003&steamid=";

    public HttpCSGOClient() {
    }

    public JSONObject getJSONStatisticsOfPlayerById(String id) {
        JSONObject obj = null;
        try {
            obj = sendUrlAndGetJSON(API_URL_GET_STATISTICS_BY_ID + id);
            JSONObject playerstats = (JSONObject) obj.get("playerstats");
            JSONArray statsarray = (JSONArray) playerstats.get("stats");
            long wins = 0;
            long totalMatches = 0;

            JSONObject statsToReturn = new JSONObject();
            for (Object anArray : statsarray) {
                JSONObject onePositionOfArray = (JSONObject) anArray;
                if (onePositionOfArray.get("name").equals("total_kills")) statsToReturn.put("kills", onePositionOfArray.get("value"));
                else if (onePositionOfArray.get("name").equals("total_matches_won")) {
                    wins = (long) onePositionOfArray.get("value");
                    statsToReturn.put("wins", wins);
                } else if (onePositionOfArray.get("name").equals("total_matches_played")) totalMatches = (long) onePositionOfArray.get("value");
            }
            statsToReturn.put("losses",totalMatches - wins);

            return statsToReturn;
        } catch (Exception e) {
            Controller.controllerInstance.exceptionOccured(e);
        }

        return null;

    }

}
