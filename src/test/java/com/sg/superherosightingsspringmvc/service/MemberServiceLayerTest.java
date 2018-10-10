/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.service;

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
public class MemberServiceLayerTest {
    private MemberServiceLayer memServiceLayer;
    private LocationServiceLayer locServiceLayer;
    private OrganizationServiceLayer orgServiceLayer;
    private SightingServiceLayer stServiceLayer;
    
    public MemberServiceLayerTest() {
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
        memServiceLayer = ctx.getBean("MemberServiceLayer", MemberServiceLayer.class);
        orgServiceLayer = ctx.getBean("OrganizationServiceLayer", OrganizationServiceLayer.class);
        locServiceLayer = ctx.getBean("LocationServiceLayer", LocationServiceLayer.class);
        stServiceLayer = ctx.getBean("SightingServiceLayer", SightingServiceLayer.class);
        
        List<Member> members = memServiceLayer.getAllMembers(Integer.MAX_VALUE, 0);
        for (Member currentMember: members) {
            memServiceLayer.removeMember(currentMember.getMemberId());
        }
        List<Organization> organizations = orgServiceLayer.getAllOrganizations(Integer.MAX_VALUE, 0);
        for (Organization currentOrg: organizations) {
            orgServiceLayer.removeOrganization(currentOrg.getOrganizationId());
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
     * Test of getMember method, of class MemberServiceLayer.
     */
    @Test
    public void testCreateAndGetMember() throws Exception {
        
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
        
        Member mb = new Member();
        mb.setFirstName("John");
        mb.setLastName("Smith");
        mb.setOrganizationID(org1.getOrganizationId());
        
        //Add and Get Member
        memServiceLayer.createMember(mb);
        Member fromService = memServiceLayer.getMember(mb.getMemberId());
        
        assertEquals(mb, fromService);
    }

    /**
     * Test of createMember method, of class MemberServiceLayer.
     */
    @Test
    public void testCreateMemberInvalidData() throws Exception {
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
        
        Member mb = new Member();
        mb.setFirstName("John");
        mb.setLastName("  ");
        mb.setOrganizationID(org1.getOrganizationId());
        
        //Test validation
        try{
           memServiceLayer.createMember(mb);
           fail("Expected MemberDataValidationException was not thrown");
        }catch(MemberDataValidationException e){
            return;
        }
        
    }

    /**
     * Test of removeMember method, of class MemberServiceLayer.
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
        
        locServiceLayer.createLocation(lo1);
   
        //Organizations
        Organization org1 = new Organization();
        org1.setLocationId(lo1.getLocationId());
        org1.setOrganizationName("Shield Headquarters");
        org1.setOrganizationDescription("The headquarters of the shield organization");
        org1.setTelephoneNumber("555-555-5555");
        
        orgServiceLayer.createOrganization(org1);
        
        Member mb1 = new Member();
        mb1.setFirstName("John");
        mb1.setLastName("Smith");
        mb1.setOrganizationID(org1.getOrganizationId());
        
        
        //Add and Get Member
        memServiceLayer.createMember(mb1);
        
        memServiceLayer.removeMember(mb1.getMemberId());
        assertEquals(0, memServiceLayer.getAllMembers(Integer.MAX_VALUE, 0).size());
        
    }

    /**
     * Test of editMember method, of class MemberServiceLayer.
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
        
        locServiceLayer.createLocation(lo1);
   
        //Organizations
        Organization org1 = new Organization();
        org1.setLocationId(lo1.getLocationId());
        org1.setOrganizationName("Shield Headquarters");
        org1.setOrganizationDescription("The headquarters of the shield organization");
        org1.setTelephoneNumber("555-555-5555");
        
        orgServiceLayer.createOrganization(org1);
        
        Member mb1 = new Member();
        mb1.setFirstName("John");
        mb1.setLastName("Smith");
        mb1.setOrganizationID(org1.getOrganizationId());
        
        Member mb2 = new Member();
        mb2.setFirstName("Jane");
        mb2.setLastName("Smith");
        mb2.setOrganizationID(org1.getOrganizationId());
        
        //Add and Get Member
        memServiceLayer.createMember(mb1);

        
        mb1.setFirstName("Jerry");
        
        Member fromService = memServiceLayer.editMember(mb1);
        
        assertEquals(mb1, fromService);
    }
    
    /**
     * Test of editMember method, of class MemberServiceLayer.
     */
    @Test
    public void testEditMemberMemberInvalidData() throws Exception {
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
        
        locServiceLayer.createLocation(lo1);
   
        //Organizations
        Organization org1 = new Organization();
        org1.setLocationId(lo1.getLocationId());
        org1.setOrganizationName("Shield Headquarters");
        org1.setOrganizationDescription("The headquarters of the shield organization");
        org1.setTelephoneNumber("555-555-5555");
        
        orgServiceLayer.createOrganization(org1);
        
        Member mb1 = new Member();
        mb1.setFirstName("John");
        mb1.setLastName("Smith");
        mb1.setOrganizationID(org1.getOrganizationId());
        
        Member mb2 = new Member();
        mb2.setFirstName("Jane");
        mb2.setLastName("Smith");
        mb2.setOrganizationID(org1.getOrganizationId());
        
        //Add and Get Member
        memServiceLayer.createMember(mb1);

        
        mb1.setFirstName(" ");
        
        try{
            memServiceLayer.editMember(mb1);
            fail("Expected MemberDataValidationException was not thrown");
        }catch(MemberDataValidationException e){
           return;
       }
        
    }
    /**
     * Test of getAllMembers method, of class MemberServiceLayer.
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
        
        locServiceLayer.createLocation(lo1);
   
        //Organizations
        Organization org1 = new Organization();
        org1.setLocationId(lo1.getLocationId());
        org1.setOrganizationName("Shield Headquarters");
        org1.setOrganizationDescription("The headquarters of the shield organization");
        org1.setTelephoneNumber("555-555-5555");
        
        orgServiceLayer.createOrganization(org1);
        
        Member mb1 = new Member();
        mb1.setFirstName("John");
        mb1.setLastName("Smith");
        mb1.setOrganizationID(org1.getOrganizationId());
        
        Member mb2 = new Member();
        mb2.setFirstName("Jane");
        mb2.setLastName("Smith");
        mb2.setOrganizationID(org1.getOrganizationId());
        
        //Add and Get Member
        memServiceLayer.createMember(mb1);
        memServiceLayer.createMember(mb2);
        
        assertEquals(2, memServiceLayer.getAllMembers(Integer.MAX_VALUE, 0).size());
    }
    @Test
    public void testFindAllMembersAtOrganization() throws Exception{
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
        
        locServiceLayer.createLocation(lo1);
   
        //Organizations
        Organization org1 = new Organization();
        org1.setLocationId(lo1.getLocationId());
        org1.setOrganizationName("Shield Headquarters");
        org1.setOrganizationDescription("The headquarters of the shield organization");
        org1.setTelephoneNumber("555-555-5555");
        
        orgServiceLayer.createOrganization(org1);
        
        Member mb1 = new Member();
        mb1.setFirstName("John");
        mb1.setLastName("Smith");
        mb1.setOrganizationID(org1.getOrganizationId());
        
        Member mb2 = new Member();
        mb2.setFirstName("Jane");
        mb2.setLastName("Smith");
        mb2.setOrganizationID(org1.getOrganizationId());
        
        //Add and Get Member
        memServiceLayer.createMember(mb1);
        memServiceLayer.createMember(mb2);
        
        assertEquals(mb1, memServiceLayer.findAllMembersAtOrganization(org1, Integer.MAX_VALUE, 0).get(0));
        assertEquals(mb2, memServiceLayer.findAllMembersAtOrganization(org1, Integer.MAX_VALUE, 0).get(1));
        assertEquals(2, memServiceLayer.findAllMembersAtOrganization(org1, Integer.MAX_VALUE, 0).size());
   
    }
    
}
