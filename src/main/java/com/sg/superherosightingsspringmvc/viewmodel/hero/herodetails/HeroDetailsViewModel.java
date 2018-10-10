/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.viewmodel.hero.herodetails;

import com.sg.superherosightingsspringmvc.viewmodel.hero.herohome.*;
import java.util.List;

/**
 *
 * @author Stephon
 */
public class HeroDetailsViewModel {
    
    private int heroId;
    private String heroName;
    private String heroDescription;
    private List<String> orgNames;
    private List<String> spNames;
    private List<String> locNames;

    public int getHeroId() {
        return heroId;
    }

    public void setHeroId(int heroId) {
        this.heroId = heroId;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public String getHeroDescription() {
        return heroDescription;
    }

    public void setHeroDescription(String heroDescription) {
        this.heroDescription = heroDescription;
    }

    public List<String> getOrgNames() {
        return orgNames;
    }

    public void setOrgNames(List<String> orgNames) {
        this.orgNames = orgNames;
    }

    public List<String> getSpNames() {
        return spNames;
    }

    public void setSpNames(List<String> spNames) {
        this.spNames = spNames;
    }

    public List<String> getLocNames() {
        return locNames;
    }

    public void setLocNames(List<String> locNames) {
        this.locNames = locNames;
    }

    
}
