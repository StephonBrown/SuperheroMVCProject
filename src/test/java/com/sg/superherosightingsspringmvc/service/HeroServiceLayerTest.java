/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.service;

import com.sg.superherosightingsspringmvc.dto.Hero;
import com.sg.superherosightingsspringmvc.dto.Location;
import com.sg.superherosightingsspringmvc.dto.Organization;
import com.sg.superherosightingsspringmvc.dto.Sighting;
import com.sg.superherosightingsspringmvc.dto.Superpower;
import java.math.BigDecimal;
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
public class HeroServiceLayerTest {
    
    private HeroServiceLayer heServiceLayer;
    private SightingServiceLayer stServiceLayer;
    private LocationServiceLayer locServiceLayer;
    private OrganizationServiceLayer orgServiceLayer;
    private SuperpowerServiceLayer spServiceLayer;
    
    public HeroServiceLayerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception{
        ApplicationContext ctx = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        heServiceLayer = ctx.getBean("HeroServiceLayer", HeroServiceLayer.class);
        orgServiceLayer = ctx.getBean("OrganizationServiceLayer", OrganizationServiceLayer.class);
        locServiceLayer = ctx.getBean("LocationServiceLayer", LocationServiceLayer.class);
        stServiceLayer = ctx.getBean("SightingServiceLayer", SightingServiceLayer.class);
        spServiceLayer = ctx.getBean("SuperpowerServiceLayer", SuperpowerServiceLayer.class);
        
        List<Hero> library = heServiceLayer.getAllHeroes(Integer.MAX_VALUE, 0);
        for (Hero currentHero : library) {
            heServiceLayer.removeHero(currentHero.getHeroId());
        }
        List<Organization> orgs = orgServiceLayer.getAllOrganizations(Integer.MAX_VALUE, 0);
        for (Organization currentOrganization: orgs) {
            orgServiceLayer.removeOrganization(currentOrganization.getOrganizationId());
        }
        List<Superpower> powers = spServiceLayer.getAllSuperpowers(Integer.MAX_VALUE, 0);
        for(Superpower currentPower : powers) {
            spServiceLayer.removeSuperpower(currentPower.getSuperPowerId());
        }
        List<Sighting> sightings = stServiceLayer.getAllSightings(Integer.MAX_VALUE, 0);
        for(Sighting currentSighting:sightings){
            stServiceLayer.removeSighting(currentSighting.getSightingId());
        }
        List<Location> locations = locServiceLayer.getAllLocations(Integer.MAX_VALUE, 0);
        for (Location currentLocation: locations) {
            locServiceLayer.removeLocation(currentLocation.getLocationId());
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getHero method, of class HeroServiceLayer.
     */
    @Test
    public void testCreateAndGetHero() throws Exception {
        Hero hero = new Hero();
        hero.setHeroName("Ice Slider");
        hero.setHeroDescription("A man that hails from the glacial islands");
        
        heServiceLayer.createHero(hero);     
        
        Hero fromService = heServiceLayer.getHero(hero.getHeroId());
        assertEquals(hero,fromService);
    }

    /**
     * Test of createHero method, of class HeroServiceLayer.
     */
    @Test
    public void testCreateHeroInvalidData() throws Exception {

        //No superpower was set
        Hero hero = new Hero();
        hero.setHeroName("Ice Slider");
        hero.setHeroDescription("");
        
        try{
          heServiceLayer.createHero(hero);
          fail("Expected HeroDataValidationException was not thrown");
        }catch(HeroDataValidationException e){
            return;
        }
        

    }

    /**
     * Test of removeHero method, of class HeroServiceLayer.
     */
    @Test
    public void testRemoveHero() throws Exception{

        Hero hero1 = new Hero();
        hero1.setHeroName("Ice Slider");
        hero1.setHeroDescription("A man that hails from the glacial islands");

        Hero hero2 = new Hero();
        hero2.setHeroName("Flower Minder");
        hero2.setHeroDescription("A man that can give a bouque and control your mind");

        heServiceLayer.createHero(hero1);
        heServiceLayer.createHero(hero2);
        
        heServiceLayer.removeHero(hero1.getHeroId());
        assertEquals(1, heServiceLayer.getAllHeroes(Integer.MAX_VALUE, 0).size());
        
        heServiceLayer.removeHero(hero2.getHeroId());
        assertEquals(0, heServiceLayer.getAllHeroes(Integer.MAX_VALUE, 0).size());

    }

    /**
     * Test of editHero method, of class HeroServiceLayer.
     */
    @Test
    public void testEditHero() throws Exception {
        
        Hero hero1 = new Hero();
        hero1.setHeroName("Ice Slider");
        hero1.setHeroDescription("A man that hails from the glacial islands");
        
        Hero hero2 = new Hero();
        hero2.setHeroName("Flower Minder");
        hero2.setHeroDescription("A man that can give a bouque and control your mind");

        heServiceLayer.createHero(hero1);
        heServiceLayer.createHero(hero2);
        
        
        hero1.setHeroName("Ice Fighter");
        heServiceLayer.editHero(hero1);
        
        Hero fromService = heServiceLayer.getHero(hero1.getHeroId());
        assertEquals(hero1, fromService);

    }
    
    /**
     * Test of editHero method, of class HeroServiceLayer.
     */
    @Test
    public void testEditHeroInvalidData() throws Exception {

        Hero hero1 = new Hero();
        hero1.setHeroName("Ice Slider");
        hero1.setHeroDescription("A man that hails from the glacial islands");
        
        Hero hero2 = new Hero();
        hero2.setHeroName("Flower Minder");
        hero2.setHeroDescription("A man that can give a bouque and control your mind");

        heServiceLayer.createHero(hero1);
        heServiceLayer.createHero(hero2);
        
        hero1.setHeroName(" ");
        try{    
            heServiceLayer.editHero(hero1); 
            fail("Expected HeroDataValidationException was not thrown");
        }catch(HeroDataValidationException e){
            return;
        }
    }

    /**
     * Test of getAllHeroes method, of class HeroServiceLayer.
     */
    @Test
    public void testGetAllHeroes() throws Exception {

        Hero hero1 = new Hero();
        hero1.setHeroName("Ice Slider");
        hero1.setHeroDescription("A man that hails from the glacial islands");
          
        Hero hero2 = new Hero();
        hero2.setHeroName("Flower Minder");
        hero2.setHeroDescription("A man that can give a bouque and control your mind");

        heServiceLayer.createHero(hero1);
        heServiceLayer.createHero(hero2);
        
        assertEquals(2, heServiceLayer.getAllHeroes(Integer.MAX_VALUE, 0).size());
    }
    @Test
    public void testGetHeroAtSighting() throws Exception{
        Location lo1 = new Location();
        lo1.setLocationName("Bronx");
        lo1.setLocationDescription("In front of the Newspaper Building");
        lo1.setStreet("567 Bobo Street");
        lo1.setCity("New York City");
        lo1.setState("New York");
        lo1.setZipCode("95609");
        lo1.setLongitude(new BigDecimal(40.712772));
        lo1.setLatitude(new BigDecimal(74.006058).negate());
        
        locServiceLayer.createLocation(lo1);
        
        Location lo2 = new Location();
        lo2.setLocationName("Lincoln Memorial");
        lo2.setLocationDescription("At the foot of Lincoln");
        lo2.setStreet("567 Bibi");
        lo2.setCity("Washington DC");
        lo2.setState("District of Columbia");
        lo2.setZipCode("95608");
        lo2.setLongitude(new BigDecimal(38.889931));
        lo2.setLatitude(new BigDecimal(77.009003).negate());
        
        locServiceLayer.createLocation(lo2);
        
        Sighting s1 = new Sighting();
        s1.setSightingDate(LocalDate.parse("2015-01-01", 
                            DateTimeFormatter.ISO_DATE));
        s1.setLocationID(lo1.getLocationId());
        
        Sighting s2 = new Sighting();
        s2.setSightingDate(LocalDate.parse("2017-01-01", 
                            DateTimeFormatter.ISO_DATE));
        s2.setLocationID(lo2.getLocationId());
        
        s1 = stServiceLayer.createSighting(s1);
        s2 = stServiceLayer.createSighting(s2);

        
        Hero hero1 = new Hero();
        hero1.setHeroName("Ice Slider");
        hero1.setHeroDescription("A man that hails from the glacial islands");
        
        Hero hero2 = new Hero();
        hero2.setHeroName("BucketHead");
        hero2.setHeroDescription("A man with a buckethead");
        
        heServiceLayer.createHero(hero1);
        heServiceLayer.createHero(hero2);
        
        stServiceLayer.insertHeroSightings(hero1, s1);
        stServiceLayer.insertHeroSightings(hero2, s2);
        
        Hero fromService1 = heServiceLayer.getHeroAtSighting(s1);
        Hero fromService2 = heServiceLayer.getHeroAtSighting(s2);

        assertEquals(hero1, fromService1);
        assertEquals(hero2, fromService2);
    };
    
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
        
        locServiceLayer.createLocation(lo1);
        
        Location lo2 = new Location();
        lo2.setLocationName("Lincoln Memorial");
        lo2.setLocationDescription("At the foot of Lincoln");
        lo2.setStreet("567 Bibi");
        lo2.setCity("Washington DC");
        lo2.setState("District of Columbia");
        lo2.setZipCode("95608");
        lo2.setLongitude(new BigDecimal(38.889931));
        lo2.setLatitude(new BigDecimal(77.009003).negate());
        
        locServiceLayer.createLocation(lo2);
        
        Sighting s1 = new Sighting();
        s1.setSightingDate(LocalDate.parse("2015-01-01", 
                            DateTimeFormatter.ISO_DATE));
        s1.setLocationID(lo1.getLocationId());
        
        Sighting s2 = new Sighting();
        s2.setSightingDate(LocalDate.parse("2017-01-01", 
                            DateTimeFormatter.ISO_DATE));
        s2.setLocationID(lo2.getLocationId());
        
        stServiceLayer.createSighting(s1);
        stServiceLayer.createSighting(s2);

        
        Hero hero1 = new Hero();
        hero1.setHeroName("Ice Slider");
        hero1.setHeroDescription("A man that hails from the glacial islands");

        Hero hero2 = new Hero();
        hero1.setHeroName("BucketHead");
        hero1.setHeroDescription("A man with a buckethead");
        
        heServiceLayer.createHero(hero1);
        heServiceLayer.createHero(hero2);
        
        stServiceLayer.insertHeroSightings(hero1, s1);
        stServiceLayer.insertHeroSightings(hero2, s1);
        
        Hero fromService1 = heServiceLayer.getAllHerosSightedAtLocation(lo1, Integer.MAX_VALUE, 0).get(0);
        Hero fromService2 = heServiceLayer.getAllHerosSightedAtLocation(lo1, Integer.MAX_VALUE, 0).get(1);
        
        assertEquals(hero1, fromService1);
        assertEquals(hero2, fromService2);
        assertEquals(2, heServiceLayer.getAllHerosSightedAtLocation(lo1,Integer.MAX_VALUE, 0).size());
    };
    
}
