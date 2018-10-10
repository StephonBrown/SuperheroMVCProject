/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.controller;

import com.sg.superherosightingsspringmvc.dto.Hero;
import com.sg.superherosightingsspringmvc.dto.Location;
import com.sg.superherosightingsspringmvc.dto.Organization;
import com.sg.superherosightingsspringmvc.dto.Sighting;
import com.sg.superherosightingsspringmvc.service.HeroDataValidationException;
import com.sg.superherosightingsspringmvc.dao.HeroPersistenceException;
import com.sg.superherosightingsspringmvc.service.HeroServiceLayer;
import com.sg.superherosightingsspringmvc.service.LocationDataValidationException;
import com.sg.superherosightingsspringmvc.dao.LocationPersistenceException;
import com.sg.superherosightingsspringmvc.service.LocationServiceLayer;
import com.sg.superherosightingsspringmvc.dao.OrganizationPersistenceException;
import com.sg.superherosightingsspringmvc.service.OrganizationServiceLayer;
import com.sg.superherosightingsspringmvc.service.SightingDataValidationException;
import com.sg.superherosightingsspringmvc.dao.SightingPersistenceException;
import com.sg.superherosightingsspringmvc.dao.SuperpowerPersistenceException;
import com.sg.superherosightingsspringmvc.service.OrganizationDataValidationException;
import com.sg.superherosightingsspringmvc.service.SightingServiceLayer;
import com.sg.superherosightingsspringmvc.service.SuperpowerDataValidationException;
import com.sg.superherosightingsspringmvc.viewmodel.sighting.sightinghome.SightingHomeViewModel;
import com.sg.superherosightingsspringmvc.webservice.interfaces.SightingWebServices;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Stephon
 */
@Controller
public class HomeController {
    @Inject
    SightingWebServices sightingWebServices;
    @Inject
    SightingServiceLayer stServiceLayer;
    @Inject
    LocationServiceLayer locServiceLayer;
    @Inject
    HeroServiceLayer heServiceLayer;
    @Inject
    OrganizationServiceLayer orgServiceLayer;
    
    
    @RequestMapping(value="/", method=RequestMethod.GET)
    public String returnHome(Model model) throws SightingPersistenceException, LocationPersistenceException, LocationDataValidationException, HeroPersistenceException, HeroDataValidationException, SightingDataValidationException, OrganizationPersistenceException, OrganizationDataValidationException, SuperpowerDataValidationException, SuperpowerPersistenceException{
        SightingHomeViewModel viewModel = sightingWebServices.getSightingHomeViewModel(10,0,1);
        model.addAttribute("viewModel", viewModel);
        return "index";
    }
}
