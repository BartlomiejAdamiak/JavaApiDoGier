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
 * Created by kaima_000 on 2016-09-05.
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
