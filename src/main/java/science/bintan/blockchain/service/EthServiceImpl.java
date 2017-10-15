package science.bintan.blockchain.service;


import science.bintan.blockchain.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.stereotype.Service;



import java.util.List;


/**
 * Created by lomo on 2017/10/11.
 */
@Service("EthService")
@EnableConfigurationProperties({BlockchainProperties.class})
public class EthServiceImpl implements EthService {

    @Autowired
    private BlockchainProperties blockchainProperties;


    @Override
    public String getBcUrl() {
        List<BlockchainProperties.BcInfo> bcInfos = blockchainProperties.getBcInfos();
        BlockchainProperties.BcInfo bcInfo = bcInfos.get(0);
        return bcInfo.getClientUrl();
    }


}
