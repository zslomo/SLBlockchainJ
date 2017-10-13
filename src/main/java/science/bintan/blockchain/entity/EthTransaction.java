package science.bintan.blockchain.entity;

import lombok.Data;

/**
 * Created by lomo on 2017/10/11.
 */
@Data
public class EthTransaction {
    private String blockHash;
    private String blockNumber;
    private String from;
    private String password;
    private String gas;
    private String gasPrice;
    private String hash;
    private String input;
    private String nonce;
    private String to;
    private String transactionIndex;
    private String value;
    private String data;
    private String v;
    private String r;
    private String s;


}
