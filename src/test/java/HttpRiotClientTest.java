/**
 * Created by QDL on 2016-09-04.
 */

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import pl.model.Player;
import pl.service.riot.HttpRiotClient;
import pl.service.wargaming.HttpWargamingClient;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HttpRiotClientTest
{
    @Spy
    private Player mockPlayer;


    @InjectMocks
    private HttpRiotClient testObject = new HttpRiotClient();



    @Test
    public void testFindById()
    {
        Player testPlayer = testObject.findPlayerById("19940663");

        //assertEquals("SIPIAIM",testPlayer.getName()); //jak się okazuje, klient nie pobiera nazwy użytkownika
        assertEquals((Integer) 15,testPlayer.getWins());
        assertEquals((Integer) 6,testPlayer.getLosses());
        assertEquals((Integer) 147,testPlayer.getKills());
    }



    @Test
    public void testFindByName()
    {
        Player testPlayer = testObject.findPlayerByName("SIPIAIM");

        assertEquals("SIPIAIM",testPlayer.getName());

        assertEquals((Integer) 15,testPlayer.getWins());
        assertEquals((Integer) 6,testPlayer.getLosses());
        assertEquals((Integer) 147,testPlayer.getKills());
    }



    //Sprawdza czy getStatistics poprawnie przypisuje otrzymane dane z JSONa do Playera
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
        testPlayer = spy.getStatistics(testPlayer);

        assertEquals((Integer) 15,testPlayer.getWins());
        assertEquals((Integer) 6,testPlayer.getLosses());
        assertEquals((Integer) 147,testPlayer.getKills());
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
        when(spy.sendUrlAndGetJSON("https://eune.api.pvp.net/api/lol/eune/v1.3/stats/by-summoner/" +
                "19940663" + "/summary?season=SEASON2015&api_key=RGAPI-1C2FC95A-EA14-425B-BBC6-B99DFCDA3F7D")).thenReturn(topObject);



        JSONObject returnObject = spy.getJSONStatisticsOfPlayerById("19940663");

        assertEquals("15",returnObject.get("wins"));
        assertEquals("RankedSolo5x5",returnObject.get("playerStatSummaryType"));
        assertEquals(28, ((JSONObject) returnObject.get("aggregatedStats")).get("totalTurretsKilled")  );
    }

}
