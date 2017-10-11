package science.bintan.blockchain.service;

import science.bintan.blockchain.entity.User;
import science.bintan.blockchain.repository.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lomo on 2017/10/6.
 */
@Service("registerService")
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RegisterRepository registerRepository;

    public void save(User user) {
        registerRepository.save(user);
    }
    public String  register(User user) {
        //判断用户是否存在
        if (registerRepository.findByUsername(user.getUsername()) == null) {
            registerRepository.save(user);
            return "register success";
        }
        else {
            return "register failed";
        }
    }

}
