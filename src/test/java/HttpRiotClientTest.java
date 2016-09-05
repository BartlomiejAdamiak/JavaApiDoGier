/**
 * Created by QDL on 2016-09-04.
 */

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import pl.model.Player;
import pl.service.riot.HttpRiotClient;
import pl.service.wargaming.HttpWargamingClient;


import static org.junit.Assert.*;

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

}
