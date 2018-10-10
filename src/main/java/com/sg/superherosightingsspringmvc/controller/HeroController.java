/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.controller;

import com.sg.superherosightingsspringmvc.commandmodel.hero.createhero.CreateHeroCommandModel;
import com.sg.superherosightingsspringmvc.commandmodel.hero.edithero.EditHeroCommandModel;
import com.sg.superherosightingsspringmvc.dto.Hero;
import com.sg.superherosightingsspringmvc.dao.ErrorMessage;
import com.sg.superherosightingsspringmvc.dto.Location;
import com.sg.superherosightingsspringmvc.dto.Organization;
import com.sg.superherosightingsspringmvc.dto.Sighting;
import com.sg.superherosightingsspringmvc.dto.Superpower;
import com.sg.superherosightingsspringmvc.service.HeroDataValidationException;
import com.sg.superherosightingsspringmvc.dao.HeroPersistenceException;
import com.sg.superherosightingsspringmvc.service.HeroServiceLayer;
import com.sg.superherosightingsspringmvc.service.LocationDataValidationException;
import com.sg.superherosightingsspringmvc.dao.LocationPersistenceException;
import com.sg.superherosightingsspringmvc.service.LocationServiceLayer;
import com.sg.superherosightingsspringmvc.service.OrganizationDataValidationException;
import com.sg.superherosightingsspringmvc.dao.OrganizationPersistenceException;
import com.sg.superherosightingsspringmvc.service.OrganizationServiceLayer;
import com.sg.superherosightingsspringmvc.service.SightingDataValidationException;
import com.sg.superherosightingsspringmvc.dao.SightingPersistenceException;
import com.sg.superherosightingsspringmvc.service.SightingServiceLayer;
import com.sg.superherosightingsspringmvc.service.SuperpowerDataValidationException;
import com.sg.superherosightingsspringmvc.dao.SuperpowerPersistenceException;
import com.sg.superherosightingsspringmvc.service.SuperpowerServiceLayer;
import com.sg.superherosightingsspringmvc.viewmodel.hero.createhero.CreateHeroViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.hero.edithero.EditHeroViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.hero.herodetails.HeroDetailsViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.hero.herohome.HeroHomeViewModel;
import com.sg.superherosightingsspringmvc.webservice.interfaces.HeroWebServices;
import java.util.ArrayList;
import java.util.List;
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
@RequestMapping(value="/hero")
public class HeroController {
    
    @Inject
    HeroWebServices heroWebServices;
    
    @Inject
    HeroServiceLayer heServiceLayer;
    
    @Inject
    SightingServiceLayer stServiceLayer;
    
    @RequestMapping(value="/home", method=RequestMethod.GET)
    public String returnHeroHome(@RequestParam(required = false) Integer offset, Model model) throws SightingPersistenceException, LocationPersistenceException, LocationDataValidationException, HeroPersistenceException, HeroDataValidationException, OrganizationPersistenceException, SuperpowerPersistenceException{
        
        HeroHomeViewModel viewModel = heroWebServices.getHeroHomeViewModel(5,offset,5);
        model.addAttribute("viewModel", viewModel);

        return "hero/home";
    }
    
    @RequestMapping(value="/details/{heroId}", method=RequestMethod.GET)
    public String displayHeroDetails(@PathVariable("heroId") int heroId, Model model) throws SightingPersistenceException, LocationPersistenceException, LocationDataValidationException, HeroPersistenceException, HeroDataValidationException, OrganizationPersistenceException, SuperpowerPersistenceException, OrganizationDataValidationException, SuperpowerDataValidationException{
        
        HeroDetailsViewModel viewModel = heroWebServices.getHeroDetailsViewModel(heroId);
        model.addAttribute("viewModel", viewModel);
        
        return "hero/details";
    }
    
    @RequestMapping(value="/create", method=RequestMethod.GET)
    public String displayCreateHero( Model model) throws SightingPersistenceException, LocationPersistenceException, LocationDataValidationException, HeroPersistenceException, HeroDataValidationException, OrganizationPersistenceException, SuperpowerPersistenceException, OrganizationDataValidationException, SuperpowerDataValidationException{
        CreateHeroViewModel viewModel = heroWebServices.getCreateHeroViewModel();
        
        model.addAttribute("viewModel", viewModel);
        model.addAttribute("commandModel", viewModel.getCreateHeroCommandModel());
        return "hero/create";
    }
    
    @RequestMapping(value="/edit/{heroId}", method=RequestMethod.GET)
    public String displayEditHero(@PathVariable("heroId") int heroId, Model model) throws SightingPersistenceException, LocationPersistenceException, LocationDataValidationException, HeroPersistenceException, HeroDataValidationException, OrganizationPersistenceException, SuperpowerPersistenceException, OrganizationDataValidationException, SuperpowerDataValidationException{
        EditHeroViewModel viewModel = heroWebServices.getEditHeroViewModel(heroId);
        
        model.addAttribute("viewModel", viewModel);
        model.addAttribute("commandModel", viewModel.getEditHeroCommandModel());
        
        return "hero/edit";
    }
    
    @RequestMapping(value="/create", method=RequestMethod.POST)
    public String createHero( @ModelAttribute("commandModel") @Valid CreateHeroCommandModel commandModel, BindingResult result, Model model) throws SightingPersistenceException, LocationPersistenceException, LocationDataValidationException, HeroPersistenceException, HeroDataValidationException, OrganizationPersistenceException, SuperpowerPersistenceException, OrganizationDataValidationException, SuperpowerDataValidationException{
        if(result.hasErrors()){
            CreateHeroViewModel viewModel = heroWebServices.getCreateHeroViewModel();
        
            model.addAttribute("viewModel", viewModel);
            model.addAttribute("commandModel", commandModel);
            return "hero/create";
            
        }else{
            heroWebServices.saveCreateHeroCommandModel(commandModel);
            return "redirect:/hero/home";
        }
    }
    
    @RequestMapping(value="/edit", method=RequestMethod.POST)
    public String editHero(@Valid @ModelAttribute("commandModel") EditHeroCommandModel commandModel, BindingResult result, Model model) throws SightingPersistenceException, LocationPersistenceException, LocationDataValidationException, HeroPersistenceException, HeroDataValidationException, OrganizationPersistenceException, SuperpowerPersistenceException, OrganizationDataValidationException, SuperpowerDataValidationException, SightingDataValidationException{
        if(result.hasErrors()){
            EditHeroViewModel viewModel = heroWebServices.getEditHeroViewModel(commandModel.getHeroId());

            model.addAttribute("viewModel", viewModel);
            model.addAttribute("commandModel", commandModel);
            return "hero/edit";
            
        }else{
            heroWebServices.saveEditHeroCommandModel(commandModel);
            return "redirect:/hero/home";
        }
    }
    
    @RequestMapping(value="/delete/{heroId}", method=RequestMethod.GET)
    public String deleteHero(@PathVariable("heroId") int heroId) throws HeroDataValidationException, HeroPersistenceException, SightingPersistenceException, SightingDataValidationException{
        Hero hero = heServiceLayer.getHero(heroId);
        List<Sighting> sights = stServiceLayer.getHeroSightings(hero, Integer.MAX_VALUE, 0);
        for(Sighting currentst: sights){
            stServiceLayer.removeSighting(currentst.getSightingId());
        }
        heServiceLayer.removeHero(heroId);
        
        return "redirect:/hero/home";
    }
}
