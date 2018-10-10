/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.service;

import com.sg.superherosightingsspringmvc.dao.HeroPersistenceException;
import com.sg.superherosightingsspringmvc.dao.LocationPersistenceException;
import com.sg.superherosightingsspringmvc.dto.Hero;
import com.sg.superherosightingsspringmvc.dto.Location;
import java.util.List;

/**
 *
 * @author sbrown6
 */
public interface LocationServiceLayer {
    Location getLocation(int locationId) throws LocationPersistenceException, LocationDataValidationException;
    
    Location createLocation(Location location)throws LocationPersistenceException, LocationDataValidationException;
    
    void removeLocation(int locationId) throws LocationPersistenceException, LocationDataValidationException;
    
    Location editLocation(Location location) throws LocationPersistenceException, LocationDataValidationException;;
    
    List<Location> getAllLocations(int limit, int offset)throws LocationPersistenceException;;
    
    List<Location> getHeroLocations(Hero hero, int limit, int offset) throws LocationPersistenceException, HeroDataValidationException, HeroPersistenceException;

}
