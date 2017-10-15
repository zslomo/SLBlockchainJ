package science.bintan.blockchain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import science.bintan.blockchain.entity.BlockchainProperties;
import science.bintan.blockchain.entity.EthAccount;
import science.bintan.blockchain.entity.User;
import science.bintan.blockchain.repository.EthAccountRepository;
import science.bintan.blockchain.utils.EthJsonRPC;

import java.util.List;

/**
 * Created by lomo on 2017/10/12.
 */
@Service("EthAccountService")
public class EthAccountServiceImpl implements EthAccountService{
    private static final Logger logger = LoggerFactory.getLogger(EthServiceImpl.class);

    @Autowired
    private EthService ethService;

    @Autowired
    private UserService userService;

    @Autowired
    private EthAccountRepository ethAccountRepository;


    @Override
    public void save(EthAccount ethAccount) {
        ethAccountRepository.save(ethAccount);
    }

    @Override
    public void update(EthAccount ethAccount) {
        //TODO
    }

    @Override
    public void delete(EthAccount ethAccount) {
        //TODO
    }

    @Override
    public EthAccount getByAddress(String address) {
        return ethAccountRepository.findByAddress(address);
    }

    @Override
    public EthAccount newAccount(String password, User user) {

        String[] methodParams = {password};

        String addr = EthJsonRPC.JsonRPC("personal_newAccount", methodParams, "1", ethService.getBcUrl());

        EthAccount ethAccount = new EthAccount();

        if (addr.charAt(0) == '#') {
            ethAccount.setErrorMessage(addr.substring(1));
            return ethAccount;
        }
        ethAccount.setAddress(addr);
        ethAccount.setPassword(password);

        try {
            save(ethAccount);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        try {
            user.getEthAccounts().add(ethAccount);
            userService.save(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return ethAccount;

    }

    @Override
    public String unlockEthAccount(String addr, String password) {
        String[] methodParams = {addr, password};

        return EthJsonRPC.JsonRPC("personal_unlockAccount", methodParams, "1", ethService.getBcUrl());


    }

    @Override
    public List<EthAccount> getEthAccount() {
        return null;
    }

    @Override
    public String getBalance(String addr) {

        String[] methodParams = {addr, "latest"};

        return EthJsonRPC.JsonRPC("eth_getBalance", methodParams, "1", ethService.getBcUrl());
    }
}
