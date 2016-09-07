import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;
import pl.model.Player;
import pl.service.valve.HttpCSGOClient;
import pl.service.valve.HttpValveClient;
import pl.service.HttpClient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
public class TestHttpValveClient {
    @Test
    public void successFindPlayerByName(){
        HttpValveClient httpValveClient = new HttpCSGOClient();

        Player player = null;
        try {
            httpValveClient.findPlayerById("76561197990828076");
            player = httpValveClient.getPlayer();
        } catch (Exception e) {
            fail();
        }
        assertEquals(player.getName(),"kaimada");
        assertEquals(player.getKills(),(Integer) 58642);
        assertEquals(player.getWins(),(Integer) 1420);
        assertEquals(player.getLosses(),(Integer) 1673);
    }

}
