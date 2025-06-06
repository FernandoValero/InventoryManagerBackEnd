package ar.com.manager.inventory.repository;

import ar.com.manager.inventory.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUserName(String username);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByUserNameAndIdNot(String userName, Integer id);
    boolean existsByEmailAndIdNot(String userName, Integer id);
    boolean existsByPhoneNumberAndIdNot(String userName, Integer id);
    List<User> findByDeletedFalse();
}
