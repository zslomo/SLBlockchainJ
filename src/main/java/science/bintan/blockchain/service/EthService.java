package science.bintan.blockchain.service;

import science.bintan.blockchain.entity.Block;
import science.bintan.blockchain.entity.EthAccount;
import science.bintan.blockchain.entity.EthTransaction;
import java.util.List;
/**
 * Created by lomo on 2017/10/11.
 */
public interface EthService {
    //account
    Boolean unlockEthAccount();
    EthAccount getEthAccount();
    int getBalance(String addr);

    //block
    String getBlockByNumber(int number);
    Block getBlockByHash(String hash);
    Block getCurrentBlock();

    //transaction
    List<EthTransaction> getAllEthTransaction();
    List<EthTransaction> getEthTransactionBySender(String address);
    List<EthTransaction> getEthTransactionByreceiver(String address);

}
