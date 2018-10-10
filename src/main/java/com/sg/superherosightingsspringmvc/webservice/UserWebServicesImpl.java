/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.webservice;

import com.sg.superherosightingsspringmvc.commandmodel.user.createuser.CreateUserCommandModel;
import com.sg.superherosightingsspringmvc.commandmodel.user.edituser.EditUserCommandModel;
import com.sg.superherosightingsspringmvc.dao.UserPersistenceException;
import com.sg.superherosightingsspringmvc.dto.User;
import com.sg.superherosightingsspringmvc.service.UserDataValidationException;
import com.sg.superherosightingsspringmvc.service.UserServiceLayer;
import com.sg.superherosightingsspringmvc.viewmodel.user.createuser.CreateUserViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.user.edituser.EditUserViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.user.userhome.UserHomeViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.user.userhome.UserViewModel;
import com.sg.superherosightingsspringmvc.webservice.interfaces.UserWebServices;
import com.sg.superherosightingsspringmvc.webservice.utilities.PageUtilities;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author sbrown6
 */
public class UserWebServicesImpl implements UserWebServices{
    
    private UserServiceLayer userServiceLayer;
    PasswordEncoder encoder;

    @Inject
    public UserWebServicesImpl(UserServiceLayer userServiceLayer, PasswordEncoder encoder) {
        this.userServiceLayer = userServiceLayer;
        this.encoder = encoder;
    }

    public UserWebServicesImpl() {
    }
    
    @Override
    public UserHomeViewModel getUSerHomeViewModel(Integer limit, Integer offset, Integer pageNumbers) {
            if(limit ==null){
                limit=5;
            }
            if(offset==null){
                offset=0;
            }
            if(pageNumbers==null){
                pageNumbers=5;
            }
            
            UserHomeViewModel userHomeViewModel = new UserHomeViewModel();
  
            List<User> users = userServiceLayer.getAllUsers(limit, offset);
            
            Integer currentPage = PageUtilities.calculatePageNumber(limit,offset);
            List<Integer> pages = PageUtilities.getPageNumbers(currentPage, pageNumbers);
            
            userHomeViewModel.setUsers(translateUser(users));
            userHomeViewModel.setPageNumber(currentPage);
            userHomeViewModel.setPageNumbers(pages);
            
            return userHomeViewModel;
    }

    @Override
    public CreateUserViewModel getCreateUserViewModel() {
        CreateUserViewModel createUserViewModel = new CreateUserViewModel();
        CreateUserCommandModel createUserCommandModel = new CreateUserCommandModel();
        
        createUserViewModel.setCreateUserCommandModel(createUserCommandModel);
        
        return createUserViewModel;
    }

    @Override
    public EditUserViewModel getEditUserViewModel(int user_id) throws UserPersistenceException {
         EditUserViewModel editUserViewModel = new EditUserViewModel();
         editUserViewModel.setOldUsername(userServiceLayer.getUser(user_id).getUsername());
                 
         User user = userServiceLayer.getUser(user_id);
         EditUserCommandModel editUserCommandModel = new EditUserCommandModel();
         editUserCommandModel.setId(user.getId());
         editUserCommandModel.setUsername(user.getUsername());
         editUserCommandModel.setPassword(user.getPassword());
         
         ArrayList<String> authorities = new ArrayList();
         
         for(String authority: user.getAuthorities()){
             authorities.add(authority);
         }
         
         editUserCommandModel.setAuthorities(authorities);
         
         editUserViewModel.setEditUserCommandModel(editUserCommandModel);
         
         return editUserViewModel;
         
    }

    @Override
    public User saveCreateUserCommandModel(CreateUserCommandModel createUserCommandModel) throws UserPersistenceException, UserDataValidationException {
        User user = new User();
        user.setUsername(createUserCommandModel.getUsername());
        String clearPw = createUserCommandModel.getPassword();
        String hashPw = encoder.encode(clearPw);
        user.setPassword(hashPw);
        
        ArrayList<String> authorities = new ArrayList();
         
        for(String authority: createUserCommandModel.getAuthorities()){
             authorities.add(authority);
         }
        user.setAuthorities(authorities);
        
        return userServiceLayer.createUser(user);
    }

    @Override
    public User saveEditUserCommandModel(EditUserCommandModel editUserCommandModel, String oldUsername) throws UserPersistenceException, UserDataValidationException {
        User user = userServiceLayer.getUser(editUserCommandModel.getId());
        
        user.setUsername(editUserCommandModel.getUsername());
        String clearPw = editUserCommandModel.getPassword();
        String hashPw = encoder.encode(clearPw);
        user.setPassword(hashPw);
        if ( editUserCommandModel.getAuthorities().contains("ROLE_ADMIN")) {
            user.addAuthority("ROLE_ADMIN");
        }
        user.addAuthority("ROLE_USER");
        userServiceLayer.editUser(user, oldUsername);
        
        return user;       
    }
    
    private UserViewModel translateUser(User user) {
        UserViewModel userViewModel = new UserViewModel();
        userViewModel.setId(user.getId());
        userViewModel.setUsername(user.getUsername());
        userViewModel.setPassword(user.getPassword());
        
        ArrayList<String> authorities = new ArrayList();
        
        user.getAuthorities().forEach((authority) -> {
            authorities.add(authority);
        });
        
        userViewModel.setAuthorities(authorities);
        
        return userViewModel;  
    }
    
    private List<UserViewModel> translateUser(List<User> users) {
        List<UserViewModel> userModels = new ArrayList();
        
        for(User currentUser: users){
            UserViewModel currentModel = translateUser(currentUser);
            userModels.add(currentModel);
        }
        
        return userModels;  
    }
}
