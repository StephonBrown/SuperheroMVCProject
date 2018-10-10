/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.controller;

import com.sg.superherosightingsspringmvc.commandmodel.organization.createorganization.CreateOrganizationCommandModel;
import com.sg.superherosightingsspringmvc.commandmodel.organization.editorganization.EditOrganizationCommandModel;
import com.sg.superherosightingsspringmvc.dto.Member;
import com.sg.superherosightingsspringmvc.dto.Organization;
import com.sg.superherosightingsspringmvc.service.HeroDataValidationException;
import com.sg.superherosightingsspringmvc.dao.HeroPersistenceException;
import com.sg.superherosightingsspringmvc.service.HeroServiceLayer;
import com.sg.superherosightingsspringmvc.service.LocationDataValidationException;
import com.sg.superherosightingsspringmvc.dao.LocationPersistenceException;
import com.sg.superherosightingsspringmvc.service.LocationServiceLayer;
import com.sg.superherosightingsspringmvc.service.MemberDataValidationException;
import com.sg.superherosightingsspringmvc.dao.MemberPersistenceException;
import com.sg.superherosightingsspringmvc.service.MemberServiceLayer;
import com.sg.superherosightingsspringmvc.service.OrganizationDataValidationException;
import com.sg.superherosightingsspringmvc.dao.OrganizationPersistenceException;
import com.sg.superherosightingsspringmvc.service.OrganizationServiceLayer;
import com.sg.superherosightingsspringmvc.dao.SightingPersistenceException;
import com.sg.superherosightingsspringmvc.dao.SuperpowerPersistenceException;
import com.sg.superherosightingsspringmvc.viewmodel.organization.createorganization.CreateOrganizationViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.organization.editorganization.EditOrganizationViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.organization.organizationdetails.OrganizationDetailsViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.organization.organizationhome.OrganizationHomeViewModel;
import com.sg.superherosightingsspringmvc.webservice.interfaces.OrganizationWebServices;
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
@RequestMapping(value="/organization")
public class OrganizationController {
    @Inject
    OrganizationWebServices orgWebServices;
    
    @Inject
    OrganizationServiceLayer orgServiceLayer;
    
    @Inject
    LocationServiceLayer locServiceLayer;
    
    @Inject
    MemberServiceLayer memServiceLayer;
    
    @Inject
    HeroServiceLayer heServiceLayer;

    
    @RequestMapping(value="/home", method=RequestMethod.GET)
    public String returnOrganizationHome(@RequestParam(required = false) Integer offset, Model model) throws LocationPersistenceException, LocationDataValidationException, OrganizationPersistenceException{
        OrganizationHomeViewModel viewModel = orgWebServices.getOrganizationHomeViewModel(5,offset,5);
        model.addAttribute("viewModel", viewModel);
        return "organization/home";
    }
    @RequestMapping(value="/details/{organizationId}", method=RequestMethod.GET)
    public String displayOrganizationDetails(@PathVariable("organizationId") int organizationId, Model model) throws LocationPersistenceException, LocationDataValidationException, OrganizationPersistenceException, OrganizationDataValidationException, MemberPersistenceException{
        OrganizationDetailsViewModel viewModel = orgWebServices.getOrganizationDetailsViewModel(organizationId);
        model.addAttribute("viewModel", viewModel);
        
        return "organization/details";
    }
    
    @RequestMapping(value="/create", method=RequestMethod.GET)
    public String displayCreateOrganization( Model model) throws SightingPersistenceException, LocationPersistenceException, LocationDataValidationException, HeroPersistenceException, HeroDataValidationException, OrganizationPersistenceException, SuperpowerPersistenceException, OrganizationDataValidationException{
        CreateOrganizationViewModel viewModel = orgWebServices.getCreateOrganizationViewModel();
        model.addAttribute("viewModel", viewModel);
        model.addAttribute("commandModel", viewModel.getCreateOrganizationCommandModel());
        
        return "organization/create";
    }
    
    @RequestMapping(value="/edit/{organizationId}", method=RequestMethod.GET)
    public String displayEditOrganization(@PathVariable("organizationId") int organizationId, Model model) throws SightingPersistenceException, LocationPersistenceException, LocationDataValidationException, HeroPersistenceException, HeroDataValidationException, OrganizationPersistenceException, SuperpowerPersistenceException, OrganizationDataValidationException{
        EditOrganizationViewModel viewModel = orgWebServices.getEditOrganizationViewModel(organizationId);
        model.addAttribute("viewModel", viewModel);
        model.addAttribute("commandModel", viewModel.getEditOrganizationCommandModel());
        
        return "organization/edit";
    }
    
    @RequestMapping(value="/create", method=RequestMethod.POST)
    public String createOrganization(@Valid @ModelAttribute("commandModel") CreateOrganizationCommandModel commandModel, BindingResult result, Model model) throws OrganizationPersistenceException, OrganizationDataValidationException, LocationPersistenceException, LocationDataValidationException{
        if(result.hasErrors()){
            CreateOrganizationViewModel viewModel = orgWebServices.getCreateOrganizationViewModel();
            model.addAttribute("viewModel", viewModel);
            model.addAttribute("commandModel", viewModel.getCreateOrganizationCommandModel());
        
            return "organization/create";
        }else{
            orgWebServices.saveCreateOrganizationCommandModel(commandModel);
            return "redirect:/organization/home";
        }
        
    }
    @RequestMapping(value="/edit", method=RequestMethod.POST)
    public String editOrganization(@Valid @ModelAttribute("commandModel") EditOrganizationCommandModel commandModel, BindingResult result, Model model) throws LocationPersistenceException, LocationDataValidationException, OrganizationPersistenceException, OrganizationDataValidationException{
        if(result.hasErrors()){
            EditOrganizationViewModel viewModel = orgWebServices.getEditOrganizationViewModel(commandModel.getOrganizationId());
            model.addAttribute("viewModel", viewModel);
            model.addAttribute("commandModel", viewModel.getEditOrganizationCommandModel());
            
            return "organization/edit";   
        }else{
            orgWebServices.saveEditOrganizationCommandModel(commandModel);
            return "redirect:/organization/home";
        }
        
    }
    @RequestMapping(value="/delete/{organizationId}", method=RequestMethod.GET)
    public String deleteOrganization(@PathVariable("organizationId") int organizationId) throws OrganizationPersistenceException, OrganizationDataValidationException, MemberPersistenceException, MemberDataValidationException {
        Organization organization = orgServiceLayer.getOrganization(organizationId);
        
        for(Member currentMember: memServiceLayer.findAllMembersAtOrganization(organization, Integer.MAX_VALUE, 0)){
                memServiceLayer.removeMember(currentMember.getMemberId());
        }
        
        orgServiceLayer.removeOrganization(organizationId);
        
        return "redirect:/organization/home";
    }
}
