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
public class HeroDaoTest {
    
    private HeroDao dao;
    private SightingDao sightingDao;
    private LocationDao locationDao;
    private OrganizationDao orgDao;
    private SuperpowerDao spDao;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        dao = ctx.getBean("HeroDao", HeroDao.class);
        orgDao = ctx.getBean("OrganizationDao", OrganizationDao.class);
        locationDao = ctx.getBean("LocationDao", LocationDao.class);
        sightingDao = ctx.getBean("SightingDao", SightingDao.class);
        spDao = ctx.getBean("SuperpowerDao", SuperpowerDao.class);
        
        List<Hero> library = dao.getAllHeroes(Integer.MAX_VALUE, 0);
        for (Hero currentHero : library) {
            dao.removeHero(currentHero.getHeroId());
        }
        List<Organization> orgs = orgDao.getAllOrganizations(Integer.MAX_VALUE, 0);
        for (Organization currentOrganization: orgs) {
            orgDao.removeOrganization(currentOrganization.getOrganizationId());
        }
        List<Superpower> powers = spDao.getAllSuperpowers(Integer.MAX_VALUE, 0);
        for(Superpower currentPower : powers) {
            spDao.removeSuperpower(currentPower.getSuperPowerId());
        }
        List<Sighting> sightings = sightingDao.getAllSightings(Integer.MAX_VALUE, 0);
        for(Sighting currentSighting:sightings){
            sightingDao.removeSighting(currentSighting.getSightingId());
        }
        List<Location> locations = locationDao.getAllLocations(Integer.MAX_VALUE, 0);
        for (Location currentLocation: locations) {
            locationDao.removeLocation(currentLocation.getLocationId());
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of AddAndGetHero method, of class HeroDao.
     */
    @Test
    public void testAddAndGetHero() throws Exception {
        Hero hero = new Hero();
        hero.setHeroName("Ice Slider");
        hero.setHeroDescription("A man that hails from the glacial islands");
        
        dao.addHero(hero);     
        
        Hero fromDao = dao.getHero(hero.getHeroId());
        assertEquals(hero,fromDao);
            
    }
    
        /**
     * Test of getAllHeros method, of class HeroDao.
     * @throws java.sql.SQLException
     */
    @Test
    public void testGetAllHeros() throws Exception {
        Hero hero1 = new Hero();
        hero1.setHeroName("Ice Slider");
        hero1.setHeroDescription("A man that hails from the glacial islands");

        Hero hero2 = new Hero();
        hero2.setHeroName("Flower Minder");
        hero2.setHeroDescription("A man that can give a bouque and control your mind");
        
        dao.addHero(hero1);
        dao.addHero(hero2);
        
        assertEquals(2, dao.getAllHeroes(Integer.MAX_VALUE, 0).size());
    }

    /**
     * Test of removeHero method, of class HeroDao.
     * @throws java.sql.SQLException
     */
    @Test
    public void testRemoveHero() throws Exception {

        Hero hero1 = new Hero();
        hero1.setHeroName("Ice Slider");
        hero1.setHeroDescription("A man that hails from the glacial islands");

        Hero hero2 = new Hero();
        hero2.setHeroName("Flower Minder");
        hero2.setHeroDescription("A man that can give a bouque and control your mind");

        dao.addHero(hero1);
        dao.addHero(hero2);
        
        dao.removeHero(hero1.getHeroId());
        assertEquals(1, dao.getAllHeroes(Integer.MAX_VALUE, 0).size());
        assertNull(dao.getHero(hero1.getHeroId()));
        
        dao.removeHero(hero2.getHeroId());
        assertEquals(0, dao.getAllHeroes(Integer.MAX_VALUE, 0).size());
        assertNull(dao.getHero(hero2.getHeroId()));   
    }

    /**
     * Test of editHero method, of class HeroDao.
     */
    @Test
    public void testEditHero() throws Exception {
        
        Hero hero1 = new Hero();
        hero1.setHeroName("Ice Slider");
        hero1.setHeroDescription("A man that hails from the glacial islands");

        Hero hero2 = new Hero();
        hero2.setHeroName("Flower Minder");
        hero2.setHeroDescription("A man that can give a bouque and control your mind");

        dao.addHero(hero1);
        dao.addHero(hero2);
        
        
        hero1.setHeroName("Ice Fighter");
        dao.editHero(hero1);
        
        Hero fromDao = dao.getHero(hero1.getHeroId());
        assertEquals(hero1, fromDao);
    
    }

    /**
     * Test of getHeroSightings method, of class HeroDao.
     */
    @Test
    public void testGetHeroAtSighting() throws Exception {
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
        
        s1 = sightingDao.addSighting(s1);
        s2 = sightingDao.addSighting(s2);

        
        Hero hero1 = new Hero();
        hero1.setHeroName("Ice Slider");
        hero1.setHeroDescription("A man that hails from the glacial islands");
        
        Hero hero2 = new Hero();
        hero2.setHeroName("BucketHead");
        hero2.setHeroDescription("A man with a buckethead");
        
        dao.addHero(hero1);
        dao.addHero(hero2);
        
        sightingDao.insertHeroSightings(hero1, s1);
        sightingDao.insertHeroSightings(hero2, s2);
        
        Hero fromDao1 = dao.getHeroAtSighting(s1);
        Hero fromDao2 = dao.getHeroAtSighting(s2);

        assertEquals(hero1, fromDao1);
        assertEquals(hero2, fromDao2);

    }

    /**
     * Test of getHeroLocations method, of class HeroDao.
     * @throws java.sql.SQLException
     */
    @Test
    public void testGetAllHerosSightedAtLocation() throws Exception{
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
        
        sightingDao.addSighting(s1);
        sightingDao.addSighting(s2);

        
        Hero hero1 = new Hero();
        hero1.setHeroName("Ice Slider");
        hero1.setHeroDescription("A man that hails from the glacial islands");

        Hero hero2 = new Hero();
        hero2.setHeroName("BucketHead");
        hero2.setHeroDescription("A man with a buckethead");
        
        dao.addHero(hero1);
        dao.addHero(hero2);
        
        sightingDao.insertHeroSightings(hero1, s1);
        sightingDao.insertHeroSightings(hero2, s1);
        
        Hero fromDao1 = dao.getAllHerosSightedAtLocation(lo1, Integer.MAX_VALUE, 0).get(0);
        Hero fromDao2 = dao.getAllHerosSightedAtLocation(lo1, Integer.MAX_VALUE, 0).get(1);
        
        assertEquals(hero1, fromDao1);
        assertEquals(hero2, fromDao2);
        assertEquals(2, dao.getAllHerosSightedAtLocation(lo1, Integer.MAX_VALUE, 0).size());
    }
    
}
