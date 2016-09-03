package pl.service.valve;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.model.Player;

/**
 * Created by kaima_000 on 2016-09-02.
 */
@Component
public class HttpL4D2Client extends HttpValveClient implements HttpValveInterface{

    final static Logger logger = Logger.getLogger(HttpL4D2Client.class);

    @Autowired
    public Player player;

    public HttpL4D2Client() {
    }

    public JSONObject getJSONStatisticsOfPlayerById(String id) {
        String url = "https://api.steampowered.com/ISteamUserStats/GetUserStatsForGame/v0002/?appid=550&key=CEA96357A7E047331D22006B6D36D003&steamid=" + id;

        JSONObject obj = sendUrlAndGetJSON(url);

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
    }

}