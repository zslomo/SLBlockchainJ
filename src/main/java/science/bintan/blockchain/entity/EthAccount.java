package science.bintan.blockchain.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by lomo on 2017/10/11.
 */
@Data
@Entity
public class EthAccount {

    private String username;
    @Id
    private String address;
    private String password;
    private int ether;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String errorMessage;
}
