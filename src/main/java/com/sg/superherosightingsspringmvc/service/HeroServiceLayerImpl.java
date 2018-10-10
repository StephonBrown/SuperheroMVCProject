/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.service;

import com.sg.superherosightingsspringmvc.dao.SightingPersistenceException;
import com.sg.superherosightingsspringmvc.dao.HeroPersistenceException;
import com.sg.superherosightingsspringmvc.dao.LocationPersistenceException;
import com.sg.superherosightingsspringmvc.dao.HeroDao;
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
public class HeroServiceLayerImpl implements HeroServiceLayer {

    private HeroDao heroDao;
    private LocationServiceLayer locServiceLayer;
    private SightingServiceLayer stServiceLayer;

    public HeroServiceLayerImpl(HeroDao heroDao) {
        this.heroDao = heroDao;
    }
    
    public void setLocationServiceLayer(LocationServiceLayer locServiceLayer){
        this.locServiceLayer = locServiceLayer;
    }
    
   public void setSightingServiceLayer(SightingServiceLayer stServiceLayer){
        this.stServiceLayer = stServiceLayer;
    }

    @Override
    public Hero getHero(int heroId) throws HeroPersistenceException, HeroDataValidationException {
            if (heroDao.getHero(heroId)==null) {
                throw new HeroDataValidationException("This is not a valid Hero ID");
            }
            return heroDao.getHero(heroId);

    }

    @Override
    public Hero createHero(Hero hero) throws HeroDataValidationException, HeroPersistenceException {
            Hero newHero = validateHero(hero);
            return heroDao.addHero(newHero);
  
    }

    @Override
    public void removeHero(int heroId) throws HeroDataValidationException, HeroPersistenceException {
            getHero(heroId);
            heroDao.removeHero(heroId);


    }

    @Override
    public Hero editHero(Hero hero) throws HeroDataValidationException, HeroPersistenceException {
        getHero(hero.getHeroId());
        Hero updatedHero = validateHero(hero);
        return heroDao.editHero(updatedHero);

    }

    @Override
    public List<Hero> getAllHeroes(int limit, int offset) throws HeroPersistenceException {
            return heroDao.getAllHeroes(limit, offset);

    }
    
    @Override
    public Hero getHeroAtSighting(Sighting sighting) throws SightingPersistenceException, SightingDataValidationException, HeroPersistenceException, HeroDataValidationException {
        stServiceLayer.getSighting(sighting.getSightingId());
        return heroDao.getHeroAtSighting(sighting);
    }

    @Override
    public List<Hero> getAllHerosSightedAtLocation(Location location, int limit, int offset) throws HeroPersistenceException, LocationPersistenceException, LocationDataValidationException {
            return heroDao.getAllHerosSightedAtLocation(location, limit, offset);
    }
    
    private Hero validateHero(Hero he) throws HeroDataValidationException {
        if (he.getHeroName() == null || he.getHeroName().trim().length() == 0
                || he.getHeroDescription() == null || he.getHeroDescription().trim().length() == 0) {
            throw new HeroDataValidationException("ERROR: All fields [Hero Name, Hero Description] are required.");
        } else {
            return he;
        }
    }
}
