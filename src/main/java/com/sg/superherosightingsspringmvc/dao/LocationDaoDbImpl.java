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
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sbrown6
 */
public class LocationDaoDbImpl implements LocationDao{
    
    JdbcTemplate jdbc;
    
    public LocationDaoDbImpl(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }
    
    private final String SQL_SELECT_LOCATION = 
            "select * from Location where LocationID = ?";
    private final String SQL_ADD_LOCATION = 
            "insert into Location (LocationName, LocationDescription, Street, City, State, ZipCode, Longitude, Latitude)"+
            "values(?,?,?,?,?,?,?,?)";
    private final String SQL_REMOVE_LOCATION = 
            "delete from Location where LocationID=?";
    private final String SQL_REMOVE_ORGANIZATION_LOCATIONID = 
            "delete from Organization where LocationID = ?";
    private final String SQL_REMOVE_HERO_SIGHTING_LOCATIONID = 
            "delete from Hero_Sighting hs left join Sighting st on hs.SightingID = st.SightingID where LocationID = ?";
    private final String SQL_REMOVE_SIGHTING_LOCATIONID = 
            "delete from Sighting where LocationID = ?";
    private final String SQL_UPDATE_LOCATION=
            "update Location set LocationName =?, LocationDescription=?, Street=?, City=?, State=?, ZipCode=?, Longitude=?, Latitude=? where LocationID=?";
    private final String SQL_SELECT_ALL_LOCATIONS = "select * from Location LIMIT ? OFFSET ?";
    private final String SQL_SELECT_LOCATION_BY_HEROID = 
            " select lo.LocationID, lo.LocationName, lo.LocationDescription, lo.Street, lo.City, lo.State, lo.ZipCode, lo.Longitude, lo.Latitude"+
            " from Location lo join Sighting st on lo.LocationID = st.LocationID"+
            " join Hero_Sighting hs on st.SightingID = hs.SightingID where HeroID = ? LIMIT ? OFFSET ?";
    @Override
    public Location getLocation(int locationId){
        try{
            return jdbc.queryForObject(SQL_SELECT_LOCATION, new LocationMapper(),locationId);    
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
    public Location addLocation(Location location) throws LocationPersistenceException{
        try{
            jdbc.update(SQL_ADD_LOCATION, location.getLocationName(), location.getLocationDescription(), 
                        location.getStreet(), location.getCity(), location.getState(),location.getZipCode(),
                    location.getLongitude(), location.getLatitude());
                    location.setLocationId(jdbc.queryForObject("select LAST_INSERT_ID()", Integer.class));
            return location;
        }catch(DataAccessException e){
            throw new LocationPersistenceException("There is a problem connecting to the database");
        }
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
    public void removeLocation(int locationId) throws LocationPersistenceException{
        try{
            jdbc.update(SQL_REMOVE_SIGHTING_LOCATIONID, locationId);
            jdbc.update(SQL_REMOVE_ORGANIZATION_LOCATIONID , locationId);
            jdbc.update(SQL_REMOVE_LOCATION, locationId);
        }catch(DataAccessException e){
            throw new LocationPersistenceException("There is a problem connecting to the database");
        }

    }

    @Override
    public Location editLocation(Location location) throws LocationPersistenceException{
        try{
            jdbc.update(SQL_UPDATE_LOCATION, location.getLocationName(), location.getLocationDescription(), 
                    location.getStreet(), location.getCity(), location.getState(),location.getZipCode(),
                    location.getLongitude(), location.getLatitude(),location.getLocationId());
            return location;
        }catch(DataAccessException e){
              throw new LocationPersistenceException("There is a problem connecting to the database");
        }
    }

    @Override
    public List<Location> getAllLocations(int limit, int offset){
       try{
           return jdbc.query(SQL_SELECT_ALL_LOCATIONS, new LocationMapper(), limit, offset);
       }catch(EmptyResultDataAccessException e){
           return null;
       }
    }

    @Override
    public List<Location> getHeroLocations(Hero hero, int limit, int offset){
        try{
            List<Location> locations =  jdbc.query(SQL_SELECT_LOCATION_BY_HEROID, new LocationMapper(), hero.getHeroId(),limit, offset);
            return locations;   
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }
    
}