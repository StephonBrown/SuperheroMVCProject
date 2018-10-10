/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.viewmodel.sighting.createsighting;

import com.sg.superherosightingsspringmvc.commandmodel.sighting.createsighting.CreateSightingCommandModel;
import java.util.List;

/**
 *
 * @author Stephon
 */
public class CreateSightingViewModel {
    private CreateSightingCommandModel createSightingCommandModel;
    private List<CreateHeroViewModel> heroes;
    private List<CreateLocationViewModel> locs;

    public CreateSightingCommandModel getCreateSightingCommandModel() {
        return createSightingCommandModel;
    }

    public void setCreateSightingCommandModel(CreateSightingCommandModel createSightingCommandModel) {
        this.createSightingCommandModel = createSightingCommandModel;
    }

    public List<CreateHeroViewModel> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<CreateHeroViewModel> heroes) {
        this.heroes = heroes;
    }

    public List<CreateLocationViewModel> getLocs() {
        return locs;
    }

    public void setLocs(List<CreateLocationViewModel> locs) {
        this.locs = locs;
    }
    
    
}
