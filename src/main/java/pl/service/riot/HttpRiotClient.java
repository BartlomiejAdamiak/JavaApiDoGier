package pl.service.riot;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.model.Player;
import pl.service.HttpClient;
import pl.service.HttpWotLolInterface;

/**
 * Created by kaima_000 on 2016-08-29.
 */
@Component
public class HttpRiotClient extends HttpClient implements HttpWotLolInterface {

    final static Logger logger = Logger.getLogger(HttpRiotClient.class);

    @Autowired
    public Player player;

    public HttpRiotClient() {
    }

    public HttpRiotClient(String playerName) {
        findPlayerByName(playerName);
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

    private Player getStatistics(Player player) {
        JSONObject statistics = getJSONStatisticsOfPlayerById(player.getId());
        player.setKills(getKills(statistics));
        player.setWins(getWins(statistics));
        player.setLosses(getLosses(statistics));

        return player;
    }

    public String getPlayerId(String name) {
        String url = "https://eune.api.pvp.net/api/lol/eune/v1.4/summoner/by-name/kaimada?api_key=RGAPI-1C2FC95A-EA14-425B-BBC6-B99DFCDA3F7D";

        JSONObject obj = sendUrlAndGetJSON(url);

        JSONObject innerObj = (JSONObject) obj.get(name);

        return innerObj.get("id").toString();
    }

    protected JSONObject getJSONStatisticsOfPlayerById(String id) {
        String url = "https://eune.api.pvp.net/api/lol/eune/v1.3/stats/by-summoner/" + id.toString() + "/summary?season=SEASON2015&api_key=RGAPI-1C2FC95A-EA14-425B-BBC6-B99DFCDA3F7D";

        JSONObject obj = sendUrlAndGetJSON(url);

        JSONArray array = (JSONArray) obj.get("playerStatSummaries");
        JSONObject findRanked5x5 = null;
        for (Object anArray : array) {
            JSONObject onePositionOfArray = (JSONObject) anArray;
            if (onePositionOfArray.get("playerStatSummaryType").equals("RankedSolo5x5")) {
                findRanked5x5 = onePositionOfArray;
                break;
            }
        }

        return findRanked5x5;
    }

    @Override
    public Integer getKills(JSONObject statistics) {
        JSONObject aggregatedStats = (JSONObject) statistics.get("aggregatedStats");
        return Integer.parseInt(aggregatedStats.get("totalChampionKills").toString());
    }
}
