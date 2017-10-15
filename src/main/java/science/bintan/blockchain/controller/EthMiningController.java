package science.bintan.blockchain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import science.bintan.blockchain.service.EthMiningService;

/**
 * Created by bintan on 17-10-15.
 */
@Controller
@RequestMapping("/ethMining")
public class EthMiningController {
    @Autowired
    private EthMiningService ethMiningService;

    /**
     * 启动挖矿
     * @return String Mining status
     */

    @CrossOrigin
    @RequestMapping(value = {"/mining",}, method = RequestMethod.GET)
    @ResponseBody
    public String mining(){
        try {
            ethMiningService.minerStart(1);
            Thread.sleep(3000);
            return ethMiningService.minerStop();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "#error";
    }
}
