/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.service;

import com.sg.superherosightingsspringmvc.dao.SightingPersistenceException;
import com.sg.superherosightingsspringmvc.dao.HeroPersistenceException;
import com.sg.superherosightingsspringmvc.dao.LocationPersistenceException;
import com.sg.superherosightingsspringmvc.dao.SightingDao;
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
public class SightingServiceLayerImpl implements SightingServiceLayer{
    private SightingDao sightingDao;
    private LocationServiceLayer locationServiceLayer;
    private HeroServiceLayer heServiceLayer;

    public SightingServiceLayerImpl(SightingDao sightingDao, LocationServiceLayer locationServiceLayer, HeroServiceLayer heServiceLayer) {
        this.sightingDao = sightingDao;
        this.locationServiceLayer = locationServiceLayer;
        this.heServiceLayer = heServiceLayer;
    }
    
    @Override
    public Sighting getSighting(int sightingId) throws SightingPersistenceException, SightingDataValidationException {
        if(sightingDao.getSighting(sightingId) == null){
            throw new SightingDataValidationException("This is not a valid Sighting ID");
        }
        return sightingDao.getSighting(sightingId);
        
    }

    @Override
    public Sighting createSighting(Sighting sighting) throws SightingPersistenceException, SightingDataValidationException, LocationPersistenceException, LocationDataValidationException {
            Sighting newSight = validateSighting(sighting);
            return sightingDao.addSighting(newSight);
    }

    @Override
    public void removeSighting(int sightingId) throws SightingPersistenceException, SightingDataValidationException {
        getSighting(sightingId);
        sightingDao.removeSighting(sightingId);
    }

    @Override
    public Sighting editSighting(Sighting sighting) throws SightingPersistenceException, SightingDataValidationException, LocationPersistenceException, LocationDataValidationException {
        getSighting(sighting.getSightingId());
        Sighting newSight = validateSighting(sighting);
        return sightingDao.editSighting(newSight);
    }

    @Override
    public List<Sighting> getAllSightings(int limit, int offset) throws SightingPersistenceException {
        return sightingDao.getAllSightings(limit, offset);

    }
    @Override
    public List<Sighting> getSightingsByDate(LocalDate date, int limit, int offset) throws SightingPersistenceException, SightingDataValidationException {
        return sightingDao.getSightingsByDate(date, limit, offset);
   
    }
    @Override
    public List<Sighting> getHeroSightings(Hero hero,int limit, int offset) throws SightingPersistenceException,HeroPersistenceException, HeroDataValidationException {
        heServiceLayer.getHero(hero.getHeroId());
        return sightingDao.getHeroSightings(hero, limit, offset);
    }
    @Override
    public void insertHeroSightings(Hero hero, Sighting sighting) throws SightingPersistenceException, SightingDataValidationException, HeroDataValidationException, HeroPersistenceException {
        getSighting(sighting.getSightingId());
        heServiceLayer.getHero(hero.getHeroId());
        sightingDao.insertHeroSightings(hero, sighting);
    }
    
    @Override
    public List<Sighting> getSightingsByLocation(Location location, int limit, int offset) throws SightingPersistenceException, SightingDataValidationException, LocationDataValidationException,LocationPersistenceException {
        locationServiceLayer.getLocation(location.getLocationId());
        return sightingDao.getSightingsByLocation(location, limit, offset);

    }
    
    private Sighting validateSighting(Sighting sighting) throws SightingDataValidationException, LocationPersistenceException, LocationDataValidationException{
        if(sighting.getSightingDate() == null || sighting.getSightingDate().toString().trim().length()==0){
            throw new SightingDataValidationException("ERROR: All fields [Sighting Date, LocationID] are required.");
        }else if(locationServiceLayer.getLocation(sighting.getlocationID()) == null ||sighting.getlocationID()==0){
            throw new LocationDataValidationException("ERROR: LocationID is not valid.");
        }else{
            return sighting;
        }
    }






 
}
