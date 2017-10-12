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

    private String getBcUrl() {
        List<BlockchainProperties.BcInfo> bcInfos = blockchainProperties.getBcInfos();
        BlockchainProperties.BcInfo bcInfo = bcInfos.get(0);
        return bcInfo.getClientUrl();
    }


    //private static final String bcUrl ="http://101.132.144.50:8545";

    @Override
    public String minerStart(int threads) {

        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("jsonrpc", "2.0");
        params.put("method", "miner_start");
        params.put("params", threads);
        params.put("id", 1);

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String requsetJson = mapper.writeValueAsString(params);
            HttpEntity<String> request = new HttpEntity<String>(requsetJson, headers);

            ResponseEntity<String> blockResult = restTemplate.exchange(getBcUrl(), HttpMethod.POST, request, String.class);
            String blockResultBody = blockResult.getBody();
            if (blockResult.getStatusCode() == HttpStatus.OK) {

                JsonNode jsonNode = mapper.readTree(blockResultBody);

                if(jsonNode.get("error") != null) return "#"+jsonNode.get("error").toString();

                else return jsonNode.get("result").toString();
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return "error";
    }

    @Override
    public String minerStop() {
        String[] methodParams = {};
        return EthJsonRPC.JsonRPC("miner_stop", methodParams, "1", getBcUrl());
    }

    @Override
    public String getMiningStatus() {
        String[] methodParams = {};
        return EthJsonRPC.JsonRPC("eth_mining", methodParams, "1", getBcUrl());
    }

    @Override
    public EthAccount newAccount(String password, User user) {

        String[] methodParams = {password};

        String addr = EthJsonRPC.JsonRPC("personal_newAccount", methodParams, "1", getBcUrl());

        EthAccount ethAccount = new EthAccount();

        if (addr.charAt(0) == '#') {
            ethAccount.setErrorMessage(addr.substring(1));
            return ethAccount;
        }
        ethAccount.setAddress(addr);
        ethAccount.setPassword(password);

        try {
            ethAccountService.save(ethAccount);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        try {
            user.getEthAccounts().add(ethAccount);
            userService.save(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return ethAccount;

    }

    @Override
    public String unlockEthAccount(String addr, String password) {
        String[] methodParams = {addr, password};

        return EthJsonRPC.JsonRPC("personal_unlockAccount", methodParams, "1", getBcUrl());


    }

    @Override
    public List<EthAccount> getEthAccount() {
        return null;
    }

    @Override
    public String getBalance(String addr) {

        String[] methodParams = {addr, "latest"};

        return EthJsonRPC.JsonRPC("eth_getBalance", methodParams, "1", getBcUrl());
    }

    @Override
    public String getBlockByNumber(int number) {

        /*Block blockRet = new Block();

        ObjectMapper mapper = new ObjectMapper();*/

        String[] methodParams = {"0x" + Integer.toHexString(number), "true"};

        return EthJsonRPC.JsonRPC("eth_getBlockByNumber", methodParams, "1", getBcUrl());
        /*try {
            //blockRet = mapper.readValue(blockResultBody, Block.class);
        }catch (Exception e) {
            logger.error(e.getMessage());
        }*/

    }

    @Override
    public String getBlockByHash(String hash) {

        String[] methodParams = {hash, "true"};

        return EthJsonRPC.JsonRPC("eth_getBlockByHash", methodParams, "1", getBcUrl());

    }

    @Override
    public String getCurrentBlock() {

        String[] methodParams = {"latest", "true"};

        return EthJsonRPC.JsonRPC("eth_getBlockByNumber", methodParams, "1", getBcUrl());


    }

    @Override
    public String getblockNumber() {

        ObjectMapper mapper = new ObjectMapper();

        String[] methodParams = {};

        return EthJsonRPC.JsonRPC("eth_blockNumber", methodParams, "1", getBcUrl());


    }

    @Override
    public String sendTansaction(String fromAddr, String fromPasswd,String toAddr) {
        String unlockStatus = unlockEthAccount(fromAddr,fromPasswd);
        if(unlockStatus.equals("ture")) {
            String transactionStatus = EthJsonRPC.JsonRPCTransaction(
                    fromAddr,
                    toAddr,
                    "0x76c0",
                    "0x9184e72a000",
                    "0x9184e72a",
                    "",
                    getBcUrl()
            );
            //TODO 重要

        }
        else{
            return unlockStatus;
        }

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

    @Override
    public String setNewBlocFilter() {
        String[] methodParams = {};

        return EthJsonRPC.JsonRPC("eth_newBlockFilter", methodParams, "1", getBcUrl());

    }

    @Override
    public String setNewPendingTransactionFilter() {
        String[] methodParams = {};

        return EthJsonRPC.JsonRPC("eth_newPendingTransactionFilter", methodParams, "1", getBcUrl());

    }

    @Override
    public String getFilterChanges(String addr) {
        String[] methodParams = {addr};

        return EthJsonRPC.JsonRPC("eth_getFilterChanges", methodParams, "1", getBcUrl());

    }
}
