package science.bintan.blockchain.repository;

import science.bintan.blockchain.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Created by lomo on 2017/10/6.
 */
@RepositoryRestResource
public interface RegisterRepository extends PagingAndSortingRepository<User, Long> {

    @Override
    @RestResource(exported = false)
    void delete(User user);

    User findByUsername(String username);
}
