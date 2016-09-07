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
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import pl.model.Player;
import pl.service.riotAndWargaming.HttpRiotClient;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpRiotClientTest {
    @Spy
    private Player mockPlayer;


    @InjectMocks
    private HttpRiotClient testObject = new HttpRiotClient();


    @Test
    public void testFindByName() {
        Player testPlayer = null;
        try {
            testObject.findPlayerByName("SIPIAIM");
            testPlayer = testObject.player;
        } catch (Exception e) {
            fail();
        }

        assertEquals("SIPIAIM", testPlayer.getName());

        assertEquals((Integer) 15, testPlayer.getWins());
        assertEquals((Integer) 6, testPlayer.getLosses());
        assertEquals((Integer) 147, testPlayer.getKills());
    }

    @Test
    public void testGetKills()
    {
        JSONObject innerJSON = new JSONObject();
        innerJSON.put("totalChampionKills",147);
        innerJSON.put("totalMinionKills",2507);
        innerJSON.put("totalAssists",180);
        innerJSON.put("totalNeutralMinionsKilled",690);
        innerJSON.put("totalTurretsKilled",28);

        JSONObject mockObject = new JSONObject();
        mockObject.put("wins","15");
        mockObject.put("modifyDate","1448635784000");
        mockObject.put("aggregatedStats",innerJSON);
        mockObject.put("losses","6");
        mockObject.put("playerStatSummaryType","RankedSolo5x5");

        Integer testResult = testObject.getKills(mockObject);

        assertEquals((Integer) 147,testResult);
    }

    @Test
    public void testGetJSONStatisticsOfPlayerById()
    {
        JSONObject innerJSON = new JSONObject();
        innerJSON.put("totalChampionKills",147);
        innerJSON.put("totalMinionKills",2507);
        innerJSON.put("totalAssists",180);
        innerJSON.put("totalNeutralMinionsKilled",690);
        innerJSON.put("totalTurretsKilled",28);

        JSONObject mockObject = new JSONObject();
        mockObject.put("wins","15");
        mockObject.put("modifyDate","1448635784000");
        mockObject.put("aggregatedStats",innerJSON);
        mockObject.put("losses","6");
        mockObject.put("playerStatSummaryType","RankedSolo5x5");

        JSONArray array = new JSONArray();
        array.add(mockObject);

        JSONObject topObject = new JSONObject();
        topObject.put("summonerID",19940663);
        topObject.put("playerStatSummaries",array);


        HttpRiotClient spy = Mockito.spy(HttpRiotClient.class);

        try
        {
            when(spy.sendUrlAndGetJSON("https://eune.api.pvp.net/api/lol/eune/v1.3/stats/by-summoner/" +
                    "19940663" + "/summary?season=SEASON2015&api_key=RGAPI-1C2FC95A-EA14-425B-BBC6-B99DFCDA3F7D")).thenReturn(topObject);
        }
        catch(Exception e){
            fail();
        }



        JSONObject returnObject = spy.getJSONStatisticsOfPlayerById("19940663");

        assertEquals("15",returnObject.get("wins"));
        assertEquals("RankedSolo5x5",returnObject.get("playerStatSummaryType"));
        assertEquals(28, ((JSONObject) returnObject.get("aggregatedStats")).get("totalTurretsKilled")  );
    }

    @Test
    public void testGetStatistics()
    {
        JSONObject innerJSON = new JSONObject();
        innerJSON.put("totalChampionKills",147);
        innerJSON.put("totalMinionKills",2507);
        innerJSON.put("totalAssists",180);
        innerJSON.put("totalNeutralMinionsKilled",690);
        innerJSON.put("totalTurretsKilled",28);

        JSONObject mockObject = new JSONObject();
        mockObject.put("wins","15");
        mockObject.put("modifyDate","1448635784000");
        mockObject.put("aggregatedStats",innerJSON);
        mockObject.put("losses","6");
        mockObject.put("playerStatSummaryType","RankedSolo5x5");


        HttpRiotClient spy = Mockito.spy(HttpRiotClient.class);
        when(spy.getJSONStatisticsOfPlayerById("19940663")).thenReturn(mockObject);

        Player testPlayer = new Player();
        //testPlayer.setName("SIPIAIM");
        testPlayer.setId("19940663");
        spy.getStatistics(testPlayer);

        assertEquals((Integer) 15,testPlayer.getWins());
        assertEquals((Integer) 6,testPlayer.getLosses());
        assertEquals((Integer) 147,testPlayer.getKills());
    }

}
