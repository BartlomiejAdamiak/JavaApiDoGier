package pl.service;

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
 * JavaApiDoGier - program służący do przedstawiania statystyk gracza
 * Copyright (C) 19../20.. Bartłomiej Adamiak, Adam Szczeciński,
 * Michał Kudlewski, Beata Cabaj
 * <p>
 * Niniejszy program jest wolnym oprogramowaniem; możesz go
 * rozprowadzać dalej i/lub modyfikować na warunkach Powszechnej
 * Licencji Publicznej GNU, wydanej przez Fundację Wolnego
 * Oprogramowania - według wersji 2-giej tej Licencji lub którejś
 * z późniejszych wersji.
 * <p>
 * Niniejszy program rozpowszechniany jest z nadzieją, iż będzie on
 * użyteczny - jednak BEZ JAKIEJKOLWIEK GWARANCJI, nawet domyślnej
 * gwarancji PRZYDATNOŚCI HANDLOWEJ albo PRZYDATNOŚCI DO OKREŚLONYCH
 * ZASTOSOWAŃ. W celu uzyskania bliższych informacji - Powszechna
 * Licencja Publiczna GNU.
 * <p>
 * Z pewnością wraz z niniejszym programem otrzymałeś też egzemplarz
 * Powszechnej Licencji Publicznej GNU (GNU General Public License);
 * jeśli nie - napisz do Free Software Foundation, Inc., 675 Mass Ave,
 * Cambridge, MA 02139, USA.
 */
@Component
public class HttpClient {
    final static Logger logger = Logger.getLogger(HttpClient.class);
    private final String USER_AGENT = "Mozilla/5.0";
    @Autowired
    public Player player;

    protected String sendGet(String url) throws Exception {
        String response;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode;
        responseCode = con.getResponseCode();
        if (responseCode != 200) {
            logger.debug("Response failed, responseCode != 200");
            throw new Exception("Response failed");
        }

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder buffer = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            buffer.append(inputLine);
        }
        in.close();

        response = buffer.toString();
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

    protected JSONObject sendUrlAndGetJSON(String url) throws Exception {
        String response;
        try {
            response = sendGet(url);
        } catch (Exception e) {
            logger.error("Exception at sendUrlAndGetJSON(String): " + e);
            throw e;
        }
        if (response.equals("Response failed")) {
            logger.debug("Response failed at: sendUrlAndGetJSON(String), returning null.");
            return null;
        }
        JSONParser parser = new JSONParser();
        JSONObject obj;
        try {
            obj = (JSONObject) parser.parse(response);
        } catch (ParseException e) {
            logger.error("Exception at sendUrlAndGetJSON(String): " + e);
            throw e;
        }
        return obj;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


}
