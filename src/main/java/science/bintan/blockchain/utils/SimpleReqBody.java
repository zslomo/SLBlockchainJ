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

    private String errorMessage;
}
