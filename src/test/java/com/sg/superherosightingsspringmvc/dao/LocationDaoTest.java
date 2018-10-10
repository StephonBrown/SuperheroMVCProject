/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.dao;
import com.sg.superherosightingsspringmvc.dto.Hero;
import com.sg.superherosightingsspringmvc.dto.Location;
import com.sg.superherosightingsspringmvc.dto.Organization;
import com.sg.superherosightingsspringmvc.dto.Sighting;
import com.sg.superherosightingsspringmvc.dto.Superpower;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author sbrown6
 */
public class LocationDaoTest {
    
    private LocationDao dao;
    private SightingDao sightingDao;
    private HeroDao heroDao;

    
    public LocationDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        dao = ctx.getBean("LocationDao", LocationDao.class);
        heroDao = ctx.getBean("HeroDao", HeroDao.class);
        sightingDao = ctx.getBean("SightingDao", SightingDao.class);

        

        List<Sighting> sightings = sightingDao.getAllSightings(Integer.MAX_VALUE, 0);
        for(Sighting currentSighting:sightings){
            sightingDao.removeSighting(currentSighting.getSightingId());
        }
        List<Hero> heroes = heroDao.getAllHeroes(Integer.MAX_VALUE, 0);
        for (Hero currentHero : heroes) {
            heroDao.removeHero(currentHero.getHeroId());
        }
        List<Location> locations = dao.getAllLocations(Integer.MAX_VALUE, 0);
        for (Location currentLocation: locations) {
            dao.removeLocation(currentLocation.getLocationId());
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getAddAndGetLocation method, of class LocationDaoDbImpl.
     */
    @Test
    public void testAddAndGetLocation() throws Exception {
        
        Location lo = new Location();
        lo.setLocationName("Bronx");
        lo.setLocationDescription("In front of the Newspaper Building");
        lo.setStreet("567 Bobo Street");
        lo.setCity("New York City");
        lo.setState("New York");
        lo.setZipCode("95609");
        lo.setLongitude(new BigDecimal(40.712772));
        lo.setLatitude(new BigDecimal(74.006058).negate());

        dao.addLocation(lo);
        Location fromDao = dao.getLocation(lo.getLocationId());
        assertEquals(lo.getLocationId(), fromDao.getLocationId());    
    }
    /**
     * Test of removeLocation method, of class LocationDaoDbImpl.
     */
    @Test
    public void testRemoveLocation() throws Exception {
        Location lo1 = new Location();
        lo1.setLocationName("Bronx");
        lo1.setLocationDescription("In front of the Newspaper Building");
        lo1.setStreet("567 Bobo Street");
        lo1.setCity("New York City");
        lo1.setState("New York");
        lo1.setZipCode("95609");
        lo1.setLongitude(new BigDecimal(40.712772));
        lo1.setLatitude(new BigDecimal(74.006058).negate());
        
        Location lo2 = new Location();
        lo2.setLocationName("Lincoln Memorial");
        lo2.setLocationDescription("At the foot of Lincoln");
        lo2.setStreet("567 Bibi");
        lo2.setCity("Washington DC");
        lo2.setState("District of Columbia");
        lo2.setZipCode("95608");
        lo2.setLongitude(new BigDecimal(38.889931));
        lo2.setLatitude(new BigDecimal(77.009003).negate());
        
        lo1 = dao.addLocation(lo1);
        lo2 = dao.addLocation(lo2);
        
        dao.removeLocation(lo1.getLocationId());
        assertEquals(1, dao.getAllLocations(Integer.MAX_VALUE, 0).size());
        assertNull(dao.getLocation(lo1.getLocationId()));
        
        dao.removeLocation(lo2.getLocationId());
        assertEquals(0, dao.getAllLocations(Integer.MAX_VALUE, 0).size());
        assertNull(dao.getLocation(lo2.getLocationId()));
        
        
    }

    /**
     * Test of editLocation method, of class LocationDaoDbImpl.
     */
    @Test
    public void testEditLocation() throws Exception {
        Location lo1 = new Location();
        lo1.setLocationName("Bronx");
        lo1.setLocationDescription("In front of the Newspaper Building");
        lo1.setStreet("567 Bobo Street");
        lo1.setCity("New York City");
        lo1.setState("New York");
        lo1.setZipCode("95609");
        lo1.setLongitude(new BigDecimal(40.712772));
        lo1.setLatitude(new BigDecimal(74.006058).negate());
        
        dao.addLocation(lo1);
        
        lo1.setStreet("863 Willy Drive");
        
        Location updateLocation = dao.editLocation(lo1);
        
        assertEquals(lo1,updateLocation);
        
    }

    /**
     * Test of getAllLocations method, of class LocationDaoDbImpl.
     */
    @Test
    public void testGetAllLocations() throws Exception {
        Location lo1 = new Location();
        lo1.setLocationName("Bronx");
        lo1.setLocationDescription("In front of the Newspaper Building");
        lo1.setStreet("567 Bobo Street");
        lo1.setCity("New York City");
        lo1.setState("New York");
        lo1.setZipCode("95609");
        lo1.setLongitude(new BigDecimal(40.712772));
        lo1.setLatitude(new BigDecimal(74.006058).negate());
        
        Location lo2 = new Location();
        lo2.setLocationName("Lincoln Memorial");
        lo2.setLocationDescription("At the foot of Lincoln");
        lo2.setStreet("567 Bibi");
        lo2.setCity("Washington DC");
        lo2.setState("District of Columbia");
        lo2.setZipCode("95608");
        lo2.setLongitude(new BigDecimal(38.889931));
        lo2.setLatitude(new BigDecimal(77.009003).negate());
        
        dao.addLocation(lo1);
        dao.addLocation(lo2);

        assertEquals(2, dao.getAllLocations(Integer.MAX_VALUE, 0).size());

    }

    /**
     * Test of getAllHerosSightedAtLocation method, of class LocationDaoDbImpl.
     * @throws java.sql.SQLException
     */
    @Test
    public void  testGetHeroLocations() throws Exception {
        //Locations
        Location lo1 = new Location();
        lo1.setLocationName("Bronx");
        lo1.setLocationDescription("In front of the Newspaper Building");
        lo1.setStreet("567 Bobo Street");
        lo1.setCity("New York City");
        lo1.setState("New York");
        lo1.setZipCode("95609");
        lo1.setLongitude(new BigDecimal(40.712772));
        lo1.setLatitude(new BigDecimal(74.006058).negate());
        
        dao.addLocation(lo1);
        
        Location lo2 = new Location();
        lo2.setLocationName("Lincoln Memorial");
        lo2.setLocationDescription("At the foot of Lincoln");
        lo2.setStreet("567 Bibi");
        lo2.setCity("Washington DC");
        lo2.setState("District of Columbia");
        lo2.setZipCode("95608");
        lo2.setLongitude(new BigDecimal(38.889931));
        lo2.setLatitude(new BigDecimal(77.009003).negate());
        
        dao.addLocation(lo2);
        
        Sighting s1 = new Sighting();
        s1.setSightingDate(LocalDate.parse("2015-01-01", 
                            DateTimeFormatter.ISO_DATE));
        s1.setLocationID(lo1.getLocationId());
        
        Sighting s2 = new Sighting();
        s2.setSightingDate(LocalDate.parse("2017-01-01", 
                            DateTimeFormatter.ISO_DATE));
        s2.setLocationID(lo2.getLocationId());
        
        sightingDao.addSighting(s1);
        sightingDao.addSighting(s2);
        

        Hero hero1 = new Hero();
        hero1.setHeroName("Ice Slider");
        hero1.setHeroDescription("A man that hails from the glacial islands");

        hero1=heroDao.addHero(hero1);
        
        sightingDao.insertHeroSightings(hero1, s1);
        sightingDao.insertHeroSightings(hero1, s2);

        assertEquals(2,dao.getHeroLocations(hero1,Integer.MAX_VALUE, 0).size());
    }
    
}
