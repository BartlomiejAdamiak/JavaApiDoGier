package pl;

import pl.model.Player;

/**
 * Created by kaima_000 on 2016-08-29.
 */
public interface HttpClientInterface {
    Player findPlayer(String gameName, String playerId);
}
