/**
 * Created by QDL on 2016-09-04.
 */

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import pl.model.Player;
import pl.service.riotAndWargaming.HttpRiotClient;


import static org.junit.Assert.*;

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

}
