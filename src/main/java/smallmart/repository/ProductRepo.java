package smallmart.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import smallmart.model.Product;

public interface ProductRepo extends CrudRepository<Product, Long> {
    //    List<Product> findAll();       //standart
    Page findAll(Pageable pageable);  //for pagination
}
