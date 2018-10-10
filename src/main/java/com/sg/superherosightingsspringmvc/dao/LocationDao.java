/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.dao;

import com.sg.superherosightingsspringmvc.dto.Hero;
import com.sg.superherosightingsspringmvc.dto.Location;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sbrown6
 */
public interface LocationDao {
    
    Location getLocation(int locationId);
    
    Location addLocation(Location location)throws LocationPersistenceException;
    
    void removeLocation(int locationId)throws LocationPersistenceException;
    
    Location editLocation(Location location)throws LocationPersistenceException;
    
    List<Location> getAllLocations(int limit, int offset);
    
    List<Location> getHeroLocations(Hero hero, int limit, int offset) ;
    
    
}
