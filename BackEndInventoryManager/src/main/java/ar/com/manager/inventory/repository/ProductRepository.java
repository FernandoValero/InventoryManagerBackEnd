package ar.com.manager.inventory.repository;

import ar.com.manager.inventory.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    boolean existsByBarCode(String barcode);
    boolean existsByNumber(String number);
    List<Product> findByDeletedFalse();
    boolean existsByNumberAndIdNot(String number, Integer id);
    boolean existsByBarCodeAndIdNot(String barCode, Integer id);
}
