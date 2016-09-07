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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import pl.model.Player;
import pl.service.riotAndWargaming.HttpRiotClient;
import pl.service.riotAndWargaming.HttpWargamingClient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class TestHttpWargamingClient {


    //    @Before
//    public void prepareStatistics(){
//        statistics.put("frags",67);
//        statistics.put("wins",86);
//        statistics.put("losses",94);
//    }

    private HttpWargamingClient warClient = new HttpWargamingClient();


    @Test
    public void TestFindPlayerByName()
    {
        Player testPlayer = null;
        try {
            warClient.findPlayerByName("kaimada");
            testPlayer = warClient.player;
        } catch (Exception e) {
            fail();
        }


        assertEquals( "502346805", testPlayer.getId());
        assertEquals( "kaimada", testPlayer.getName());
        assertEquals( (Integer) 67, testPlayer.getKills());
        assertEquals( (Integer) 86, testPlayer.getWins());
        assertEquals( (Integer) 94, testPlayer.getLosses());
    }

    @Test
    public void testGetKills()
    {
        JSONObject mockObject = new JSONObject();
        mockObject.put("frags","67");

        Integer testResult = warClient.getKills(mockObject);

        assertEquals((Integer) 67,testResult);
    }

    @Test
    public void testGetJSONStatisticsOfPlayerById()
    {
        JSONObject firstJSON = new JSONObject();
            JSONObject innerJSON = new JSONObject();
                JSONObject innerInnerJSON = new JSONObject();
                    JSONObject innermostJSON = new JSONObject();
            innerJSON.put("private",null);
            innerJSON.put("ban_time",null);
            innerJSON.put("created_at",1328554689);
            innerJSON.put("client_language","pl");
            innerJSON.put("ban_info",null);
            innerJSON.put("account_id",502346805);
            innerJSON.put("updated_at",1466640375);
            innerJSON.put("nickname","kaimada");
            innerJSON.put("clan_id",null);
            innerJSON.put("last_battle_time",1432139385);
            innerJSON.put("global_rating",775);
            innerJSON.put("logout_at",1432139474);
                    innermostJSON.put("losses",94);
                    innermostJSON.put("frags",67);
                    innermostJSON.put("wins",67);
                innerInnerJSON.put("all",innermostJSON);
            innerJSON.put("statistics",innerInnerJSON);
        firstJSON.put("502346805",innerJSON);


        JSONObject secondJSON = new JSONObject();

        JSONArray array = new JSONArray();

        JSONObject topObject = new JSONObject();
        topObject.put("data",firstJSON);
        topObject.put("meta",secondJSON);
        topObject.put("status","ok");

        HttpWargamingClient spy = Mockito.spy(HttpWargamingClient.class);

        try
        {
            when(spy.sendUrlAndGetJSON("https://api.worldoftanks.eu/wot/account/info/?application_id=demo&account_id=" +
                    "502346805")).thenReturn(topObject);
        }
        catch(Exception e){
            fail();
        }



        JSONObject returnObject = spy.getJSONStatisticsOfPlayerById("502346805");

        assertEquals((Integer) 67,returnObject.get("wins"));
        assertEquals((Integer) 94,returnObject.get("losses"));
        assertEquals((Integer) 67,returnObject.get("frags"));
    }


}
