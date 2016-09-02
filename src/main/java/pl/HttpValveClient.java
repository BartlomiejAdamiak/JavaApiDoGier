package pl;

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
public abstract class HttpValveClient extends HttpClient{
    final static Logger logger = Logger.getLogger(HttpValveClient.class);

    @Autowired
    public Player player;

    public HttpValveClient() {
    }

    public Player findPlayerById(String playerId) {
        player.setId(playerId);
        player.setName(getName(playerId));
        player = getStatistics(player);

        return player;
    }

    private String getName(String playerId){
        String url = "http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=CEA96357A7E047331D22006B6D36D003&steamids=" + playerId;

        JSONObject obj = sendUrlAndGetJSON(url);

        JSONObject response = (JSONObject) obj.get("response");
        JSONArray playersArray = (JSONArray) response.get("players");

        JSONObject playerFound = (JSONObject) playersArray.get(0);

        return (String) playerFound.get("personaname");
    }

    private Player getStatistics(Player player) {
        JSONObject statistics = getJSONStatisticsOfPlayerById(player.getId());
        player.setKills(getKills(statistics));
        player.setWins(getWins(statistics));
        player.setLosses(getLosses(statistics));

        return player;
    }

    protected abstract JSONObject getJSONStatisticsOfPlayerById(String id);
}
