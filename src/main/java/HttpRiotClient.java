import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by kaima_000 on 2016-08-29.
 */
public class HttpRiotClient extends HttpClient{

    LeagueOfLegendsPlayer player = null;
    public HttpRiotClient(String playerName) {
        findPlayerByName(playerName);
    }

    public LeagueOfLegendsPlayer findPlayerByName(String name){
        player = new LeagueOfLegendsPlayer(name);
        player = getStatistics(player);

        return player;
    }

    private LeagueOfLegendsPlayer getStatistics(LeagueOfLegendsPlayer player){
        Integer id = getId(player.getName());
        player.setId(id);
        JSONObject statistics = getJSONStatisticsOfPlayerById(id);
        player.setKills(getKills(statistics));
        player.setWins(getWins(statistics));
        player.setLosses(getLosses(statistics));

        return player;
    }

    private Integer getId(String name){
        String url = "https://eune.api.pvp.net/api/lol/eune/v1.4/summoner/by-name/kaimada?api_key=RGAPI-1C2FC95A-EA14-425B-BBC6-B99DFCDA3F7D";
        String response = new String();
        try{
            response = sendGet(url);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        if(response.equals("Response failed")) return 0;

        JSONObject obj = parseToJSONObject(response);
        JSONObject innerObj = (JSONObject) obj.get(name);

        return Integer.parseInt(innerObj.get("id").toString());
    }

    protected JSONObject getJSONStatisticsOfPlayerById(Integer id){
        String url = "https://eune.api.pvp.net/api/lol/eune/v1.3/stats/by-summoner/" + id.toString() + "/summary?season=SEASON2015&api_key=RGAPI-1C2FC95A-EA14-425B-BBC6-B99DFCDA3F7D";
        String response = new String();
        try{
            response = sendGet(url);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        if(response.equals("Response failed")) return null;
        JSONParser parser = new JSONParser();
        JSONObject obj = new JSONObject();
        try {
            obj = (JSONObject) parser.parse(response);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
