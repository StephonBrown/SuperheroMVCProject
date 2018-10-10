/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.dao;


import com.sg.superherosightingsspringmvc.dto.Hero;
import com.sg.superherosightingsspringmvc.dto.Location;
import com.sg.superherosightingsspringmvc.dto.Member;
import com.sg.superherosightingsspringmvc.dto.Organization;
import java.math.BigDecimal;
import java.sql.SQLException;
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
public class OrganizationDaoTest {
    OrganizationDao dao;
    LocationDao locationDao;
    MemberDao memberDao;
    HeroDao heroDao;
    
    public OrganizationDaoTest() {
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
        dao = ctx.getBean("OrganizationDao", OrganizationDao.class);
        locationDao = ctx.getBean("LocationDao", LocationDao.class);
        memberDao = ctx.getBean("MemberDao", MemberDao.class);
        heroDao = ctx.getBean("HeroDao", HeroDao.class);
        

        List<Organization> orgs = dao.getAllOrganizations(Integer.MAX_VALUE, 0);
        for (Organization currentOrganization: orgs) {
            dao.removeOrganization(currentOrganization.getOrganizationId());
        }
        List<Member> members = memberDao.getAllMembers(Integer.MAX_VALUE, 0);
        for(Member currentMember : members) {
            memberDao.removeMember(currentMember.getMemberId());
        }
        List<Hero> heroes = heroDao.getAllHeroes(Integer.MAX_VALUE, 0);
        for (Hero currentHero: heroes) {
            heroDao.removeHero(currentHero.getHeroId());
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
     * Test of getOrganization method, of class OrganizationDao.
     */
    @Test
    public void testAddAndGetOrganization() throws Exception {
        
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
   
        //Organizations
        Organization org1 = new Organization();
        org1.setLocationId(lo1.getLocationId());
        org1.setOrganizationName("Shield Headquarters");
        org1.setOrganizationDescription("The headquarters of the shield organization");
        org1.setTelephoneNumber("555-555-5555");

        
        dao.addOrganization(org1);
        
        Organization fromDao = dao.getOrganization(org1.getOrganizationId());
        
        assertEquals(org1, fromDao);
    }

    /**
     * Test of removeOrganization method, of class OrganizationDao.
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
        
        lo1=locationDao.addLocation(lo1);
        lo2=locationDao.addLocation(lo2);
        
        //Organizations
        Organization org1 = new Organization();
        org1.setLocationId(lo1.getLocationId());
        org1.setOrganizationName("Shield Headquarters");
        org1.setOrganizationDescription("The headquarters of the shield organization");
        org1.setTelephoneNumber("555-555-5555");
       
        Organization org2 = new Organization();
        org2.setLocationId(lo2.getLocationId());
        org2.setOrganizationName("Avengers Headquarters");
        org2.setOrganizationDescription("The headquarters of the Avengers");
        org2.setTelephoneNumber("444-444-4444");
        
        dao.addOrganization(org1);
        dao.addOrganization(org2);
        
        dao.removeOrganization(org1.getOrganizationId());
        assertEquals(1, dao.getAllOrganizations(Integer.MAX_VALUE, 0).size());
        assertNull(dao.getOrganization(org1.getOrganizationId()));
        
        dao.removeOrganization(org2.getOrganizationId());
        assertEquals(0, dao.getAllOrganizations(Integer.MAX_VALUE, 0).size());
        assertNull(dao.getOrganization(org2.getOrganizationId()));
    }

    /**
     * Test of editOrganization method, of class OrganizationDao.
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
        
        locationDao.addLocation(lo1);
        locationDao.addLocation(lo2);
        
        //Organizations
        Organization org1 = new Organization();
        org1.setLocationId(lo1.getLocationId());
        org1.setOrganizationName("Shield Headquarters");
        org1.setOrganizationDescription("The headquarters of the shield organization");
        org1.setTelephoneNumber("555-555-5555");
        
        dao.addOrganization(org1);
        
        //Update Organization
        org1.setLocationId(lo2.getLocationId());
        dao.editOrganization(org1);
        
        Organization fromDao = dao.getOrganization(org1.getOrganizationId());
        
        assertEquals(org1, fromDao);
        
    }

    /**
     * Test of getAllOrganizations method, of class OrganizationDao.
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
        
        locationDao.addLocation(lo1);
        locationDao.addLocation(lo2);
        
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
        
        dao.addOrganization(org1);
        dao.addOrganization(org2);
        
        assertEquals(2, dao.getAllOrganizations(Integer.MAX_VALUE, 0).size());
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
        
        locationDao.addLocation(lo1);
        
        //Organizations
        Organization org1 = new Organization();
        org1.setLocationId(lo1.getLocationId());
        org1.setOrganizationName("Shield Headquarters");
        org1.setOrganizationDescription("The headquarters of Shield");
        org1.setTelephoneNumber("555-555-5555");
        
        dao.addOrganization(org1);
        
        Hero hero1 = new Hero();
        hero1.setHeroName("Ice Slider");
        hero1.setHeroDescription("A man that hails from the glacial islands");

        Hero hero2 = new Hero();
        hero2.setHeroName("BucketHead");
        hero2.setHeroDescription("A man with a buckethead");
        
        heroDao.addHero(hero1);
        heroDao.addHero(hero2);
        
        dao.insertOrganizationHero(org1, hero1);
        dao.insertOrganizationHero(org1, hero2);
        
        Organization fromDao1 = dao.getHeroOrganizations(hero1, Integer.MAX_VALUE, 0).get(0);
        Organization fromDao2 = dao.getHeroOrganizations(hero2, Integer.MAX_VALUE, 0).get(0);
        
        
        assertEquals(org1, fromDao1);
        assertEquals(org1, fromDao2);
            
    }
    
}
