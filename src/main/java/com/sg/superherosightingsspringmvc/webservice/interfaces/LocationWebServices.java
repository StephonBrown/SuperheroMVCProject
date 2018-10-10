/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.webservice.interfaces;

import com.sg.superherosightingsspringmvc.commandmodel.location.createlocation.CreateLocationCommandModel;
import com.sg.superherosightingsspringmvc.commandmodel.location.editlocation.EditLocationCommandModel;
import com.sg.superherosightingsspringmvc.dao.LocationPersistenceException;
import com.sg.superherosightingsspringmvc.dto.Location;
import com.sg.superherosightingsspringmvc.service.LocationDataValidationException;
import com.sg.superherosightingsspringmvc.viewmodel.location.locationdetails.LocationDetailsViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.location.locationhome.LocationHomeViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.location.createlocation.CreateLocationViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.location.editlocation.EditLocationViewModel;
import com.sg.superherosightingsspringmvc.webservice.interfaces.LocationWebServices;

/**
 *
 * @author Stephon
 */
public interface LocationWebServices {
    LocationHomeViewModel getLocationHomeViewModel(Integer limit, Integer offset, Integer pageNumbers) throws LocationPersistenceException;
    LocationDetailsViewModel getLocationDetailsViewMode(int locationId)throws LocationPersistenceException, LocationDataValidationException;
    CreateLocationViewModel getCreateLocationViewModel();
    EditLocationViewModel getEditLocationViewModel(int locationId)throws LocationPersistenceException, LocationDataValidationException;
    Location saveCreateLocationCommandModel(CreateLocationCommandModel createLocationCommandModel) throws LocationPersistenceException, LocationDataValidationException;
    Location SaveEditLocationCommandModel(EditLocationCommandModel editLocationCommandModel) throws LocationPersistenceException, LocationDataValidationException;            
}
