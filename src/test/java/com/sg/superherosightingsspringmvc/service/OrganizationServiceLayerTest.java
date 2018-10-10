/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.service;


import com.sg.superherosightingsspringmvc.dto.Hero;
import com.sg.superherosightingsspringmvc.dto.Location;
import com.sg.superherosightingsspringmvc.dto.Member;
import com.sg.superherosightingsspringmvc.dto.Organization;
import com.sg.superherosightingsspringmvc.dto.Sighting;
import java.math.BigDecimal;
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
public class OrganizationServiceLayerTest {
    
    private OrganizationServiceLayer orgServiceLayer;
    private LocationServiceLayer locServiceLayer;
    private MemberServiceLayer memServiceLayer;
    private SightingServiceLayer stServiceLayer;
    private HeroServiceLayer heServiceLayer;
    
    public OrganizationServiceLayerTest() {
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
        orgServiceLayer = ctx.getBean("OrganizationServiceLayer", OrganizationServiceLayer.class);
        locServiceLayer = ctx.getBean("LocationServiceLayer", LocationServiceLayer.class);
        memServiceLayer = ctx.getBean("MemberServiceLayer", MemberServiceLayer.class);
        stServiceLayer = ctx.getBean("SightingServiceLayer", SightingServiceLayer.class);
        heServiceLayer = ctx.getBean("HeroServiceLayer", HeroServiceLayer.class);
        
        List<Hero> heroes = heServiceLayer.getAllHeroes(Integer.MAX_VALUE, 0);
        for (Hero currentHero: heroes) {
            heServiceLayer.removeHero(currentHero.getHeroId());
        }
        List<Organization> orgs = orgServiceLayer.getAllOrganizations(Integer.MAX_VALUE, 0);
        for (Organization currentOrganization: orgs) {
            orgServiceLayer.removeOrganization(currentOrganization.getOrganizationId());
        }
        List<Member> members = memServiceLayer.getAllMembers(Integer.MAX_VALUE, 0);
        for(Member currentMember : members) {
            memServiceLayer.removeMember(currentMember.getMemberId());
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
     * Test of getOrganization method, of class OrganizationServiceLayer.
     */
    @Test
    public void testCreateAndGetOrganization() throws Exception {
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
   
        //Organizations
        Organization org1 = new Organization();
        org1.setLocationId(lo1.getLocationId());
        org1.setOrganizationName("Shield Headquarters");
        org1.setOrganizationDescription("The headquarters of the shield organization");
        org1.setTelephoneNumber("555-555-5555");

        
        orgServiceLayer.createOrganization(org1);
        
        Organization fromDao = orgServiceLayer.getOrganization(org1.getOrganizationId());
        
        assertEquals(org1, fromDao);
    }

    /**
     * Test of createOrganization method, of class OrganizationServiceLayer.
     */
    @Test
    public void testCreateOrganizationInvalidData() throws Exception {
        
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
   
        //Organizations
        Organization org1 = new Organization();
        org1.setLocationId(lo1.getLocationId());
        org1.setOrganizationName("Shield Headquarters");
        org1.setOrganizationDescription("The headquarters of the shield organization");
        org1.setTelephoneNumber(" ");
        
        
        try{
           orgServiceLayer.createOrganization(org1);
           fail("Expected OrganizationDataValidationException was not thrown");
        }catch(OrganizationDataValidationException e){
           return;
        }
  
    }

    /**
     * Test of removeOrganization method, of class OrganizationServiceLayer.
     */
    @Test
    public void testRemoveOrganization() throws Exception {
                
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
        
        //Organizations
        Organization org1 = new Organization();
        org1.setLocationId(lo1.getLocationId());
        org1.setOrganizationName("Shield Headquarters");
        org1.setOrganizationDescription("The headquarters of the Shield Organization");
        org1.setTelephoneNumber("555-555-5555");
       
        Organization org2 = new Organization();
        org2.setLocationId(lo2.getLocationId());
        org2.setOrganizationName("Avengers Headquarters");
        org2.setOrganizationDescription("The headquarters of The Avengers");
        org2.setTelephoneNumber("444-444-4444");
        
        orgServiceLayer.createOrganization(org1);
        orgServiceLayer.createOrganization(org2);
        
        orgServiceLayer.removeOrganization(org1.getOrganizationId());
        assertEquals(1, orgServiceLayer.getAllOrganizations(Integer.MAX_VALUE, 0).size());

    }

    /**
     * Test of editOrganization method, of class OrganizationServiceLayer.
     */
    @Test
    public void testEditOrganization() throws Exception {
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
        
        //Organizations
        Organization org1 = new Organization();
        org1.setLocationId(lo1.getLocationId());
        org1.setOrganizationName("Shield Headquarters");
        org1.setOrganizationDescription("The headquarters of the shield organization");
        org1.setTelephoneNumber("555-555-5555");
        
        orgServiceLayer.createOrganization(org1);
        
        //Update Organization
        org1.setLocationId(lo2.getLocationId());
        
        
        try{
            orgServiceLayer.editOrganization(org1);
        }catch(OrganizationDataValidationException e){
            return;
        }
       
    }
    
    /**
     * Test of editOrganization method, of class OrganizationServiceLayer.
     */
    @Test
    public void testEditOrganizationInvalidData() throws Exception {
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
        
        //Organizations
        Organization org1 = new Organization();
        org1.setLocationId(lo1.getLocationId());
        org1.setOrganizationName("Shield Headquarters");
        org1.setOrganizationDescription("The headquarters of the shield organization");
        org1.setTelephoneNumber("555-555-5555");
        
        orgServiceLayer.createOrganization(org1);
        
        //Update Organization
        org1.setOrganizationDescription(" ");
        
        
        try{
            orgServiceLayer.editOrganization(org1);
        }catch(OrganizationDataValidationException e){
            return;
        }
    }
    

    /**
     * Test of getAllOrganizations method, of class OrganizationServiceLayer.
     */
    @Test
    public void testGetAllOrganizations() throws Exception { 
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
        
        //Organizations
        Organization org1 = new Organization();
        org1.setLocationId(lo1.getLocationId());
        org1.setOrganizationName("Shield Headquarters");
        org1.setOrganizationDescription("The headquarters of Shield");
        org1.setTelephoneNumber("555-555-5555");
       
        Organization org2 = new Organization();
        org2.setLocationId(lo2.getLocationId());
        org2.setOrganizationName("Avengers Headquarters");
        org2.setOrganizationDescription("The headquarters of The Avengers");
        org2.setTelephoneNumber("444-444-4444");
        
        orgServiceLayer.createOrganization(org1);
        orgServiceLayer.createOrganization(org2);
        
        assertEquals(2, orgServiceLayer.getAllOrganizations(Integer.MAX_VALUE, 0).size());
    }

    /**
     * Test of findAllMembersAtOrganization method, of class OrganizationDao.
     */
    @Test
    public void testGetHeroOrganizations() throws Exception{
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
        
        //Organizations
        Organization org1 = new Organization();
        org1.setLocationId(lo1.getLocationId());
        org1.setOrganizationName("Shield Headquarters");
        org1.setOrganizationDescription("The headquarters of Shield");
        org1.setTelephoneNumber("555-555-5555");
        
        orgServiceLayer.createOrganization(org1);
        
        Hero hero1 = new Hero();
        hero1.setHeroName("Ice Slider");
        hero1.setHeroDescription("A man that hails from the glacial islands");

        Hero hero2 = new Hero();
        hero2.setHeroName("BucketHead");
        hero2.setHeroDescription("A man with a buckethead");
        
        heServiceLayer.createHero(hero1);
        heServiceLayer.createHero(hero2);
        
        orgServiceLayer.insertOrganizationHero(org1, hero1);
        orgServiceLayer.insertOrganizationHero(org1, hero2);
        
        Organization fromService1 = orgServiceLayer.getHeroOrganizations(hero1, Integer.MAX_VALUE, 0).get(0);
        Organization fromService2 = orgServiceLayer.getHeroOrganizations(hero2, Integer.MAX_VALUE, 0).get(0);
        
        assertEquals(org1, fromService1);
        assertEquals(org1, fromService2);
            
    }
}
