/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.dto;

import java.util.Objects;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author sbrown6
 */
public class Superpower {
    
    public Superpower(){
        
    }
    
    private int superPowerId;
    
    @NotNull
    @NotEmpty(message="You must supply a SuperPower Name")
    @Length(min=1, max =45, message="SuperPower Name can not exceed 45 characters")
    private String superPowerName;
    
    @NotNull
    @NotEmpty(message="You must supply a SuperPower Description")
    @Length(min=1, max =250, message="SuperPower Name can not exceed 250 characters")
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.superPowerId;
        hash = 67 * hash + Objects.hashCode(this.superPowerName);
        hash = 67 * hash + Objects.hashCode(this.superPowerDescription);
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
        final Superpower other = (Superpower) obj;
        if (this.superPowerId != other.superPowerId) {
            return false;
        }
        if (!Objects.equals(this.superPowerName, other.superPowerName)) {
            return false;
        }
        if (!Objects.equals(this.superPowerDescription, other.superPowerDescription)) {
            return false;
        }
        return true;
    }
    

}
