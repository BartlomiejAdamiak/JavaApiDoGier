package pl.service.riotAndWargaming;
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

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
import pl.Control.Controller;
import pl.service.HttpWotLolInterface;

@Component
public class HttpWargamingClient extends HttpRiotAndWargaming implements HttpWotLolInterface {

    final static Logger logger = Logger.getLogger(HttpWargamingClient.class);
    private final static String API_URL_GET_PLAYER_ID = "https://api.worldoftanks.eu/wot/account/list/?application_id=demo&search=";
    private final static String API_URL_GET_STATISTICS_BY_ID = "https://api.worldoftanks.eu/wot/account/info/?application_id=demo&account_id=";

    public HttpWargamingClient(String playerName) {
        findPlayerByName(playerName);
    }

    public HttpWargamingClient() {

    }

    public String getPlayerId(String name) {
        JSONObject obj;
        try {
            obj = sendUrlAndGetJSON(API_URL_GET_PLAYER_ID + name + "&limit=1");
            JSONArray data = (JSONArray) obj.get("data");
            JSONObject firstObjectOfArray = (JSONObject) data.get(0);
            return firstObjectOfArray.get("account_id").toString();
        } catch (Exception e) {
            logger.error("Exception at getPlayerId(String): " + e);
        }
        return null;
    }

    protected JSONObject getJSONStatisticsOfPlayerById(String id) {
        JSONObject obj;
        try {
            obj = sendUrlAndGetJSON(API_URL_GET_STATISTICS_BY_ID + id);
            JSONObject data = (JSONObject) obj.get("data");
            JSONObject idValue = (JSONObject) data.get(id);
            JSONObject statistics = (JSONObject) idValue.get("statistics");
            JSONObject all = (JSONObject) statistics.get("all");
            return all;
        } catch (Exception e) {
            logger.error("Exception at getJSONStatisticsOfPlayerById(String): " + e);
            Controller.controllerInstance.exceptionOccured(e);
        }
        return null;
    }

    @Override
    public Integer getKills(JSONObject statistics) {
        return Integer.parseInt(statistics.get("frags").toString());
    }
}
