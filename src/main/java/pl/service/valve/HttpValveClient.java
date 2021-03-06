package pl.service.valve;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import pl.model.Player;
import pl.service.HttpClient;

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
@Service
public abstract class HttpValveClient extends HttpClient implements HttpValveInterface {
    final static Logger logger = Logger.getLogger(HttpValveClient.class);
    private final static String API_URL_GET_NAME = "http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=CEA96357A7E047331D22006B6D36D003&steamids=";

    public HttpValveClient() {
    }

    public Player findPlayerById(String playerId) throws Exception {
        player = new Player();
        JSONObject statistics = getJSONStatisticsOfPlayerById(playerId);
        player.setId(playerId);
        player.setName(getName(playerId));
        player.setKills(getKills(statistics));
        player.setWins(getWins(statistics));
        player.setLosses(getLosses(statistics));

        return player;
    }

    public String getName(String playerId) throws Exception {
        JSONObject obj = sendUrlAndGetJSON(API_URL_GET_NAME + playerId);

        JSONObject response = (JSONObject) obj.get("response");
        JSONArray playersArray = (JSONArray) response.get("players");

        JSONObject playerFound = (JSONObject) playersArray.get(0);

        return (String) playerFound.get("personaname");
    }

    public abstract JSONObject getJSONStatisticsOfPlayerById(String id);
}
