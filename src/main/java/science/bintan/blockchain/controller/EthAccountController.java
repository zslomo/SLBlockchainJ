package science.bintan.blockchain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import science.bintan.blockchain.entity.EthAccount;
import science.bintan.blockchain.entity.User;
import science.bintan.blockchain.service.EthAccountService;
import science.bintan.blockchain.utils.SimpleReqBody;

/**
 * Created by bintan on 17-10-15.
 */
@Controller
@RequestMapping("/ethAccount")
public class EthAccountController {
    @Autowired
    private EthAccountService ethAccountService;

    /**
     * 新建eth账号，返回的是私钥
     * @return String account hash
     */

    @CrossOrigin
    @RequestMapping(value = {"/newAccount",}, method = RequestMethod.POST)
    @ResponseBody
    public EthAccount personalNewAccount(@RequestBody SimpleReqBody body){
        User user =new User();
        user.setUsername(body.getUsername());
        return ethAccountService.newAccount(body.getPassword(),user);
    }

}
