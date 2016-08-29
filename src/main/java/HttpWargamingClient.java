/**
 * Created by kaima_000 on 2016-06-09.
 */
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class HttpWargamingClient extends HttpClient{

    WorldOfTanksPlayer player;
    public HttpWargamingClient(String playerName) {
        findPlayerByName(playerName);
    }

    public WorldOfTanksPlayer findPlayerByName(String name){
        player = new WorldOfTanksPlayer(name);
        player = getStatistics(player);

        return player;
    }

    protected WorldOfTanksPlayer getStatistics(WorldOfTanksPlayer player){
        Integer id = getId(player.getName());
        player.setId(id);
        JSONObject statistics = getJSONStatisticsOfPlayerById(id);
        player.setKills(getKills(statistics));
        player.setWins(getWins(statistics));
        player.setLosses(getLosses(statistics));

        return player;
    }

    protected Integer getId(String name){
        String url = "https://api.worldoftanks.eu/wot/account/list/?application_id=demo&search="+ name +"&limit=1";
        String response = new String();
        try{
            response = sendGet(url);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        if(response.equals("Response failed")) return 0;

        JSONObject obj = parseToJSONObject(response);
        JSONArray data = (JSONArray) obj.get("data");
        JSONObject firstObjectOfArray = (JSONObject) data.get(0);

        return Integer.parseInt(firstObjectOfArray.get("account_id").toString());
    }

    protected JSONObject getJSONStatisticsOfPlayerById(Integer id){
        String url = "https://api.worldoftanks.eu/wot/account/info/?application_id=demo&account_id=" + id.toString();
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
