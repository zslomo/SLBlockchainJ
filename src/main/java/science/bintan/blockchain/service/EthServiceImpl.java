package science.bintan.blockchain.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import science.bintan.blockchain.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
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

    @Autowired
    private BlockchainProperties blockchainProperties;

    @Autowired
    private EthAccountService ethAccountService;

    @Autowired
    private UserService userService;

    private String getBcUrl(){
        List<BlockchainProperties.BcInfo> bcInfos = blockchainProperties.getBcInfos();
        BlockchainProperties.BcInfo bcInfo = bcInfos.get(0);
        return bcInfo.getClientUrl();
    }


    //private static final String bcUrl ="http://101.132.144.50:8545";

    @Override
    public EthAccount newAccount(String password,User user) {

        String[] methodParams = { password };

        String addr =  EthJsonRPC.JsonRPC("personal_newAccount",methodParams,"1",getBcUrl());

        EthAccount ethAccount = new EthAccount();
        ethAccount.setAddress(addr);
        ethAccount.setPassword(password);
        try {
            ethAccountService.save(ethAccount);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        try {
            user.getEthAccounts().add(ethAccount);
            userService.save(user);
        }catch (Exception e){
            logger.error(e.getMessage());
        }

        return ethAccount;

    }

    @Override
    public String unlockEthAccount(String addr,String password) {
        String[] methodParams = { addr, password};

        return EthJsonRPC.JsonRPC("personal_unlockAccount",methodParams,"1",getBcUrl());


    }

    @Override
    public List<EthAccount> getEthAccount() {
        return null;
    }

    @Override
    public int getBalance(String addr) {

        String[] methodParams = { addr ,"latest"};

        String balanceResultBody = EthJsonRPC.JsonRPC("eth_getBalance",methodParams,"1",getBcUrl());

        return Integer.parseInt(balanceResultBody);
    }

    @Override
    public String getBlockByNumber(int number) {

        /*Block blockRet = new Block();

        ObjectMapper mapper = new ObjectMapper();*/

        String[] methodParams = {"0x"+Integer.toHexString(number),"true"};

       return EthJsonRPC.JsonRPC("eth_getBlockByNumber",methodParams,"1",getBcUrl());
        /*try {
            //blockRet = mapper.readValue(blockResultBody, Block.class);
        }catch (Exception e) {
            logger.error(e.getMessage());
        }*/

    }

    @Override
    public String getBlockByHash(String hash) {

        String[] methodParams = { hash ,"true"};

        return EthJsonRPC.JsonRPC("eth_getBlockByHash",methodParams,"1",getBcUrl());

    }

    @Override
    public String getCurrentBlock() {

        String[] methodParams = {"latest","true"};

        return EthJsonRPC.JsonRPC("eth_getBlockByNumber",methodParams,"1",getBcUrl());


    }

    @Override
    public int getblockNumber() {

        ObjectMapper mapper = new ObjectMapper();

        String[] methodParams = {};

        String resp = EthJsonRPC.JsonRPC("eth_blockNumber",methodParams,"1",getBcUrl());

        return Integer.parseInt(resp.split("x")[1].replace("\"",""));

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
