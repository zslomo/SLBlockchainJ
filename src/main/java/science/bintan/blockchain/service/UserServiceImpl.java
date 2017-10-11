package science.bintan.blockchain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import science.bintan.blockchain.entity.User;
import science.bintan.blockchain.repository.UserRepository;

import java.util.List;

/**
 * Created by lomo on 2017/10/6.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(User user) {
        //TODO
    }

    @Override
    public void update(User user) {
        //TODO
    }

    @Override
    public void delete(String username) {
        //TODO
    }

    @Override
    public User getById(Long id) {
        //TODO
        return null;
    }

    @Override
    public User getByUsername(String username) {
        //TODO
        return null;
    }

    @Override
    public List<User> getList() {
        return (List<User>)userRepository.findAll();
    }

    @Override
    public String userNameModify(String username, String modify) {
        //TODO
        return null;
    }

    @Override
    public String userPasswdModify(String username, String modify) {
        //TODO
        return null;
    }

    @Override
    public String userAccountModify(User user, Double modify) {
        //TODO
        return null;
    }

    @Override
    public String userNameModifyChain(String username, String modify) {
        //TODO
        return null;
    }

    @Override
    public String userPasswdModifyChain(String username, String modify) {
        //TODO
        return null;
    }
}
