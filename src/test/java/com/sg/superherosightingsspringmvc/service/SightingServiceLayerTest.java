/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.service;

import com.sg.superherosightingsspringmvc.dto.Hero;
import com.sg.superherosightingsspringmvc.dto.Location;
import com.sg.superherosightingsspringmvc.dto.Sighting;
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
public class SightingServiceLayerTest {
    private SightingServiceLayer stServiceLayer;
    private LocationServiceLayer locServiceLayer;
    private HeroServiceLayer heServiceLayer;
        
    
    public SightingServiceLayerTest() {
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
        stServiceLayer = ctx.getBean("SightingServiceLayer", SightingServiceLayer.class);
        locServiceLayer = ctx.getBean("LocationServiceLayer", LocationServiceLayer.class);
        heServiceLayer = ctx.getBean("HeroServiceLayer", HeroServiceLayer.class);
        
        List<Hero> heroes = heServiceLayer.getAllHeroes(Integer.MAX_VALUE, 0);
        for (Hero currentHero: heroes) {
            heServiceLayer.removeHero(currentHero.getHeroId());
        }
        List<Sighting> sightings = stServiceLayer.getAllSightings(Integer.MAX_VALUE, 0);
        for(Sighting currentSighting:sightings){
            stServiceLayer.removeSighting(currentSighting.getSightingId());
        }
        List<Location> locationList = locServiceLayer.getAllLocations(Integer.MAX_VALUE, 0);
        for(Location currentLocation:locationList){
            locServiceLayer.removeLocation(currentLocation.getLocationId());
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getSighting method, of class SightingServiceLayer.
     */
    @Test
    public void testCreateAndGetSighting() throws Exception {
        
        Location lo = new Location();
        lo.setLocationName("Bronx");
        lo.setLocationDescription("In front of the Newspaper Building");
        lo.setStreet("567 Bobo Street");
        lo.setCity("New York City");
        lo.setState("New York");
        lo.setZipCode("95609");
        lo.setLongitude(new BigDecimal(40.712772));
        lo.setLatitude(new BigDecimal(74.006058).negate());
        
        locServiceLayer.createLocation(lo);
        
        
        Sighting s1 = new Sighting();
        s1.setSightingDate(LocalDate.parse("2015-01-01", 
                            DateTimeFormatter.ISO_DATE));
        s1.setLocationID(lo.getLocationId());
        
        stServiceLayer.createSighting(s1);
        
        Sighting fromDao = stServiceLayer.getSighting(s1.getSightingId());
        assertEquals(s1, fromDao);  
    }

    /**
     * Test of createSighting method, of class SightingServiceLayer.
     */
    @Test
    public void testCreateSightingInvalidData() throws Exception {
        
        Location lo = new Location();
        lo.setLocationName("Bronx");
        lo.setLocationDescription("In front of the Newspaper Building");
        lo.setStreet("567 Bobo Street");
        lo.setCity("New York City");
        lo.setState("New York");
        lo.setZipCode("95609");
        lo.setLongitude(new BigDecimal(40.712772));
        lo.setLatitude(new BigDecimal(74.006058).negate());
        
        locServiceLayer.createLocation(lo);
        
        
        Sighting s1 = new Sighting();
        s1.setSightingDate(null);
        s1.setLocationID(lo.getLocationId());

        try{
           stServiceLayer.createSighting(s1); 
           fail("Expected SightingDataValidationException was not thrown");
        }catch(SightingDataValidationException e){
            return;
        }

    }

    /**
     * Test of removeSighting method, of class SightingServiceLayer.
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
        
        
        stServiceLayer.createSighting(s1);

        stServiceLayer.removeSighting(s1.getSightingId());
        assertEquals(0,stServiceLayer.getAllSightings(Integer.MAX_VALUE, 0).size());

    }

    /**
     * Test of editSighting method, of class SightingServiceLayer.
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
        stServiceLayer.createSighting(s1);
        
        s1.setLocationID(lo2.getLocationId());
        stServiceLayer.editSighting(s1);
        
        Sighting fromService = stServiceLayer.getSighting(s1.getSightingId());
        
        assertEquals(s1,fromService);
    }
    
    /**
     * Test of editSighting method, of class SightingServiceLayer.
     */
    @Test
    public void testEditSightingInvalidData() throws Exception {
                
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
        stServiceLayer.createSighting(s1);
        
        s1.setSightingDate(null);
        
        try{
           stServiceLayer.editSighting(s1); 
           fail("Expected SightingDataValidationException was not thrown");
        }catch(SightingDataValidationException e){
            return;
        }
        
        
       

    }

    /**
     * Test of getAllSightings method, of class SightingServiceLayer.
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
        
        assertEquals(2, stServiceLayer.getAllSightings(Integer.MAX_VALUE, 0).size());

    }

    /**
     * Test of getSightingsByDate method, of class SightingServiceLayer.
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
        
        Sighting fromService = stServiceLayer.getSightingsByDate(LocalDate.parse("2017-01-01", DateTimeFormatter.ISO_DATE), Integer.MAX_VALUE, 0).get(0);
        assertEquals(s2, fromService);
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
        hero2.setHeroName("BucketHead");
        hero2.setHeroDescription("A man with a buckethead");
        
        heServiceLayer.createHero(hero1);
        heServiceLayer.createHero(hero2);
        
        stServiceLayer.insertHeroSightings(hero1, s1);
        stServiceLayer.insertHeroSightings(hero2, s2);
        
        Sighting fromService1 = stServiceLayer.getHeroSightings(hero1, Integer.MAX_VALUE, 0).get(0);
        Sighting fromService2 = stServiceLayer.getHeroSightings(hero2, Integer.MAX_VALUE, 0).get(0);
        
        assertEquals(s1, fromService1);
        assertEquals(s2, fromService2);   
    }
}
