/**
 * Created by kaima_000 on 2016-06-21.
 */
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class TestHttpWargamingClient {


//    @Before
//    public void prepareStatistics(){
//        statistics.put("frags",67);
//        statistics.put("wins",86);
//        statistics.put("losses",94);
//    }
    @Test
    public void successFindPlayerByName(){
        JSONObject statistics = new JSONObject();
        statistics.put("frags",67);
        statistics.put("wins",86);
        statistics.put("losses",94);
        HttpWargamingClient c = mock(HttpWargamingClient.class);
        when(c.getJSONStatisticsOfPlayerById(anyInt())).thenReturn(statistics);
        when(c.findPlayerByName(anyString())).thenCallRealMethod();
        when(c.getKills(any(JSONObject.class))).thenCallRealMethod();
        when(c.getWins(any(JSONObject.class))).thenCallRealMethod();
        when(c.getLosses(any(JSONObject.class))).thenCallRealMethod();

        WorldOfTanksPlayer player = c.findPlayerByName("kaimada");
        assertEquals(player.getId(),(Integer) 502346805);
        assertEquals(player.getName(),(String) "kaimada");
        assertEquals(player.getKills(),(Integer) 67);
        assertEquals(player.getWins(),(Integer) 86);
        assertEquals(player.getLosses(),(Integer) 94);

    }
}
