/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.webservice.interfaces;

import com.sg.superherosightingsspringmvc.commandmodel.hero.createhero.CreateHeroCommandModel;
import com.sg.superherosightingsspringmvc.commandmodel.hero.edithero.EditHeroCommandModel;
import com.sg.superherosightingsspringmvc.commandmodel.user.createuser.CreateUserCommandModel;
import com.sg.superherosightingsspringmvc.commandmodel.user.edituser.EditUserCommandModel;
import com.sg.superherosightingsspringmvc.dao.UserPersistenceException;
import com.sg.superherosightingsspringmvc.dto.User;
import com.sg.superherosightingsspringmvc.service.UserDataValidationException;
import com.sg.superherosightingsspringmvc.viewmodel.user.createuser.CreateUserViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.user.edituser.EditUserViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.user.userhome.UserHomeViewModel;

/**
 *
 * @author sbrown6
 */
public interface UserWebServices {
    UserHomeViewModel getUSerHomeViewModel(Integer limit, Integer offset, Integer pageNumbers);
    
    CreateUserViewModel getCreateUserViewModel();
    
    EditUserViewModel getEditUserViewModel(int user_id) throws UserPersistenceException;
    
    User saveCreateUserCommandModel(CreateUserCommandModel createUserCommandModel) throws UserPersistenceException, UserDataValidationException;
    User saveEditUserCommandModel(EditUserCommandModel editUserCommandModel,  String oldUsername) throws UserPersistenceException, UserDataValidationException;
}
