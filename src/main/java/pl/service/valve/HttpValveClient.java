package pl.service.valve;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
import pl.model.Player;
import pl.service.HttpClient;

/**
 * Created by kaima_000 on 2016-09-02.
 */
@Component
public abstract class HttpValveClient extends HttpClient implements HttpValveInterface{
    final static Logger logger = Logger.getLogger(HttpValveClient.class);
    private final static String API_URL_GET_NAME = "http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=CEA96357A7E047331D22006B6D36D003&steamids=";

    public HttpValveClient() {
    }

    public void findPlayerById(String playerId) throws Exception {
        player = new Player();
        JSONObject statistics = getJSONStatisticsOfPlayerById(playerId);
        player.setId(playerId);
        player.setName(getName(playerId));
        player.setKills(getKills(statistics));
        player.setWins(getWins(statistics));
        player.setLosses(getLosses(statistics));
    }

    public String getName(String playerId) throws Exception {
        JSONObject obj = sendUrlAndGetJSON(API_URL_GET_NAME + playerId);

        JSONObject response = (JSONObject) obj.get("response");
        JSONArray playersArray = (JSONArray) response.get("players");

        JSONObject playerFound = (JSONObject) playersArray.get(0);

        return (String) playerFound.get("personaname");
    }

    public abstract JSONObject getJSONStatisticsOfPlayerById(String id);
}
