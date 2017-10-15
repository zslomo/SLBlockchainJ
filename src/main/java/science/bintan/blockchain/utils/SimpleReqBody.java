package science.bintan.blockchain.utils;

import lombok.Data;

/**
 * Created by lomo on 2017/10/11.
 */
@Data
public class SimpleReqBody {
    private String address;
    private int number;
    private String hash;
    private String password;
    private String username;

    private String fromAddr;
    private String toAddr;

    private String blockHash;
    private String blockNumber;
    private String gas;
    private String gasPrice;
    private String input;
    private String nonce;
    private String transactionIndex;
    private String value;
    private String data;
    private String v;
    private String r;
    private String s;

    private String errorMessage;
}
