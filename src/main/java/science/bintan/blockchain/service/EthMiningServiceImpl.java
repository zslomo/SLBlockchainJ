package science.bintan.blockchain.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import science.bintan.blockchain.entity.BlockchainProperties;
import science.bintan.blockchain.utils.EthJsonRPC;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lomo on 2017/10/15.
 */
@Service("EthMiningService")
public class EthMiningServiceImpl implements EthMiningService{
    private static final Logger logger = LoggerFactory.getLogger(EthServiceImpl.class);

    @Autowired
    private EthService ethService;

    @Override
    public String minerStart(int threads) {

        String[] methodParams = {String.valueOf(threads)};

        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("jsonrpc", "2.0");
        params.put("method", "miner_start");
        params.put("params", methodParams);
        params.put("id", 1);

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String requsetJson = mapper.writeValueAsString(params)
                    .replace("[\"","[")
                    .replace("\"]","]");
            HttpEntity<String> request = new HttpEntity<String>(requsetJson, headers);

            ResponseEntity<String> blockResult = restTemplate.exchange(ethService.getBcUrl(), HttpMethod.POST, request, String.class);
            String blockResultBody = blockResult.getBody();
            if (blockResult.getStatusCode() == HttpStatus.OK) {

                JsonNode jsonNode = mapper.readTree(blockResultBody);

                if (jsonNode.get("error") != null) return "#" + jsonNode.get("error").toString();

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
        return EthJsonRPC.JsonRPC("miner_stop", methodParams, "1", ethService.getBcUrl());
    }

    @Override
    public String getMiningStatus() {
        String[] methodParams = {};
        return EthJsonRPC.JsonRPC("eth_mining", methodParams, "1", ethService.getBcUrl());
    }
}
