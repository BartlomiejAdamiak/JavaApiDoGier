package pl;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.model.Player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by kaima_000 on 2016-06-09.
 */
@Component
public class HttpClient implements HttpClientInterface {
    private final String USER_AGENT = "Mozilla/5.0";
    final static Logger logger = Logger.getLogger(HttpClient.class);

    @Autowired
    public Player player;


    @Override
    public Player findPlayer(String gameName, String playerId) {

        HttpClient client = null;
        switch (gameName) {
            case "WorldOfTanks": {
                client = new HttpWargamingClient(playerId);
            }
        }
        return client.player;
    }

    protected String sendGet(String url) throws Exception {
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
        StringBuilder buffer = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            buffer.append(inputLine);
        }
        in.close();

        response = buffer.toString();
        if (responseCode != 200) return "Response failed";
        return response;
    }

    protected Integer getKills(JSONObject statistics) {
        return Integer.parseInt(statistics.get("kills").toString());
    }

    protected Integer getWins(JSONObject statistics) {
        return Integer.parseInt(statistics.get("wins").toString());
    }

    protected Integer getLosses(JSONObject statistics) {
        return Integer.parseInt(statistics.get("losses").toString());
    }

    protected JSONObject parseToJSONObject(String stringToParse) {
        JSONParser parser = new JSONParser();
        JSONObject obj = new JSONObject();
        try {
            obj = (JSONObject) parser.parse(stringToParse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return obj;
    }

    protected JSONObject sendUrlAndGetJSON(String url){
        String response = new String();
        try {
            response = sendGet(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (response.equals("Response failed")) return null;
        JSONParser parser = new JSONParser();
        JSONObject obj = new JSONObject();
        try {
            obj = (JSONObject) parser.parse(response);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return obj;
    }


}
