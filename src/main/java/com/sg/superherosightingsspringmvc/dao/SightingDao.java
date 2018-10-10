/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.dao;

import com.sg.superherosightingsspringmvc.dto.Hero;
import com.sg.superherosightingsspringmvc.dto.Location;
import com.sg.superherosightingsspringmvc.dto.Sighting;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author sbrown6
 */
public interface SightingDao {
    
   Sighting getSighting(int sightingId);
   
   Sighting addSighting(Sighting sighting)throws SightingPersistenceException;
   
   void removeSighting(int sightingId)throws SightingPersistenceException;
   
   Sighting editSighting(Sighting sighting)throws SightingPersistenceException; 
    
   List<Sighting> getAllSightings(int limit, int offset);
   
   List<Sighting> getSightingsByDate(LocalDate date, int limit, int offset);
   
   List<Sighting> getHeroSightings(Hero hero, int limit, int offset);
   
   List<Sighting> getSightingsByLocation(Location location, int limit, int offset);
   
   void insertHeroSightings(Hero hero, Sighting sighting)throws SightingPersistenceException;

}
