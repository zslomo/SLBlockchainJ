package science.bintan.blockchain.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.node.NullNode;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import science.bintan.blockchain.entity.BlockchainProperties;

import javax.print.attribute.standard.Media;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lomo on 2017/10/11.
 */
@EnableConfigurationProperties({BlockchainProperties.class})
public class EthJsonRPC {

    /*
    eth 接口中 如果有true false不能加引号的参数很麻烦
    此函数进行去引号处理
    */
    private static String myJsonFormat(String requsetJson){
        return requsetJson
                .replace("\"true\"","true")
                .replace("\"false\"","false");
    }

    public static String JsonRPC(String method, String[] methodParams, String id, String url){

        String ret = "";

        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("jsonrpc", "2.0");
        params.put("method", method);
        params.put("params", methodParams);
        params.put("id", id);

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String requsetJson = myJsonFormat(mapper.writeValueAsString(params));
            HttpEntity<String> request = new HttpEntity<String>(requsetJson, headers);

            ResponseEntity<String> blockResult = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
            String blockResultBody = blockResult.getBody();
            if (blockResult.getStatusCode() == HttpStatus.OK) {

                JsonNode jsonNode = mapper.readTree(blockResultBody);

                if(jsonNode.get("error") != null) return "#"+jsonNode.get("error").toString();

                return jsonNode.get("result").toString();
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return "#error";
    }

    public static String JsonRPCTransaction(String fromAddr,String toAddr,String gas,String gasPrice,String value,String data,String url){

        ObjectMapper mapper = new ObjectMapper();
        String methodParams = "";
        /*
           jackson 不能直接把双层map对象转换成json，
           也不能把转换好的json文件直接当成数组塞进去
           因为会乱加"，还会把带"的字符全部加上转义符
           有时间看看jckson的官方文档，这边就直接这么做了
        */
        try {
            Map<String, String> methodParamsMap = new HashMap<String, String>();
            methodParamsMap.put("from", fromAddr);
            methodParamsMap.put("to", toAddr);
            methodParamsMap.put("gas", gas);
            methodParamsMap.put("gasPrice", gasPrice);
            methodParamsMap.put("value", value);
            methodParamsMap.put("data", data);

            String methodParamsTemp = mapper.writeValueAsString(methodParamsMap);
            methodParams = '['+methodParamsTemp+']';
        }catch (Throwable  e){
            e.printStackTrace();
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("jsonrpc", "2.0");
        params.put("method", "eth_sendTransaction");
        params.put("params", methodParams);
        params.put("id",1);

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String requsetJson = mapper.writeValueAsString(params)
                    .replace("\"[","[")
                    .replace("]\"","]")
                    .replace("\\","");
            HttpEntity<String> request = new HttpEntity<String>(requsetJson, headers);

            ResponseEntity<String> blockResult = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
            String blockResultBody = blockResult.getBody();
            if (blockResult.getStatusCode() == HttpStatus.OK) {

                JsonNode jsonNode = mapper.readTree(blockResultBody);

                if(jsonNode.get("error") != null) return "#"+jsonNode.get("error").toString();

                return jsonNode.get("result").toString();
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return "#error";
    }

}
