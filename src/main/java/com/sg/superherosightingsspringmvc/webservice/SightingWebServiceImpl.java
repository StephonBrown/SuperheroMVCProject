/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.webservice;

import com.sg.superherosightingsspringmvc.commandmodel.sighting.createsighting.CreateSightingCommandModel;
import com.sg.superherosightingsspringmvc.commandmodel.sighting.editsighting.EditSightingCommandModel;
import com.sg.superherosightingsspringmvc.dao.HeroPersistenceException;
import com.sg.superherosightingsspringmvc.dao.LocationPersistenceException;
import com.sg.superherosightingsspringmvc.dao.OrganizationPersistenceException;
import com.sg.superherosightingsspringmvc.dao.SightingPersistenceException;
import com.sg.superherosightingsspringmvc.dao.SuperpowerPersistenceException;
import com.sg.superherosightingsspringmvc.dto.Hero;
import com.sg.superherosightingsspringmvc.dto.Location;
import com.sg.superherosightingsspringmvc.dto.Organization;
import com.sg.superherosightingsspringmvc.dto.Sighting;
import com.sg.superherosightingsspringmvc.service.HeroDataValidationException;
import com.sg.superherosightingsspringmvc.service.HeroServiceLayer;
import com.sg.superherosightingsspringmvc.service.LocationDataValidationException;
import com.sg.superherosightingsspringmvc.service.LocationServiceLayer;
import com.sg.superherosightingsspringmvc.service.OrganizationDataValidationException;
import com.sg.superherosightingsspringmvc.service.OrganizationServiceLayer;
import com.sg.superherosightingsspringmvc.service.SightingDataValidationException;
import com.sg.superherosightingsspringmvc.service.SightingServiceLayer;
import com.sg.superherosightingsspringmvc.service.SuperpowerDataValidationException;
import com.sg.superherosightingsspringmvc.viewmodel.sighting.editsighting.EditHeroViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.sighting.createsighting.CreateHeroViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.sighting.createsighting.CreateLocationViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.sighting.sightingdetails.HeroDetailsViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.sighting.createsighting.CreateSightingViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.sighting.editsighting.EditLocationViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.sighting.editsighting.EditSightingViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.sighting.sightingdetails.LocationDetailsViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.sighting.sightingdetails.SightingDetailsViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.sighting.sightinghome.HeroViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.sighting.sightinghome.LocationViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.sighting.sightinghome.OrganizationViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.sighting.sightinghome.SightingHomeViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.sighting.sightinghome.SightingViewModel;
import com.sg.superherosightingsspringmvc.webservice.interfaces.SightingWebServices;
import com.sg.superherosightingsspringmvc.webservice.utilities.PageUtilities;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Stephon
 */
public class SightingWebServiceImpl implements SightingWebServices{
    @Inject
    private  HeroServiceLayer heServiceLayer;
    @Inject
    private  LocationServiceLayer locServiceLayer;
    @Inject
    private  SightingServiceLayer stServiceLayer;
    @Inject
    private  OrganizationServiceLayer orgServiceLayer;

    @Override
    public SightingHomeViewModel getSightingHomeViewModel(Integer limit, Integer offset, Integer pageNumbers) throws HeroPersistenceException, HeroDataValidationException,  OrganizationDataValidationException, SuperpowerDataValidationException, LocationDataValidationException, OrganizationPersistenceException, 
            SuperpowerPersistenceException, SightingPersistenceException, SightingDataValidationException, LocationPersistenceException {
            if(limit ==null){
                limit=5;
            }
            if(offset==null){
                offset=0;
            }
            if(pageNumbers==null){
                pageNumbers=5;
            }
            
            SightingHomeViewModel sightingHomeViewModel = new SightingHomeViewModel();
  
            List<Sighting> sights = stServiceLayer.getAllSightings(limit,offset);
            
            Integer currentPage = PageUtilities.calculatePageNumber(limit,offset);
            List<Integer> pages = PageUtilities.getPageNumbers(currentPage, pageNumbers);
            
            sightingHomeViewModel.setSts(translateSighting(sights));
            sightingHomeViewModel.setPageNumber(currentPage);
            sightingHomeViewModel.setPageNumbers(pages);
            
            return sightingHomeViewModel;
    }

    @Override
    public SightingDetailsViewModel getSightingDetailsViewModel(int sightingId) throws HeroPersistenceException, HeroDataValidationException,  OrganizationDataValidationException, SuperpowerDataValidationException, LocationDataValidationException, OrganizationPersistenceException, 
            SuperpowerPersistenceException, SightingPersistenceException, SightingDataValidationException, LocationPersistenceException{
        
        SightingDetailsViewModel sightingDetailsViewModel = new SightingDetailsViewModel();
        
        Sighting sighting = stServiceLayer.getSighting(sightingId);
        
        sightingDetailsViewModel.setSightingId(sighting.getSightingId());
        sightingDetailsViewModel.setSightingDate(sighting.getSightingDate());
        sightingDetailsViewModel.setLocation(translateLocationDetails(locServiceLayer.getLocation(sighting.getlocationID())));
        sightingDetailsViewModel.setHero(translateHeroDetails(heServiceLayer.getHeroAtSighting(sighting)));
        
        return sightingDetailsViewModel;
        
    }

    @Override
    public CreateSightingViewModel getCreateSightingViewModel()throws HeroPersistenceException, HeroDataValidationException,  OrganizationDataValidationException, SuperpowerDataValidationException, LocationDataValidationException, OrganizationPersistenceException, 
            SuperpowerPersistenceException, SightingPersistenceException, SightingDataValidationException, LocationPersistenceException{
        
        CreateSightingViewModel createSightingViewModel = new CreateSightingViewModel();
        CreateSightingCommandModel createSightingCommandModel = new CreateSightingCommandModel();
        
        createSightingViewModel.setCreateSightingCommandModel(createSightingCommandModel);
        
        List<Location> locs = locServiceLayer.getAllLocations(Integer.MAX_VALUE, 0);
        List<Hero> heroes = heServiceLayer.getAllHeroes(Integer.MAX_VALUE, 0);
        
        createSightingViewModel.setHeroes(translateCreateHero(heroes));
        createSightingViewModel.setLocs(translateCreateLocation(locs));
        
        return createSightingViewModel;
          
    }

    @Override
    public EditSightingViewModel getEditSightingViewModel(int sightingId) throws HeroPersistenceException, HeroDataValidationException,  OrganizationDataValidationException, SuperpowerDataValidationException, LocationDataValidationException, OrganizationPersistenceException, 
        SuperpowerPersistenceException, SightingPersistenceException, SightingDataValidationException, LocationPersistenceException {
        
        EditSightingViewModel editSightingViewModel = new EditSightingViewModel();
        
        Sighting sighting = stServiceLayer.getSighting(sightingId);
        
        List<Location> allLocs = locServiceLayer.getAllLocations(Integer.MAX_VALUE, 0);
        List<Hero> allHeroes = heServiceLayer.getAllHeroes(Integer.MAX_VALUE, 0);
        
        editSightingViewModel.setHeroes(translateEditHero(allHeroes));
        editSightingViewModel.setLocs(translateEditLocation(allLocs));
        
        //Set Command model to poulate previous information
        EditSightingCommandModel editSightingCommandModel = new EditSightingCommandModel();
        editSightingCommandModel.setSightingId(sighting.getSightingId());
        editSightingCommandModel.setSightingDate(sighting.getSightingDate());
        editSightingCommandModel.setLocationID(sighting.getlocationID());
        editSightingCommandModel.setHeroID(heServiceLayer.getHeroAtSighting(sighting).getHeroId());
        
        editSightingViewModel.setCreateSightingCommandModel(editSightingCommandModel);
        
        return editSightingViewModel;    
    }

    @Override
    public Sighting saveCreateSightingCommandModel(CreateSightingCommandModel createSightingCommandModel) throws SightingPersistenceException, SightingDataValidationException, HeroPersistenceException, LocationPersistenceException, LocationDataValidationException, HeroDataValidationException{
        Sighting sighting = new Sighting();
        
        sighting.setSightingDate(createSightingCommandModel.getSightingDate());
        sighting.setLocationID(createSightingCommandModel.getLocationID());
        
        stServiceLayer.createSighting(sighting);
        stServiceLayer.insertHeroSightings(heServiceLayer.getHero(createSightingCommandModel.getHeroID()), sighting);
        
        return sighting;
        
    }

    @Override
    public Sighting saveEditSightingCommandModel(EditSightingCommandModel editSightingCommandModel) throws SightingPersistenceException, SightingDataValidationException, LocationPersistenceException, LocationDataValidationException, HeroPersistenceException, HeroDataValidationException {
        Sighting sighting = stServiceLayer.getSighting(editSightingCommandModel.getSightingId());
        
        sighting.setSightingDate(editSightingCommandModel.getSightingDate());
        sighting.setLocationID(editSightingCommandModel.getLocationID()); 
        stServiceLayer.editSighting(sighting);
        
        stServiceLayer.insertHeroSightings(heServiceLayer.getHero(editSightingCommandModel.getHeroID()), sighting);
        
        return sighting;
        
    }
    
    //Helper methods for translating objects to viewmodels
     private List<SightingViewModel> translateSighting(List<Sighting> sightings) throws OrganizationPersistenceException, HeroPersistenceException, HeroDataValidationException, SuperpowerPersistenceException, SightingPersistenceException, SightingDataValidationException, LocationPersistenceException, LocationDataValidationException{
         List<SightingViewModel> sightingModels = new ArrayList();
         for(Sighting currentSt: sightings){
             sightingModels.add(translateSighting(currentSt));
         }
         return sightingModels;
     }
    
    private SightingViewModel translateSighting(Sighting sighting) throws OrganizationPersistenceException, HeroPersistenceException, HeroDataValidationException, SuperpowerPersistenceException, SightingPersistenceException, SightingDataValidationException, LocationPersistenceException, LocationDataValidationException{
        SightingViewModel sightingViewModel = new SightingViewModel();
        
        sightingViewModel.setSightingId(sighting.getSightingId());
        sightingViewModel.setHero(translateHero(heServiceLayer.getHeroAtSighting(sighting)));
        sightingViewModel.setLocation(translateLocation(locServiceLayer.getLocation(sighting.getlocationID())));
        sightingViewModel.setSightingDate(sighting.getSightingDate());
 
        return sightingViewModel;  
    }
    
    private HeroViewModel translateHero(Hero hero) throws OrganizationPersistenceException, HeroPersistenceException, HeroDataValidationException, SuperpowerPersistenceException, LocationPersistenceException, LocationDataValidationException{
        HeroViewModel heroViewModel = new HeroViewModel();
        heroViewModel.setHeroId(hero.getHeroId());
        heroViewModel.setHeroName(hero.getHeroName());
        heroViewModel.setHeroDescription(hero.getHeroDescription());
        heroViewModel.setOrgs(translateOrganization(orgServiceLayer.getHeroOrganizations(hero, Integer.MAX_VALUE, 0)));
        return heroViewModel;  
    }
    
    private OrganizationViewModel translateOrganization(Organization org) throws LocationPersistenceException, LocationDataValidationException, OrganizationPersistenceException, HeroPersistenceException, HeroDataValidationException, SuperpowerPersistenceException{
        OrganizationViewModel organizationViewModel = new OrganizationViewModel();
        organizationViewModel.setOrganizationId(org.getOrganizationId());
        organizationViewModel.setOrganizationName(org.getOrganizationName());
        organizationViewModel.setOrganizationDescription(org.getOrganizationDescription());
        organizationViewModel.setLocationId(org.getLocationId());
        organizationViewModel.setTelephoneNumber(org.getTelephoneNumber());
        return organizationViewModel;
   }
    
    private List<OrganizationViewModel> translateOrganization(List<Organization> orgs) throws LocationPersistenceException, LocationDataValidationException, OrganizationPersistenceException, HeroPersistenceException, HeroDataValidationException, SuperpowerPersistenceException{
        List<OrganizationViewModel> orgModels = new ArrayList();
        
        for(Organization currentOrg: orgs){
            orgModels.add(translateOrganization(currentOrg));
        }
        
        return orgModels;
    }
    
    private LocationViewModel translateLocation(Location location) throws OrganizationPersistenceException, HeroPersistenceException, HeroDataValidationException, SuperpowerPersistenceException{
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
    
    private HeroDetailsViewModel translateHeroDetails(Hero hero) throws OrganizationPersistenceException, HeroPersistenceException, HeroDataValidationException, SuperpowerPersistenceException{
        HeroDetailsViewModel heroViewModel = new HeroDetailsViewModel();
        heroViewModel.setHeroId(hero.getHeroId());
        heroViewModel.setHeroName(hero.getHeroName());
        heroViewModel.setHeroDescription(hero.getHeroDescription());
        
        return heroViewModel;  
    }
    
    private LocationDetailsViewModel translateLocationDetails(Location location) throws OrganizationPersistenceException, HeroPersistenceException, HeroDataValidationException, SuperpowerPersistenceException{
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
    
    private CreateHeroViewModel translateCreateHero(Hero hero) throws OrganizationPersistenceException, HeroPersistenceException, HeroDataValidationException, SuperpowerPersistenceException{
        CreateHeroViewModel heroViewModel = new CreateHeroViewModel();
        heroViewModel.setHeroId(hero.getHeroId());
        heroViewModel.setHeroName(hero.getHeroName());
        heroViewModel.setHeroDescription(hero.getHeroDescription());
        
        return heroViewModel;  
    }
    
    private CreateLocationViewModel translateCreateLocation(Location location) throws OrganizationPersistenceException, HeroPersistenceException, HeroDataValidationException, SuperpowerPersistenceException{
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
    
        
    private List<CreateHeroViewModel> translateCreateHero(List<Hero> heroes) throws OrganizationPersistenceException, HeroPersistenceException, HeroDataValidationException, SuperpowerPersistenceException{
        List<CreateHeroViewModel> heroModels = new ArrayList();
        for(Hero currentHero: heroes){
            heroModels.add(translateCreateHero(currentHero)); 
        }
        return heroModels;
    }
    
    private List<CreateLocationViewModel> translateCreateLocation(List<Location> locations) throws OrganizationPersistenceException, HeroPersistenceException, HeroDataValidationException, SuperpowerPersistenceException{
        List<CreateLocationViewModel> locModels = new ArrayList();
        for(Location currentLoc: locations){
            locModels.add(translateCreateLocation(currentLoc)); 
        }
        return locModels;
    }
    
    private EditHeroViewModel translateEditHero(Hero hero) throws OrganizationPersistenceException, HeroPersistenceException, HeroDataValidationException, SuperpowerPersistenceException{
        EditHeroViewModel heroViewModel = new EditHeroViewModel();
        heroViewModel.setHeroId(hero.getHeroId());
        heroViewModel.setHeroName(hero.getHeroName());
        heroViewModel.setHeroDescription(hero.getHeroDescription());
        
        return heroViewModel;  
    }
    
    private EditLocationViewModel translateEditLocation(Location location) throws OrganizationPersistenceException, HeroPersistenceException, HeroDataValidationException, SuperpowerPersistenceException{
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
    
        
    private List<EditHeroViewModel> translateEditHero(List<Hero> heroes) throws OrganizationPersistenceException, HeroPersistenceException, HeroDataValidationException, SuperpowerPersistenceException{
        List<EditHeroViewModel> heroModels = new ArrayList();
        for(Hero currentHero: heroes){
            heroModels.add(translateEditHero(currentHero)); 
        }
        return heroModels;
    }
    
    private List<EditLocationViewModel> translateEditLocation(List<Location> locations) throws OrganizationPersistenceException, HeroPersistenceException, HeroDataValidationException, SuperpowerPersistenceException{
        List<EditLocationViewModel> locModels = new ArrayList();
        for(Location currentLoc: locations){
            locModels.add(translateEditLocation(currentLoc)); 
        }
        return locModels;
    }
}
