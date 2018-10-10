/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.webservice.interfaces;

import com.sg.superherosightingsspringmvc.commandmodel.organization.createorganization.CreateOrganizationCommandModel;
import com.sg.superherosightingsspringmvc.commandmodel.organization.editorganization.EditOrganizationCommandModel;
import com.sg.superherosightingsspringmvc.dao.LocationPersistenceException;
import com.sg.superherosightingsspringmvc.dao.MemberPersistenceException;
import com.sg.superherosightingsspringmvc.dao.OrganizationPersistenceException;
import com.sg.superherosightingsspringmvc.dto.Location;
import com.sg.superherosightingsspringmvc.dto.Member;
import com.sg.superherosightingsspringmvc.dto.Organization;
import com.sg.superherosightingsspringmvc.service.HeroServiceLayer;
import com.sg.superherosightingsspringmvc.service.LocationDataValidationException;
import com.sg.superherosightingsspringmvc.service.LocationServiceLayer;
import com.sg.superherosightingsspringmvc.service.MemberDataValidationException;
import com.sg.superherosightingsspringmvc.service.MemberServiceLayer;
import com.sg.superherosightingsspringmvc.service.OrganizationDataValidationException;
import com.sg.superherosightingsspringmvc.service.OrganizationServiceLayer;
import com.sg.superherosightingsspringmvc.viewmodel.organization.createorganization.CreateLocationViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.organization.createorganization.CreateOrganizationViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.organization.editorganization.EditLocationViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.organization.editorganization.EditOrganizationViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.organization.organizationdetails.OrganizationDetailsViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.organization.organizationhome.OrganizationHomeViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.organization.organizationhome.OrganizationViewModel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Stephon
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test-applicationContext.xml"})
@Rollback
@Transactional
public class OrganizationWebServicesTest {
    
    @Inject
    OrganizationWebServices orgWebServices;
    @Inject
    OrganizationServiceLayer orgServiceLayer;
    @Inject
    LocationServiceLayer locServiceLayer;
    @Inject
    MemberServiceLayer memServiceLayer;
    @Inject
    HeroServiceLayer heServiceLayer;
    /**
     * Test of getOrganizationHomeViewModel method, of class OrganizationWebServices.
     */
    @Test
    public void testGetOrganizationHomeViewModel() throws Exception {
        List<Organization> orgs = createTestOrganizations(15);
        
        OrganizationHomeViewModel organizationHomeViewModel = orgWebServices.getOrganizationHomeViewModel(5, 0, 5);
        
        assert organizationHomeViewModel.getOrgs().size()==5;
        assert organizationHomeViewModel.getPageNumber() == 1;
        
        assert organizationHomeViewModel.getPageNumbers().size() == 5;
        
        assert organizationHomeViewModel.getPageNumbers().get(1) == 2;
        assert organizationHomeViewModel.getPageNumbers().get(4) == 5;
        
        int count = 0;
        for(OrganizationViewModel orgViewModel: organizationHomeViewModel.getOrgs()){
            assert("Shield Headquarters" +count).equals(orgViewModel.getOrganizationName());
            assert("The headquarters of Shield"+count).equals(orgViewModel.getOrganizationDescription());
            assert("555-555-55"+(30-count)).equals(orgViewModel.getTelephoneNumber());
            
            Organization sameOrg = orgs.get(count);
            Location loc = locServiceLayer.getLocation(sameOrg.getLocationId());
  
            assert loc.getLocationId() == orgViewModel.getLocation().getLocationId();
            assert loc.getLocationName().equals(orgViewModel.getLocation().getLocationName());
            assert loc.getLocationDescription().equals(orgViewModel.getLocation().getLocationDescription());
            assert loc.getStreet().equals(orgViewModel.getLocation().getStreet());
            assert loc.getCity().equals(orgViewModel.getLocation().getCity());
            assert loc.getState().equals(orgViewModel.getLocation().getState());
            assert loc.getZipCode().equals(orgViewModel.getLocation().getZipCode());
            assert loc.getLongitude().equals(orgViewModel.getLocation().getLongitude());
            assert loc.getLatitude().equals(orgViewModel.getLocation().getLatitude());
            
            count++;                       
        }
    }

    /**
     * Test of getOrganizationDetailsViewModel method, of class OrganizationWebServices.
     */
    @Test
    public void testGetOrganizationDetailsViewModel() throws Exception {
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
        org1.setOrganizationDescription("The headquarters of the Shield Organization");
        org1.setTelephoneNumber("555-555-5555");
        
        org1 = orgServiceLayer.createOrganization(org1);
        
        Member mb1 = new Member();
        mb1.setFirstName("John");
        mb1.setLastName("Smith");
        mb1.setOrganizationID(org1.getOrganizationId());
        
        memServiceLayer.createMember(mb1);
        
        OrganizationDetailsViewModel organizationDetailsViewModel = orgWebServices.getOrganizationDetailsViewModel(org1.getOrganizationId());
        
        assert organizationDetailsViewModel.getOrganizationId() == org1.getOrganizationId();
        assert organizationDetailsViewModel.getOrganizationName().equals(org1.getOrganizationName());
        assert organizationDetailsViewModel.getOrganizationDescription().equals(org1.getOrganizationDescription());
        assert organizationDetailsViewModel.getTelephoneNumber().equals(org1.getTelephoneNumber());
        assert organizationDetailsViewModel.getLocation().getLocationName().equals(locServiceLayer.getLocation(org1.getLocationId()).getLocationName());
        assert organizationDetailsViewModel.getNumberOfMembers() == memServiceLayer.findAllMembersAtOrganization(org1, Integer.MAX_VALUE, 0).size();
    }

    /**
     * Test of getCreateOrganizationViewModel method, of class OrganizationWebServices.
     */
    @Test
    public void testGetCreateOrganizationViewModel() throws Exception {
        for(Location location: locServiceLayer.getAllLocations(Integer.MAX_VALUE, 0)){
            locServiceLayer.removeLocation(location.getLocationId());
        }
        List<Location> locs = createTestLocations(15);
        
        CreateOrganizationViewModel createOrganizationViewModel = orgWebServices.getCreateOrganizationViewModel();
        
        assert createOrganizationViewModel.getLocs().size()== locs.size();
        
        for(CreateLocationViewModel viewModel: createOrganizationViewModel.getLocs()){
            assert viewModel.getLocationId() !=0;
            assert viewModel.getLocationName() !=null;
            assert viewModel.getLocationDescription() !=null;
            assert viewModel.getStreet() !=null;
            assert viewModel.getCity() != null;
            assert viewModel.getState() != null;
            assert viewModel.getZipCode() != null;
            assert viewModel.getLongitude() != null;
            assert viewModel.getLatitude() != null;
        }
    }

    /**
     * Test of getEditOrganizationViewModel method, of class OrganizationWebServices.
     */
    @Test
    public void testGetEditOrganizationViewModel() throws Exception {
        for(Location location: locServiceLayer.getAllLocations(Integer.MAX_VALUE, 0)){
            locServiceLayer.removeLocation(location.getLocationId());
        }
        List<Location> locs = createTestLocations(15);
        
        Organization existingOrg = createTestOrganization(locs.get(0));
        
        EditOrganizationViewModel editOrganizationViewModel = orgWebServices.getEditOrganizationViewModel(existingOrg.getOrganizationId());
        
        assert editOrganizationViewModel.getLocs().size()== locs.size();
        

        for(EditLocationViewModel viewModel: editOrganizationViewModel.getLocs()){
            assert viewModel.getLocationId() !=0;
            assert viewModel.getLocationName() !=null;
            assert viewModel.getLocationDescription() !=null;
            assert viewModel.getStreet() !=null;
            assert viewModel.getCity() != null;
            assert viewModel.getState() != null;
            assert viewModel.getZipCode() != null;
            assert viewModel.getLongitude() != null;
            assert viewModel.getLatitude() != null;
        }
        EditOrganizationCommandModel commandModel = editOrganizationViewModel.getEditOrganizationCommandModel();
        
        assert commandModel.getOrganizationId() == existingOrg.getOrganizationId();
        assert commandModel.getOrganizationName().equals(existingOrg.getOrganizationName());
        assert commandModel.getOrganizationDescription().equals(existingOrg.getOrganizationDescription());
        assert commandModel.getTelephoneNumber().equals(existingOrg.getTelephoneNumber());
        assert commandModel.getLocationId() == existingOrg.getLocationId();
    }

    /**
     * Test of saveCreateOrganizationCommandModel method, of class OrganizationWebServices.
     */
    @Test
    public void testSaveCreateOrganizationCommandModel() throws Exception {
        List<Location> locs = createTestLocations(2);
        
        List<Integer> locIds = new ArrayList();
        locIds.add(locs.get(0).getLocationId());
        locIds.add(locs.get(1).getLocationId());
        
        CreateOrganizationCommandModel commandModel = new CreateOrganizationCommandModel();
        commandModel.setLocationId(locIds.get(0));
        commandModel.setOrganizationName("Shield Headquarters");
        commandModel.setOrganizationDescription("The headquarters of Shield");
        commandModel.setTelephoneNumber("555-555-5555");
        
        Organization org = orgWebServices.saveCreateOrganizationCommandModel(commandModel);
        
        assert org.getOrganizationId() != 0;
        assert org.getOrganizationName().equals("Shield Headquarters");
        assert org.getOrganizationDescription().equals("The headquarters of Shield");
        assert org.getLocationId() == locs.get(0).getLocationId();
        assert org.getTelephoneNumber().equals("555-555-5555");
    }

    /**
     * Test of saveEditOrganizationCommandModel method, of class OrganizationWebServices.
     */
    @Test
    public void testSaveEditOrganizationCommandModel() throws Exception {
        List<Location> locs = createTestLocations(2);
        
        List<Integer> locIds = new ArrayList();
        locIds.add(locs.get(0).getLocationId());
        locIds.add(locs.get(1).getLocationId());
        
        Organization existingOrg = createTestOrganization(locs.get(0));
        
        EditOrganizationCommandModel commandModel = new EditOrganizationCommandModel();
        commandModel.setOrganizationId(existingOrg.getOrganizationId());
        commandModel.setLocationId(locIds.get(1));
        commandModel.setOrganizationName("Boo Headquarters");
        commandModel.setOrganizationDescription("The headquarters of Boo");
        commandModel.setTelephoneNumber("555-555-44444");
        
        Organization editedOrg= orgWebServices.saveEditOrganizationCommandModel(commandModel);
        
        assert editedOrg.getOrganizationId() != 0;
        assert editedOrg.getOrganizationName().equals("Boo Headquarters");
        assert editedOrg.getOrganizationDescription().equals("The headquarters of Boo");
        assert editedOrg.getTelephoneNumber().equals("555-555-44444");
        assert editedOrg.getLocationId() == locs.get(1).getLocationId();
    }
    
    private Organization createTestOrganization(Location loc) throws OrganizationDataValidationException, OrganizationPersistenceException, LocationPersistenceException, LocationDataValidationException, MemberDataValidationException, MemberPersistenceException{
            
                Organization org1 = new Organization();
                org1.setLocationId(loc.getLocationId());
                org1.setOrganizationName("Shield Headquarters");
                org1.setOrganizationDescription("The headquarters of Shield");
                org1.setTelephoneNumber("555-555-5555");

                org1 = orgServiceLayer.createOrganization(org1);
                
                Member mb1 = new Member();
                mb1.setFirstName("John");
                mb1.setLastName("Smith");
                mb1.setOrganizationID(org1.getOrganizationId());
        
                memServiceLayer.createMember(mb1);
            
            return org1;
     }
    
    private List<Organization> createTestOrganizations(int numberOfOrgs) throws LocationPersistenceException, LocationDataValidationException, OrganizationDataValidationException, OrganizationPersistenceException{
            List<Organization> orgs = new ArrayList();
            
            for(int i=0;i< numberOfOrgs; i ++){
                Location lo1 = new Location();
                lo1.setLocationName("Bronx"+i);
                lo1.setLocationDescription("In front of the Newspaper Building"+i);
                lo1.setStreet("567 Bobo Street"+i);
                lo1.setCity("New York City"+i);
                lo1.setState("New York"+i);
                lo1.setZipCode("95609");
                lo1.setLongitude(new BigDecimal(40.712772).add(BigDecimal.ONE));
                lo1.setLatitude(new BigDecimal(74.006058).negate().add(BigDecimal.ONE));
                locServiceLayer.createLocation(lo1);

                Organization org1 = new Organization();
                org1.setLocationId(lo1.getLocationId());
                org1.setOrganizationName("Shield Headquarters"+i);
                org1.setOrganizationDescription("The headquarters of Shield"+i);
                org1.setTelephoneNumber("555-555-55"+(30-i));

                org1 = orgServiceLayer.createOrganization(org1);
                
                orgs.add(org1);
            }
            
            return orgs;
     }
     
    private List<Location> createTestLocations(int numberOfLocs) throws LocationPersistenceException, LocationDataValidationException, OrganizationDataValidationException, OrganizationPersistenceException{
            List<Location> locs = new ArrayList();
            
            for(int i=0;i< numberOfLocs; i ++){
                Location lo1 = new Location();
                lo1.setLocationName("Bronx"+i);
                lo1.setLocationDescription("In front of the Newspaper Building"+i);
                lo1.setStreet("567 Bobo Street"+i);
                lo1.setCity("New York City"+i);
                lo1.setState("New York"+i);
                lo1.setZipCode("95609");
                lo1.setLongitude(new BigDecimal(40.712772).add(BigDecimal.ONE));
                lo1.setLatitude(new BigDecimal(74.006058).negate().add(BigDecimal.ONE));
                locServiceLayer.createLocation(lo1);
                
                locs.add(lo1);
            }
            
            return locs;
     }
    
}
