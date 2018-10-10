/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.service;

import com.sg.superherosightingsspringmvc.dao.HeroPersistenceException;
import com.sg.superherosightingsspringmvc.dao.LocationPersistenceException;
import com.sg.superherosightingsspringmvc.dao.LocationDao;
import com.sg.superherosightingsspringmvc.dto.Hero;
import com.sg.superherosightingsspringmvc.dto.Location;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sbrown6
 */
public class LocationServiceLayerImpl implements LocationServiceLayer{
    private LocationDao locationDao;
    private HeroServiceLayer heroService;

    public LocationServiceLayerImpl(LocationDao locationDao, HeroServiceLayer heroService) {
        this.locationDao = locationDao;
        this.heroService = heroService;
    }
    
    @Override
    public Location getLocation(int locationId) throws LocationPersistenceException, LocationDataValidationException {
            if(locationDao.getLocation(locationId) == null){
                throw new LocationDataValidationException("This is not a valid Location ID");
            }
            return locationDao.getLocation(locationId);
    }

    @Override
    public Location createLocation(Location location) throws LocationPersistenceException, LocationDataValidationException {
            Location newLocation = validateLocation(location);
            return locationDao.addLocation(newLocation);
    }

    @Override
    public void removeLocation(int locationId) throws LocationPersistenceException, LocationDataValidationException {
        getLocation(locationId);
        locationDao.removeLocation(locationId);

    }

    @Override
    public Location editLocation(Location location) throws LocationPersistenceException, LocationDataValidationException {
        getLocation(location.getLocationId());
        validateLocation(location);
        return locationDao.editLocation(location);  
    }

    @Override
    public List<Location> getAllLocations(int limit, int offset) throws LocationPersistenceException {
            return locationDao.getAllLocations(limit, offset);
      
    }
    @Override
    public List<Location> getHeroLocations(Hero hero, int limit, int offset) throws LocationPersistenceException, HeroDataValidationException, HeroPersistenceException {
        heroService.getHero(hero.getHeroId());
        return locationDao.getHeroLocations(hero, limit,offset);

    }
    private Location validateLocation(Location lo) throws LocationDataValidationException {
        if(lo.getLocationName()==null||lo.getLocationName().trim().length()==0
                || lo.getLocationDescription()==null|| lo.getLocationDescription().trim().length()==0
                || lo.getStreet()==null ||lo.getStreet().trim().length()==0||
                lo.getState()==null || lo.getState().trim().length()==0||
                lo.getCity()==null || lo.getCity().trim().length()==0||
                lo.getZipCode()==null || lo.getZipCode().trim().length()==0||lo.getZipCode().trim().length()>5||
                lo.getLongitude().compareTo(new BigDecimal(180.000000))==1 || lo.getLongitude().compareTo(new BigDecimal(180.000000).negate())==-1 ||lo.getLongitude()==null
                || lo.getLatitude().compareTo(new BigDecimal(90.000000))==1 || lo.getLatitude().compareTo(new BigDecimal(90.000000).negate())==-1 ||lo.getLatitude()==null){
            throw new LocationDataValidationException("ERROR: All fields [Location Name, Location Description,Street, City, State, ZipCode, Latitude, Longitude] are required.");   
        }else{
            return lo;
        }
    }
}

