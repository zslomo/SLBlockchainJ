package science.bintan.blockchain.entity;

import lombok.Data;

import javax.persistence.Id;

/**
 * Created by lomo on 2017/10/11.
 */
@Data
public class EthTransaction {
    @Id
    private String hash;
    private String blockHash;
    private String blockNumber;
    private String from;
    private String gas;
    private String gasPrice;
    private String input;
    private String nonce;
    private String to;
    private String transactionIndex;
    private String value;
    private String data;
    private String v;
    private String r;
    private String s;
    private EthTransactionStatus status;

    public EthTransaction(String blockHash, String blockNumber, String from, String gas, String gasPrice, String hash, String input, String nonce, String to, String transactionIndex, String value, String data, String v, String r, String s, EthTransactionStatus status) {

        this.blockHash = blockHash;
        this.blockNumber = blockNumber;
        this.from = from;
        this.gas = gas;
        this.gasPrice = gasPrice;
        this.hash = hash;
        this.input = input;
        this.nonce = nonce;
        this.to = to;
        this.transactionIndex = transactionIndex;
        this.value = value;
        this.data = data;
        this.v = v;
        this.r = r;
        this.s = s;
        this.status = status;
    }
}
