/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.viewmodel.organization.editorganization;


import com.sg.superherosightingsspringmvc.commandmodel.organization.editorganization.EditOrganizationCommandModel;
import java.util.List;


/**
 *
 * @author Stephon
 */
public class EditOrganizationViewModel {
   private EditOrganizationCommandModel editOrganizationCommandModel;
   private List<EditLocationViewModel> locs;

    public EditOrganizationCommandModel getEditOrganizationCommandModel() {
        return editOrganizationCommandModel;
    }

    public void setEditOrganizationCommandModel(EditOrganizationCommandModel editOrganizationCommandModel) {
        this.editOrganizationCommandModel = editOrganizationCommandModel;
    }

    public List<EditLocationViewModel> getLocs() {
        return locs;
    }

    public void setLocs(List<EditLocationViewModel> locs) {
        this.locs = locs;
    }


   
}
