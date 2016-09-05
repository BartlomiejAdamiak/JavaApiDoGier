package pl.service;

import org.springframework.stereotype.Component;
import pl.model.Player;

/**
 * Created by Adam on 2016-09-03.
 */
@Component
public interface HttpWotLolInterface {
    void findPlayerByName(String name) throws Exception;
    Player getPlayer();
    void setPlayer(Player player);
}
