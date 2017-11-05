package science.bintan.blockchain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import science.bintan.blockchain.service.EthTransactionService;
import science.bintan.blockchain.utils.SimpleReqBody;

/**
 * Created by bintan on 17-10-15.
 */
@Controller
@RequestMapping("/ethTransaction")
public class EthTransactionController {
    @Autowired
    private EthTransactionService ethTransactionService;

    /**
     * 发送一笔交易，返回交易地址，包含挖矿
     * @return String transaction hash
     */

    @CrossOrigin
    @RequestMapping(value = {"/send",}, method = RequestMethod.POST)
    @ResponseBody
    public String sendTransaction(@RequestBody SimpleReqBody body){
        return ethTransactionService.sendTransaction(
                body.getFromAddr(),
                body.getPassword(),
                body.getGas(),
                body.getGasPrice(),
                body.getValue(),
                body.getToAddr(),
                body.getData()
        );
    }
    /**
     * 发送一笔交易但不包含挖矿，需要挖矿来写入交易
     * @return String transaction hash
     */

    @CrossOrigin
    @RequestMapping(value = {"/sendWithoutMining",}, method = RequestMethod.POST)
    @ResponseBody
    public String sendTransactionWithoutMining(@RequestBody SimpleReqBody body){
        return ethTransactionService.sendTransactionWithoutMining(
                body.getFromAddr(),
                body.getPassword(),
                body.getGas(),
                body.getGasPrice(),
                body.getValue(),
                body.getToAddr(),
                body.getData()
        );
    }


}
