package pl.service.valve;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

/**
 JavaApiDoGier - program służący do przedstawiania statystyk gracza
 Copyright (C) 19../20.. Bartłomiej Adamiak, Adam Szczeciński,
 Michał Kudlewski, Beata Cabaj

 Niniejszy program jest wolnym oprogramowaniem; możesz go
 rozprowadzać dalej i/lub modyfikować na warunkach Powszechnej
 Licencji Publicznej GNU, wydanej przez Fundację Wolnego
 Oprogramowania - według wersji 2-giej tej Licencji lub którejś
 z późniejszych wersji.

 Niniejszy program rozpowszechniany jest z nadzieją, iż będzie on
 użyteczny - jednak BEZ JAKIEJKOLWIEK GWARANCJI, nawet domyślnej
 gwarancji PRZYDATNOŚCI HANDLOWEJ albo PRZYDATNOŚCI DO OKREŚLONYCH
 ZASTOSOWAŃ. W celu uzyskania bliższych informacji - Powszechna
 Licencja Publiczna GNU.

 Z pewnością wraz z niniejszym programem otrzymałeś też egzemplarz
 Powszechnej Licencji Publicznej GNU (GNU General Public License);
 jeśli nie - napisz do Free Software Foundation, Inc., 675 Mass Ave,
 Cambridge, MA 02139, USA.
 */
@Component
public class HttpL4D2Client extends HttpValveClient implements HttpValveInterface{

    final static Logger logger = Logger.getLogger(HttpL4D2Client.class);
    private final static String API_URL_GET_STATISTICS_BY_ID = "https://api.steampowered.com/ISteamUserStats/GetUserStatsForGame/v0002/?appid=550&key=CEA96357A7E047331D22006B6D36D003&steamid=";

    public HttpL4D2Client() {
    }

    public JSONObject getJSONStatisticsOfPlayerById(String id) {
        JSONObject obj;
        try {
            obj = sendUrlAndGetJSON(API_URL_GET_STATISTICS_BY_ID + id);
            JSONObject playerstats = (JSONObject) obj.get("playerstats");
            JSONArray statsarray = (JSONArray) playerstats.get("stats");
            long wins = 0;
            long totalMatches = 0;

            JSONObject statsToReturn = new JSONObject();
            for (Object anArray : statsarray) {
                JSONObject onePositionOfArray = (JSONObject) anArray;
                if (onePositionOfArray.get("name").equals("Stat.InfectedKilled.Total")) statsToReturn.put("kills", onePositionOfArray.get("value"));
                else if (onePositionOfArray.get("name").equals("Stat.FinaleFinished.Total")) {
                    wins = (long) onePositionOfArray.get("value");
                    statsToReturn.put("wins", wins);
                } else if (onePositionOfArray.get("name").equals("Stat.GamesPlayed.Total")) totalMatches = (long) onePositionOfArray.get("value");
            }
            statsToReturn.put("losses",totalMatches - wins);

            return statsToReturn;
        } catch (Exception e) {
            logger.error("Exception at getJSONStatisticsOfPlayerById(String): " + e);
        }
        return null;
    }

}
