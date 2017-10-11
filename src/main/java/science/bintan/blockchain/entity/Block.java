package science.bintan.blockchain.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

/**
 * Created by lomo on 2017/10/11.
 */
@Data
@Entity
public class Block {
    @Id
    private String hash;

    private String difficulty;
    private String extraData;
    private String gasLimit;
    private String gasUsed;
    private String logsBloom;
    private String miner;
    private String mixHash;
    private String nonce;
    private String number;
    private String parentHash;
    private String receiptsRoot;
    private String sha3Uncles;
    private String size;
    private String stateRoot;
    private String timestamp;
    private String totalDifficulty;
    private String transactions;
    private String transactionsRoot;
    private String uncles;

}
