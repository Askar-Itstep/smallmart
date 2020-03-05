package smallmart.repository;

import org.springframework.data.repository.CrudRepository;
import smallmart.model.Cart;
import smallmart.model.Item;
import smallmart.model.Product;

import java.util.List;

public interface ItemRepo extends CrudRepository<Item,Long> {
    List<Item> findAllByCart(Cart cart);
}
