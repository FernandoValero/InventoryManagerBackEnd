package ar.com.manager.inventory.repository;

import ar.com.manager.inventory.entity.Sale;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {
    List<Sale> findByDeletedFalse();

    List<Sale> findByClientId(Integer clientId);
    List<Sale> findByUserId(Integer userId);

    @Query("SELECT s FROM Sale s WHERE MONTH(s.saleDate) = :month")
    List<Sale> findBySaleDateMonth(@Param("month") int month);

    @Query("SELECT s FROM Sale s WHERE YEAR(s.saleDate) = :year")
    List<Sale> findBySaleDateYear(@Param("year") int year);

    List<Sale> findBySaleDateBetweenAndDeletedFalse(LocalDateTime startDate, LocalDateTime endDate);

}