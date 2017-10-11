package science.bintan.blockchain.controller;

import science.bintan.blockchain.entity.User;
import science.bintan.blockchain.repository.UserRepository;
import science.bintan.blockchain.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lomo on 2017/10/6.
 */
@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @Autowired
    private UserRepository userRepository;

    @CrossOrigin
    @RequestMapping(value = {"/add",}, method = RequestMethod.POST)
    @ResponseBody
    public String addAction(@RequestBody User pu) {
        User user = new User();
        user.setUsername(pu.getUsername());
        user.setPassword(pu.getPassword());
        if (registerService.register(user).equals("failed")) {
            return "username existed";
        } else {
            userRepository.save(user);
            return "success";
        }
    }


}