package science.bintan.blockchain.entity;

import lombok.Data;

/**
 * Created by lomo on 2017/10/11.
 */
@Data
public class EthAccount {
    private String username;
    private String address;
    private String password;
    private int ether;
}
