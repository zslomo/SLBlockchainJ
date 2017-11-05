package science.bintan.blockchain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.EthBlock;
import science.bintan.blockchain.entity.BlockchainProperties;
import science.bintan.blockchain.utils.EthJsonRPC;

import java.util.List;

/**
 * Created by lomo on 2017/10/15.
 */
@Service("EthBlockService")
public class EthBlockServiceImpl implements EthBlockService{
    private static final Logger logger = LoggerFactory.getLogger(EthServiceImpl.class);

    @Autowired
    private EthService ethService;

    @Override
    public String getBlockByNumber(int number) {

        /*Block blockRet = new Block();

        ObjectMapper mapper = new ObjectMapper();*/

        String[] methodParams = {"0x" + Integer.toHexString(number), "true"};

        return EthJsonRPC.JsonRPC("eth_getBlockByNumber", methodParams, "1", ethService.getBcUrl());
        /*try {
            //blockRet = mapper.readValue(blockResultBody, Block.class);
        }catch (Exception e) {
            logger.error(e.getMessage());
        }*/

    }

    @Override
    public String getBlockByHash(String hash) {

        String[] methodParams = {hash, "true"};

        return EthJsonRPC.JsonRPC("eth_getBlockByHash", methodParams, "1", ethService.getBcUrl());

    }

    @Override
    public String getCurrentBlock() {

        String[] methodParams = {"latest", "true"};

        return EthJsonRPC.JsonRPC("eth_getBlockByNumber", methodParams, "1", ethService.getBcUrl());


    }

    @Override
    public String getblockNumber() {

        ObjectMapper mapper = new ObjectMapper();

        String[] methodParams = {};

        return EthJsonRPC.JsonRPC("eth_blockNumber", methodParams, "1", ethService.getBcUrl());


    }
}
