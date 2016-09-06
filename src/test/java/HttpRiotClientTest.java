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
import pl.service.riotAndWargaming.HttpRiotClient;


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
    public void testFindByName()
    {
        Player testPlayer = null;
        try {
            testObject.findPlayerByName("SIPIAIM");
            testPlayer = testObject.player;
        } catch (Exception e) {
            fail();
        }

        assertEquals("SIPIAIM",testPlayer.getName());

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



}
