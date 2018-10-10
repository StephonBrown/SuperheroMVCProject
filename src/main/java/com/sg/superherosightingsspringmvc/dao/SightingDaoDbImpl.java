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
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sbrown6
 */
public class SightingDaoDbImpl implements SightingDao{
    JdbcTemplate jdbc;
    
    public SightingDaoDbImpl(JdbcTemplate jdbc){
        this.jdbc=jdbc;
    }
    private final String SQL_SELECT_SIGHTING = 
            "select * from Sighting where SightingID = ?";
    private final String SQL_ADD_SIGHTING = 
            "insert into Sighting (SightingDate, LocationID)"+
            "values(?,?)";
    private final String SQL_REMOVE_HERO_SIGHTING=
            "delete from hero_sighting where SightingID = ?";
    private final String SQL_REMOVE_SIGHTING =
            "delete from Sighting where SightingID=?";
    private final String SQL_UPDATE_SIGHTING=
            "update Sighting set SightingDate=?, LocationID=? where SightingID=?";
    private final String SQL_INSERT_HERO_SIGHTING =
            "insert into Hero_Sighting(HeroID, SightingID)"
            +"values(?,?)"; 
    private final String SQL_SELECT_ALL_SIGHTINGS=
            "select * from Sighting ORDER BY SightingDate DESC LIMIT ? OFFSET ?";
    private final String SQL_SELECT_SIGHTINGS_BY_DATE =
            "select * from sighting where SightingDate=? LIMIT ? OFFSET ?";
    private final String SQL_SELECT_SIGHTINGS_BY_LOCATIONID =
            "select * from sighting where LocationID=? LIMIT ? OFFSET ?";
    private final String SQL_SELECT_SIGHTING_BY_HEROID = 
            "select st.SightingId, st.sightingDate, st.LocationID "+
            "from Sighting st join Hero_Sighting hs on st.SightingID = hs.SightingID where hs.HeroID = ? LIMIT ? OFFSET ?";
    @Override
    public Sighting getSighting(int sightingId){
        try{
            return jdbc.queryForObject(SQL_SELECT_SIGHTING,new SightingMapper(), sightingId);
        }catch(EmptyResultDataAccessException e){
            return null;
        }  
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
    public Sighting addSighting(Sighting sighting) throws SightingPersistenceException{
        try{   
            jdbc.update(SQL_ADD_SIGHTING, sighting.getSightingDate(), sighting.getlocationID());
            sighting.setSightingId(jdbc.queryForObject("select LAST_INSERT_ID()", Integer.class));
            return sighting;
        }catch(DataAccessException e){
            throw new SightingPersistenceException("There is a problem connecting to the database");
            
        }
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
    public void removeSighting(int sightingId) throws SightingPersistenceException{
        try{
            jdbc.update(SQL_REMOVE_HERO_SIGHTING, sightingId);
            jdbc.update(SQL_REMOVE_SIGHTING, sightingId);
        }catch(DataAccessException e){
            throw new SightingPersistenceException("There is a problem connecting to the database");
            
        }
        
    }

    @Override
    public Sighting editSighting(Sighting sighting) throws SightingPersistenceException{
        try{
            jdbc.update(SQL_UPDATE_SIGHTING,sighting.getSightingDate(),sighting.getlocationID(),sighting.getSightingId());
            jdbc.update(SQL_REMOVE_HERO_SIGHTING, sighting.getSightingId());
            return sighting;
        }catch(DataAccessException e){
            throw new SightingPersistenceException("There is a problem connecting to the database");   
        }
    }

    @Override
    public List<Sighting> getAllSightings(int limit, int offset){
        try{
            return jdbc.query(SQL_SELECT_ALL_SIGHTINGS, new SightingMapper(), limit, offset);
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDate date, int limit, int offset){
        try{
            return jdbc.query(SQL_SELECT_SIGHTINGS_BY_DATE, new SightingMapper(),date, limit, offset);
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }
    @Override
    public List<Sighting> getSightingsByLocation(Location location, int limit, int offset){
        try{
            return jdbc.query(SQL_SELECT_SIGHTINGS_BY_LOCATIONID, new SightingMapper(),location.getLocationId(), limit, offset);
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }
    
    @Override
    public List<Sighting> getHeroSightings(Hero hero, int limit, int offset){
        try{
            List<Sighting> sightings =  jdbc.query(SQL_SELECT_SIGHTING_BY_HEROID, new SightingMapper(), hero.getHeroId(), limit, offset);
            return sightings;   
        }catch(EmptyResultDataAccessException e){
            return null;
        }

    }    
    @Override
    public void insertHeroSightings(Hero hero, Sighting sighting)throws SightingPersistenceException{
        try{
            jdbc.update(SQL_INSERT_HERO_SIGHTING, hero.getHeroId(), sighting.getSightingId());
        }catch(DataAccessException e){
            throw new SightingPersistenceException("There is a problem connecting to the database");   
        }

    }

}
