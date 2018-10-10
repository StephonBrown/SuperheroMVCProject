/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.controller;

/**
 *
 * @author sbrown6
 */
import com.sg.superherosightingsspringmvc.commandmodel.user.createuser.CreateUserCommandModel;
import com.sg.superherosightingsspringmvc.commandmodel.user.edituser.EditUserCommandModel;
import com.sg.superherosightingsspringmvc.dao.UserDao;
import com.sg.superherosightingsspringmvc.dao.UserPersistenceException;
import com.sg.superherosightingsspringmvc.dto.User;
import com.sg.superherosightingsspringmvc.service.UserDataValidationException;
import com.sg.superherosightingsspringmvc.service.UserServiceLayer;
import com.sg.superherosightingsspringmvc.viewmodel.user.createuser.CreateUserViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.user.edituser.EditUserViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.user.userhome.UserHomeViewModel;
import com.sg.superherosightingsspringmvc.webservice.interfaces.UserWebServices;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserWebServices userWebServices;
    private UserServiceLayer userServiceLayer;
    private PasswordEncoder encoder;
    
    @Inject
    public UserController(UserWebServices userWebServices, PasswordEncoder encoder, UserServiceLayer userServiceLayer) {
        this.userWebServices = userWebServices;
        this.encoder = encoder;
        this.userServiceLayer =  userServiceLayer;
    }

    // This endpoint retrieves all users from the database and puts the
    // List of users on the model
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String displayUserHome(@RequestParam(required = false) Integer offset, Model model) {
        UserHomeViewModel viewModel = userWebServices.getUSerHomeViewModel(5, offset, 5);
        model.addAttribute("viewModel", viewModel);
        return "user/home";
    }
    
    @RequestMapping(value="/create", method=RequestMethod.GET)
    public String displayCreateUser( Model model){
        CreateUserViewModel viewModel = userWebServices.getCreateUserViewModel();
        
        model.addAttribute("viewModel", viewModel);
        model.addAttribute("commandModel", viewModel.getCreateUserCommandModel());
        
        return "user/create";
    }
    
    @RequestMapping(value="/edit/{userId}", method=RequestMethod.GET)
    public String displayEditUser(@PathVariable("userId") int userId, Model model) throws UserPersistenceException{
        EditUserViewModel viewModel = userWebServices.getEditUserViewModel(userId);
        viewModel.setOldUsername(userServiceLayer.getUser(userId).getUsername());
        model.addAttribute("viewModel", viewModel);
        model.addAttribute("commandModel", viewModel.getEditUserCommandModel());
        
        return "user/edit";
    }
    
    @RequestMapping(value="/create", method=RequestMethod.POST)
    public String createUser(@Valid @ModelAttribute("commandModel") CreateUserCommandModel commandModel, BindingResult result, Model model) throws UserPersistenceException, UserDataValidationException {
        if(result.hasErrors()){
            CreateUserViewModel viewModel = userWebServices.getCreateUserViewModel();

            model.addAttribute("viewModel", viewModel);
            model.addAttribute("commandModel", viewModel.getCreateUserCommandModel());

            return "user/create";
            
        }else{
            userWebServices.saveCreateUserCommandModel(commandModel); 
            return "redirect:/user/home";
        }
        
    }
    @RequestMapping(value="/edit", method=RequestMethod.POST)
    public String editUser(@Valid @ModelAttribute("commandModel") EditUserCommandModel commandModel, BindingResult result, Model model) throws UserPersistenceException, UserDataValidationException{
        if(result.hasErrors()){
            EditUserViewModel viewModel = userWebServices.getEditUserViewModel(commandModel.getId());
            viewModel.setOldUsername(userServiceLayer.getUser(commandModel.getId()).getUsername());
            model.addAttribute("viewModel", viewModel);
            model.addAttribute("commandModel", viewModel.getEditUserCommandModel());

            return "user/edit";
            
        }else{
            EditUserViewModel viewModel = userWebServices.getEditUserViewModel(commandModel.getId());
            userWebServices.saveEditUserCommandModel(commandModel, viewModel.getOldUsername());
            return "redirect:/user/home";
        }
        
    }
    
    @RequestMapping(value="/delete/{userId}", method=RequestMethod.GET)
    public String deleteSighting(@PathVariable("userId") int userId) throws UserPersistenceException, UserDataValidationException{
        User user = userServiceLayer.getUser(userId);
        userServiceLayer.deleteUser(user.getId());
        
        return "redirect:/user/home";
    }
}
