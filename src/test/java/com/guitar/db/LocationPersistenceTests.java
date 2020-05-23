package com.guitar.db;

import com.guitar.db.model.Location;
import com.guitar.db.repository.LocationJpaRepository;
import com.guitar.db.repository.LocationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration(locations = {"classpath:com/guitar/db/applicationTests-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class LocationPersistenceTests {

    @Autowired
    private LocationJpaRepository locationJpaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testJpaFind() {
        List<Location> locations = locationJpaRepository.findAll();
        assertNotNull(locations);
    }

    @Test
    public void testJpaAnd() {
        List<Location> locations = locationJpaRepository.findByStateAndCountry("Utah", "United States");
        assertEquals("Utah", locations.get(0).getState());
        assertNotNull(locations);
    }

    @Test
    public void testJpaOr() {
        List<Location> locations = locationJpaRepository.findByStateOrCountry("Alabama", "United States");
        assertEquals("Alabama", locations.get(0).getState());
        assertNotNull(locations);
    }

    @Test
    public void testJpaIsAndEquals() {
        List<Location> locations = locationJpaRepository.findByStateIsAndCountryEquals("Utah", "United States");
        assertEquals("Utah", locations.get(0).getState());
        assertNotNull(locations);
    }

    @Test
    public void testJpaNot() {
        List<Location> locations = locationJpaRepository.findByStateNot("Alabama");
        assertNotSame("Alabama", locations.get(0).getState());
        assertNotNull(locations);
    }

    @Test
    @Transactional
    public void testSaveAndGetAndDelete() throws Exception {
        Location location = new Location();
        location.setCountry("Canada");
        location.setState("British Columbia");
        location = locationJpaRepository.saveAndFlush(location);

        // clear the persistence context so we don't return the previously cached location object
        // this is a test only thing and normally doesn't need to be done in prod code
        entityManager.clear();

        Location otherLocation = locationJpaRepository.findOne(location.getId());
        assertEquals("Canada", otherLocation.getCountry());
        assertEquals("British Columbia", otherLocation.getState());

        //delete BC location now
        locationJpaRepository.delete(otherLocation);
    }

    @Test
    public void testFindWithLike() throws Exception {
        List<Location> locs = locationJpaRepository.findByStateLike("New%");
        locs = locationJpaRepository.findByStateNotLike("New%");
        assertEquals(46, locs.size());
    }





    @Test
    @Transactional  //note this is needed because we will get a lazy load exception unless we are in a tx
    public void testFindWithChildren() throws Exception {
        Location arizona = locationJpaRepository.findOne(3L);
        assertEquals("United States", arizona.getCountry());
        assertEquals("Arizona", arizona.getState());

        assertEquals(1, arizona.getManufacturers().size());

        assertEquals("Fender Musical Instruments Corporation", arizona.getManufacturers().get(0).getName());
    }
}
