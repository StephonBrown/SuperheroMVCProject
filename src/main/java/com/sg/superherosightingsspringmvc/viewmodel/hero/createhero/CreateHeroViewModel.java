/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.viewmodel.hero.createhero;

import com.sg.superherosightingsspringmvc.commandmodel.hero.createhero.CreateHeroCommandModel;
import java.util.List;

/**
 *
 * @author Stephon
 */
public class CreateHeroViewModel {
    
    private CreateHeroCommandModel createHeroCommandModel;
    private List<CreateOrganizationViewModel> orgs;
    private List<CreateSuperpowerViewModel> sps;

    public CreateHeroCommandModel getCreateHeroCommandModel() {
        return createHeroCommandModel;
    }

    public void setCreateHeroCommandModel(CreateHeroCommandModel createHeroCommandModel) {
        this.createHeroCommandModel = createHeroCommandModel;
    }

    public List<CreateOrganizationViewModel> getOrgs() {
        return orgs;
    }

    public void setOrgs(List<CreateOrganizationViewModel> orgs) {
        this.orgs = orgs;
    }

    public List<CreateSuperpowerViewModel> getSps() {
        return sps;
    }

    public void setSps(List<CreateSuperpowerViewModel> sps) {
        this.sps = sps;
    }
    
    
    
            
}
