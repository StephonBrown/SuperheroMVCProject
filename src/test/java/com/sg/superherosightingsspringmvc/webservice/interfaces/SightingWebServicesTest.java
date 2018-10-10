/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.webservice.interfaces;

import com.sg.superherosightingsspringmvc.commandmodel.sighting.createsighting.CreateSightingCommandModel;
import com.sg.superherosightingsspringmvc.commandmodel.sighting.editsighting.EditSightingCommandModel;
import com.sg.superherosightingsspringmvc.dao.HeroPersistenceException;
import com.sg.superherosightingsspringmvc.dao.LocationPersistenceException;
import com.sg.superherosightingsspringmvc.dao.OrganizationPersistenceException;
import com.sg.superherosightingsspringmvc.dao.SightingPersistenceException;
import com.sg.superherosightingsspringmvc.dao.SuperpowerPersistenceException;
import com.sg.superherosightingsspringmvc.dto.Hero;
import com.sg.superherosightingsspringmvc.dto.Location;
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
import com.sg.superherosightingsspringmvc.viewmodel.sighting.createsighting.CreateHeroViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.sighting.createsighting.CreateLocationViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.sighting.createsighting.CreateSightingViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.sighting.editsighting.EditHeroViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.sighting.editsighting.EditLocationViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.sighting.editsighting.EditSightingViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.sighting.sightingdetails.SightingDetailsViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.sighting.sightinghome.SightingHomeViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.sighting.sightinghome.SightingViewModel;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.junit.Test;
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
public class SightingWebServicesTest {
    
    @Inject
    SightingWebServices sightingWebServices;
    
    @Inject
    HeroServiceLayer heServiceLayer;
    
    @Inject
    LocationServiceLayer locServiceLayer;
    
    @Inject
    SightingServiceLayer stServiceLayer;
    
    @Inject
    SuperpowerServiceLayer spServiceLayer;
    
    @Inject
    OrganizationServiceLayer orgServiceLayer;

    /**
     * Test of getSightingHomeViewModel method, of class SightingWebServices.
     */
    @Test
    public void testGetSightingHomeViewModel() throws Exception {
        for(Location location: locServiceLayer.getAllLocations(Integer.MAX_VALUE, 0)){
            locServiceLayer.removeLocation(location.getLocationId());
        }
        List<Sighting> sightings = createTestSighting(15);
        
        SightingHomeViewModel sightingHomeViewModel = sightingWebServices.getSightingHomeViewModel(5, 0, 5);
        
        assert sightingHomeViewModel.getSts().size()==5;
        assert sightingHomeViewModel.getPageNumber() == 1;
        
        assert sightingHomeViewModel.getPageNumbers().size() == 5;
        
        assert sightingHomeViewModel.getPageNumbers().get(1) == 2;
        assert sightingHomeViewModel.getPageNumbers().get(4) == 5;
        
        int count = 0;
        for(SightingViewModel sightingViewModel: sightingHomeViewModel.getSts()){
            assert("2015-01-" + (30-count)).equals(sightingViewModel.getSightingDate().toString());
            
            Sighting sameSighting = sightings.get(count);
            Location loc = locServiceLayer.getLocation(sameSighting.getlocationID());
            Hero hero = heServiceLayer.getHeroAtSighting(sameSighting);
            
            assert loc.getLocationId() == sightingViewModel.getLocation().getLocationId();
            assert loc.getLocationName().equals(sightingViewModel.getLocation().getLocationName());
            assert loc.getLocationDescription().equals(sightingViewModel.getLocation().getLocationDescription());
            assert loc.getStreet().equals(sightingViewModel.getLocation().getStreet());
            assert loc.getCity().equals(sightingViewModel.getLocation().getCity());
            assert loc.getState().equals(sightingViewModel.getLocation().getState());
            assert loc.getZipCode().equals(sightingViewModel.getLocation().getZipCode());
            assert loc.getLongitude().equals(sightingViewModel.getLocation().getLongitude());
            assert loc.getLatitude().equals(sightingViewModel.getLocation().getLatitude());

            assert hero.getHeroId() == sightingViewModel.getHero().getHeroId();
            assert hero.getHeroName().equals(sightingViewModel.getHero().getHeroName());
            assert hero.getHeroDescription().equals(sightingViewModel.getHero().getHeroDescription());
            
            count++;                       
        }
    }

    /**
     * Test of getSightingDetailsViewModel method, of class SightingWebServices.
     */
    @Test
    public void testGetSightingDetailsViewModel() throws Exception {
        
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
        
        Sighting s1 = new Sighting();
        s1.setSightingDate(LocalDate.parse("2015-01-01", 
                            DateTimeFormatter.ISO_DATE));
        s1.setLocationID(lo1.getLocationId());
        
        stServiceLayer.createSighting(s1);

        Hero hero = new Hero();
        hero.setHeroName("BucketHead");
        hero.setHeroDescription("A man with a buckethead");
        
        heServiceLayer.createHero(hero);
        
        stServiceLayer.insertHeroSightings(hero, s1);
        
        SightingDetailsViewModel sightingDetailsViewModel = sightingWebServices.getSightingDetailsViewModel(s1.getSightingId());
        
        assert sightingDetailsViewModel.getSightingId() == s1.getSightingId();
        assert sightingDetailsViewModel.getSightingDate().equals(s1.getSightingDate());
        assert sightingDetailsViewModel.getLocation().getLocationName().equals(locServiceLayer.getLocation(s1.getlocationID()).getLocationName());
        assert sightingDetailsViewModel.getHero().getHeroName().equals(heServiceLayer.getHeroAtSighting(s1).getHeroName());
    }

    /**
     * Test of getCreatSightingViewModel method, of class SightingWebServices.
     */
    @Test
    public void testGetCreateSightingViewModel() throws Exception {
        for(Location location: locServiceLayer.getAllLocations(Integer.MAX_VALUE, 0)){
            locServiceLayer.removeLocation(location.getLocationId());
        }
        List<Hero> heroes = createTestHeroes(15);
        List<Location> locs = createTestLocations(15);
        
        CreateSightingViewModel createSightingViewModel = sightingWebServices.getCreateSightingViewModel();
        
        assert createSightingViewModel.getHeroes().size()== heroes.size();
        assert createSightingViewModel.getLocs().size()== locs.size();
        
        for(CreateHeroViewModel viewModel: createSightingViewModel.getHeroes()){
            assert viewModel.getHeroId() != 0;
            assert viewModel.getHeroName() != null;
            assert viewModel.getHeroDescription() !=null;
        }
        for(CreateLocationViewModel viewModel: createSightingViewModel.getLocs()){
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
     * Test of getEditSightingViewModel method, of class SightingWebServices.
     */
    @Test
    public void testGetEditSightingViewModel() throws Exception {
        for(Location location: locServiceLayer.getAllLocations(Integer.MAX_VALUE, 0)){
            locServiceLayer.removeLocation(location.getLocationId());
        }
        List<Hero> heroes = createTestHeroes(15);
        List<Location> locs = createTestLocations(15);
        
        Sighting existingSighting = createTestSighting(locs.get(0),heroes.get(0));
        
        EditSightingViewModel editSightingViewModel = sightingWebServices.getEditSightingViewModel(existingSighting.getSightingId());
        
        assert editSightingViewModel.getHeroes().size()== heroes.size();
        assert editSightingViewModel.getLocs().size()== locs.size();
        
        for(EditHeroViewModel viewModel: editSightingViewModel.getHeroes()){
            assert viewModel.getHeroId() != 0;
            assert viewModel.getHeroName() != null;
            assert viewModel.getHeroDescription() !=null;
        }
        for(EditLocationViewModel viewModel: editSightingViewModel.getLocs()){
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
        EditSightingCommandModel commandModel = editSightingViewModel.getEditSightingCommandModel();
        
        assert commandModel.getSightingId() == existingSighting.getSightingId();
        assert commandModel.getSightingDate().equals(existingSighting.getSightingDate());
        assert commandModel.getLocationID() == existingSighting.getlocationID();
        assert commandModel.getHeroID() == heServiceLayer.getHeroAtSighting(existingSighting).getHeroId();
    }

    /**
     * Test of saveCreateSightingCommandModel method, of class SightingWebServices.
     */
    @Test
    public void testSaveCreateSightingCommandModel() throws Exception {
        List<Hero> heroes = createTestHeroes(2);
        List<Location> locs = createTestLocations(2);
        
        List<Integer> heroIds = new ArrayList();
        heroIds.add(heroes.get(0).getHeroId());
        
        List<Integer> locIds = new ArrayList();
        locIds.add(locs.get(0).getLocationId());
        
        CreateSightingCommandModel commandModel = new CreateSightingCommandModel();
        commandModel.setSightingDate(LocalDate.parse("2015-01-01", DateTimeFormatter.ISO_DATE));
        commandModel.setLocationID(locIds.get(0));
        commandModel.setHeroID(heroIds.get(0));
        
        Sighting sighting = sightingWebServices.saveCreateSightingCommandModel(commandModel);
        
        assert sighting.getSightingId() != 0;
        assert sighting.getSightingDate().toString().equals("2015-01-01");
        assert sighting.getlocationID() == locs.get(0).getLocationId();
        assert heServiceLayer.getHeroAtSighting(sighting).getHeroId() == heroes.get(0).getHeroId();
 
    }

    /**
     * Test of saveEditSightingCommandModel method, of class SightingWebServices.
     */
    @Test
    public void testSaveEditSightingCommandModel() throws Exception {
        
        List<Hero> heroes = createTestHeroes(2);
        List<Location> locs = createTestLocations(2);
        
        List<Integer> heroIds = new ArrayList();
        heroIds.add(heroes.get(0).getHeroId());
        heroIds.add(heroes.get(1).getHeroId());
        
        List<Integer> locIds = new ArrayList();
        locIds.add(locs.get(0).getLocationId());
        locIds.add(locs.get(1).getLocationId());
        
        Sighting existingSighting = createTestSighting(locs.get(0),heroes.get(0));
        
        EditSightingCommandModel commandModel = new EditSightingCommandModel();
        commandModel.setSightingId(existingSighting.getSightingId());
        commandModel.setSightingDate(LocalDate.parse("2018-02-02", DateTimeFormatter.ISO_DATE));
        commandModel.setLocationID(locIds.get(1));
        commandModel.setHeroID(heroIds.get(1));
        
        Sighting editedSighting = sightingWebServices.saveEditSightingCommandModel(commandModel);
        
        
        assert editedSighting.getSightingId() != 0;
        assert editedSighting.getSightingDate().toString().equals("2018-02-02");
        assert editedSighting.getlocationID() == locs.get(1).getLocationId();
        assert editedSighting.getlocationID() != locs.get(0).getLocationId();
        assert heServiceLayer.getHeroAtSighting(editedSighting).getHeroId() == heroes.get(1).getHeroId();
        assert heServiceLayer.getHeroAtSighting(editedSighting).getHeroId() != heroes.get(0).getHeroId();

    }
    
    private List<Sighting> createTestSighting(int numberOfSightings) throws LocationPersistenceException, LocationDataValidationException, SightingPersistenceException, SightingDataValidationException, HeroDataValidationException, HeroPersistenceException{
        List<Sighting> sightings = new ArrayList();
        
        for(int i=0;i< numberOfSightings; i ++){
            Location lo1 = new Location();
            lo1.setLocationName("Bronx"+i);
            lo1.setLocationDescription("In front of the Newspaper Building"+i);
            lo1.setStreet("567 Bobo Street"+i);
            lo1.setCity("New York City"+i);
            lo1.setState("New York"+i);
            lo1.setZipCode("95609");
            lo1.setLongitude(new BigDecimal("40.712772").add(BigDecimal.ONE));
            lo1.setLatitude(new BigDecimal("74.006058").negate().add(BigDecimal.ONE));

            locServiceLayer.createLocation(lo1);

            int j = 30-i;
            Sighting s1 = new Sighting();
            s1.setSightingDate(LocalDate.parse("2015-01-"+j, 
                                DateTimeFormatter.ISO_DATE));
            s1.setLocationID(lo1.getLocationId());

            stServiceLayer.createSighting(s1);

            Hero hero = new Hero();
            hero.setHeroName("BucketHead"+i);
            hero.setHeroDescription("A man with a buckethead"+i);

            heServiceLayer.createHero(hero);
            stServiceLayer.insertHeroSightings(hero, s1);
            
            sightings.add(s1);
        }
        
        return sightings;

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
                lo1.setLongitude(new BigDecimal("40.712772"));
                lo1.setLatitude(new BigDecimal("74.006058").negate());
                locServiceLayer.createLocation(lo1);
                
                locs.add(lo1);
            }
            
            return locs;
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
            
            spServiceLayer.insertHeroSuperPower(sp1, hero);
            heroes.add(hero);        
        }
        
        return heroes;

    }
    
  private Sighting createTestSighting(Location loc, Hero hero) throws HeroDataValidationException, HeroPersistenceException, OrganizationPersistenceException, OrganizationDataValidationException, SuperpowerPersistenceException, SuperpowerDataValidationException, SightingPersistenceException, SightingDataValidationException, LocationPersistenceException, LocationDataValidationException{
      Sighting sighting = new Sighting();

      sighting.setSightingDate(LocalDate.parse("2015-01-01", DateTimeFormatter.ISO_DATE));
      sighting.setLocationID(loc.getLocationId());
       
      stServiceLayer.createSighting(sighting);
      stServiceLayer.insertHeroSightings(hero, sighting);
       
      return sighting;
    }
    
}
