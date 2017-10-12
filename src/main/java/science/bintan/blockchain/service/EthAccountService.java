package science.bintan.blockchain.service;

import science.bintan.blockchain.entity.EthAccount;
import science.bintan.blockchain.entity.User;

/**
 * Created by lomo on 2017/10/12.
 */
public interface EthAccountService {

    void save(EthAccount ethAccount);

    void update(EthAccount ethAccount);

    void delete(EthAccount ethAccount);

    EthAccount getByAddress(String address);

}
