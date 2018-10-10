/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.viewmodel.sighting.sightinghome;

import java.util.List;

/**
 *
 * @author Stephon
 */
public class HeroViewModel {
    
    private int heroId;
    private String heroName;
    private String heroDescription;
    private List<OrganizationViewModel> orgs;

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

    public List<OrganizationViewModel> getOrgs() {
        return orgs;
    }

    public void setOrgs(List<OrganizationViewModel> orgs) {
        this.orgs = orgs;
    }    
}
