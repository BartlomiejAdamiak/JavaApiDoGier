package pl.service.valve;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public Player player;

    public HttpValveClient() {
    }

    public Player findPlayerById(String playerId) {
        player = new Player();
        player.setId(playerId);
        player.setName(getName(playerId));
        player = getStatistics(player);

        return player;
    }

    public String getName(String playerId){
        JSONObject obj = sendUrlAndGetJSON(API_URL_GET_NAME + playerId);

        JSONObject response = (JSONObject) obj.get("response");
        JSONArray playersArray = (JSONArray) response.get("players");

        JSONObject playerFound = (JSONObject) playersArray.get(0);

        return (String) playerFound.get("personaname");
    }

    public Player getStatistics(Player player) {
        JSONObject statistics = getJSONStatisticsOfPlayerById(player.getId());
        player.setKills(getKills(statistics));
        player.setWins(getWins(statistics));
        player.setLosses(getLosses(statistics));

        return player;
    }

    public abstract JSONObject getJSONStatisticsOfPlayerById(String id);
}
