/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.commandmodel.organization.editorganization;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author sbrown6
 */
public class EditOrganizationCommandModel {
        
    private int organizationId;
    
    @NotNull
    @NotEmpty(message = "You must supply a value for Organization Name.")
    @Length(max=55, message = "Organization Name must be no more than 55 characters in length.")
    private String organizationName;
    
    @NotNull
    @NotEmpty(message = "You must supply a value for Organization Description.")
    @Length(max=250, message = "Organization Description must be no more than 250 characters in length.")
    private String organizationDescription;
    
    @NotNull
    private int locationId;
    
    @NotNull
    @NotEmpty(message = "You must supply a value for Telephone Number.")
    @Pattern(regexp="\\d{3}-\\d{3}-\\d{4}" , message="You must adhere to the following format: 999-999-9999")
    private String TelephoneNumber;
    
    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationDescription() {
        return organizationDescription;
    }

    public void setOrganizationDescription(String organizationDescription) {
        this.organizationDescription = organizationDescription;
    }

    public String getTelephoneNumber() {
        return TelephoneNumber;
    }

    public void setTelephoneNumber(String TelephoneNumber) {
        this.TelephoneNumber = TelephoneNumber;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }
    
}
