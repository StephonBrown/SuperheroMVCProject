/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.controller;


import com.sg.superherosightingsspringmvc.commandmodel.location.createlocation.CreateLocationCommandModel;
import com.sg.superherosightingsspringmvc.commandmodel.location.editlocation.EditLocationCommandModel;
import com.sg.superherosightingsspringmvc.dto.Location;
import com.sg.superherosightingsspringmvc.dto.Member;
import com.sg.superherosightingsspringmvc.dto.Organization;
import com.sg.superherosightingsspringmvc.dto.Sighting;
import com.sg.superherosightingsspringmvc.service.HeroDataValidationException;
import com.sg.superherosightingsspringmvc.dao.HeroPersistenceException;
import com.sg.superherosightingsspringmvc.service.LocationDataValidationException;
import com.sg.superherosightingsspringmvc.dao.LocationPersistenceException;
import com.sg.superherosightingsspringmvc.service.LocationServiceLayer;
import com.sg.superherosightingsspringmvc.service.MemberDataValidationException;
import com.sg.superherosightingsspringmvc.dao.MemberPersistenceException;
import com.sg.superherosightingsspringmvc.service.MemberServiceLayer;
import com.sg.superherosightingsspringmvc.service.OrganizationDataValidationException;
import com.sg.superherosightingsspringmvc.dao.OrganizationPersistenceException;
import com.sg.superherosightingsspringmvc.service.OrganizationServiceLayer;
import com.sg.superherosightingsspringmvc.service.SightingDataValidationException;
import com.sg.superherosightingsspringmvc.dao.SightingPersistenceException;
import com.sg.superherosightingsspringmvc.service.SightingServiceLayer;
import com.sg.superherosightingsspringmvc.service.SuperpowerDataValidationException;
import com.sg.superherosightingsspringmvc.dao.SuperpowerPersistenceException;
import com.sg.superherosightingsspringmvc.viewmodel.location.createlocation.CreateLocationViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.location.editlocation.EditLocationViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.location.locationdetails.LocationDetailsViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.location.locationhome.LocationHomeViewModel;
import com.sg.superherosightingsspringmvc.webservice.interfaces.LocationWebServices;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
@RequestMapping(value="/location")
public class LocationController {
    
    @Inject
    LocationWebServices locWebServices;
     
    @Inject
    LocationServiceLayer locServiceLayer;
    
    @Inject
    OrganizationServiceLayer orgServiceLayer;
    
    @Inject
    SightingServiceLayer stServiceLayer;
    
    @Inject
    MemberServiceLayer memServiceLayer;
    
    
    @RequestMapping(value="/home", method=RequestMethod.GET)
    public String returnLocationHome(@RequestParam(required = false) Integer offset, Model model) throws SightingPersistenceException, LocationPersistenceException, LocationDataValidationException, HeroPersistenceException, HeroDataValidationException{
        LocationHomeViewModel viewModel = locWebServices.getLocationHomeViewModel(5, offset, 5);
        model.addAttribute("viewModel", viewModel);
        return "location/home";
    }
    @RequestMapping(value="/details/{locationId}", method=RequestMethod.GET)
    public String displayLocationDetails(@PathVariable("locationId") int locationId, Model model) throws LocationPersistenceException, LocationDataValidationException{
        LocationDetailsViewModel viewModel = locWebServices.getLocationDetailsViewMode(locationId);
        model.addAttribute("viewModel", viewModel);
        return "location/details";
    }
    
    @RequestMapping(value="/create", method=RequestMethod.GET)
    public String displayCreateLocation( Model model) throws LocationPersistenceException, LocationDataValidationException{
        CreateLocationViewModel viewModel = locWebServices.getCreateLocationViewModel();
        
        model.addAttribute("viewModel", viewModel);
        model.addAttribute("commandModel", viewModel.getCreateLocationCommandModel());
        
        return "location/create";
    }
    
    @RequestMapping(value="/edit/{locationId}", method=RequestMethod.GET)
    public String displayEditLocation(@PathVariable("locationId") int locationId, Model model) throws SightingPersistenceException, LocationPersistenceException, LocationDataValidationException, HeroPersistenceException, HeroDataValidationException, OrganizationPersistenceException, SuperpowerPersistenceException{
        EditLocationViewModel viewModel = locWebServices.getEditLocationViewModel(locationId);
        
        model.addAttribute("viewModel", viewModel);
        model.addAttribute("commandModel", viewModel.getEditLocationCommandModel());
        
        return "location/edit";
    }
    
    @RequestMapping(value="/create", method=RequestMethod.POST)
    public String createLocation(@Valid @ModelAttribute("commandModel") CreateLocationCommandModel commandModel, BindingResult result, Model model) throws SightingPersistenceException, LocationPersistenceException, LocationDataValidationException, HeroPersistenceException, HeroDataValidationException, OrganizationPersistenceException, SuperpowerPersistenceException, OrganizationDataValidationException, SuperpowerDataValidationException{
        if(result.hasErrors()){
            CreateLocationViewModel viewModel = locWebServices.getCreateLocationViewModel();
        
            model.addAttribute("viewModel", viewModel);
            model.addAttribute("commandModel", viewModel.getCreateLocationCommandModel());
            return "location/create";
        }else{
            locWebServices.saveCreateLocationCommandModel(commandModel);
            return "redirect:/location/home";
        }  
    }
    @RequestMapping(value="/edit", method=RequestMethod.POST)
    public String editLocation(@Valid @ModelAttribute("commandModel") EditLocationCommandModel commandModel, BindingResult result, Model model) throws LocationPersistenceException, LocationDataValidationException {
        if(result.hasErrors()){
            EditLocationViewModel viewModel = locWebServices.getEditLocationViewModel(commandModel.getLocationId());
        
            model.addAttribute("viewModel", viewModel);
            model.addAttribute("commandModel", viewModel.getEditLocationCommandModel());
            return "location/edit";
        }else{
            locWebServices.SaveEditLocationCommandModel(commandModel);
            return "redirect:/location/home";
        }
        
    }
    
    @RequestMapping(value="/delete/{locationId}", method=RequestMethod.GET)
    public String deleteLocation(@PathVariable("locationId") int locationId) throws OrganizationPersistenceException, SightingPersistenceException, LocationPersistenceException, LocationDataValidationException, OrganizationDataValidationException, SightingDataValidationException, MemberPersistenceException, MemberDataValidationException{
        Location location = locServiceLayer.getLocation(locationId);
        
        List<Organization> orgs = orgServiceLayer.getOrganizationsByLocationId(location, Integer.MAX_VALUE, 0);
        List<Sighting> sights =  stServiceLayer.getSightingsByLocation(location,Integer.MAX_VALUE, 0);
        
        for(Organization currentOrg: orgs){
            for(Member currentMember: memServiceLayer.findAllMembersAtOrganization(currentOrg,Integer.MAX_VALUE,0)){
                memServiceLayer.removeMember(currentMember.getMemberId());
            }
            orgServiceLayer.removeOrganization(currentOrg.getOrganizationId());      
        }
        for(Sighting currentst: sights){
            stServiceLayer.removeSighting(currentst.getSightingId());
        }
        
        locServiceLayer.removeLocation(locationId);
        
        return "redirect:/location/home";
    }
}
