/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.service;

import com.sg.superherosightingsspringmvc.dao.SightingPersistenceException;
import com.sg.superherosightingsspringmvc.dao.HeroPersistenceException;
import com.sg.superherosightingsspringmvc.dao.LocationPersistenceException;
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
public interface SightingServiceLayer {
        
   Sighting getSighting(int sightingId) throws SightingPersistenceException, SightingDataValidationException;
   
   Sighting createSighting(Sighting sighting) throws SightingPersistenceException, SightingDataValidationException, LocationPersistenceException, LocationDataValidationException;
   
   void removeSighting(int sightingId) throws SightingPersistenceException, SightingDataValidationException;
   
   Sighting editSighting(Sighting sighting)throws SightingPersistenceException, SightingDataValidationException, LocationPersistenceException, LocationDataValidationException; 
    
   List<Sighting> getAllSightings(int limit, int offset)throws SightingPersistenceException;
   
   List<Sighting> getSightingsByDate(LocalDate date, int limit, int offset) throws SightingPersistenceException, SightingDataValidationException;
   
   List<Sighting> getHeroSightings(Hero hero, int limit, int offset) throws  SightingPersistenceException, HeroPersistenceException, HeroDataValidationException;
   
   List<Sighting> getSightingsByLocation(Location location, int limit, int offset) throws SightingPersistenceException, SightingDataValidationException, LocationDataValidationException,LocationPersistenceException;
   
   void insertHeroSightings(Hero hero, Sighting sighting)throws SightingPersistenceException,SightingDataValidationException, HeroDataValidationException, HeroPersistenceException;
}
