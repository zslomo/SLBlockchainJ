package science.bintan.blockchain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import science.bintan.blockchain.entity.BlockchainProperties;
import science.bintan.blockchain.entity.EthAccount;
import science.bintan.blockchain.entity.EthTransaction;
import science.bintan.blockchain.entity.EthTransactionStatus;
import science.bintan.blockchain.repository.EthTransactionRepository;
import science.bintan.blockchain.utils.EthJsonRPC;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.Map;

/**
 * Created by lomo on 2017/10/14.
 */
@Service("EthAccountService")
public class EthTransactionServiceImpl implements EthTransactionService{

    @Autowired
    private BlockchainProperties blockchainProperties;

    @Autowired
    private EthTransactionRepository ethTransactionRepository;

    private String getBcUrl() {
        List<BlockchainProperties.BcInfo> bcInfos = blockchainProperties.getBcInfos();
        BlockchainProperties.BcInfo bcInfo = bcInfos.get(0);
        return bcInfo.getClientUrl();
    }

    @Override
    public void save(EthTransaction ethTransaction) {
        ethTransactionRepository.save(ethTransaction);
    }

    @Override
    public String getByAddress(String addr) {

        ObjectMapper mapper = new ObjectMapper();
        String[] methodParams = {addr};

        String transactionBody = EthJsonRPC.JsonRPC("eth_getTransactionByHash", methodParams, "1", getBcUrl());
        try {
            Map m = mapper.readValue(transactionBody, Map.class);
            EthTransactionStatus status = EthTransactionStatus.SUBMITED;
            if(m.get("blockNumber") == null) status =EthTransactionStatus.PENDING;
            EthTransaction ethTransaction = new EthTransaction(
                    m.get("blockHash").toString(),
                    m.get("blockNumber").toString(),
                    m.get("from").toString(),
                    m.get("gas").toString(),
                    m.get("gasPrice").toString(),
                    m.get("hash").toString(),
                    m.get("input").toString(),
                    m.get("nonce").toString(),
                    m.get("to").toString(),
                    m.get("transactionIndex").toString(),
                    m.get("value").toString(),
                    m.get("data").toString(),
                    m.get("v").toString(),
                    m.get("r").toString(),
                    m.get("s").toString(),
                    status
            );
        }catch (Throwable e){
            e.printStackTrace();
        }

        return transactionBody;
    }

    @Override
    public List<EthTransaction> getAllEthTransaction() {
        return null;
    }

    @Override
    public List<EthTransaction> getEthTransactionBySender(String address) {
        return null;
    }

    @Override
    public List<EthTransaction> getEthTransactionByreceiver(String address) {
        return null;
    }
}
