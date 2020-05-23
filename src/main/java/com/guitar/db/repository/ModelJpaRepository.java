package com.guitar.db.repository;

import com.guitar.db.model.Manufacturer;
import com.guitar.db.model.Model;
import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ModelJpaRepository extends JpaRepository<Model, Long> {
    List<Model> findByPriceGreaterThanEqualAndPriceLessThanEqual(BigDecimal low, BigDecimal high);

    List<Model> findByModelTypeNameIn(List<String> types);

    @Query("select m from Model m where m.price >= :lowest and m.price <= :highest and m.woodType like :wood")
    List<Model> queryByPriceRangeAAndWoodType(@Param("lowest") BigDecimal lowest,
                                              @Param("highest") BigDecimal highest,
                                              @Param("wood") String wood);
}
