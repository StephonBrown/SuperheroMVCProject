/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.webservice;

import com.sg.superherosightingsspringmvc.commandmodel.hero.createhero.CreateHeroCommandModel;
import com.sg.superherosightingsspringmvc.commandmodel.hero.edithero.EditHeroCommandModel;
import com.sg.superherosightingsspringmvc.dao.HeroPersistenceException;
import com.sg.superherosightingsspringmvc.dao.LocationPersistenceException;
import com.sg.superherosightingsspringmvc.dao.OrganizationPersistenceException;
import com.sg.superherosightingsspringmvc.dao.SightingPersistenceException;
import com.sg.superherosightingsspringmvc.dao.SuperpowerPersistenceException;
import com.sg.superherosightingsspringmvc.dto.Hero;
import com.sg.superherosightingsspringmvc.dto.Location;
import com.sg.superherosightingsspringmvc.dto.Organization;
import com.sg.superherosightingsspringmvc.dto.Sighting;
import com.sg.superherosightingsspringmvc.dto.Superpower;
import com.sg.superherosightingsspringmvc.service.HeroDataValidationException;
import com.sg.superherosightingsspringmvc.service.HeroServiceLayer;
import com.sg.superherosightingsspringmvc.service.LocationDataValidationException;
import com.sg.superherosightingsspringmvc.service.LocationServiceLayer;
import com.sg.superherosightingsspringmvc.service.OrganizationDataValidationException;
import com.sg.superherosightingsspringmvc.service.OrganizationServiceLayer;
import com.sg.superherosightingsspringmvc.service.SightingDataValidationException;
import com.sg.superherosightingsspringmvc.service.SightingServiceLayer;
import com.sg.superherosightingsspringmvc.service.SuperpowerDataValidationException;
import com.sg.superherosightingsspringmvc.service.SuperpowerServiceLayer;
import com.sg.superherosightingsspringmvc.viewmodel.hero.herodetails.HeroDetailsViewModel;

import com.sg.superherosightingsspringmvc.viewmodel.hero.herohome.SuperpowerViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.hero.herohome.HeroHomeViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.hero.herohome.HeroViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.hero.herohome.OrganizationViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.hero.createhero.CreateHeroViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.hero.createhero.CreateOrganizationViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.hero.createhero.CreateSuperpowerViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.hero.edithero.EditHeroViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.hero.edithero.EditOrganizationViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.hero.edithero.EditSuperpowerViewModel;
import com.sg.superherosightingsspringmvc.webservice.interfaces.HeroWebServices;
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
public class HeroWebServicesImpl implements HeroWebServices{
    @Inject
    private  HeroServiceLayer heServiceLayer;
    @Inject
    private  SuperpowerServiceLayer spServiceLayer;
    @Inject
    private  OrganizationServiceLayer orgServiceLayer;
    @Inject
    private  LocationServiceLayer locServiceLayer;
    @Inject
    private  SightingServiceLayer stServiceLayer;
    
    
    
    
    @Override
    public HeroHomeViewModel getHeroHomeViewModel(Integer limit, Integer offset, Integer pageNumbers) throws OrganizationPersistenceException, HeroPersistenceException, HeroDataValidationException, SuperpowerPersistenceException {
            if(limit ==null){
                limit=5;
            }
            if(offset==null){
                offset=0;
            }
            if(pageNumbers==null){
                pageNumbers=5;
            }
            
            HeroHomeViewModel heroHomeViewModel = new HeroHomeViewModel();
  
            List<Hero> heroes = heServiceLayer.getAllHeroes(limit,offset);
            
            Integer currentPage = PageUtilities.calculatePageNumber(limit,offset);
            List<Integer> pages = PageUtilities.getPageNumbers(currentPage, pageNumbers);
            
            heroHomeViewModel.setHeroes(translateHero(heroes));
            heroHomeViewModel.setPageNumber(currentPage);
            heroHomeViewModel.setPageNumbers(pages);
            
            return heroHomeViewModel;
    
    }

    /**
     *
     * @param heroId
     * @return
     * @throws HeroPersistenceException
     * @throws HeroDataValidationException
     * @throws OrganizationDataValidationException
     * @throws SuperpowerDataValidationException
     * @throws LocationDataValidationException
     * @throws OrganizationPersistenceException
     * @throws SuperpowerPersistenceException
     * @throws LocationPersistenceException
     */
    @Override
    public HeroDetailsViewModel getHeroDetailsViewModel(int heroId) throws HeroPersistenceException, HeroDataValidationException, 
            OrganizationDataValidationException, SuperpowerDataValidationException, LocationDataValidationException, OrganizationPersistenceException, 
            SuperpowerPersistenceException,LocationPersistenceException{
       HeroDetailsViewModel heroDetailsViewModel = new HeroDetailsViewModel();
       List<String> orgNames = new ArrayList();
       List<String> spNames = new ArrayList();
       List<String> locNames = new ArrayList();
       
       Hero hero = heServiceLayer.getHero(heroId);
     
       
       heroDetailsViewModel.setHeroId(hero.getHeroId());
       heroDetailsViewModel.setHeroName(hero.getHeroName());
       heroDetailsViewModel.setHeroDescription(hero.getHeroDescription());
       for(Organization currentOrg: orgServiceLayer.getHeroOrganizations(hero, Integer.MAX_VALUE,0)){
           orgNames.add(currentOrg.getOrganizationName());
       }
       for(Superpower currentSp: spServiceLayer.getHeroesSuperPowers(hero,Integer.MAX_VALUE,0)){
           spNames.add(currentSp.getSuperPowerName());
       }
        for(Location currentLoc: locServiceLayer.getHeroLocations(hero, Integer.MAX_VALUE,0)){
           locNames.add(currentLoc.getLocationName());
       }
        
        heroDetailsViewModel.setOrgNames(orgNames);
        heroDetailsViewModel.setSpNames(spNames);
        heroDetailsViewModel.setLocNames(locNames);
        
        return heroDetailsViewModel;  
    }

    @Override
    public CreateHeroViewModel getCreateHeroViewModel() throws HeroPersistenceException, HeroDataValidationException, 
            OrganizationDataValidationException, SuperpowerDataValidationException, LocationDataValidationException, OrganizationPersistenceException, 
            SuperpowerPersistenceException,LocationPersistenceException{
        CreateHeroViewModel createHeroViewModel = new CreateHeroViewModel();
        List<Organization> orgs = orgServiceLayer.getAllOrganizations(Integer.MAX_VALUE,0);
        List<Superpower> sps = spServiceLayer.getAllSuperpowers(Integer.MAX_VALUE,0);
        
        CreateHeroCommandModel createHeroCommandModel = new CreateHeroCommandModel();
        createHeroViewModel.setCreateHeroCommandModel(createHeroCommandModel);
        createHeroViewModel.setOrgs(translateCreateOrganization(orgs));
        createHeroViewModel.setSps(translateCreateSuperpower(sps));
        
        return createHeroViewModel;
    }

    @Override
    public EditHeroViewModel getEditHeroViewModel(int heroId)throws HeroPersistenceException, HeroDataValidationException, 
            OrganizationDataValidationException, SuperpowerDataValidationException, LocationDataValidationException, OrganizationPersistenceException, 
            SuperpowerPersistenceException,LocationPersistenceException {
        EditHeroViewModel editHeroViewModel = new EditHeroViewModel();
        
        Hero existingHero = heServiceLayer.getHero(heroId);
        
        List<Organization> allOrgs = orgServiceLayer.getAllOrganizations(Integer.MAX_VALUE,0);
        List<Superpower> allSps = spServiceLayer.getAllSuperpowers(Integer.MAX_VALUE,0);
        editHeroViewModel.setOrgs(translateEditOrganization(allOrgs));
        editHeroViewModel.setSps(translateEditSuperpower(allSps));
        
        //For Heroes existing organizationIDs and superpowers
        List<Integer> HeroesOrgs = new ArrayList();
        List<Integer> HeroesSps = new ArrayList();
        
        EditHeroCommandModel commandModel = new EditHeroCommandModel();
        commandModel.setHeroId(existingHero.getHeroId());
        commandModel.setHeroName(existingHero.getHeroName());
        commandModel.setHeroDescription(existingHero.getHeroDescription());
        //find all organizations
        for(Organization currentorg:orgServiceLayer.getHeroOrganizations(existingHero, Integer.MAX_VALUE,0)){
            HeroesOrgs.add(currentorg.getOrganizationId());
        }
        for(Superpower currentsp:spServiceLayer.getHeroesSuperPowers(existingHero, Integer.MAX_VALUE,0)){
            HeroesSps.add(currentsp.getSuperPowerId());
        }
        commandModel.setOrgs(HeroesOrgs);
        commandModel.setSps(HeroesSps);
        
        editHeroViewModel.setEditHeroCommandModel(commandModel);
        
        return editHeroViewModel;
   
    }

    @Override
    public Hero saveCreateHeroCommandModel(CreateHeroCommandModel createHeroCommandModel)throws HeroPersistenceException, HeroDataValidationException,  OrganizationDataValidationException, SuperpowerDataValidationException, LocationDataValidationException, OrganizationPersistenceException, 
            SuperpowerPersistenceException{
        Hero hero = new Hero();
        
        List<Integer> sps = createHeroCommandModel.getSps();
        List<Integer> orgs = createHeroCommandModel.getOrgs();
        
        hero.setHeroName(createHeroCommandModel.getHeroName());
        hero.setHeroDescription(createHeroCommandModel.getHeroDescription());
        heServiceLayer.createHero(hero);
            
        for(int currentOrg: orgs){
                orgServiceLayer.insertOrganizationHero(orgServiceLayer.getOrganization(currentOrg), hero);
        }
        
        for(int currentSP: sps){
                spServiceLayer.insertHeroSuperPower(spServiceLayer.getSuperpower(currentSP), hero);
        }
        
        return hero;
    }

    @Override
    public Hero saveEditHeroCommandModel(EditHeroCommandModel editHeroCommandModel) throws HeroPersistenceException, HeroDataValidationException,  OrganizationDataValidationException, SuperpowerDataValidationException, LocationDataValidationException, OrganizationPersistenceException, 
            SuperpowerPersistenceException, SightingPersistenceException, SightingDataValidationException {
        Hero hero = heServiceLayer.getHero(editHeroCommandModel.getHeroId());
        
        List<Integer> sps = editHeroCommandModel.getSps();
        List<Integer> orgs = editHeroCommandModel.getOrgs();
        List<Sighting> sights = stServiceLayer.getHeroSightings(hero, Integer.MAX_VALUE,0);
        
        hero.setHeroName(editHeroCommandModel.getHeroName());
        hero.setHeroDescription(editHeroCommandModel.getHeroDescription());
 
        heServiceLayer.editHero(hero);
            
        for(int currentOrg: orgs){
            orgServiceLayer.insertOrganizationHero(orgServiceLayer.getOrganization(currentOrg), hero);
        }
        for(int currentSP: sps){
            spServiceLayer.insertHeroSuperPower(spServiceLayer.getSuperpower(currentSP), hero);
        }
        for(Sighting currentSt:sights){
            stServiceLayer.insertHeroSightings(hero, currentSt);
        }
        
        return hero;
        
    }
    
    private HeroViewModel translateHero(Hero hero) throws OrganizationPersistenceException, HeroPersistenceException, HeroDataValidationException, SuperpowerPersistenceException{
        HeroViewModel heroViewModel = new HeroViewModel();
        heroViewModel.setHeroId(hero.getHeroId());
        heroViewModel.setHeroName(hero.getHeroName());
        heroViewModel.setHeroDescription(hero.getHeroDescription());
        if(orgServiceLayer.getHeroOrganizations(hero, Integer.MAX_VALUE,0) != null){
            heroViewModel.setOrgs(translateOrganization(orgServiceLayer.getHeroOrganizations(hero, Integer.MAX_VALUE,0)) );
        }
        heroViewModel.setSps(translateSuperpower(spServiceLayer.getHeroesSuperPowers(hero,Integer.MAX_VALUE,0)));
        
        return heroViewModel;  
    }
    
    private List<HeroViewModel> translateHero(List<Hero> heroes) throws OrganizationPersistenceException, HeroPersistenceException, HeroDataValidationException, SuperpowerPersistenceException{
        List<HeroViewModel> heroModels = new ArrayList();
        for(Hero currentHero: heroes){
            heroModels.add(translateHero(currentHero));
        }
        return heroModels;
    }
    private OrganizationViewModel translateOrganization(Organization org){
        OrganizationViewModel organizationViewModel = new OrganizationViewModel();
        organizationViewModel.setOrganizationId(org.getOrganizationId());
        organizationViewModel.setOrganizationName(org.getOrganizationName());
        organizationViewModel.setOrganizationDescription(org.getOrganizationDescription());
        organizationViewModel.setLocationId(org.getLocationId());
        organizationViewModel.setTelephoneNumber(org.getTelephoneNumber());
        return organizationViewModel;
    }
    
    private List<OrganizationViewModel> translateOrganization(List<Organization> orgs){
        List<OrganizationViewModel> orgModels = new ArrayList();
        
        for(Organization currentOrg: orgs){
            orgModels.add(translateOrganization(currentOrg));
        }
        
        return orgModels;
    }
    
    private SuperpowerViewModel translateSuperpower(Superpower sp){
        SuperpowerViewModel superpowerViewModel = new SuperpowerViewModel();
        superpowerViewModel.setSuperPowerId(sp.getSuperPowerId());
        superpowerViewModel.setSuperPowerName(sp.getSuperPowerName());
        superpowerViewModel.setSuperPowerDescription(sp.getSuperPowerDescription());
        
        return superpowerViewModel;
    }
    
    private List<SuperpowerViewModel> translateSuperpower(List<Superpower> sps){
        List<SuperpowerViewModel> spModels = new ArrayList();
        for(Superpower currentSp: sps){
            spModels.add(translateSuperpower(currentSp));
        }
        
        return spModels;
    }
    
    private CreateOrganizationViewModel translateCreateOrganization(Organization org){
        CreateOrganizationViewModel organizationViewModel = new CreateOrganizationViewModel();
        organizationViewModel.setOrganizationId(org.getOrganizationId());
        organizationViewModel.setOrganizationName(org.getOrganizationName());
        organizationViewModel.setOrganizationDescription(org.getOrganizationDescription());
        organizationViewModel.setLocationId(org.getLocationId());
        organizationViewModel.setTelephoneNumber(org.getTelephoneNumber());
        return organizationViewModel;
    }
    
    private List<CreateOrganizationViewModel> translateCreateOrganization(List<Organization> orgs){
        List<CreateOrganizationViewModel> orgModels = new ArrayList();
        
        for(Organization currentOrg: orgs){
            orgModels.add(translateCreateOrganization(currentOrg));
        }
        
        return orgModels;
    }
    
    private CreateSuperpowerViewModel translateCreateSuperpower(Superpower sp){
        CreateSuperpowerViewModel superpowerViewModel = new CreateSuperpowerViewModel();
        superpowerViewModel.setSuperPowerId(sp.getSuperPowerId());
        superpowerViewModel.setSuperPowerName(sp.getSuperPowerName());
        superpowerViewModel.setSuperPowerDescription(sp.getSuperPowerDescription());
        
        return superpowerViewModel;
    }
    
    private List<CreateSuperpowerViewModel> translateCreateSuperpower(List<Superpower> sps){
        List<CreateSuperpowerViewModel> spModels = new ArrayList();
        for(Superpower currentSp: sps){
            spModels.add(translateCreateSuperpower(currentSp));
        }
        
        return spModels;
    }
    
        
    private EditOrganizationViewModel translateEditOrganization(Organization org){
        EditOrganizationViewModel organizationViewModel = new EditOrganizationViewModel();
        organizationViewModel.setOrganizationId(org.getOrganizationId());
        organizationViewModel.setOrganizationName(org.getOrganizationName());
        organizationViewModel.setOrganizationDescription(org.getOrganizationDescription());
        organizationViewModel.setLocationId(org.getLocationId());
        organizationViewModel.setTelephoneNumber(org.getTelephoneNumber());
        return organizationViewModel;
    }
    
    private List<EditOrganizationViewModel> translateEditOrganization(List<Organization> orgs){
        List<EditOrganizationViewModel> orgModels = new ArrayList();
        
        for(Organization currentOrg: orgs){
            orgModels.add(translateEditOrganization(currentOrg));
        }
        
        return orgModels;
    }
    
    private EditSuperpowerViewModel translateEditSuperpower(Superpower sp){
        EditSuperpowerViewModel superpowerViewModel = new EditSuperpowerViewModel();
        superpowerViewModel.setSuperPowerId(sp.getSuperPowerId());
        superpowerViewModel.setSuperPowerName(sp.getSuperPowerName());
        superpowerViewModel.setSuperPowerDescription(sp.getSuperPowerDescription());
        
        return superpowerViewModel;
    }
    
    private List<EditSuperpowerViewModel> translateEditSuperpower(List<Superpower> sps){
        List<EditSuperpowerViewModel> spModels = new ArrayList();
        for(Superpower currentSp: sps){
            spModels.add(translateEditSuperpower(currentSp));
        }
        
        return spModels;
    }
}
