/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.viewmodel.hero.edithero;

import com.sg.superherosightingsspringmvc.viewmodel.hero.createhero.*;
import com.sg.superherosightingsspringmvc.commandmodel.hero.createhero.CreateHeroCommandModel;
import com.sg.superherosightingsspringmvc.commandmodel.hero.edithero.EditHeroCommandModel;
import java.util.List;

/**
 *
 * @author Stephon
 */
public class EditHeroViewModel {
    
    private EditHeroCommandModel editHeroCommandModel;
    private List<EditOrganizationViewModel> orgs;
    private List<EditSuperpowerViewModel> sps;

    public EditHeroCommandModel getEditHeroCommandModel() {
        return editHeroCommandModel;
    }

    public void setEditHeroCommandModel(EditHeroCommandModel editHeroCommandModel) {
        this.editHeroCommandModel = editHeroCommandModel;
    }

    public List<EditOrganizationViewModel> getOrgs() {
        return orgs;
    }

    public void setOrgs(List<EditOrganizationViewModel> orgs) {
        this.orgs = orgs;
    }

    public List<EditSuperpowerViewModel> getSps() {
        return sps;
    }

    public void setSps(List<EditSuperpowerViewModel> sps) {
        this.sps = sps;
    }
            
}
