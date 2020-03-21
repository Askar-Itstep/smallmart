package smallmart.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import smallmart.model.Cart;
import smallmart.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends CrudRepository<User, Long> {
    Optional<List<User>> findByUserName(String userName);

    Optional<User> findFirstByUserNameAndEmail(String userName, String email);

    @Modifying
    @Query("update User u set u.cart = ?1 where id=?2")
    void updateCartByUserId(Cart cart, Long id);

    @Modifying
    @Query("update User set username = ?1 where id=?2")
    void updateUsername(String username, Long id);

    @Modifying
    @Query("update User u set u.password = ?1 where id=?2")
    void updatePassword(String password, Long id);

    @Modifying
    @Query("update User u set u.phone = ?1 where id=?2")
    void updateUserPhone(String phone, Long id);

    @Modifying
    @Query("update User u set u.address = ?1 where id=?2")
    void updateUserAddrress(String address, Long userId);

//    @Modifying        //без этого работ.
//    @Query("update User set roles = ?1 where id=?2")
//    void updateUserRole(Set<Role> roles, Long userId);

    @Query("select max(id) from User")
    Long getMaxid();

    User findByActivationCode(String code);
}
