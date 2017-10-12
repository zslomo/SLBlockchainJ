package science.bintan.blockchain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import science.bintan.blockchain.entity.Block;
import science.bintan.blockchain.entity.EthAccount;
import science.bintan.blockchain.entity.User;
import science.bintan.blockchain.service.EthService;
import science.bintan.blockchain.utils.EasyReqBody;
/**
 * Created by lomo on 2017/10/11.
 */
@Controller
@RequestMapping("/eth")
public class EthController {
    @Autowired
    private EthService ethService;

    @CrossOrigin
    @RequestMapping(value = {"/blockByNumber",}, method = RequestMethod.POST)
    @ResponseBody
    public String getBlockByNumber(@RequestBody EasyReqBody body) {
        return ethService.getBlockByNumber(body.getNumber());
    }

    @CrossOrigin
    @RequestMapping(value = {"/blockNumber",}, method = RequestMethod.GET)
    @ResponseBody
    public int getBlockNumber() {
        return ethService.getblockNumber();
    }

    @CrossOrigin
    @RequestMapping(value = {"/newAccount",}, method = RequestMethod.GET)
    @ResponseBody
    public EthAccount personalNewAccount(@RequestBody EasyReqBody body){
        User user =new User();
        user.setUsername(body.getUsername());
        return ethService.newAccount(body.getPassword(),user);
    }
}
