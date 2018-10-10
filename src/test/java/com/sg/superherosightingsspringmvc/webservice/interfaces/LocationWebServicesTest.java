/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.webservice.interfaces;

import com.sg.superherosightingsspringmvc.commandmodel.location.createlocation.CreateLocationCommandModel;
import com.sg.superherosightingsspringmvc.commandmodel.location.editlocation.EditLocationCommandModel;
import com.sg.superherosightingsspringmvc.dao.LocationPersistenceException;
import com.sg.superherosightingsspringmvc.dao.OrganizationPersistenceException;
import com.sg.superherosightingsspringmvc.dto.Location;
import com.sg.superherosightingsspringmvc.service.LocationDataValidationException;
import com.sg.superherosightingsspringmvc.service.LocationServiceLayer;
import com.sg.superherosightingsspringmvc.service.OrganizationDataValidationException;
import com.sg.superherosightingsspringmvc.viewmodel.location.createlocation.CreateLocationViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.location.editlocation.EditLocationViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.location.locationdetails.LocationDetailsViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.location.locationhome.LocationHomeViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.location.locationhome.LocationViewModel;
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
public class LocationWebServicesTest {
    
    @Inject
    LocationWebServices locWebServices;
    @Inject
    LocationServiceLayer locServiceLayer;

    /**
     * Test of getLocationHomeViewModel method, of class LocationWebServices.
     */
    @Test
    public void testGetLocationHomeViewModel() throws Exception {
        for(Location location: locServiceLayer.getAllLocations(Integer.MAX_VALUE, 0)){
            locServiceLayer.removeLocation(location.getLocationId());
        }
        
        List<Location> locs = createTestLocations(15);
        
        LocationHomeViewModel locationHomeViewModel = locWebServices.getLocationHomeViewModel(5, 0, 5);
        
        assert locationHomeViewModel.getLocs().size()==5;
        assert locationHomeViewModel.getPageNumber() == 1;
        
        assert locationHomeViewModel.getPageNumbers().size() == 5;
        
        assert locationHomeViewModel.getPageNumbers().get(1) == 2;
        assert locationHomeViewModel.getPageNumbers().get(4) == 5;
        
        int count = 0;
        for(LocationViewModel locViewModel: locationHomeViewModel.getLocs()){
  
            assert locs.get(count).getLocationId() != 0;
            assert locs.get(count).getLocationName().equals(locViewModel.getLocationName());
            assert locs.get(count).getLocationDescription().equals(locViewModel.getLocationDescription());
            assert locs.get(count).getStreet().equals(locViewModel.getStreet());
            assert locs.get(count).getCity().equals(locViewModel.getCity());
            assert locs.get(count).getState().equals(locViewModel.getState());
            assert locs.get(count).getZipCode().equals(locViewModel.getZipCode());
            assert locs.get(count).getLongitude().equals(locViewModel.getLongitude());
            assert locs.get(count).getLatitude().equals(locViewModel.getLatitude());
            
            count++;                       
        }
    }

    /**
     * Test of getLocationDetailsViewMode method, of class LocationWebServices.
     */
    @Test
    public void testGetLocationDetailsViewMode() throws Exception {
        //Locations
        Location lo1 = new Location();
        lo1.setLocationName("Bronx");
        lo1.setLocationDescription("In front of the Newspaper Building");
        lo1.setStreet("567 Bobo Street");
        lo1.setCity("New York City");
        lo1.setState("New York");
        lo1.setZipCode("95609");
        lo1.setLongitude(new BigDecimal("40.71277200"));
        lo1.setLatitude(new BigDecimal("74.00605800").negate());
        
        locServiceLayer.createLocation(lo1);
        
        
        LocationDetailsViewModel locationDetailsViewModel = locWebServices.getLocationDetailsViewMode(lo1.getLocationId());
        
        assert lo1.getLocationId() == locationDetailsViewModel.getLocationId();
        assert lo1.getLocationName().equals(locationDetailsViewModel.getLocationName());
        assert lo1.getLocationDescription().equals(locationDetailsViewModel.getLocationDescription());
        assert lo1.getStreet().equals(locationDetailsViewModel.getStreet());
        assert lo1.getCity().equals(locationDetailsViewModel.getCity());
        assert lo1.getState().equals(locationDetailsViewModel.getState());
        assert lo1.getZipCode().equals(locationDetailsViewModel.getZipCode());
        assert lo1.getLongitude().equals(locationDetailsViewModel.getLongitude()) ;
        assert lo1.getLatitude().equals(locationDetailsViewModel.getLatitude());
        
    }


    /**
     * Test of getEditLocationViewModel method, of class LocationWebServices.
     */
    @Test
    public void testGetEditLocationViewModel() throws Exception {
        //Locations
        Location lo1 = new Location();
        lo1.setLocationName("Bronx");
        lo1.setLocationDescription("In front of the Newspaper Building");
        lo1.setStreet("567 Bobo Street");
        lo1.setCity("New York City");
        lo1.setState("New York");
        lo1.setZipCode("95609");
        lo1.setLongitude(new BigDecimal("40.71277200"));
        lo1.setLatitude(new BigDecimal("74.00605800").negate());
        
        Location existingLocation = locServiceLayer.createLocation(lo1);
        
        EditLocationViewModel editLocationViewModel = locWebServices.getEditLocationViewModel(existingLocation.getLocationId()); 
        
        EditLocationCommandModel commandModel = editLocationViewModel.getEditLocationCommandModel();
        
        assert existingLocation.getLocationId() == commandModel.getLocationId();
        assert existingLocation.getLocationName().equals(commandModel.getLocationName());
        assert existingLocation.getLocationDescription().equals(commandModel.getLocationDescription());
        assert existingLocation.getStreet().equals(commandModel.getStreet());
        assert existingLocation.getCity().equals(commandModel.getCity());
        assert existingLocation.getState().equals(commandModel.getState());
        assert existingLocation.getZipCode().equals(commandModel.getZipCode());
        assert (existingLocation.getLongitude().equals(commandModel.getLongitude())) == true;
        assert (existingLocation.getLatitude().equals(commandModel.getLatitude())) == true;
    }

    /**
     * Test of saveCreateLocationCommandModel method, of class LocationWebServices.
     */
    @Test
    public void testSaveCreateLocationCommandModel() throws Exception {
        
        CreateLocationCommandModel commandModel = new CreateLocationCommandModel();
        commandModel.setLocationName("Bronx");
        commandModel.setLocationDescription("In front of the Newspaper Building");
        commandModel.setStreet("567 Bobo Street");
        commandModel.setCity("New York City");
        commandModel.setState("New York");
        commandModel.setZipCode("95609");
        commandModel.setLongitude(new BigDecimal("40.71277200"));
        commandModel.setLatitude(new BigDecimal("74.00605800").negate());
        
        Location loc = locWebServices.saveCreateLocationCommandModel(commandModel);
        
        assert loc.getLocationId() != 0;
        assert loc.getLocationName().equals("Bronx");
        assert loc.getLocationDescription().equals("In front of the Newspaper Building");
        assert loc.getStreet().equals("567 Bobo Street");
        assert loc.getCity().equals("New York City");
        assert loc.getState().equals("New York");
        assert loc.getZipCode().equals("95609");
        assert loc.getLongitude().equals(new BigDecimal("40.71277200"));
        assert loc.getLatitude().equals(new BigDecimal("74.00605800").negate());
    }

    /**
     * Test of SaveEditLocationCommandModel method, of class LocationWebServices.
     */
    @Test
    public void testSaveEditLocationCommandModel() throws Exception {
        Location lo1 = new Location();
        lo1.setLocationName("Bronx");
        lo1.setLocationDescription("In front of the Newspaper Building");
        lo1.setStreet("567 Bobo Street");
        lo1.setCity("New York City");
        lo1.setState("New York");
        lo1.setZipCode("95609");
        lo1.setLongitude(new BigDecimal("40.71277200"));
        lo1.setLatitude(new BigDecimal("74.00605800").negate());
        
        Location existingLocation = locServiceLayer.createLocation(lo1);
        
        EditLocationCommandModel commandModel = new EditLocationCommandModel();
        commandModel.setLocationId(existingLocation.getLocationId());
        commandModel.setLocationName("Brooklyn");
        commandModel.setLocationDescription("In front of the Ham Building");
        commandModel.setStreet("5511 Booo Street");
        commandModel.setCity("New York Shifty");
        commandModel.setState("New Bork");
        commandModel.setZipCode("85699");
        commandModel.setLongitude(new BigDecimal("30.71277200"));
        commandModel.setLatitude(new BigDecimal("34.00605800").negate());
        
        Location loc = locWebServices.SaveEditLocationCommandModel(commandModel);
        
        assert loc.getLocationId() != 0;
        assert loc.getLocationName().equals("Brooklyn");
        assert loc.getLocationDescription().equals("In front of the Ham Building");
        assert loc.getStreet().equals("5511 Booo Street");
        assert loc.getCity().equals("New York Shifty");
        assert loc.getState().equals("New Bork");
        assert loc.getZipCode().equals("85699");
        assert loc.getLongitude().equals(new BigDecimal("30.71277200"));
        assert loc.getLatitude().equals(new BigDecimal("34.00605800").negate());
    }

    private List<Location> createTestLocations(int numberOfLocs) throws LocationPersistenceException, LocationDataValidationException{
            List<Location> locs = new ArrayList();
            
            for(int i=0;i< numberOfLocs; i ++){
                Location lo1 = new Location();
                lo1.setLocationName("Bronx"+i);
                lo1.setLocationDescription("In front of the Newspaper Building"+i);
                lo1.setStreet("567 Bobo Street"+i);
                lo1.setCity("New York City"+i);
                lo1.setState("New York"+i);
                lo1.setZipCode("95609");
                lo1.setLongitude(new BigDecimal("40.71277200"));
                lo1.setLatitude(new BigDecimal("74.00605800").negate());
                lo1 = locServiceLayer.createLocation(lo1);
                
                locs.add(lo1);
            }
            
            return locs;
     }
    
}
