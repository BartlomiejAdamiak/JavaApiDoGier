package pl.service.valve;

import pl.model.Player;

/**
 * Created by Adam on 2016-09-03.
 */
public interface HttpValveInterface {
    Player findPlayerById(String playerId);
}
