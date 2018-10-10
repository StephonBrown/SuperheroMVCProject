/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.viewmodel.location.createlocation;

import com.sg.superherosightingsspringmvc.commandmodel.location.createlocation.CreateLocationCommandModel;
import com.sg.superherosightingsspringmvc.viewmodel.hero.herodetails.*;
import java.math.BigDecimal;

/**
 *
 * @author Stephon
 */
public class CreateLocationViewModel {
    private CreateLocationCommandModel createLocationCommandModel;

    public CreateLocationCommandModel getCreateLocationCommandModel() {
        return createLocationCommandModel;
    }

    public void setCreateLocationCommandModel(CreateLocationCommandModel createLocationCommandModel) {
        this.createLocationCommandModel = createLocationCommandModel;
    }
    
}
