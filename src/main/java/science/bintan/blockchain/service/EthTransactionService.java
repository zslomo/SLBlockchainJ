package science.bintan.blockchain.service;

import science.bintan.blockchain.entity.EthTransaction;

import java.util.List;

/**
 * Created by lomo on 2017/10/14.
 */
public interface EthTransactionService {

    void save(EthTransaction ethTransaction);

    String getByAddress(String addr);

    List<EthTransaction> getAllEthTransaction();

    List<EthTransaction> getEthTransactionBySender(String address);

    List<EthTransaction> getEthTransactionByreceiver(String address);
}
