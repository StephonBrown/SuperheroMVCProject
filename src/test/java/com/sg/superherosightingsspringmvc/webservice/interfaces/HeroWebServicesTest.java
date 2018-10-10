/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.webservice.interfaces;

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
import com.sg.superherosightingsspringmvc.viewmodel.hero.createhero.CreateHeroViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.hero.createhero.CreateOrganizationViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.hero.createhero.CreateSuperpowerViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.hero.edithero.EditHeroViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.hero.edithero.EditOrganizationViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.hero.edithero.EditSuperpowerViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.hero.herodetails.HeroDetailsViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.hero.herohome.HeroHomeViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.hero.herohome.HeroViewModel;
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
public class HeroWebServicesTest {
    
    @Inject
    HeroWebServices heroWebServices;
            
    @Inject
    HeroServiceLayer heServiceLayer;
    
    @Inject
    SuperpowerServiceLayer spServiceLayer;
    
    @Inject
    OrganizationServiceLayer orgServiceLayer;
    
    @Inject
    LocationServiceLayer locServiceLayer;
    
    @Inject
    SightingServiceLayer stServiceLayer;
   
    /**
     * Test of getHeroHomeViewModel method, of class HeroWebServices.
     */
    @Test
    public void testGetHeroHomeViewModel() throws Exception {
        List<Hero> heroes = createTestHeroes(15);
        
        HeroHomeViewModel heroHomeViewModel = heroWebServices.getHeroHomeViewModel(5, 0, 5);
        
        assert heroHomeViewModel.getHeroes().size()==5;
        assert heroHomeViewModel.getPageNumber() == 1;
        
        assert heroHomeViewModel.getPageNumbers().size() == 5;
        
        assert heroHomeViewModel.getPageNumbers().get(1) == 2;
        assert heroHomeViewModel.getPageNumbers().get(4) == 5;
        
        int count = 0;
        for(HeroViewModel heroViewModel: heroHomeViewModel.getHeroes()){
            assert("Ice Slider" +count).equals(heroViewModel.getHeroName());
            assert("A man that hails from the glacial islands" + count).equals(heroViewModel.getHeroDescription());
            
            Hero sameHero = heroes.get(count);
            Superpower sp = spServiceLayer.getHeroesSuperPowers(sameHero, Integer.MAX_VALUE, 0).get(0);
            Organization org = orgServiceLayer.getHeroOrganizations(sameHero, Integer.MAX_VALUE, 0).get(0);
            
            assert sp.getSuperPowerName().equals(heroViewModel.getSps().get(0).getSuperPowerName());
            assert org.getOrganizationName().equals(heroViewModel.getOrgs().get(0).getOrganizationName());
            
            count++;                       
        }
        
        
    }

    /**
     * Test of getHeroDetailsViewModel method, of class HeroWebServices.
     */
    @Test
    public void testGetHeroDetailsViewModel() throws Exception {
            Hero hero = new Hero();
            hero.setHeroName("Ice Slider");
            hero.setHeroDescription("A man that hails from the glacial islands");
            hero = heServiceLayer.createHero(hero);
            
            Superpower sp1 = new Superpower();
            sp1.setSuperPowerName("Ice Power");
            sp1.setSuperPowerDescription("Control the elements of ice" );
            spServiceLayer.createSuperpower(sp1);
            
            Superpower sp2 = new Superpower();
            sp2.setSuperPowerName("Fire Power");
            sp2.setSuperPowerDescription("Control the elements of fire");
            spServiceLayer.createSuperpower(sp2);
            
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
            
            Organization org1 = new Organization();
            org1.setLocationId(lo1.getLocationId());
            org1.setOrganizationName("Shield Headquarters");
            org1.setOrganizationDescription("The headquarters of Shield");
            org1.setTelephoneNumber("555-555-5555");
            
            orgServiceLayer.createOrganization(org1);
            spServiceLayer.insertHeroSuperPower(sp1, hero);
            spServiceLayer.insertHeroSuperPower(sp2, hero);
            orgServiceLayer.insertOrganizationHero(org1, hero);
            
            HeroDetailsViewModel heroDetailsViewModel = heroWebServices.getHeroDetailsViewModel(hero.getHeroId());
            
            assert heroDetailsViewModel.getHeroId() == hero.getHeroId();
            assert heroDetailsViewModel.getHeroName().equals(hero.getHeroName());
            assert heroDetailsViewModel.getHeroDescription().equals(hero.getHeroDescription());
            
            boolean containssp1 = false;
            boolean containssp2 = false;
            boolean containsorg = false;
            
            for(String spName:heroDetailsViewModel.getSpNames()){
                if("Ice Power".equals(spName)) containssp1 = true;
                if("Fire Power".equals(spName)) containssp2 = true;
            }
            
            for(String orgName:heroDetailsViewModel.getOrgNames()){
                if("Shield Headquarters".equals(orgName)) containsorg = true;
            }
            
            assert containssp1 == true;
            assert containssp2 == true;
            assert containsorg == true; 
    }

    /**
     * Test of getCreateHeroViewModel method, of class HeroWebServices.
     */
    @Test
    public void testGetCreateHeroViewModel() throws Exception {
        List<Organization> orgs = createTestOrganizations(15);
        List<Superpower> sps = createTestSuperpowers(15);
        
        CreateHeroViewModel createHeroViewModel = heroWebServices.getCreateHeroViewModel();
        
        assert createHeroViewModel.getOrgs().size()==orgs.size();
        assert createHeroViewModel.getSps().size()==sps.size();
        
        for(CreateSuperpowerViewModel viewModel: createHeroViewModel.getSps()){
            assert viewModel.getSuperPowerId() != 0;
            assert viewModel.getSuperPowerName() != null;
            assert viewModel.getSuperPowerDescription() !=null;
        }
        for(CreateOrganizationViewModel createOrganizationViewModel: createHeroViewModel.getOrgs()){
            assert createOrganizationViewModel.getOrganizationId() !=0;
            assert createOrganizationViewModel.getOrganizationName() !=null;
            assert createOrganizationViewModel.getOrganizationDescription() !=null;
            assert createOrganizationViewModel.getLocationId() !=0;
            assert createOrganizationViewModel.getTelephoneNumber() !=null;
        }
        
        
    }

    /**
     * Test of getEditHeroViewModel method, of class HeroWebServices.
     */
    @Test
    public void testGetEditHeroViewModel() throws Exception {
        List<Organization> orgs = createTestOrganizations(15);
        List<Superpower> sps = createTestSuperpowers(15);
        
        List<Organization> selectedOrg = new ArrayList<>();
        selectedOrg.add(orgs.get(0));
        
        List<Superpower> selectedSp = new ArrayList<>();
        selectedSp.add(sps.get(0));
        selectedSp.add(sps.get(1));
        
        Hero existingHero = createTestHero(selectedSp, selectedOrg);
        
        EditHeroViewModel editHeroViewModel = heroWebServices.getEditHeroViewModel(existingHero.getHeroId());
        
        assert editHeroViewModel.getOrgs().size()==orgs.size();
        assert editHeroViewModel.getSps().size()==sps.size();
               
                
        for(EditSuperpowerViewModel viewModel: editHeroViewModel.getSps()){
            assert viewModel.getSuperPowerId() != 0;
            assert viewModel.getSuperPowerName() != null;
            assert viewModel.getSuperPowerDescription() !=null;
        }
        for(EditOrganizationViewModel editOrganizationViewModel: editHeroViewModel.getOrgs()){
            assert editOrganizationViewModel.getOrganizationId() !=0;
            assert editOrganizationViewModel.getOrganizationName() !=null;
            assert editOrganizationViewModel.getOrganizationDescription() !=null;
            assert editOrganizationViewModel.getLocationId() !=0;
            assert editOrganizationViewModel.getTelephoneNumber() !=null;
        }
        
        
        EditHeroCommandModel commandModel = editHeroViewModel.getEditHeroCommandModel();

        assert commandModel.getHeroId() == (existingHero.getHeroId());
        assert commandModel.getHeroName().equals(existingHero.getHeroName());
        assert commandModel.getHeroDescription().equals(existingHero.getHeroDescription());



        boolean containssp1 = false;
        boolean containssp2 = false;
        boolean containsorg = false;

        for(Integer sp:commandModel.getSps()){
            if(sp == sps.get(0).getSuperPowerId()) containssp1 = true;
            if(sp == sps.get(0).getSuperPowerId()) containssp2 = true;
        }

        for(Integer org:commandModel.getOrgs()){
            if(org == orgs.get(0).getOrganizationId()) containsorg = true;
        }

        assert containssp1 == true;
        assert containssp2 == true;
        assert containsorg == true; 
        
    }

    /**
     * Test of saveCreateHeroCommandModel method, of class HeroWebServices.
     */
    @Test
    public void testSaveCreateHeroCommandModel() throws Exception {
        List<Organization> orgs = createTestOrganizations(1);
        List<Superpower> sps = createTestSuperpowers(2);
        
        List<Integer> orgIds = new ArrayList();
        orgIds.add(orgs.get(0).getOrganizationId());
        
        List<Integer> spIds = new ArrayList();
        spIds.add(sps.get(0).getSuperPowerId());
        spIds.add(sps.get(1).getSuperPowerId());
        
        CreateHeroCommandModel createHeroCommandModel = new CreateHeroCommandModel();
        createHeroCommandModel.setHeroName("Ice Slider");
        createHeroCommandModel.setHeroDescription("A man that hails from the glacial islands");
        createHeroCommandModel.setOrgs(orgIds);
        createHeroCommandModel.setSps(spIds);
        
        Hero hero = heroWebServices.saveCreateHeroCommandModel(createHeroCommandModel);
        
        assert hero.getHeroId() != 0;
        assert "Ice Slider".equals(hero.getHeroName());
        assert "A man that hails from the glacial islands".equals(hero.getHeroDescription());
        
        boolean containssp1 = false;
        boolean containssp2 = false;
        boolean containsorg = false;
            
        for(Superpower superpower : spServiceLayer.getHeroesSuperPowers(hero, Integer.MAX_VALUE, 0)){
            if(sps.get(0).getSuperPowerId() == superpower.getSuperPowerId()) containssp1 = true;
            if(sps.get(1).getSuperPowerId() == superpower.getSuperPowerId()) containssp2 = true;
        }

        for(Organization org:orgServiceLayer.getHeroOrganizations(hero,Integer.MAX_VALUE, 0)){
            if(orgs.get(0).getOrganizationId()== org.getOrganizationId()) containsorg = true;
        }

        assert containssp1 == true;
        assert containssp2 == true;
        assert containsorg == true; 
                
        
    }

    /**
     * Test of saveEditHeroCommandModel method, of class HeroWebServices.
     */
    @Test
    public void testSaveEditHeroCommandModel() throws Exception {
        List<Organization> orgs = createTestOrganizations(2);
        List<Superpower> sps = createTestSuperpowers(2);
        
        List<Organization> selectedOrg = new ArrayList();
        
        List<Integer> selectedOrgID = new ArrayList();
        List<Integer> selectedSpIDs = new ArrayList();
        
        selectedOrg.add(orgs.get(0));
        selectedOrg.add(orgs.get(1));
        
        Hero existingHero = createTestHero(sps,selectedOrg);
        
        EditHeroCommandModel editHeroCommandModel = new EditHeroCommandModel();
        editHeroCommandModel.setHeroId(existingHero.getHeroId());
        editHeroCommandModel.setHeroName("Ice Slider");
        editHeroCommandModel.setHeroDescription("A man that hails from the glacial islands");
        //Change Organization
        selectedOrgID.add(selectedOrg.get(1).getOrganizationId());
        //Change Superpowers
        selectedSpIDs.add(sps.get(0).getSuperPowerId());
        
        editHeroCommandModel.setOrgs(selectedOrgID);
        editHeroCommandModel.setSps(selectedSpIDs);
        
        Hero editedHero = heroWebServices.saveEditHeroCommandModel(editHeroCommandModel);
        
        assert editedHero.getHeroId() != 0;
        assert "Ice Slider".equals(editedHero.getHeroName());
        assert "A man that hails from the glacial islands".equals(editedHero.getHeroDescription());
        
        boolean savedSp1 = false;
        boolean deletedSp2 = true;
        boolean savedOrg = false;
        boolean deletedOrg = true;
            
        for(Superpower superpower : spServiceLayer.getHeroesSuperPowers(editedHero, Integer.MAX_VALUE, 0)){
            if(sps.get(0).getSuperPowerId() == superpower.getSuperPowerId()) savedSp1 = true;
            if(sps.get(1).getSuperPowerId() == superpower.getSuperPowerId()) deletedSp2 = false;
        }
        
        for(Organization org:orgServiceLayer.getHeroOrganizations(editedHero,Integer.MAX_VALUE, 0)){
            if(orgs.get(1).getOrganizationId()== org.getOrganizationId()) savedOrg = true;
            if(orgs.get(0).getOrganizationId()== org.getOrganizationId()) deletedOrg = false;
        }
        
        assert savedSp1 == true;
        assert deletedSp2 == true;
        assert savedOrg == true;
        assert deletedOrg == true;

    }
    
    private List<Hero> createTestHeroes(int numberOfHeroes) throws HeroDataValidationException, HeroPersistenceException, OrganizationDataValidationException, OrganizationPersistenceException, LocationPersistenceException, LocationDataValidationException, SuperpowerDataValidationException, SuperpowerPersistenceException{
        List<Hero> heroes = new ArrayList();
        
        for(int i=0;i< numberOfHeroes; i ++){
            Hero hero = new Hero();
            hero.setHeroName("Ice Slider"+i);
            hero.setHeroDescription("A man that hails from the glacial islands"+i);
            heServiceLayer.createHero(hero);
            
            Superpower sp1 = new Superpower();
            sp1.setSuperPowerName("Ice Power" + i);
            sp1.setSuperPowerDescription("Control the elements of ice" + i);
            spServiceLayer.createSuperpower(sp1);
            
            Location lo1 = new Location();
            lo1.setLocationName("Bronx"+i);
            lo1.setLocationDescription("In front of the Newspaper Building"+i);
            lo1.setStreet("567 Bobo Street"+i);
            lo1.setCity("New York City"+i);
            lo1.setState("New York"+i);
            lo1.setZipCode("95609");
            lo1.setLongitude(new BigDecimal(40.712772).add(new BigDecimal(i)));
            lo1.setLatitude(new BigDecimal(74.006058).negate().add(new BigDecimal(i)));
            locServiceLayer.createLocation(lo1);
            
            Organization org1 = new Organization();
            org1.setLocationId(lo1.getLocationId());
            org1.setOrganizationName("Shield Headquarters"+i);
            org1.setOrganizationDescription("The headquarters of Shield"+i);
            org1.setTelephoneNumber("555-555-555"+i);
            
            orgServiceLayer.createOrganization(org1);
            
            spServiceLayer.insertHeroSuperPower(sp1, hero);
            orgServiceLayer.insertOrganizationHero(org1, hero);
            heroes.add(hero);        
        }
        
        return heroes;

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
                lo1.setLongitude(new BigDecimal(40.712772).add(new BigDecimal(i)));
                lo1.setLatitude(new BigDecimal(74.006058).negate().add(new BigDecimal(i)));
                locServiceLayer.createLocation(lo1);

                Organization org1 = new Organization();
                org1.setLocationId(lo1.getLocationId());
                org1.setOrganizationName("Shield Headquarters"+i);
                org1.setOrganizationDescription("The headquarters of Shield"+i);
                org1.setTelephoneNumber("555-555-555"+i);

                org1 = orgServiceLayer.createOrganization(org1);
                
                orgs.add(org1);
            }
            
            return orgs;
     }
     
    private List<Superpower> createTestSuperpowers(int numberOfSps) throws LocationPersistenceException, LocationDataValidationException, OrganizationDataValidationException, OrganizationPersistenceException, SuperpowerDataValidationException, SuperpowerPersistenceException{
            List<Superpower> sps = new ArrayList();
            
            for(int i=0;i< numberOfSps; i ++){
                
                Superpower sp1 = new Superpower();
                sp1.setSuperPowerName("Ice Power" + i);
                sp1.setSuperPowerDescription("Control the elements of ice" + i);
                sp1 = spServiceLayer.createSuperpower(sp1);
                
                sps.add(sp1);
            }
            
            return sps;
     }
    
    private Hero createTestHero(List<Superpower> sps, List<Organization> orgs) throws HeroDataValidationException, HeroPersistenceException, OrganizationPersistenceException, OrganizationDataValidationException, SuperpowerPersistenceException, SuperpowerDataValidationException{
            Hero hero = new Hero();
            hero.setHeroName("Ice Slider");
            hero.setHeroDescription("A man that hails from the glacial islands");
            hero = heServiceLayer.createHero(hero);
            
            for(Organization org: orgs){
                orgServiceLayer.insertOrganizationHero(org, hero);
            }
            
            for(Superpower sp: sps){
                spServiceLayer.insertHeroSuperPower(sp, hero);
            }
            
            return hero;
    }
    
    
}
