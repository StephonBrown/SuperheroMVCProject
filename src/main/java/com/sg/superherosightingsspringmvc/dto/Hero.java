/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.dto;

import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author sbrown6
 */
public class Hero {
    
    private int heroId;
    
    @NotNull
    @NotEmpty(message = "You must supply a value for Hero Name.")
    @Length(max = 45, message = "Hero Name must be no more than 45 characters in length.")
    private String heroName;
    
    @NotNull
    @NotEmpty(message = "You must supply a value for Hero Description.")
    @Length(max = 250, message = "Hero Description must be no more than 250 characters in length.")
    private String heroDescription;

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.heroId;
        hash = 89 * hash + Objects.hashCode(this.heroName);
        hash = 89 * hash + Objects.hashCode(this.heroDescription);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Hero other = (Hero) obj;
        if (this.heroId != other.heroId) {
            return false;
        }
        if (!Objects.equals(this.heroName, other.heroName)) {
            return false;
        }
        if (!Objects.equals(this.heroDescription, other.heroDescription)) {
            return false;
        }
        return true;
    }
    
    
    
    
}
