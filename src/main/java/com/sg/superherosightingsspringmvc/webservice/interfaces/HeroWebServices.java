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
import com.sg.superherosightingsspringmvc.service.HeroDataValidationException;
import com.sg.superherosightingsspringmvc.service.LocationDataValidationException;
import com.sg.superherosightingsspringmvc.service.OrganizationDataValidationException;
import com.sg.superherosightingsspringmvc.service.SightingDataValidationException;
import com.sg.superherosightingsspringmvc.service.SuperpowerDataValidationException;
import com.sg.superherosightingsspringmvc.viewmodel.hero.herodetails.HeroDetailsViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.hero.herohome.HeroHomeViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.hero.createhero.CreateHeroViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.hero.edithero.EditHeroViewModel;

/**
 *
 * @author Stephon
 */
public interface HeroWebServices {
    HeroHomeViewModel getHeroHomeViewModel(Integer limit, Integer offset, Integer pageNumbers)throws HeroPersistenceException, OrganizationPersistenceException, HeroPersistenceException, HeroDataValidationException, SuperpowerPersistenceException;
    HeroDetailsViewModel getHeroDetailsViewModel(int heroId)throws HeroPersistenceException, HeroDataValidationException, 
            OrganizationDataValidationException, SuperpowerDataValidationException, LocationDataValidationException, OrganizationPersistenceException, 
            SuperpowerPersistenceException,LocationPersistenceException;
    
    CreateHeroViewModel getCreateHeroViewModel()throws HeroPersistenceException, HeroDataValidationException, 
            OrganizationDataValidationException, SuperpowerDataValidationException, LocationDataValidationException, OrganizationPersistenceException, 
            SuperpowerPersistenceException,LocationPersistenceException;
    EditHeroViewModel getEditHeroViewModel(int heroId)throws HeroPersistenceException, HeroDataValidationException, 
            OrganizationDataValidationException, SuperpowerDataValidationException, LocationDataValidationException, OrganizationPersistenceException, 
            SuperpowerPersistenceException,LocationPersistenceException;
    
    Hero saveCreateHeroCommandModel(CreateHeroCommandModel createHeroCommandModel)throws HeroPersistenceException, HeroDataValidationException,OrganizationDataValidationException, SuperpowerDataValidationException, LocationDataValidationException, OrganizationPersistenceException, 
            SuperpowerPersistenceException;
    Hero saveEditHeroCommandModel(EditHeroCommandModel editHeroCommandModel) throws HeroPersistenceException, HeroDataValidationException,  OrganizationDataValidationException, SuperpowerDataValidationException, LocationDataValidationException, OrganizationPersistenceException, 
            SuperpowerPersistenceException, SightingPersistenceException, SightingDataValidationException ;
    
}
