package science.bintan.blockchain.controller;

import science.bintan.blockchain.entity.User;
import science.bintan.blockchain.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lomo on 2017/10/6.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @CrossOrigin
    @RequestMapping(value = {"", "/", "list", "index"},method = RequestMethod.GET)
    @ResponseBody
    public List<User> list(){

        return userService.getList();
    }



}

