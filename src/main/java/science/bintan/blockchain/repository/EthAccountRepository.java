package science.bintan.blockchain.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import science.bintan.blockchain.entity.EthAccount;
import science.bintan.blockchain.entity.User;

import java.util.List;

/**
 * Created by lomo on 2017/10/12.
 */
@RepositoryRestResource
public interface EthAccountRepository extends PagingAndSortingRepository<EthAccount, Long> {
    EthAccount findByAddress(String addr);

    List<EthAccount> findAllByUser(User user);
}
