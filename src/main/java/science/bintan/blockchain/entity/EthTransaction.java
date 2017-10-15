package science.bintan.blockchain.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by lomo on 2017/10/11.
 */
@Data
@Entity
@Table
public class EthTransaction {
    @Id
    private String hash;
    private String blockHash;
    private String blockNumber;
    private String fromAddr;
    private String gas;
    private String gasPrice;
    private String input;
    private String nonce;
    private String toAddr;
    private String transactionIndex;
    private String value;
    private String data;
    private String v;
    private String r;
    private String s;
    private EthTransactionStatus status;

    public EthTransaction(String hash, String blockHash, String blockNumber, String fromAddr, String gas, String gasPrice, String input, String nonce, String toAddr, String transactionIndex, String value, String data, String v, String r, String s, EthTransactionStatus status) {
        this.hash = hash;
        this.blockHash = blockHash;
        this.blockNumber = blockNumber;
        this.fromAddr = fromAddr;
        this.gas = gas;
        this.gasPrice = gasPrice;
        this.input = input;
        this.nonce = nonce;
        this.toAddr = toAddr;
        this.transactionIndex = transactionIndex;
        this.value = value;
        this.data = data;
        this.v = v;
        this.r = r;
        this.s = s;
        this.status = status;
    }
}
