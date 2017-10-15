package science.bintan.blockchain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import science.bintan.blockchain.entity.BlockchainProperties;
import science.bintan.blockchain.utils.EthJsonRPC;

import java.util.List;

/**
 * Created by lomo on 2017/10/15.
 */
@Service("EthFilterService")
public class EthFilterServiceImpl implements EthFilterService{
    private static final Logger logger = LoggerFactory.getLogger(EthServiceImpl.class);

    @Autowired
    private EthService ethService;


    @Override
    public String setNewBlocFilter() {
        String[] methodParams = {};

        return EthJsonRPC.JsonRPC("eth_newBlockFilter", methodParams, "1", ethService.getBcUrl());

    }

    @Override
    public String setNewPendingTransactionFilter() {
        String[] methodParams = {};

        return EthJsonRPC.JsonRPC("eth_newPendingTransactionFilter", methodParams, "1", ethService.getBcUrl());

    }

    @Override
    public String getFilterChanges(String addr) {
        String[] methodParams = {addr};

        return EthJsonRPC.JsonRPC("eth_getFilterChanges", methodParams, "1", ethService.getBcUrl());

    }

    @Override
    public String uninstallFilter(String filterId) {
        String[] methodParams = {filterId};

        return EthJsonRPC.JsonRPC("eth_uninstallFilter", methodParams, "1", ethService.getBcUrl());

    }
}
