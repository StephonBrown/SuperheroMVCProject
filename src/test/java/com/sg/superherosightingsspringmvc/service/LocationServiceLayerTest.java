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
public class LocationServiceLayerTest {
    private LocationServiceLayer locServiceLayer;
    private SightingServiceLayer stServiceLayer;
    private SuperpowerServiceLayer spServiceLayer;
    private HeroServiceLayer heServiceLayer;
    private OrganizationServiceLayer orgServiceLayer;
    
    public LocationServiceLayerTest() {
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
        orgServiceLayer = ctx.getBean("OrganizationServiceLayer", OrganizationServiceLayer.class);
        locServiceLayer = ctx.getBean("LocationServiceLayer", LocationServiceLayer.class);
        spServiceLayer = ctx.getBean("SuperpowerServiceLayer", SuperpowerServiceLayer.class);
        heServiceLayer = ctx.getBean("HeroServiceLayer", HeroServiceLayer.class);
        stServiceLayer = ctx.getBean("SightingServiceLayer", SightingServiceLayer.class);
        
        List<Sighting> sightings = stServiceLayer.getAllSightings(Integer.MAX_VALUE, 0);
        for(Sighting currentSighting : sightings) {
            stServiceLayer.removeSighting(currentSighting.getSightingId());
        }
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
        List<Location> locations = locServiceLayer.getAllLocations(Integer.MAX_VALUE, 0);
        for (Location currentLocation: locations) {
            locServiceLayer.removeLocation(currentLocation.getLocationId());
        }
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getLocation method, of class LocationServiceLayer.
     */
    @Test
    public void testCreateAndGetLocation() throws Exception {
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
        Location fromService = locServiceLayer.getLocation(lo.getLocationId());
        assertEquals(lo.getLocationId(), fromService.getLocationId());
    }

    /**
     * Test of createLocation method, of class LocationServiceLayer.
     */
    @Test
    public void testCreateLocationInvalidDataCoordinate() throws Exception {
        Location lo = new Location();
        lo.setLocationName("Bronx");
        lo.setLocationDescription("In front of the Newspaper Building");
        lo.setStreet("567 Bobo Street");
        lo.setCity("New York City");
        lo.setState("New York");
        lo.setZipCode("95609");
        lo.setLongitude(new BigDecimal(200.000000));
        lo.setLatitude(new BigDecimal(74.006058).negate());
        
        try{
          locServiceLayer.createLocation(lo);
          fail("Expected LocationDataValidationException was not thrown");
        }catch(LocationDataValidationException e){
            return;
        }
        

    }

    /**
     * Test of removeLocation method, of class LocationServiceLayer.
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
        
        locServiceLayer.createLocation(lo1);
        
        locServiceLayer.removeLocation(lo1.getLocationId());
        assertEquals(0, locServiceLayer.getAllLocations(Integer.MAX_VALUE, 0).size());
        
    }

    /**
     * Test of editLocation method, of class LocationServiceLayer.
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
        
        locServiceLayer.createLocation(lo1);
        
        lo1.setStreet("863 Willy Drive");
        
        Location updatedLocation = locServiceLayer.editLocation(lo1);
        
        assertEquals(lo1,updatedLocation);
    }
    
    /**
     * Test of editLocation method, of class LocationServiceLayer.
     */
    @Test
    public void testEditLocationInvalidData() throws Exception {
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
        
        lo1.setLatitude(new BigDecimal(800.000000).negate());
        try{
            Location updatedLocation = locServiceLayer.editLocation(lo1);
            fail("Expected LocationDataValidationException was not thrown");
        }catch(LocationDataValidationException e){
            return;
        }  
    }

    /**
     * Test of getAllLocations method, of class LocationServiceLayer.
     * @throws java.lang.Exception
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
        
        locServiceLayer.createLocation(lo1);
        locServiceLayer.createLocation(lo2);

        assertEquals(2, locServiceLayer.getAllLocations(Integer.MAX_VALUE, 0).size());
    }
    
    @Test
    public void testGetHeroLocations() throws Exception {
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

        hero1=heServiceLayer.createHero(hero1);
        
        stServiceLayer.insertHeroSightings(hero1, s1);
        stServiceLayer.insertHeroSightings(hero1, s2);
        
        Location fromDao1 = locServiceLayer.getHeroLocations(hero1, Integer.MAX_VALUE, 0).get(0);
        Location fromDao2 = locServiceLayer.getHeroLocations(hero1, Integer.MAX_VALUE, 0).get(1);
        
        assertEquals(2,locServiceLayer.getHeroLocations(hero1, Integer.MAX_VALUE, 0).size());
    }
}
