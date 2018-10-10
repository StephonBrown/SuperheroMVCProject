/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.viewmodel.location.editlocation;

import com.sg.superherosightingsspringmvc.commandmodel.location.editlocation.EditLocationCommandModel;


/**
 *
 * @author Stephon
 */
public class EditLocationViewModel {
    private EditLocationCommandModel editLocationCommandModel;

    public EditLocationCommandModel getEditLocationCommandModel() {
        return editLocationCommandModel;
    }

    public void setEditLocationCommandModel(EditLocationCommandModel editLocationCommandModel) {
        this.editLocationCommandModel = editLocationCommandModel;
    }

}
