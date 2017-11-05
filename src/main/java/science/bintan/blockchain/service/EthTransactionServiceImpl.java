package science.bintan.blockchain.service;

import com.fasterxml.jackson.core.type.TypeReference;
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

import java.util.*;

/**
 * Created by lomo on 2017/10/14.
 */
@Service("EthTransactionService")
public class EthTransactionServiceImpl implements EthTransactionService {

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
    public String sendTransaction(
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
            if (transactionAddr.charAt(0) == '#') return transactionAddr;
            ethMiningService.minerStart(1);

            String filterID = ethFilterService.setNewBlocFilter();
            /*
            用下面注释掉的方法无法挖矿，即便给足够的延迟依然不行，会卡在准备阶段，非常吊诡
            所以此处用了鱼唇的延迟大法，3s大约是启动+挖一块的时间
            监听器于minerStart函数设置，minerStop函数销毁，暂时没有用，数据库不存放区块
            区块信息只用eth接口访问，我们的目标是：“去中心化！”
            */
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            /*String BlockListStr = ethFilterService.getFilterChanges(filterID.replace("\"", ""));

            *//*
             *监听是否产生新的区块，产生，或者8s后停止
             *TODO 如果是8s后仍未发现新的区块，没有返回值，无法获取这种情况
            *//*
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                int times = 0;

                public void run() {
                    if (times >= 8) timer.cancel();

                    String BlockListStr = ethFilterService.getFilterChanges(filterID.replace("\"", ""));
                    if (BlockListStr.length() > 2) {
                        timer.cancel();
                    }
                    times++;

                }
            }, 3000, 1000);*/
            ethFilterService.uninstallFilter(filterID);
            ethMiningService.minerStop();
            EthTransaction ethTransaction = getTransactionEntityByAddress(transactionAddr.replace("\"", ""));
            save(ethTransaction);
            return transactionAddr;

        } else {
            return unlockStatus;
        }

    }

    @Override
    public String sendTransactionWithoutMining(String fromAddr, String fromPasswd, String gas, String gasPrice, String value, String toAddr, String data) {
        String unlockStatus = ethAccountService.unlockEthAccount(fromAddr, fromPasswd);
        if (unlockStatus.equals("true")) {
            String transactionAddr = EthJsonRPC.JsonRPCTransaction(fromAddr, toAddr, gas, gasPrice, value, data, ethService.getBcUrl());
            if (transactionAddr.charAt(0) == '#') return transactionAddr;

            EthTransaction ethTransaction = getTransactionEntityByAddress(transactionAddr.replace("\"", ""));
            save(ethTransaction);
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
     *
     * @return EthTransactionEntity
     */
    @Override
    public EthTransaction getTransactionEntityByAddress(String addr) {
        ObjectMapper mapper = new ObjectMapper();
        String transactionBody = getTransactionByAddress(addr);
        EthTransaction ethTransaction;
        try {
            Map<String, String> m = mapper.readValue(transactionBody, new TypeReference<Map<String, String>>() {
            });
            return new EthTransaction(
                    m.get("hash"),
                    m.get("blockHash"),
                    m.get("blockNumber"),
                    m.get("from"),
                    m.get("gas"),
                    m.get("gasPrice"),
                    m.get("input"),
                    m.get("nonce"),
                    m.get("to"),
                    m.get("transactionIndex"),
                    m.get("value"),
                    m.get("data"),
                    m.get("v"),
                    m.get("r"),
                    m.get("s"),
                    m.get("blockNumber") == null ? EthTransactionStatus.PENDING : EthTransactionStatus.SUBMITED
            );
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    /**
     * 提供api接口，暂时提供其他函数使用
     *
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
        return (List<EthTransaction>)ethTransactionRepository.findAll();
    }

    @Override
    public List<EthTransaction> getEthTransactionBySender(String address) {
        return null;
    }

    @Override
    public List<EthTransaction> getEthTransactionByReceiver(String address) {
        return null;
    }
}
