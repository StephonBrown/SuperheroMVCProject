/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.viewmodel.hero.herohome;

import com.sg.superherosightingsspringmvc.viewmodel.hero.edithero.*;
import com.sg.superherosightingsspringmvc.viewmodel.hero.createhero.*;

/**
 *
 * @author Stephon
 */
public class SuperpowerViewModel {
    
    private int superPowerId;
    private String superPowerName;
    private String superPowerDescription;

    public int getSuperPowerId() {
        return superPowerId;
    }

    public void setSuperPowerId(int superPowerId) {
        this.superPowerId = superPowerId;
    }

    public String getSuperPowerName() {
        return superPowerName;
    }

    public void setSuperPowerName(String superPowerName) {
        this.superPowerName = superPowerName;
    }

    public String getSuperPowerDescription() {
        return superPowerDescription;
    }

    public void setSuperPowerDescription(String superPowerDescription) {
        this.superPowerDescription = superPowerDescription;
    }
    
}
