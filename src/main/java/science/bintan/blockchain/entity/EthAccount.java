package science.bintan.blockchain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by lomo on 2017/10/11.
 */
@Data
@Entity
@Table
public class EthAccount {

    private String username;
    @Id
    private String address;
    private String password;
    private int ether;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ethAccount")
    List<EthTransaction> ethTransactions;

    private String errorMessage;
}
