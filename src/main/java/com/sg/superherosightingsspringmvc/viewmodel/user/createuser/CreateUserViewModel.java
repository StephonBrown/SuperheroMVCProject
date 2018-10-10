/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.viewmodel.user.createuser;

import com.sg.superherosightingsspringmvc.commandmodel.user.createuser.CreateUserCommandModel;

/**
 *
 * @author sbrown6
 */
public class CreateUserViewModel {
    private CreateUserCommandModel createUserCommandModel;

    public CreateUserCommandModel getCreateUserCommandModel() {
        return createUserCommandModel;
    }

    public void setCreateUserCommandModel(CreateUserCommandModel createUserCommandModel) {
        this.createUserCommandModel = createUserCommandModel;
    }
    
    
}
