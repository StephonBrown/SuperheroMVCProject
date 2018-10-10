/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.viewmodel.sighting.editsighting;

import com.sg.superherosightingsspringmvc.viewmodel.sighting.createsighting.*;
import com.sg.superherosightingsspringmvc.commandmodel.sighting.createsighting.CreateSightingCommandModel;
import com.sg.superherosightingsspringmvc.commandmodel.sighting.editsighting.EditSightingCommandModel;
import java.util.List;

/**
 *
 * @author Stephon
 */
public class EditSightingViewModel {
    private EditSightingCommandModel editSightingCommandModel;
    private List<EditHeroViewModel> heroes;
    private List<EditLocationViewModel> locs;

    public EditSightingCommandModel getEditSightingCommandModel() {
        return editSightingCommandModel;
    }

    public void setCreateSightingCommandModel(EditSightingCommandModel createSightingCommandModel) {
        this.editSightingCommandModel = createSightingCommandModel;
    }

    public List<EditHeroViewModel> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<EditHeroViewModel> heroes) {
        this.heroes = heroes;
    }

    public List<EditLocationViewModel> getLocs() {
        return locs;
    }

    public void setLocs(List<EditLocationViewModel> locs) {
        this.locs = locs;
    }
    
    
}
