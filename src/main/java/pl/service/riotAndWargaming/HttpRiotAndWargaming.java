package pl.service.riotAndWargaming;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
import pl.model.Player;
import pl.service.HttpClient;

/**
 * Created by kaima_000 on 2016-08-29.
 */
@Component
public abstract class HttpRiotAndWargaming extends HttpClient{

    final static Logger logger = Logger.getLogger(HttpRiotAndWargaming.class);

    public void findPlayerByName(String name) {
        player = new Player();
        player.setName(name);
        player.setId(getPlayerId(name));
        if(!(player.getId()==null)) getStatistics(player);
    }

    private void getStatistics(Player player){
        JSONObject statistics = getJSONStatisticsOfPlayerById(player.getId());
        player.setKills(getKills(statistics));
        player.setWins(getWins(statistics));
        player.setLosses(getLosses(statistics));
    }

    public abstract String getPlayerId(String name);

    protected abstract JSONObject getJSONStatisticsOfPlayerById(String id);
}
