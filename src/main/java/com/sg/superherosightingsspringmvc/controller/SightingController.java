/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.controller;

import com.sg.superherosightingsspringmvc.dto.Sighting;
import com.sg.superherosightingsspringmvc.commandmodel.sighting.createsighting.CreateSightingCommandModel;
import com.sg.superherosightingsspringmvc.commandmodel.sighting.editsighting.EditSightingCommandModel;
import com.sg.superherosightingsspringmvc.service.HeroDataValidationException;
import com.sg.superherosightingsspringmvc.dao.HeroPersistenceException;
import com.sg.superherosightingsspringmvc.service.HeroServiceLayer;
import com.sg.superherosightingsspringmvc.service.LocationDataValidationException;
import com.sg.superherosightingsspringmvc.dao.LocationPersistenceException;
import com.sg.superherosightingsspringmvc.service.LocationServiceLayer;
import com.sg.superherosightingsspringmvc.dao.OrganizationPersistenceException;
import com.sg.superherosightingsspringmvc.service.SightingDataValidationException;
import com.sg.superherosightingsspringmvc.dao.SightingPersistenceException;
import com.sg.superherosightingsspringmvc.service.SightingServiceLayer;
import com.sg.superherosightingsspringmvc.service.SuperpowerDataValidationException;
import com.sg.superherosightingsspringmvc.dao.SuperpowerPersistenceException;
import com.sg.superherosightingsspringmvc.service.OrganizationDataValidationException;
import com.sg.superherosightingsspringmvc.service.SuperpowerServiceLayer;
import com.sg.superherosightingsspringmvc.viewmodel.sighting.createsighting.CreateSightingViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.sighting.editsighting.EditSightingViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.sighting.sightingdetails.SightingDetailsViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.sighting.sightinghome.SightingHomeViewModel;
import com.sg.superherosightingsspringmvc.webservice.interfaces.SightingWebServices;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author sbrown6
 */
@Controller
@RequestMapping(value="/sighting")
public class SightingController {
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
    
    @RequestMapping(value="/home", method=RequestMethod.GET)
    public String returnSightingHome(@RequestParam(required = false) Integer offset, Model model) throws SightingPersistenceException, LocationPersistenceException, LocationDataValidationException, HeroPersistenceException, HeroDataValidationException, SightingDataValidationException, OrganizationPersistenceException, OrganizationDataValidationException, SuperpowerDataValidationException, SuperpowerPersistenceException{
        SightingHomeViewModel viewModel = sightingWebServices.getSightingHomeViewModel(5,offset,5);
        model.addAttribute("viewModel", viewModel);
        
        return "sighting/home";
    }
    @RequestMapping(value="/details/{sightingId}", method=RequestMethod.GET)
    public String displaySightingDetails(@PathVariable("sightingId") int sightingId, Model model) throws SightingPersistenceException, SightingDataValidationException, HeroPersistenceException, LocationPersistenceException, LocationDataValidationException, HeroDataValidationException, OrganizationDataValidationException, SuperpowerDataValidationException, OrganizationPersistenceException, SuperpowerPersistenceException{
        SightingDetailsViewModel viewModel = sightingWebServices.getSightingDetailsViewModel(sightingId);
        model.addAttribute("viewModel", viewModel);
        
        return "sighting/details";
    }
    
    @RequestMapping(value="/create", method=RequestMethod.GET)
    public String displayCreateSighting( Model model) throws SightingPersistenceException, LocationPersistenceException, LocationDataValidationException, HeroPersistenceException, HeroDataValidationException, OrganizationPersistenceException, SuperpowerPersistenceException, OrganizationDataValidationException, SuperpowerDataValidationException, SightingDataValidationException{
        CreateSightingViewModel viewModel = sightingWebServices.getCreateSightingViewModel();
        
        model.addAttribute("viewModel", viewModel);
        model.addAttribute("commandModel", viewModel.getCreateSightingCommandModel());
        
        return "sighting/create";
    }
    
    @RequestMapping(value="/edit/{sightingId}", method=RequestMethod.GET)
    public String displayEditSighting(@PathVariable("sightingId") int sightingId, Model model) throws SightingPersistenceException, LocationPersistenceException, LocationDataValidationException, HeroPersistenceException, HeroDataValidationException, OrganizationPersistenceException, SuperpowerPersistenceException, SightingDataValidationException, OrganizationDataValidationException, SuperpowerDataValidationException{
        EditSightingViewModel viewModel = sightingWebServices.getEditSightingViewModel(sightingId);
        
        model.addAttribute("viewModel", viewModel);
        model.addAttribute("commandModel", viewModel.getEditSightingCommandModel());
        
        return "sighting/edit";
    }
    
    @RequestMapping(value="/create", method=RequestMethod.POST)
    public String createSighting(@Valid @ModelAttribute("commandModel") CreateSightingCommandModel commandModel, BindingResult result, Model model) throws SightingPersistenceException, LocationPersistenceException, LocationDataValidationException, HeroPersistenceException, HeroDataValidationException, OrganizationPersistenceException, SuperpowerPersistenceException, OrganizationDataValidationException, SuperpowerDataValidationException, SightingDataValidationException{
        if(result.hasErrors()){
            CreateSightingViewModel viewModel = sightingWebServices.getCreateSightingViewModel();

            model.addAttribute("viewModel", viewModel);
            model.addAttribute("commandModel", viewModel.getCreateSightingCommandModel());

            return "sighting/create";
            
        }else{
            sightingWebServices.saveCreateSightingCommandModel(commandModel); 
            return "redirect:/sighting/home";
        }
        
    }
    @RequestMapping(value="/edit", method=RequestMethod.POST)
    public String editSighting(@Valid @ModelAttribute("commandModel") EditSightingCommandModel commandModel, BindingResult result, Model model) throws SightingPersistenceException, LocationPersistenceException, LocationDataValidationException, HeroPersistenceException, HeroDataValidationException, OrganizationPersistenceException, SuperpowerPersistenceException, OrganizationDataValidationException, SuperpowerDataValidationException, SightingDataValidationException{
        if(result.hasErrors()){
            EditSightingViewModel viewModel = sightingWebServices.getEditSightingViewModel(commandModel.getSightingId());

            model.addAttribute("viewModel", viewModel);
            model.addAttribute("commandModel", viewModel.getEditSightingCommandModel());

            return "sighting/edit";
            
        }else{
            sightingWebServices.saveEditSightingCommandModel(commandModel);
            return "redirect:/sighting/home";
        }
        
    }
    
    @RequestMapping(value="/delete/{sightingId}", method=RequestMethod.GET)
    public String deleteSighting(@PathVariable("sightingId") int sightingId) throws HeroDataValidationException, HeroPersistenceException, SightingPersistenceException, SightingDataValidationException{
        Sighting sighting = stServiceLayer.getSighting(sightingId);
        stServiceLayer.removeSighting(sighting.getSightingId());
        
        return "redirect:/sighting/home";
    }
}
