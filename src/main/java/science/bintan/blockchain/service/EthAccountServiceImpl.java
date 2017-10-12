package science.bintan.blockchain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import science.bintan.blockchain.entity.EthAccount;
import science.bintan.blockchain.repository.EthAccountRepository;

/**
 * Created by lomo on 2017/10/12.
 */
@Service("EthAccountService")
public class EthAccountServiceImpl implements EthAccountService{
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
}
