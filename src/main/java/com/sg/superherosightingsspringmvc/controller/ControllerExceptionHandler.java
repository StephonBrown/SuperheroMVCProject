/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.controller;
import com.sg.superherosightingsspringmvc.dao.ErrorMessage;
import com.sg.superherosightingsspringmvc.dao.HeroPersistenceException;
import com.sg.superherosightingsspringmvc.dao.LocationPersistenceException;
import com.sg.superherosightingsspringmvc.dao.OrganizationPersistenceException;
import com.sg.superherosightingsspringmvc.dao.SightingPersistenceException;
import com.sg.superherosightingsspringmvc.service.HeroDataValidationException;
import com.sg.superherosightingsspringmvc.service.LocationDataValidationException;
import com.sg.superherosightingsspringmvc.service.OrganizationDataValidationException;
import com.sg.superherosightingsspringmvc.service.SightingDataValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Stephon
 */
@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(HeroPersistenceException.class)
    public String processHeroPersistenceException(HeroPersistenceException e,  RedirectAttributes redirectAttrs) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(e.getMessage());
        redirectAttrs.addFlashAttribute("errorMessage", errorMessage);
        return "redirect:/hero/home";
    }
    
    @ExceptionHandler(HeroDataValidationException.class)
    public String processHeroDataValidationException(HeroDataValidationException e,  RedirectAttributes redirectAttrs) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(e.getMessage());
        redirectAttrs.addFlashAttribute("errorMessage", errorMessage);
        return "redirect:/hero/home";
    }
    
    @ExceptionHandler(LocationPersistenceException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String processLocationPersistenceException(LocationPersistenceException e, Model model) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(e.getMessage());
        model.addAttribute("errorMessage", errorMessage);
        return "redirect:location/home";
    }
    
    @ExceptionHandler(LocationDataValidationException.class)
    public String processLocationDataValidationException(LocationDataValidationException e,  RedirectAttributes redirectAttrs) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(e.getMessage());
        redirectAttrs.addFlashAttribute("errorMessage", errorMessage);
        return "redirect:/location/home";
    }
    
    
    @ExceptionHandler(SightingPersistenceException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String processSightingPersistenceException(SightingPersistenceException e, RedirectAttributes redirectAttrs) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(e.getMessage());
        redirectAttrs.addFlashAttribute("errorMessage", errorMessage);
        return "redirect:/sighting/home";
    }
    
    @ExceptionHandler(SightingDataValidationException.class)
    public String processSightingDataValidationException(SightingDataValidationException e,  RedirectAttributes redirectAttrs) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(e.getMessage());
        redirectAttrs.addFlashAttribute("errorMessage", errorMessage);
        return "redirect:/sighting/home";
    }
        
    @ExceptionHandler(OrganizationPersistenceException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String processOrganizationSightingHeroPersistenceException(OrganizationPersistenceException e, Model model) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(e.getMessage());
        model.addAttribute("errorMessage", errorMessage);
        return "organization/home";
    }
    
    @ExceptionHandler(OrganizationDataValidationException.class)
    public String processOrganizationDataValidationException(OrganizationDataValidationException e,  RedirectAttributes redirectAttrs) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(e.getMessage());
        redirectAttrs.addFlashAttribute("errorMessage", errorMessage);
        return "redirect:/organization/home";
    }
}
