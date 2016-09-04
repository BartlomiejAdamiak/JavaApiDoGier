/**
 * Created by QDL on 2016-09-04.
 */

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import pl.model.Player;
import pl.service.wargaming.HttpWargamingClient;
import org.junit.Test;
import org.junit.Assert;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class MockAutowireTest
{
    @Mock
    private Player mockPlayer;


    @InjectMocks
    private HttpWargamingClient testObject = new HttpWargamingClient();


//    @Before
//    public void initMocs(){
//        MockitoAnnotations.initMocks(this);
//    }

    @Test
    public void testAutowire()
    {

//        HttpWargamingClient testObject = new HttpWargamingClient("kaimada");
        //HttpWargamingClient testObject = new HttpWargamingClient();
        assertNotNull(testObject);
        assertNotNull(testObject.player);
    }
}
