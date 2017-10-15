package science.bintan.blockchain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import science.bintan.blockchain.entity.EthAccount;
import science.bintan.blockchain.entity.EthTransaction;
import science.bintan.blockchain.entity.User;
import science.bintan.blockchain.service.*;
import science.bintan.blockchain.utils.SimpleReqBody;
/**
 * Created by lomo on 2017/10/11.
 */
@Controller
@RequestMapping("/eth")
public class EthController {
    @Autowired
    private EthTransactionService ethTransactionService;

    @Autowired
    private EthAccountService ethAccountService;

    @Autowired
    private EthBlockService ethBlockService;

    @Autowired
    private EthMiningService ethMiningService;


    @CrossOrigin
    @RequestMapping(value = {"/blockByNumber",}, method = RequestMethod.POST)
    @ResponseBody
    public String getBlockByNumber(@RequestBody SimpleReqBody body) {
        return ethBlockService.getBlockByNumber(body.getNumber());
    }

    @CrossOrigin
    @RequestMapping(value = {"/blockNumber",}, method = RequestMethod.GET)
    @ResponseBody
    public String getBlockNumber() {
        return ethBlockService.getblockNumber();
    }

    @CrossOrigin
    @RequestMapping(value = {"/newAccount",}, method = RequestMethod.POST)
    @ResponseBody
    public EthAccount personalNewAccount(@RequestBody SimpleReqBody body){
        User user =new User();
        user.setUsername(body.getUsername());
        return ethAccountService.newAccount(body.getPassword(),user);
    }


    @CrossOrigin
    @RequestMapping(value = {"/sendTransaction",}, method = RequestMethod.POST)
    @ResponseBody
    public String sendTransaction(@RequestBody SimpleReqBody body){
        return ethTransactionService.sendTansaction(
                body.getFromAddr(),
                body.getPassword(),
                body.getGas(),
                body.getGasPrice(),
                body.getValue(),
                body.getToAddr(),
                body.getData());
    }
    @CrossOrigin
    @RequestMapping(value = {"/mining",}, method = RequestMethod.GET)
    @ResponseBody
    public String mining(){
        return ethMiningService.minerStart(1);
    }
}
