package science.bintan.blockchain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import science.bintan.blockchain.service.EthBlockService;
import science.bintan.blockchain.utils.SimpleReqBody;

/**
 * Created by bintan on 17-10-15.
 */
@Controller
@RequestMapping("/ethBlock")
public class EthBlockController {
    @Autowired
    private EthBlockService ethBlockService;

    /**
     * 根据块号返回区块
     * @return String Block
     */

    @CrossOrigin
    @RequestMapping(value = {"/getByNumber",}, method = RequestMethod.POST)
    @ResponseBody
    public String getBlockByNumber(@RequestBody SimpleReqBody body) {
        return ethBlockService.getBlockByNumber(body.getNumber());
    }
    /**
     * 返回区块总数
     * @return String number
     */
    @CrossOrigin
    @RequestMapping(value = {"/getNumber",}, method = RequestMethod.GET)
    @ResponseBody
    public String getBlockNumber() {
        return ethBlockService.getblockNumber();
    }
}
