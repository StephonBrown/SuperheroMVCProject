/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.webservice;

import com.sg.superherosightingsspringmvc.commandmodel.organization.createorganization.CreateOrganizationCommandModel;
import com.sg.superherosightingsspringmvc.commandmodel.organization.editorganization.EditOrganizationCommandModel;
import com.sg.superherosightingsspringmvc.dao.LocationPersistenceException;
import com.sg.superherosightingsspringmvc.dao.MemberPersistenceException;
import com.sg.superherosightingsspringmvc.dao.OrganizationPersistenceException;
import com.sg.superherosightingsspringmvc.dto.Location;
import com.sg.superherosightingsspringmvc.dto.Organization;
import com.sg.superherosightingsspringmvc.service.HeroServiceLayer;
import com.sg.superherosightingsspringmvc.service.LocationDataValidationException;
import com.sg.superherosightingsspringmvc.service.LocationServiceLayer;
import com.sg.superherosightingsspringmvc.service.MemberServiceLayer;
import com.sg.superherosightingsspringmvc.service.OrganizationDataValidationException;
import com.sg.superherosightingsspringmvc.service.OrganizationServiceLayer;
import com.sg.superherosightingsspringmvc.viewmodel.organization.createorganization.CreateLocationViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.organization.createorganization.CreateOrganizationViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.organization.editorganization.EditLocationViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.organization.editorganization.EditOrganizationViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.organization.organizationdetails.LocationDetailsViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.organization.organizationdetails.OrganizationDetailsViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.organization.organizationhome.LocationViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.organization.organizationhome.OrganizationHomeViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.organization.organizationhome.OrganizationViewModel;
import com.sg.superherosightingsspringmvc.webservice.interfaces.OrganizationWebServices;
import com.sg.superherosightingsspringmvc.webservice.utilities.PageUtilities;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author Stephon
 */
public class OrganizationWebServicesImpl implements OrganizationWebServices{
    
    @Inject
    private  OrganizationServiceLayer orgServiceLayer;
    @Inject
    private  LocationServiceLayer locServiceLayer;
    @Inject
    private  MemberServiceLayer memServiceLayer;
    @Inject
    private  HeroServiceLayer heServiceLayer;

     
    
    @Override
    public OrganizationHomeViewModel getOrganizationHomeViewModel(Integer limit, Integer offset, Integer pageNumbers) throws OrganizationPersistenceException, LocationPersistenceException, LocationDataValidationException{
        if (limit == null) limit = 5;
        if (offset == null) offset = 0;
        if (pageNumbers == null) pageNumbers = 5;
        
        OrganizationHomeViewModel organizationHomeViewModel = new OrganizationHomeViewModel();
       
       List<Organization> orgs = orgServiceLayer.getAllOrganizations(limit, offset);
       
       organizationHomeViewModel.setOrgs(translateOrganization(orgs));
 
       
       Integer currentPage = PageUtilities.calculatePageNumber(limit,offset);
       List<Integer> pages = PageUtilities.getPageNumbers(currentPage, pageNumbers);
       
       organizationHomeViewModel.setPageNumber(currentPage);
       organizationHomeViewModel.setPageNumbers(pages);
        
       return  organizationHomeViewModel;
    }

    @Override
    public OrganizationDetailsViewModel getOrganizationDetailsViewModel(int organizationId) throws MemberPersistenceException, OrganizationPersistenceException, OrganizationDataValidationException, LocationPersistenceException, LocationDataValidationException{
        OrganizationDetailsViewModel organizationDetailsViewModel = new OrganizationDetailsViewModel();
        
        Organization org = orgServiceLayer.getOrganization(organizationId);
        
        organizationDetailsViewModel.setOrganizationId(org.getOrganizationId());
        organizationDetailsViewModel.setOrganizationName(org.getOrganizationName());
        organizationDetailsViewModel.setOrganizationDescription(org.getOrganizationDescription());
        organizationDetailsViewModel.setLocation(translateDetailsLocation(locServiceLayer.getLocation(org.getLocationId())));
        organizationDetailsViewModel.setTelephoneNumber(org.getTelephoneNumber());
        organizationDetailsViewModel.setNumberOfMembers(memServiceLayer.findAllMembersAtOrganization(org, Integer.MAX_VALUE, 0).size());
        
        return organizationDetailsViewModel;
        
    }

    @Override
    public CreateOrganizationViewModel getCreateOrganizationViewModel() throws LocationPersistenceException {
        CreateOrganizationViewModel createOrganizationViewModel = new CreateOrganizationViewModel();
        CreateOrganizationCommandModel createOrganizationCommandModel = new CreateOrganizationCommandModel();
        
        List<Location> locs = locServiceLayer.getAllLocations(Integer.MAX_VALUE, 0);
        
        createOrganizationViewModel.setLocs(translateCreateLocation(locs));
        createOrganizationViewModel.setCreateOrganizationCommandModel(createOrganizationCommandModel);
        
        return createOrganizationViewModel;
        
    }

    @Override
    public EditOrganizationViewModel getEditOrganizationViewModel(int organizationId) throws LocationPersistenceException, OrganizationPersistenceException, OrganizationDataValidationException {
        EditOrganizationViewModel editOrganizationViewModel = new EditOrganizationViewModel();
        Organization org = orgServiceLayer.getOrganization(organizationId);
        EditOrganizationCommandModel commandModel = new EditOrganizationCommandModel();
                
        List<Location> locs = locServiceLayer.getAllLocations(Integer.MAX_VALUE, 0);
        editOrganizationViewModel.setLocs(translateEditLocation(locs));
        
        commandModel.setOrganizationId(org.getOrganizationId());
        commandModel.setOrganizationName(org.getOrganizationName());
        commandModel.setOrganizationDescription(org.getOrganizationDescription());
        commandModel.setLocationId(org.getLocationId());
        commandModel.setTelephoneNumber(org.getTelephoneNumber());
        editOrganizationViewModel.setEditOrganizationCommandModel(commandModel);
        
        return editOrganizationViewModel;
  
    }

    @Override
    public Organization saveCreateOrganizationCommandModel(CreateOrganizationCommandModel createOrganizationCommandModel) throws OrganizationDataValidationException, OrganizationPersistenceException, LocationPersistenceException, LocationDataValidationException {
        Organization org = new Organization();
        
        org.setOrganizationName(createOrganizationCommandModel.getOrganizationName());
        org.setOrganizationDescription(createOrganizationCommandModel.getOrganizationDescription());
        org.setLocationId(createOrganizationCommandModel.getLocationId());
        org.setTelephoneNumber(createOrganizationCommandModel.getTelephoneNumber());
        
        orgServiceLayer.createOrganization(org);
        
        return org;
    }

    @Override
    public Organization saveEditOrganizationCommandModel(EditOrganizationCommandModel editOrganizationCommandModel) throws OrganizationPersistenceException, OrganizationDataValidationException, LocationPersistenceException, LocationDataValidationException {
        Organization org = orgServiceLayer.getOrganization(editOrganizationCommandModel.getOrganizationId());
        
        org.setOrganizationName(editOrganizationCommandModel.getOrganizationName());
        org.setOrganizationDescription(editOrganizationCommandModel.getOrganizationDescription());
        org.setLocationId(editOrganizationCommandModel.getLocationId());
        org.setTelephoneNumber(editOrganizationCommandModel.getTelephoneNumber());
        
        orgServiceLayer.editOrganization(org);
        
        return org;
        
    }
    
   private OrganizationViewModel translateOrganization(Organization org) throws LocationPersistenceException, LocationDataValidationException{
        OrganizationViewModel organizationViewModel = new OrganizationViewModel();
        organizationViewModel.setOrganizationId(org.getOrganizationId());
        organizationViewModel.setOrganizationName(org.getOrganizationName());
        organizationViewModel.setOrganizationDescription(org.getOrganizationDescription());
        organizationViewModel.setLocation(translateLocation(locServiceLayer.getLocation(org.getLocationId())));
        organizationViewModel.setTelephoneNumber(org.getTelephoneNumber());
        return organizationViewModel;
   }
    
   private List<OrganizationViewModel> translateOrganization(List<Organization> orgs) throws LocationPersistenceException, LocationDataValidationException{
        List<OrganizationViewModel> orgModels = new ArrayList();
        
        for(Organization currentOrg: orgs){
            orgModels.add(translateOrganization(currentOrg));
        }
        
        return orgModels;
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
      
    private CreateLocationViewModel translateCreateLocation(Location location){
        CreateLocationViewModel locationViewModel = new CreateLocationViewModel();
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
    
    private List<CreateLocationViewModel> translateCreateLocation(List<Location> locations){
        List<CreateLocationViewModel> locModels = new ArrayList();
        for(Location currentLoc: locations){
            locModels.add(translateCreateLocation(currentLoc)); 
        }
        return locModels;
    }
    
    private EditLocationViewModel translateEditLocation(Location location) throws OrganizationPersistenceException{
        EditLocationViewModel locationViewModel = new EditLocationViewModel();
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
    
    private List<EditLocationViewModel> translateEditLocation(List<Location> locations) throws OrganizationPersistenceException{
        List<EditLocationViewModel> locModels = new ArrayList();
        for(Location currentLoc: locations){
            locModels.add(translateEditLocation(currentLoc)); 
        }
        return locModels;
    }
}
