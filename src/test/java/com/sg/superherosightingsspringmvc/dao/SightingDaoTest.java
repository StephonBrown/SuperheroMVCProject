/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.dao;

import com.sg.superherosightingsspringmvc.dto.Hero;
import com.sg.superherosightingsspringmvc.dto.Location;
import com.sg.superherosightingsspringmvc.dto.Sighting;
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
public class SightingDaoTest {
    
    private SightingDao dao;
    private LocationDao locationDao;
    private HeroDao heroDao;
    
    public SightingDaoTest() {
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
        dao = ctx.getBean("SightingDao", SightingDao.class);
        locationDao = ctx.getBean("LocationDao", LocationDao.class);
        heroDao = ctx.getBean("HeroDao", HeroDao.class);
        
        List<Sighting> sightings = dao.getAllSightings(Integer.MAX_VALUE, 0);
        for(Sighting currentSighting:sightings){
            dao.removeSighting(currentSighting.getSightingId());
        }
        List<Hero> heroes = heroDao.getAllHeroes(Integer.MAX_VALUE, 0);
        for (Hero currentHero: heroes) {
            heroDao.removeHero(currentHero.getHeroId());
        }
        List<Location> locationList = locationDao.getAllLocations(Integer.MAX_VALUE, 0);
        for(Location currentLocation:locationList){
            locationDao.removeLocation(currentLocation.getLocationId());
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addAndGetSighting method, of class SightingDao.
     */
    @Test
    public void testAddAndGetSighting() throws Exception {
        Location lo = new Location();
        lo.setLocationName("Bronx");
        lo.setLocationDescription("In front of the Newspaper Building");
        lo.setStreet("567 Bobo Street");
        lo.setCity("New York City");
        lo.setState("New York");
        lo.setZipCode("95609");
        lo.setLongitude(new BigDecimal(40.712772));
        lo.setLatitude(new BigDecimal(74.006058).negate());
        
        lo = locationDao.addLocation(lo);
        
        
        Sighting s1 = new Sighting();
        s1.setSightingDate(LocalDate.parse("2015-01-01", 
                            DateTimeFormatter.ISO_DATE));
        s1.setLocationID(lo.getLocationId());
        
        dao.addSighting(s1);
        
        Sighting fromDao = dao.getSighting(s1.getSightingId());
        assertEquals(s1, fromDao);  
    }

    /**
     * Test of removeSighting method, of class SightingDao.
     */
    @Test
    public void testRemoveSighting() throws Exception {
        Location lo1 = new Location();
        lo1.setLocationName("Bronx");
        lo1.setLocationDescription("In front of the Newspaper Building");
        lo1.setStreet("567 Bobo Street");
        lo1.setCity("New York City");
        lo1.setState("New York");
        lo1.setZipCode("95609");
        lo1.setLongitude(new BigDecimal(40.712772));
        lo1.setLatitude(new BigDecimal(74.006058).negate());
        
        locationDao.addLocation(lo1);
        
        Location lo2 = new Location();
        lo2.setLocationName("Lincoln Memorial");
        lo2.setLocationDescription("At the foot of Lincoln");
        lo2.setStreet("567 Bibi");
        lo2.setCity("Washington DC");
        lo2.setState("District of Columbia");
        lo2.setZipCode("95608");
        lo2.setLongitude(new BigDecimal(38.889931));
        lo2.setLatitude(new BigDecimal(77.009003).negate());
        
        locationDao.addLocation(lo2);
        
        Sighting s1 = new Sighting();
        s1.setSightingDate(LocalDate.parse("2015-01-01", 
                            DateTimeFormatter.ISO_DATE));
        s1.setLocationID(lo1.getLocationId());
        
        Sighting s2 = new Sighting();
        s2.setSightingDate(LocalDate.parse("2017-01-01", 
                            DateTimeFormatter.ISO_DATE));
        s2.setLocationID(lo2.getLocationId());
        
        dao.addSighting(s1);
        dao.addSighting(s2);
        
        dao.removeSighting(s1.getSightingId());
        assertEquals(1,dao.getAllSightings(Integer.MAX_VALUE, 0).size());
        assertNull(dao.getSighting(s1.getSightingId()));
        
        dao.removeSighting(s2.getSightingId());
        assertEquals(0,dao.getAllSightings(Integer.MAX_VALUE, 0).size());
        assertNull(dao.getSighting(s2.getSightingId())); 
    }

    /**
     * Test of editSighting method, of class SightingDao.
     */
    @Test
    public void testEditSighting() throws Exception {
        Location lo1 = new Location();
        lo1.setLocationName("Bronx");
        lo1.setLocationDescription("In front of the Newspaper Building");
        lo1.setStreet("567 Bobo Street");
        lo1.setCity("New York City");
        lo1.setState("New York");
        lo1.setZipCode("95609");
        lo1.setLongitude(new BigDecimal(40.712772));
        lo1.setLatitude(new BigDecimal(74.006058).negate());
        
        locationDao.addLocation(lo1);
        
        Location lo2 = new Location();
        lo2.setLocationName("Lincoln Memorial");
        lo2.setLocationDescription("At the foot of Lincoln");
        lo2.setStreet("567 Bibi");
        lo2.setCity("Washington DC");
        lo2.setState("District of Columbia");
        lo2.setZipCode("95608");
        lo2.setLongitude(new BigDecimal(38.889931));
        lo2.setLatitude(new BigDecimal(77.009003).negate());
        
        locationDao.addLocation(lo2);
        
        Sighting s1 = new Sighting();
        s1.setSightingDate(LocalDate.parse("2015-01-01", 
                            DateTimeFormatter.ISO_DATE));
        s1.setLocationID(lo1.getLocationId());
        s1 = dao.addSighting(s1);
        
        s1.setLocationID(lo2.getLocationId());
        dao.editSighting(s1);
        
        Sighting fromDao = dao.getSighting(s1.getSightingId());
        
        assertEquals(s1,fromDao);
        
        
    }

    /**
     * Test of getAllSightings method, of class SightingDao.
     */
    @Test
    public void testGetAllSightings() throws Exception {
        Location lo1 = new Location();
        lo1.setLocationName("Bronx");
        lo1.setLocationDescription("In front of the Newspaper Building");
        lo1.setStreet("567 Bobo Street");
        lo1.setCity("New York City");
        lo1.setState("New York");
        lo1.setZipCode("95609");
        lo1.setLongitude(new BigDecimal(40.712772));
        lo1.setLatitude(new BigDecimal(74.006058).negate());
        
        locationDao.addLocation(lo1);
        
        Location lo2 = new Location();
        lo2.setLocationName("Lincoln Memorial");
        lo2.setLocationDescription("At the foot of Lincoln");
        lo2.setStreet("567 Bibi");
        lo2.setCity("Washington DC");
        lo2.setState("District of Columbia");
        lo2.setZipCode("95608");
        lo2.setLongitude(new BigDecimal(38.889931));
        lo2.setLatitude(new BigDecimal(77.009003).negate());
        
        locationDao.addLocation(lo2);
        
        Sighting s1 = new Sighting();
        s1.setSightingDate(LocalDate.parse("2015-01-01", 
                            DateTimeFormatter.ISO_DATE));
        s1.setLocationID(lo1.getLocationId());
        
        Sighting s2 = new Sighting();
        s2.setSightingDate(LocalDate.parse("2017-01-01", 
                            DateTimeFormatter.ISO_DATE));
        s2.setLocationID(lo2.getLocationId());
        
        dao.addSighting(s1);
        dao.addSighting(s2);
        
        assertEquals(2, dao.getAllSightings(Integer.MAX_VALUE, 0).size());
        
    }

    /**
     * Test of getSightingsByDate method, of class SightingDao.
     * @throws java.sql.SQLException
     */
    @Test
    public void testGetSightingsByDate() throws Exception {
        
        Location lo1 = new Location();
        lo1.setLocationName("Bronx");
        lo1.setLocationDescription("In front of the Newspaper Building");
        lo1.setStreet("567 Bobo Street");
        lo1.setCity("New York City");
        lo1.setState("New York");
        lo1.setZipCode("95609");
        lo1.setLongitude(new BigDecimal(40.712772));
        lo1.setLatitude(new BigDecimal(74.006058).negate());
        
        locationDao.addLocation(lo1);
        
        Location lo2 = new Location();
        lo2.setLocationName("Lincoln Memorial");
        lo2.setLocationDescription("At the foot of Lincoln");
        lo2.setStreet("567 Bibi");
        lo2.setCity("Washington DC");
        lo2.setState("District of Columbia");
        lo2.setZipCode("95608");
        lo2.setLongitude(new BigDecimal(38.889931));
        lo2.setLatitude(new BigDecimal(77.009003).negate());
        
        locationDao.addLocation(lo2);
        
        Sighting s1 = new Sighting();
        s1.setSightingDate(LocalDate.parse("2015-01-01", 
                            DateTimeFormatter.ISO_DATE));
        s1.setLocationID(lo1.getLocationId());
        
        Sighting s2 = new Sighting();
        s2.setSightingDate(LocalDate.parse("2017-01-01", 
                            DateTimeFormatter.ISO_DATE));
        s2.setLocationID(lo2.getLocationId());
        
        dao.addSighting(s1);
        dao.addSighting(s2);
        
        Sighting fromDao = dao.getSightingsByDate(LocalDate.parse("2017-01-01", DateTimeFormatter.ISO_DATE), Integer.MAX_VALUE, 0).get(0);
        assertEquals(s2, fromDao);
       

    }
    @Test
    public void testGetHeroSightings() throws Exception{
        
        Location lo1 = new Location();
        lo1.setLocationName("Bronx");
        lo1.setLocationDescription("In front of the Newspaper Building");
        lo1.setStreet("567 Bobo Street");
        lo1.setCity("New York City");
        lo1.setState("New York");
        lo1.setZipCode("95609");
        lo1.setLongitude(new BigDecimal(40.712772));
        lo1.setLatitude(new BigDecimal(74.006058).negate());
        
        locationDao.addLocation(lo1);
        
        Location lo2 = new Location();
        lo2.setLocationName("Lincoln Memorial");
        lo2.setLocationDescription("At the foot of Lincoln");
        lo2.setStreet("567 Bibi");
        lo2.setCity("Washington DC");
        lo2.setState("District of Columbia");
        lo2.setZipCode("95608");
        lo2.setLongitude(new BigDecimal(38.889931));
        lo2.setLatitude(new BigDecimal(77.009003).negate());
        
        locationDao.addLocation(lo2);
        
        Sighting s1 = new Sighting();
        s1.setSightingDate(LocalDate.parse("2015-01-01", 
                            DateTimeFormatter.ISO_DATE));
        s1.setLocationID(lo1.getLocationId());
        
        Sighting s2 = new Sighting();
        s2.setSightingDate(LocalDate.parse("2017-01-01", 
                            DateTimeFormatter.ISO_DATE));
        s2.setLocationID(lo2.getLocationId());
        
        dao.addSighting(s1);
        dao.addSighting(s2);

        
        Hero hero1 = new Hero();
        hero1.setHeroName("Ice Slider");
        hero1.setHeroDescription("A man that hails from the glacial islands");

        Hero hero2 = new Hero();
        hero2.setHeroName("BucketHead");
        hero2.setHeroDescription("A man with a buckethead");
        
        heroDao.addHero(hero1);
        heroDao.addHero(hero2);
        
        dao.insertHeroSightings(hero1, s1);
        dao.insertHeroSightings(hero2, s2);
        
        Sighting fromDao1 = dao.getHeroSightings(hero1, Integer.MAX_VALUE, 0).get(0);
        Sighting fromDao2 = dao.getHeroSightings(hero2, Integer.MAX_VALUE, 0).get(0);
        
        assertEquals(s1, fromDao1);
        assertEquals(s2, fromDao2);
        
    }
    
}
