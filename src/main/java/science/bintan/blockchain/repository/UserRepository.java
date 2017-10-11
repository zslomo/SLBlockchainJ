package science.bintan.blockchain.repository;


import org.springframework.stereotype.Repository;
import science.bintan.blockchain.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Created by lomo on 2017/10/6.
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    @Override
    @RestResource(exported = false)
    void delete(Long userid);

    @Override
    @RestResource(exported = false)
    void delete(User user);

    User findAllByUserId(Long userId);


    User findByUsername(String username);

    void deleteByUsername(String username);
}
