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
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lomo on 2017/10/14.
 */
@Service("EthTransactionService")
public class EthTransactionServiceImpl implements EthTransactionService{

    @Autowired
    private EthService ethService;

    @Autowired
    private EthAccountService ethAccountService;

    @Autowired
    private EthMiningService ethMiningService;

    @Autowired
    private EthFilterService ethFilterService;

    @Autowired
    private EthTransactionRepository ethTransactionRepository;

    @Override
    public String sendTansaction(
            String fromAddr,
            String fromPasswd,
            String gas,
            String gasPrice,
            String value,
            String toAddr,
            String data) {
        String unlockStatus = ethAccountService.unlockEthAccount(fromAddr, fromPasswd);
        if (unlockStatus.equals("true")) {
            String transactionAddr = EthJsonRPC.JsonRPCTransaction(fromAddr, toAddr, gas, gasPrice, value, data, ethService.getBcUrl());
            EthTransaction ethTransaction = getTransactionEntiyByAddress(transactionAddr);
            save(ethTransaction);
            if(transactionAddr.charAt(0)=='#') return transactionAddr;
            ethMiningService.minerStart(1);
            String filterID = ethFilterService.setNewBlocFilter();
            /*
             *监听是否产生新的区块，产生，或者8s后停止
             *TODO 如果是8s后仍未发现新的区块，没有返回值，无法获取这种情况
            */
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                int times = 0;
                public void run() {
                    if(times>=80) timer.cancel();

                    String BlockListStr = ethFilterService.getFilterChanges(filterID);
                    if (!BlockListStr.isEmpty()) {
                        String[] BlockList = BlockListStr.split(",");
                        for (String e : BlockList) {
                            save(getTransactionEntiyByAddress(e.replace("\"", "")));
                        }
                        timer.cancel();
                    }
                    times++;

                }
            }, 0,100);

            ethMiningService.minerStop(filterID);
            return transactionAddr;

        } else {
            return unlockStatus;
        }

    }

    @Override
    public void save(EthTransaction ethTransaction) {
        ethTransactionRepository.save(ethTransaction);
    }

    /**
     * 这里非常不严谨，如果eth返回错误会GG,此处提供的addr是查询eth所得，链没挂一般不会崩
     * @return EthTransactionEtity
     */
    @Override
    public EthTransaction getTransactionEntiyByAddress(String addr) {
        ObjectMapper mapper = new ObjectMapper();
        String transactionBody = getTransactionByAddress(addr);
        try {
            Map m = mapper.readValue(transactionBody, Map.class);
            EthTransactionStatus status = EthTransactionStatus.SUBMITED;
            if(m.get("blockNumber") == null) status =EthTransactionStatus.PENDING;
            return new EthTransaction(
                    m.get("blockNumber").toString(),
                    m.get("blockHash").toString(),
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
        return null;
    }

    /**
     * 提供api接口，暂时提供其他函数使用
     * @return EthTransactionEtity
     */
    @Override
    public String getTransactionByAddress(String addr) {

        ObjectMapper mapper = new ObjectMapper();
        String[] methodParams = {addr};

        return EthJsonRPC.JsonRPC("eth_getTransactionByHash", methodParams, "1", ethService.getBcUrl());

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
