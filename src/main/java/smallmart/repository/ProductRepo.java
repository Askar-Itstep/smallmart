package smallmart.repository;

import org.springframework.data.repository.CrudRepository;
import smallmart.model.Product;

public interface ProductRepo extends CrudRepository<Product, Long> {

}
