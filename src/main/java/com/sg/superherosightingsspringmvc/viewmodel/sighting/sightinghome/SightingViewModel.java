/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.viewmodel.sighting.sightinghome;

import java.time.LocalDate;

/**
 *
 * @author Stephon
 */
public class SightingViewModel {
    private int sightingId;
    private LocalDate sightingDate; 
    private LocationViewModel location;
    private HeroViewModel hero;

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

    public LocationViewModel getLocation() {
        return location;
    }

    public void setLocation(LocationViewModel location) {
        this.location = location;
    }

    public HeroViewModel getHero() {
        return hero;
    }

    public void setHero(HeroViewModel hero) {
        this.hero = hero;
    }

}
