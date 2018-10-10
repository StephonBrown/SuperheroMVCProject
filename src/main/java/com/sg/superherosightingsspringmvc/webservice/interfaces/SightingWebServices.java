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
import com.sg.superherosightingsspringmvc.dto.Sighting;
import com.sg.superherosightingsspringmvc.service.HeroDataValidationException;
import com.sg.superherosightingsspringmvc.service.LocationDataValidationException;
import com.sg.superherosightingsspringmvc.service.OrganizationDataValidationException;
import com.sg.superherosightingsspringmvc.service.SightingDataValidationException;
import com.sg.superherosightingsspringmvc.service.SuperpowerDataValidationException;
import com.sg.superherosightingsspringmvc.viewmodel.sighting.createsighting.CreateSightingViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.sighting.editsighting.EditSightingViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.sighting.sightingdetails.SightingDetailsViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.sighting.sightinghome.SightingHomeViewModel;

/**
 *
 * @author Stephon
 */
public interface SightingWebServices {
    SightingHomeViewModel getSightingHomeViewModel(Integer limit, Integer offset, Integer pageNumbers) throws HeroPersistenceException, HeroDataValidationException,  OrganizationDataValidationException, SuperpowerDataValidationException, LocationDataValidationException, OrganizationPersistenceException, 
            SuperpowerPersistenceException, SightingPersistenceException, SightingDataValidationException, LocationPersistenceException;
    SightingDetailsViewModel getSightingDetailsViewModel(int sightingId)throws HeroPersistenceException, HeroDataValidationException,  OrganizationDataValidationException, SuperpowerDataValidationException, LocationDataValidationException, OrganizationPersistenceException, 
            SuperpowerPersistenceException, SightingPersistenceException, SightingDataValidationException, LocationPersistenceException;
    
    CreateSightingViewModel getCreateSightingViewModel()throws HeroPersistenceException, HeroDataValidationException,  OrganizationDataValidationException, SuperpowerDataValidationException, LocationDataValidationException, OrganizationPersistenceException, 
            SuperpowerPersistenceException, SightingPersistenceException, SightingDataValidationException, LocationPersistenceException;
    EditSightingViewModel getEditSightingViewModel(int sightingId)throws HeroPersistenceException, HeroDataValidationException,  OrganizationDataValidationException, SuperpowerDataValidationException, LocationDataValidationException, OrganizationPersistenceException, 
            SuperpowerPersistenceException, SightingPersistenceException, SightingDataValidationException, LocationPersistenceException;
    
    Sighting saveCreateSightingCommandModel(CreateSightingCommandModel createSightingCommandModel)throws HeroPersistenceException, HeroDataValidationException,  OrganizationDataValidationException, SuperpowerDataValidationException, LocationDataValidationException, OrganizationPersistenceException, 
            SuperpowerPersistenceException, SightingPersistenceException, SightingDataValidationException, LocationPersistenceException;
    Sighting saveEditSightingCommandModel(EditSightingCommandModel editSightingCommandModel)throws HeroPersistenceException, HeroDataValidationException,  OrganizationDataValidationException, SuperpowerDataValidationException, LocationDataValidationException, OrganizationPersistenceException, 
            SuperpowerPersistenceException, SightingPersistenceException, SightingDataValidationException, LocationPersistenceException;
}
