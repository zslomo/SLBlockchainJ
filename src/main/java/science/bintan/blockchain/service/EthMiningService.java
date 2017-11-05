package science.bintan.blockchain.service;

/**
 * Created by lomo on 2017/10/15.
 */
public interface EthMiningService {
    String minerStart(int threads);
    String minerStop();
    String getMiningStatus();
}
