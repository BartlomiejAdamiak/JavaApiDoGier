import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;
import pl.model.Player;
import pl.service.riotAndWargaming.HttpRiotClient;
import pl.service.valve.HttpCSGOClient;
import pl.service.valve.HttpValveClient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

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
public class TestHttpValveClient {
    @Test
    public void successFindPlayerByName() {
        HttpValveClient httpValveClient = new HttpCSGOClient();

        Player player = null;
        try {
            httpValveClient.findPlayerById("76561197968656365");
            player = httpValveClient.getPlayer();
        } catch (Exception e) {
            fail();
        }
        assertEquals("S.P.A.M",player.getName());
        assertEquals( (Integer) 12411,player.getKills());
        assertEquals( (Integer) 269, player.getWins());
        assertEquals( (Integer) 446, player.getLosses());
    }

    @Test
    public void testGetName() {
        HttpValveClient httpValveClient = new HttpCSGOClient();


        String result = null;
        try {
            result = httpValveClient.getName("76561197968656365");
        } catch (Exception e) {
            fail();
        }
        assertEquals("S.P.A.M",result);
    }

    @Test
    public void testGetJSONStatisticsOfPlayerById()
    {
        JSONObject innerJSON1 = new JSONObject();
        JSONObject innerJSON2 = new JSONObject();
        JSONObject innerJSON3 = new JSONObject();

        innerJSON1.put("name","total_kills");
        innerJSON1.put("value",12411L);
        innerJSON2.put("name","total_matches_won");
        innerJSON2.put("value",269L);
        innerJSON3.put("name","total_matches_played");
        innerJSON3.put("value",446L);

        JSONArray array = new JSONArray();
        array.add(innerJSON1);
        array.add(innerJSON2);
        array.add(innerJSON3);

        JSONObject topObject = new JSONObject();
        topObject.put("steamId",(Long) 76561197968656365L);
        topObject.put("achievements",null);     //size 108, nothx
        topObject.put("gameName","ValveTestApp260");
        topObject.put("stats",array);

        JSONObject topperObject = new JSONObject();
        topperObject.put("playerstats",topObject);

        HttpValveClient spy = Mockito.spy(HttpCSGOClient.class);

        try
        {
            when(spy.sendUrlAndGetJSON("https://api.steampowered.com/ISteamUserStats/GetUserStatsForGame/v0002/?appid=730&key=CEA96357A7E047331D22006B6D36D003&steamid=" +
                    "76561197968656365")).thenReturn(topperObject);
        }
        catch(Exception e){
            fail();
        }


        //76561197968656365
        JSONObject returnObject = spy.getJSONStatisticsOfPlayerById("76561197968656365");

        assertEquals(12411L,returnObject.get("kills"));
        assertEquals(269L,returnObject.get("wins"));
        assertEquals(177L, returnObject.get("losses"));
    }

}
