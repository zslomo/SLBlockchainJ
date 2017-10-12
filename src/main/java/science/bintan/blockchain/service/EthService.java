package science.bintan.blockchain.service;

import science.bintan.blockchain.entity.Block;
import science.bintan.blockchain.entity.EthAccount;
import science.bintan.blockchain.entity.EthTransaction;
import science.bintan.blockchain.entity.User;

import java.util.List;
/**
 * Created by lomo on 2017/10/11.
 */
public interface EthService {

    //account
    EthAccount newAccount(String password, User user);
    String unlockEthAccount(String addr,String password);
    List<EthAccount> getEthAccount();
    int getBalance(String addr);

    //block
    String getBlockByNumber(int number);
    String getBlockByHash(String hash);
    String getCurrentBlock();
    int getblockNumber();

    //transaction
    List<EthTransaction> getAllEthTransaction();
    List<EthTransaction> getEthTransactionBySender(String address);
    List<EthTransaction> getEthTransactionByreceiver(String address);

}
