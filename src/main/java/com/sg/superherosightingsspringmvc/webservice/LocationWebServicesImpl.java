/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.webservice;

import com.sg.superherosightingsspringmvc.commandmodel.location.createlocation.CreateLocationCommandModel;
import com.sg.superherosightingsspringmvc.commandmodel.location.editlocation.EditLocationCommandModel;
import com.sg.superherosightingsspringmvc.dao.LocationPersistenceException;
import com.sg.superherosightingsspringmvc.dto.Location;
import com.sg.superherosightingsspringmvc.service.LocationDataValidationException;
import com.sg.superherosightingsspringmvc.service.LocationServiceLayer;
import com.sg.superherosightingsspringmvc.viewmodel.location.locationdetails.LocationDetailsViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.location.locationhome.LocationHomeViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.location.createlocation.CreateLocationViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.location.editlocation.EditLocationViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.location.locationhome.LocationViewModel;
import com.sg.superherosightingsspringmvc.webservice.interfaces.LocationWebServices;
import com.sg.superherosightingsspringmvc.webservice.utilities.PageUtilities;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Stephon
 */
public class LocationWebServicesImpl implements LocationWebServices{
    @Inject
    private LocationServiceLayer locServiceLayer;
    
    @Override
    public LocationHomeViewModel getLocationHomeViewModel(Integer limit, Integer offset, Integer pageNumbers) throws LocationPersistenceException{
        
        if (limit == null) limit = 5;
        if (offset == null) offset = 0;
        if (pageNumbers == null) pageNumbers = 5;
        
        LocationHomeViewModel locationHomeViewModel = new LocationHomeViewModel();
        
        List<Location> locs = locServiceLayer.getAllLocations(limit,offset);
   
       
        Integer currentPage = PageUtilities.calculatePageNumber(limit,offset);
        List<Integer> pages = PageUtilities.getPageNumbers(currentPage, pageNumbers);
        
        locationHomeViewModel.setLocs(translateLocation(locs));
        locationHomeViewModel.setPageNumber(currentPage);
        locationHomeViewModel.setPageNumbers(pages);
        
        return locationHomeViewModel;
    }

    @Override
    public LocationDetailsViewModel getLocationDetailsViewMode(int locationId) throws LocationPersistenceException,LocationDataValidationException {
        LocationDetailsViewModel locationDetailsViewModel = new LocationDetailsViewModel();
        Location loc = locServiceLayer.getLocation(locationId);
        
        locationDetailsViewModel = translateDetailsLocation(loc);
        
        return locationDetailsViewModel;
    }

    @Override
    public CreateLocationViewModel getCreateLocationViewModel() {
        CreateLocationViewModel createLocationViewModel = new CreateLocationViewModel();
        CreateLocationCommandModel createLocationCommandModel = new CreateLocationCommandModel();
        
        createLocationViewModel.setCreateLocationCommandModel(createLocationCommandModel);
        
        return createLocationViewModel;   
    }

    @Override
    public EditLocationViewModel getEditLocationViewModel(int locationId) throws LocationPersistenceException, LocationDataValidationException {
        EditLocationViewModel editLocationViewModel = new EditLocationViewModel();
        Location location = locServiceLayer.getLocation(locationId);
        
         EditLocationCommandModel editLocationCommandModel = new EditLocationCommandModel();
         editLocationCommandModel.setLocationId(location.getLocationId());
         editLocationCommandModel.setLocationName(location.getLocationName());
         editLocationCommandModel.setLocationDescription(location.getLocationDescription());
         editLocationCommandModel.setStreet(location.getStreet());
         editLocationCommandModel.setCity(location.getCity());
         editLocationCommandModel.setState(location.getState());
         editLocationCommandModel.setZipCode(location.getZipCode());
         editLocationCommandModel.setLongitude(location.getLongitude());
         editLocationCommandModel.setLatitude(location.getLatitude());
         
         editLocationViewModel.setEditLocationCommandModel(editLocationCommandModel);
         
         return editLocationViewModel;
         
    }

    @Override
    public Location saveCreateLocationCommandModel(CreateLocationCommandModel createLocationCommandModel) throws LocationPersistenceException, LocationDataValidationException {
       Location location = new Location();
       location.setLocationName(createLocationCommandModel.getLocationName());
       location.setLocationDescription(createLocationCommandModel.getLocationDescription());
       location.setStreet(createLocationCommandModel.getStreet());
       location.setCity(createLocationCommandModel.getCity());
       location.setState(createLocationCommandModel.getState());
       location.setZipCode(createLocationCommandModel.getZipCode());
       location.setLongitude(createLocationCommandModel.getLongitude());
       location.setLatitude(createLocationCommandModel.getLatitude());
       
       locServiceLayer.createLocation(location);
     
       
       return location;
               
    }

    @Override
    public Location SaveEditLocationCommandModel(EditLocationCommandModel editLocationCommandModel)  throws LocationPersistenceException, LocationDataValidationException{
        Location loc = locServiceLayer.getLocation(editLocationCommandModel.getLocationId());
        
       loc.setLocationName(editLocationCommandModel.getLocationName());
       loc.setLocationDescription(editLocationCommandModel.getLocationDescription());
       loc.setStreet(editLocationCommandModel.getStreet());
       loc.setCity(editLocationCommandModel.getCity());
       loc.setState(editLocationCommandModel.getState());
       loc.setZipCode(editLocationCommandModel.getZipCode());
       loc.setLongitude(editLocationCommandModel.getLongitude());
       loc.setLatitude(editLocationCommandModel.getLatitude());
       
       locServiceLayer.editLocation(loc);
       
       return loc;
    }
    
    private LocationViewModel translateLocation(Location location){
        LocationViewModel locationViewModel = new LocationViewModel();
        locationViewModel.setLocationId(location.getLocationId());
        locationViewModel.setLocationName(location.getLocationName());
        locationViewModel.setLocationDescription(location.getLocationDescription());
        locationViewModel.setStreet(location.getStreet());
        locationViewModel.setCity(location.getCity());
        locationViewModel.setState(location.getState());
        locationViewModel.setZipCode(location.getZipCode());
        locationViewModel.setLongitude(location.getLongitude());
        locationViewModel.setLatitude(location.getLatitude());
        
        return locationViewModel;  
    }
    
    private List<LocationViewModel> translateLocation(List<Location> locations){
        List<LocationViewModel> locModels = new ArrayList();
        for(Location currentLoc: locations){
            locModels.add(translateLocation(currentLoc)); 
        }
        return locModels; 
    }
    
    private LocationDetailsViewModel translateDetailsLocation(Location location){
        LocationDetailsViewModel locationViewModel = new LocationDetailsViewModel();
        locationViewModel.setLocationId(location.getLocationId());
        locationViewModel.setLocationName(location.getLocationName());
        locationViewModel.setLocationDescription(location.getLocationDescription());
        locationViewModel.setStreet(location.getStreet());
        locationViewModel.setCity(location.getCity());
        locationViewModel.setState(location.getState());
        locationViewModel.setZipCode(location.getZipCode());
        locationViewModel.setLongitude(location.getLongitude());
        locationViewModel.setLatitude(location.getLatitude());
        
        return locationViewModel;  
    }
    
}
