package pl.service;

import org.springframework.stereotype.Component;
import pl.model.Player;

/**
 * Created by Adam on 2016-09-03.
 */
@Component
public interface HttpWotLolInterface {
    Player findPlayerByName(String name);
}
