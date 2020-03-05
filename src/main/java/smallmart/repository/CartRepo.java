package smallmart.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import smallmart.model.Cart;
import smallmart.model.Item;
import smallmart.model.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CartRepo extends CrudRepository<Cart, Long> {
    List<Cart> findAllByUser(User user);

//    Cart findByDateAfter(Date date);

    Optional<Cart> findCartByUserAndDateAfter(User user, Date date);

    @Modifying
    @Query("update Cart c set c.cost = :cost where c.id = :id")
    void updateCostCart(@Param("id") Long id, @Param("cost") double cost);

    @Modifying
    @Query("update Cart set is_queue = :isQueue where id = :id")
    void updateQueue(@Param("id")Long id, @Param("isQueue") boolean isQueue);

    Optional<Cart> findByUser(User user);
}
