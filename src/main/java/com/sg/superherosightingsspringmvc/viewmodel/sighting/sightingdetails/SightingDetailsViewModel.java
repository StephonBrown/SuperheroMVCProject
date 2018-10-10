/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.viewmodel.sighting.sightingdetails;

import java.time.LocalDate;

/**
 *
 * @author Stephon
 */
public class SightingDetailsViewModel {
    private int sightingId;
    private LocalDate sightingDate;
    private LocationDetailsViewModel location;
    private HeroDetailsViewModel hero;

    public int getSightingId() {
        return sightingId;
    }

    public void setSightingId(int sightingId) {
        this.sightingId = sightingId;
    }

    public LocalDate getSightingDate() {
        return sightingDate;
    }

    public void setSightingDate(LocalDate sightingDate) {
        this.sightingDate = sightingDate;
    }

    public LocationDetailsViewModel getLocation() {
        return location;
    }

    public void setLocation(LocationDetailsViewModel location) {
        this.location = location;
    }

    public HeroDetailsViewModel getHero() {
        return hero;
    }

    public void setHero(HeroDetailsViewModel hero) {
        this.hero = hero;
    }
    
    
}
