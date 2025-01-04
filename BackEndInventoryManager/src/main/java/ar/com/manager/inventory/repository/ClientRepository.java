package ar.com.manager.inventory.repository;

import ar.com.manager.inventory.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    boolean existsByDni(String dni);
    Client findByDni(String dni);
    List<Client> findByDeletedFalse();
    boolean existsByDniAndIdNot(String dni, Integer id);

}
