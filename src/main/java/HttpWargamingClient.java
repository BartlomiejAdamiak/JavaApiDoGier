/**
 * Created by kaima_000 on 2016-06-09.
 */
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpWargamingClient implements HttpClientInterface {
    private final String USER_AGENT = "Mozilla/5.0";

    private String sendGet(String url) throws Exception {
        String response;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode;
        responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer buffer = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            buffer.append(inputLine);
        }
        in.close();

        response = buffer.toString();
        if(responseCode!=200) return "Response failed";
        return response;
    }

    public WorldOfTanksPlayer findPlayerByName(String name){
        WorldOfTanksPlayer player = new WorldOfTanksPlayer(name);
        player = getStatistics(player);

        return player;
    }

    private WorldOfTanksPlayer getStatistics(WorldOfTanksPlayer player){
        Integer id = getId(player.getName());
        player.setId(id);
        JSONObject statistics = getJSONStatisticsOfPlayerById(id);
        player.setKills(getKills(statistics));
        player.setWins(getWins(statistics));
        player.setLosses(getLosses(statistics));

        return player;
    }

    private Integer getId(String name){
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

    public JSONObject getJSONStatisticsOfPlayerById(Integer id){
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

    public Integer getKills(JSONObject statistics) {
        return Integer.parseInt(statistics.get("frags").toString());
    }

    public Integer getWins(JSONObject statistics){
        return Integer.parseInt(statistics.get("wins").toString());
    }

    public Integer getLosses(JSONObject statistics){
        return Integer.parseInt(statistics.get("losses").toString());
    }

    private JSONObject parseToJSONObject(String stringToParse){
        JSONParser parser = new JSONParser();
        JSONObject obj = new JSONObject();
        try {
            obj = (JSONObject) parser.parse(stringToParse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
