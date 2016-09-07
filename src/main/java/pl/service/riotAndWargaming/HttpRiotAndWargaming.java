package pl.service.riotAndWargaming;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
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
@Component
public abstract class HttpRiotAndWargaming extends HttpClient {

    final static Logger logger = Logger.getLogger(HttpRiotAndWargaming.class);

    public Player findPlayerByName(String name) {
        player = new Player();
        player.setName(name);
        player.setId(getPlayerId(name));
        if (!(player.getId() == null)) getStatistics(player);

        return player;
    }

    private void getStatistics(Player player) {
        JSONObject statistics = getJSONStatisticsOfPlayerById(player.getId());
        player.setKills(getKills(statistics));
        player.setWins(getWins(statistics));
        player.setLosses(getLosses(statistics));
    }

    public abstract String getPlayerId(String name);

    protected abstract JSONObject getJSONStatisticsOfPlayerById(String id);
}
