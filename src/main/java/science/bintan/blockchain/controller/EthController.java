package science.bintan.blockchain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import science.bintan.blockchain.entity.EthAccount;
import science.bintan.blockchain.entity.EthTransaction;
import science.bintan.blockchain.entity.User;
import science.bintan.blockchain.service.EthService;
import science.bintan.blockchain.utils.SimpleReqBody;
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
    public String getBlockByNumber(@RequestBody SimpleReqBody body) {
        return ethService.getBlockByNumber(body.getNumber());
    }

    @CrossOrigin
    @RequestMapping(value = {"/blockNumber",}, method = RequestMethod.GET)
    @ResponseBody
    public String getBlockNumber() {
        return ethService.getblockNumber();
    }

    @CrossOrigin
    @RequestMapping(value = {"/newAccount",}, method = RequestMethod.POST)
    @ResponseBody
    public EthAccount personalNewAccount(@RequestBody SimpleReqBody body){
        User user =new User();
        user.setUsername(body.getUsername());
        return ethService.newAccount(body.getPassword(),user);
    }


    @CrossOrigin
    @RequestMapping(value = {"/sendTransaction",}, method = RequestMethod.POST)
    @ResponseBody
    public String sendTransaction(@RequestBody EthTransaction body){
        return ethService.sendTansaction(
                body.getFrom(),
                body.getPassword(),
                body.getGas(),
                body.getGasPrice(),
                body.getValue(),
                body.getTo(),
                body.getData());
    }
    @CrossOrigin
    @RequestMapping(value = {"/mining",}, method = RequestMethod.GET)
    @ResponseBody
    public String mining(){
        return ethService.minerStart(1);
    }
}
