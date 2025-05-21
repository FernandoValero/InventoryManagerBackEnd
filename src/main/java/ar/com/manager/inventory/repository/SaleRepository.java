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

    List<Sale> findByClientIdAndDeletedFalse(Integer clientId);
    List<Sale> findByUserIdAndDeletedFalse(Integer userId);

    @Query("SELECT s FROM Sale s WHERE MONTH(s.saleDate) = :month AND s.deleted = false")
    List<Sale> findBySaleDateMonthAndDeletedFalse(@Param("month") int month);

    @Query("SELECT s FROM Sale s WHERE YEAR(s.saleDate) = :year AND s.deleted = false")
    List<Sale> findBySaleDateYearAndDeletedFalse(@Param("year") int year);

    List<Sale> findBySaleDateBetweenAndDeletedFalse(LocalDateTime startDate, LocalDateTime endDate);

}