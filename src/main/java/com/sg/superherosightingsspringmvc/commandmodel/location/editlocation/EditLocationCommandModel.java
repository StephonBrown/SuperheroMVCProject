/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.commandmodel.location.editlocation;


import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author sbrown6
 */
public class EditLocationCommandModel {
        
    private int locationId;
    
    @NotNull
    @NotEmpty(message = "You must supply a value for Location Name.")
    @Length(min = 1,max = 30, message = "Location Description must be no more than 30 characters in length.")
    private String locationName;
    
    @NotNull
    @NotEmpty(message = "You must supply a value for Location Description.")
    @Length(min = 1, max = 250, message = "Location Description must be no more than 250 characters in length.")
    private String locationDescription;
    
    @DecimalMin(value="-180.00000000")
    @DecimalMax(value="180.00000000")
    @Digits(integer=3, fraction=8, message="This must be filled")
    private BigDecimal longitude;
    
    @DecimalMin(value= "-90.00000000")
    @DecimalMax(value= "90.00000000")
    @Digits(integer=2, fraction=8, message="This must be filled")
    private BigDecimal latitude;
    
    @NotNull
    @NotEmpty(message = "You must supply a value for Street.")
    @Length(max = 30, message = "Street must be no more than 30 characters in length.")
    private String street;
    
    @NotNull
    @NotEmpty(message = "You must supply a value for State.")
    @Length(max = 30, message = "State must be no more than 30 characters in length.")
    private String state;
    
    @NotNull
    @NotEmpty(message = "You must supply a value for ZipCode.")
    @Length(min = 2,max=5, message = "ZipCode must be no more than 5 characters in length.")
    private String zipCode;
    
    @NotNull
    @NotEmpty(message = "You must supply a value for City.")
    @Length(max=30, message = "City must be no more than 30 characters in length.")
    private String city;

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
