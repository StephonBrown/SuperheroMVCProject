/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.viewmodel.organization.createorganization;

import com.sg.superherosightingsspringmvc.commandmodel.organization.createorganization.CreateOrganizationCommandModel;
import java.util.List;


/**
 *
 * @author Stephon
 */
public class CreateOrganizationViewModel {
   private CreateOrganizationCommandModel createOrganizationCommandModel;
   private List<CreateLocationViewModel> locs;

    public CreateOrganizationCommandModel getCreateOrganizationCommandModel() {
        return createOrganizationCommandModel;
    }

    public void setCreateOrganizationCommandModel(CreateOrganizationCommandModel createOrganizationCommandModel) {
        this.createOrganizationCommandModel = createOrganizationCommandModel;
    }

    public List<CreateLocationViewModel> getLocs() {
        return locs;
    }

    public void setLocs(List<CreateLocationViewModel> locs) {
        this.locs = locs;
    }
   
}
