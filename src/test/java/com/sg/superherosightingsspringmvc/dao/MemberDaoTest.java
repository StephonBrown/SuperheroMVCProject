/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.dao;

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
public class MemberDaoTest {
    
    MemberDao dao;
    LocationDao locationDao;
    OrganizationDao orgDao;
    
    public MemberDaoTest() {
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
        dao = ctx.getBean("MemberDao", MemberDao.class);
        orgDao = ctx.getBean("OrganizationDao", OrganizationDao.class);
        locationDao = ctx.getBean("LocationDao", LocationDao.class);
        
        List<Member> members = dao.getAllMembers(Integer.MAX_VALUE, 0);
        for(Member currentMember : members) {
            dao.removeMember(currentMember.getMemberId());
        }
        List<Organization> orgs = orgDao.getAllOrganizations(Integer.MAX_VALUE, 0);
        for (Organization currentOrganization: orgs) {
            orgDao.removeOrganization(currentOrganization.getOrganizationId());
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
     * Test of getMember method, of class MemberDao.
     */
    @Test
    public void testAddAndGetMember() throws Exception {
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
        
        orgDao.addOrganization(org1);
        
        Member mb = new Member();
        mb.setFirstName("John");
        mb.setLastName("Smith");
        mb.setOrganizationID(org1.getOrganizationId());
        
        //Add and Get Member
        dao.addMember(mb);
        Member fromDao = dao.getMember(mb.getMemberId());
        
        assertEquals(mb, fromDao);
        
    }

    /**
     * Test of removeMember method, of class MemberDao.
     */
    @Test
    public void testRemoveMember() throws Exception {
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
        
        orgDao.addOrganization(org1);
        
        Member mb1 = new Member();
        mb1.setFirstName("John");
        mb1.setLastName("Smith");
        mb1.setOrganizationID(org1.getOrganizationId());
        
        Member mb2 = new Member();
        mb2.setFirstName("Jane");
        mb2.setLastName("Smith");
        mb2.setOrganizationID(org1.getOrganizationId());
        
        //Add and Get Member
        dao.addMember(mb1);
        dao.addMember(mb2);
        
        dao.removeMember(mb1.getMemberId());
        assertEquals(1, dao.getAllMembers(Integer.MAX_VALUE, 0).size());
        assertNull(dao.getMember(mb1.getMemberId()));
        
        dao.removeMember(mb2.getMemberId());
        assertEquals(0, dao.getAllMembers(Integer.MAX_VALUE, 0).size());
        assertNull(dao.getMember(mb2.getMemberId()));
        
    }

    /**
     * Test of editMember method, of class MemberDao.
     */
    @Test
    public void testEditMember() throws Exception {
        //Location
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
        
        orgDao.addOrganization(org1);
        
        Member mb1 = new Member();
        mb1.setFirstName("John");
        mb1.setLastName("Smith");
        mb1.setOrganizationID(org1.getOrganizationId());
        
        Member mb2 = new Member();
        mb2.setFirstName("Jane");
        mb2.setLastName("Smith");
        mb2.setOrganizationID(org1.getOrganizationId());
        
        //Add and Get Member
        dao.addMember(mb1);
        dao.addMember(mb2);
        
        mb1.setFirstName("Jerry");
        
        dao.editMember(mb1);
        
        Member fromDao = dao.getMember(mb1.getMemberId());
        
        assertEquals(mb1, fromDao);
        
    }

    /**
     * Test of getAllMembers method, of class MemberDao.
     */
    @Test
    public void testGetAllMembers() throws Exception {
        //Location
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
        
        orgDao.addOrganization(org1);
        
        Member mb1 = new Member();
        mb1.setFirstName("John");
        mb1.setLastName("Smith");
        mb1.setOrganizationID(org1.getOrganizationId());
        
        Member mb2 = new Member();
        mb2.setFirstName("Jane");
        mb2.setLastName("Smith");
        mb2.setOrganizationID(org1.getOrganizationId());
        
        //Add and Get Member
        dao.addMember(mb1);
        dao.addMember(mb2);
        
        assertEquals(2, dao.getAllMembers(Integer.MAX_VALUE, 0).size());
    }
    
    @Test
    public void testFindAllMembersAtOrganization()throws Exception{
        //Location
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
        
        orgDao.addOrganization(org1);
        
        Member mb1 = new Member();
        mb1.setFirstName("John");
        mb1.setLastName("Smith");
        mb1.setOrganizationID(org1.getOrganizationId());
        
        Member mb2 = new Member();
        mb2.setFirstName("Jane");
        mb2.setLastName("Smith");
        mb2.setOrganizationID(org1.getOrganizationId());
        
        //Add and Get Member
        dao.addMember(mb1);
        dao.addMember(mb2);
        
        assertEquals(mb1, dao.findAllMembersAtOrganization(org1, Integer.MAX_VALUE, 0).get(0));
        assertEquals(mb2, dao.findAllMembersAtOrganization(org1, Integer.MAX_VALUE, 0).get(1));
        assertEquals(2, dao.findAllMembersAtOrganization(org1, Integer.MAX_VALUE, 0).size());
   
    }
}
