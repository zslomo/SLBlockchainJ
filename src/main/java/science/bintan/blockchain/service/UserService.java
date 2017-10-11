package science.bintan.blockchain.service;

import java.util.List;
import science.bintan.blockchain.entity.User;

/**
 * Created by lomo on 2017/10/6.
 */
public interface UserService {
    void save(User user);

    void update(User user);

    void delete(String username);

    User getById(Long id);

    User getByUsername(String username);

    List<User> getList();


    String userNameModify(String username, String modify);

    String userPasswdModify(String username, String modify);

    String userAccountModify(User user,Double modify);

    String userNameModifyChain(String username, String modify);

    String userPasswdModifyChain(String username, String modify);


}
