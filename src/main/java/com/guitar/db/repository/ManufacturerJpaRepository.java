package com.guitar.db.repository;

import com.guitar.db.model.Location;
import com.guitar.db.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerJpaRepository extends JpaRepository<Manufacturer, Long> {

}
