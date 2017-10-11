package science.bintan.blockchain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import science.bintan.blockchain.entity.BlockchainProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import science.bintan.blockchain.entity.Block;
import science.bintan.blockchain.entity.EthAccount;
import science.bintan.blockchain.entity.EthTransaction;
import science.bintan.blockchain.utils.EthJsonRPC;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by lomo on 2017/10/11.
 */
@Service("EthService")
@EnableConfigurationProperties({BlockchainProperties.class})
public class EthServiceImpl implements EthService {
    private static final Logger logger = LoggerFactory.getLogger(EthServiceImpl.class);

    //@Autowired
    //private BlockchainProperties blockchainProperties;

    //List<BlockchainProperties.BcInfo> bcInfos = blockchainProperties.getBcInfos();
    //BlockchainProperties.BcInfo bcInfo = bcInfos.get(0);

    private static final String bcUrl ="http://101.132.144.50:8545";

    @Override
    public Boolean unlockEthAccount() {
        return null;
    }

    @Override
    public EthAccount getEthAccount() {
        return null;
    }

    @Override
    public int getBalance(String addr) {

        ObjectMapper mapper = new ObjectMapper();

        String[] methodParams = {addr,"true"};

        String balanceResultBody = EthJsonRPC.JsonRPC("eth_getBlockByNumber",methodParams,"1",bcUrl);

        return Integer.parseInt(balanceResultBody);
    }

    @Override
    public String getBlockByNumber(int number) {

        Block blockRet = new Block();

        ObjectMapper mapper = new ObjectMapper();

        String[] methodParams = {"0x"+Integer.toHexString(number),"true"};

        String blockResultBody = EthJsonRPC.JsonRPC("eth_getBlockByNumber",methodParams,"1",bcUrl);
        try {
            //blockRet = mapper.readValue(blockResultBody, Block.class);
        }catch (Exception e) {
            logger.error(e.getMessage());
        }
        return blockResultBody;
    }

    @Override
    public Block getBlockByHash(String hash) {
        Block blockRet = new Block();

        ObjectMapper mapper = new ObjectMapper();

        String[] methodParams = { hash ,"true"};

        String blockResultBody = EthJsonRPC.JsonRPC("eth_getBlockByHash",methodParams,"1",bcUrl);
        try {
            blockRet = mapper.readValue(blockResultBody, Block.class);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return blockRet;
    }

    @Override
    public Block getCurrentBlock() {
        Block blockRet = new Block();

        ObjectMapper mapper = new ObjectMapper();

        String[] methodParams = {"latest","true"};

        String blockResultBody = EthJsonRPC.JsonRPC("eth_getBlockByNumber",methodParams,"1",bcUrl);
        try {
            blockRet = mapper.readValue(blockResultBody, Block.class);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return blockRet;
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
