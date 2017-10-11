package science.bintan.blockchain.service;

import science.bintan.blockchain.entity.User;
/**
 * Created by lomo on 2017/10/6.
 */

public interface RegisterService {

    void save(User user);

    String register(User user);
}
