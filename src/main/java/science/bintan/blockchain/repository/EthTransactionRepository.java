package science.bintan.blockchain.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import science.bintan.blockchain.entity.EthTransaction;

import java.util.List;

/**
 * Created by lomo on 2017/10/14.
 */
public interface EthTransactionRepository extends PagingAndSortingRepository<EthTransaction, Long> {

    //EthTransaction findByAddress(String addr);
}
