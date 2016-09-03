package pl.service.wargaming;
/**
 * Created by kaima_000 on 2016-06-09.
 */

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.model.Player;
import pl.service.HttpClient;
import pl.service.HttpWotLolInterface;

@Component
public class HttpWargamingClient extends HttpClient implements HttpWotLolInterface {

    final static Logger logger = Logger.getLogger(HttpWargamingClient.class);


    @Autowired
    public Player player;

    public HttpWargamingClient(String playerName) {
        findPlayerByName(playerName);
    }

    public HttpWargamingClient() {
    }


    public Player findPlayerByName(String name) {
        player.setName(name);
        player.setId(getPlayerId(name));
        player = getStatistics(player);

        return player;
    }

    public Player findPlayerById(String playerId) {
        player.setId(playerId);
        player = getStatistics(player);

        return player;
    }

    public Player getStatistics(Player player) {
        JSONObject statistics = getJSONStatisticsOfPlayerById(player.getId());
        player.setKills(getKills(statistics));
        player.setWins(getWins(statistics));
        player.setLosses(getLosses(statistics));

        return player;
    }

    public String getPlayerId(String name) {
        String url = "https://api.worldoftanks.eu/wot/account/list/?application_id=demo&search=" + name + "&limit=1";

        JSONObject obj = sendUrlAndGetJSON(url);

        JSONArray data = (JSONArray) obj.get("data");
        JSONObject firstObjectOfArray = (JSONObject) data.get(0);

        return firstObjectOfArray.get("account_id").toString();
    }

    public JSONObject getJSONStatisticsOfPlayerById(String id) {
        String url = "https://api.worldoftanks.eu/wot/account/info/?application_id=demo&account_id=" + id.toString();

        JSONObject obj = sendUrlAndGetJSON(url);

        JSONObject data = (JSONObject) obj.get("data");
        JSONObject idValue = (JSONObject) data.get(id.toString());
        JSONObject statistics = (JSONObject) idValue.get("statistics");
        JSONObject all = (JSONObject) statistics.get("all");

        return all;
    }

    @Override
    public Integer getKills(JSONObject statistics) {
        return Integer.parseInt(statistics.get("frags").toString());
    }
}
