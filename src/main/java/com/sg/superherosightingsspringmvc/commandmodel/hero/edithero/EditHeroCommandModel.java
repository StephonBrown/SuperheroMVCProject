/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.commandmodel.hero.edithero;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author sbrown6
 */
public class EditHeroCommandModel {
    
    private int heroId;
    
    @NotNull
    @NotEmpty(message = "You must supply a value for Hero Name.")
    @Length(max = 45, message = "Hero Name must be no more than 45 characters in length.")
    private String heroName;
    
    @NotNull
    @NotEmpty(message = "You must supply a value for Hero Description.")
    @Length(max = 250, message = "Hero Description must be no more than 250 characters in length.")
    private String heroDescription;

    @NotNull
    private List<Integer> orgs;
    
    @NotNull
    @NotEmpty(message="There must at least be one selected superpower")
    @Size(min=1)
    private List<Integer> sps;

    public EditHeroCommandModel() {
    }

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

    public List<Integer> getOrgs() {
        return orgs;
    }

    public void setOrgs(List<Integer> orgs) {
        this.orgs = orgs;
    }

    public List<Integer> getSps() {
        return sps;
    }

    public void setSps(List<Integer> sps) {
        this.sps = sps;
    }

}
