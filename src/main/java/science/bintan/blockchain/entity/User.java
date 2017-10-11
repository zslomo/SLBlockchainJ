package science.bintan.blockchain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by lomo on 2017/10/6.
 */
@Data
@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;

    @Basic
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private double account;

    private String userPassPhrase;

    private String userChainAdress;

    private String userPrivateKey;

    private String contractAddress;
}
