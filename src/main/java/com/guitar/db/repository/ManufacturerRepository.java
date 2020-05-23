package com.guitar.db.repository;

import com.guitar.db.model.Manufacturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Repository
public class ManufacturerRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    ManufacturerJpaRepository manufacturerJpaRepository;

    /**
     * Create
     */
    public Manufacturer create(Manufacturer man) {
        return manufacturerJpaRepository.saveAndFlush(man);
    }

    /**
     * Update
     */
    public Manufacturer update(Manufacturer man) {
        return manufacturerJpaRepository.saveAndFlush(man);
    }

    /**
     * Delete
     */
    public void delete(Manufacturer man) {
        manufacturerJpaRepository.delete(man);
    }

    /**
     * Find
     */
    public Manufacturer find(Long id) {
        return manufacturerJpaRepository.findOne(id);
    }

    /**
     * Custom finder
     */
    public List<Manufacturer> getManufacturersFoundedBeforeDate(Date date) {
//        @SuppressWarnings("unchecked")
//        List<Manufacturer> mans = entityManager
//                .createQuery("select m from Manufacturer m where m.foundedDate < :date")
//                .setParameter("date", date).getResultList();
//        return mans;
        return manufacturerJpaRepository.findByFoundedDateBefore(date);
    }

    /**
     * Custom finder
     */
    public Manufacturer getManufacturerByName(String name) {
        Manufacturer man = (Manufacturer) entityManager
                .createQuery("select m from Manufacturer m where m.name like :name")
                .setParameter("name", name + "%").getSingleResult();
        return man;
    }

    /**
     * Native Query finder
     */
    public List<Manufacturer> getManufacturersThatSellModelsOfType(String modelType) {
//        @SuppressWarnings("unchecked")
//        List<Manufacturer> mans = entityManager
//                .createNamedQuery("Manufacturer.getAllThatSellAcoustics")
//                .setParameter(1, modelType).getResultList();
//        return mans;
        return manufacturerJpaRepository.getAllThatSellAcoustics(modelType);
    }
}
