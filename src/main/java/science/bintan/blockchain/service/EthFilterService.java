package science.bintan.blockchain.service;

/**
 * Created by lomo on 2017/10/15.
 */
public interface EthFilterService {
    String setNewBlocFilter();
    String setNewPendingTransactionFilter();
    String getFilterChanges(String addr);
    String uninstallFilter(String filterId);
}
