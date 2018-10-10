/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.dao;

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
public interface HeroDao {
    
    Hero getHero(int heroId);
    
    Hero addHero(Hero hero) throws HeroPersistenceException;
    
    void removeHero(int heroId) throws HeroPersistenceException;
    
    Hero editHero(Hero hero) throws HeroPersistenceException;
    
    List<Hero> getAllHeroes(int limit, int offset);
    
    Hero getHeroAtSighting(Sighting sighting);
    
    List<Hero> getAllHerosSightedAtLocation(Location location, int limit, int offset );

    
}
