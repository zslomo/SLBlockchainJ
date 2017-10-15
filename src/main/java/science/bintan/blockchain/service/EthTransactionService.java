package science.bintan.blockchain.service;

import science.bintan.blockchain.entity.EthTransaction;

import java.util.List;

/**
 * Created by lomo on 2017/10/14.
 */
public interface EthTransactionService {

    void save(EthTransaction ethTransaction);

    EthTransaction getTransactionEntityByAddress(String addr);

    String getTransactionByAddress(String addr);

    List<EthTransaction> getAllEthTransaction();

    List<EthTransaction> getEthTransactionBySender(String address);

    List<EthTransaction> getEthTransactionByReceiver(String address);

    String sendTransaction(String fromAddr,String fromPasswd,String gas,String gasPrice,String value,String toAddr,String data);

    String sendTransactionWithoutMining(String fromAddr,String fromPasswd,String gas,String gasPrice,String value,String toAddr,String data);
}
