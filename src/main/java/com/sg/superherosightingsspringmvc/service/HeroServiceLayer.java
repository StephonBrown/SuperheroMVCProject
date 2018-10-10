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
import com.sg.superherosightingsspringmvc.dto.Organization;
import com.sg.superherosightingsspringmvc.dto.Sighting;
import com.sg.superherosightingsspringmvc.dto.Superpower;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sbrown6
 */
public interface HeroServiceLayer{
    Hero getHero(int heroId)throws HeroPersistenceException, HeroDataValidationException;
    
    Hero createHero(Hero hero) throws HeroDataValidationException, HeroPersistenceException;
    
    void removeHero(int heroId)throws HeroDataValidationException, HeroPersistenceException;
    
    Hero editHero(Hero hero) throws HeroDataValidationException, HeroPersistenceException;
    
    List<Hero> getAllHeroes(int limit, int offset)throws HeroPersistenceException;
    
    Hero getHeroAtSighting(Sighting sighting) throws SightingPersistenceException,SightingDataValidationException, HeroPersistenceException, HeroDataValidationException;
    
    List<Hero> getAllHerosSightedAtLocation(Location location, int limit, int offset) throws HeroPersistenceException,LocationPersistenceException, LocationDataValidationException;

}
