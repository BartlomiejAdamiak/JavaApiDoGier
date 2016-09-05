package pl.service.valve;

import pl.model.Player;

/**
 * Created by Adam on 2016-09-03.
 */
public interface HttpValveInterface {
    void findPlayerById(String playerId) throws Exception;
    Player getPlayer();
    void setPlayer(Player player);
}
