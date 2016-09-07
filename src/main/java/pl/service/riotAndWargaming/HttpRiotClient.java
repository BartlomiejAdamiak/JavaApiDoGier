package pl.service.riotAndWargaming;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import pl.control.Controller;
import pl.service.HttpWotLolInterface;

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
public class HttpRiotClient extends HttpRiotAndWargaming implements HttpWotLolInterface {

    final static Logger logger = Logger.getLogger(HttpRiotClient.class);
    private final static String API_URL_GET_PLAYER_ID_PART_1 = "https://eune.api.pvp.net/api/lol/eune/v1.4/summoner/by-name/";
    private final static String API_URL_GET_PLAYER_ID_PART_2 = "?api_key=RGAPI-1C2FC95A-EA14-425B-BBC6-B99DFCDA3F7D";
    private final static String API_URL_GET_STATISTICS_BY_ID_PART_1 = "https://eune.api.pvp.net/api/lol/eune/v1.3/stats/by-summoner/";
    private final static String API_URL_GET_STATISTICS_BY_ID_PART_2 = "/summary?season=SEASON2015&api_key=RGAPI-1C2FC95A-EA14-425B-BBC6-B99DFCDA3F7D";

    public HttpRiotClient(String playerName) {
        findPlayerByName(playerName);
    }

    public HttpRiotClient() {

    }

    public String getPlayerId(String name) {
        try {
            JSONObject obj = sendUrlAndGetJSON(API_URL_GET_PLAYER_ID_PART_1 + name + API_URL_GET_PLAYER_ID_PART_2);
            JSONObject innerObj = (JSONObject) obj.get(name.toLowerCase());
            return innerObj.get("id").toString();
        } catch (Exception e) {
            logger.error("Exception at getPlayerId(String): " + e);
        }
        return null;
    }

    protected JSONObject getJSONStatisticsOfPlayerById(String id) {
        JSONObject obj;
        try {
            obj = sendUrlAndGetJSON(API_URL_GET_STATISTICS_BY_ID_PART_1 + id + API_URL_GET_STATISTICS_BY_ID_PART_2);
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
        } catch (Exception e) {
            logger.error("Exception at getJSONStatisticsOfPlayerById(String): " + e);
            Controller.controllerInstance.exceptionOccured(e);
        }
        return null;
    }

    @Override
    public Integer getKills(JSONObject statistics) {
        JSONObject aggregatedStats = (JSONObject) statistics.get("aggregatedStats");
        return Integer.parseInt(aggregatedStats.get("totalChampionKills").toString());
    }
}
