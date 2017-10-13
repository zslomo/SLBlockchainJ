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
    //miner
    String minerStart(int threads);
    String minerStop();
    String getMiningStatus();

    //account
    EthAccount newAccount(String password, User user);
    String unlockEthAccount(String addr,String password);
    List<EthAccount> getEthAccount();
    String getBalance(String addr);

    //block
    String getBlockByNumber(int number);
    String getBlockByHash(String hash);
    String getCurrentBlock();
    String getblockNumber();

    //transaction
    String sendTansaction(String fromAddr,String fromPasswd,String gas,String gasPrice,String value,String toAddr,String data);
    List<EthTransaction> getAllEthTransaction();
    List<EthTransaction> getEthTransactionBySender(String address);
    List<EthTransaction> getEthTransactionByreceiver(String address);

    //filter
    String setNewBlocFilter();
    String setNewPendingTransactionFilter();
    String getFilterChanges(String addr);

}
