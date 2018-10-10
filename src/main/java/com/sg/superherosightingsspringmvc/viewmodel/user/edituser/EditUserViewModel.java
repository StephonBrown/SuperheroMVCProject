/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.viewmodel.user.edituser;

import com.sg.superherosightingsspringmvc.commandmodel.user.edituser.EditUserCommandModel;

/**
 *
 * @author sbrown6
 */
public class EditUserViewModel {
    private EditUserCommandModel editUserCommandModel;
    private String oldUsername;

    public String getOldUsername() {
        return oldUsername;
    }

    public void setOldUsername(String oldUsername) {
        this.oldUsername = oldUsername;
    }

    public EditUserCommandModel getEditUserCommandModel() {
        return editUserCommandModel;
    }

    public void setEditUserCommandModel(EditUserCommandModel editUserCommandModel) {
        this.editUserCommandModel = editUserCommandModel;
    }
    
}
