/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.dao;

import com.sg.superherosightingsspringmvc.dto.Hero;
import com.sg.superherosightingsspringmvc.dto.Superpower;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sbrown6
 */
public class SuperpowerDaoDbImpl implements SuperpowerDao{
    
    JdbcTemplate jdbc;
    
    public SuperpowerDaoDbImpl(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }
    
    private final String SQL_SELECT_SUPERPOWER = 
            "select * from SuperPower where SuperPowerID = ?";
    private final String SQL_ADD_SUPERPOWER = 
            "insert into SuperPower(SuperPowerName, SuperPowerDescription)"+
            "values(?,?)";
    private final String SQL_REMOVE_HERO_SUPERPOWER=
            "delete from Hero_SuperPower where SuperPowerID = ?";
    private final String SQL_REMOVE_SUPERPOWER=
            "delete from SuperPower where SuperPowerID = ?";
    private final String SQL_UPDATE_SUPERPOWER=
            "update Superpower set SuperPowerName=?, SuperPowerDescription=? WHERE SuperPowerID = ?";
    private final String SQL_GET_ALL_SUPERPOWERS =
            "select* from SuperPower LIMIT ? OFFSET ?";
    private final String SQL_SELECT_SUPERPOWERS_BY_HEROID = 
            "select sp.SuperPowerID, sp.SuperPowerName, sp.SuperPowerDescription from SuperPower sp"+
            " join hero_superpower hs on sp.SuperPowerID = hs.SuperPowerID where hs.HeroID = ? LIMIT ? OFFSET ?";
    private final String SQL_INSERT_HERO_SUPERPOWER =
            "insert into Hero_SuperPower(HeroID, SuperpowerId)"
            +"values(?,?)";
    
    @Override
    public Superpower getSuperpower(int superpowerId){
        try{
            return jdbc.queryForObject(SQL_SELECT_SUPERPOWER, new SuperPowerMapper(), superpowerId);
        }catch(EmptyResultDataAccessException e){
            return null;
        }   
    }
    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly = false)
    public Superpower addSuperpower(Superpower superPower) throws SuperpowerPersistenceException{
        try{
            jdbc.update(SQL_ADD_SUPERPOWER, superPower.getSuperPowerName(), superPower.getSuperPowerDescription());
            superPower.setSuperPowerId(jdbc.queryForObject("select LAST_INSERT_ID()", Integer.class));
            return superPower;
        }catch(DataAccessException e){
            throw new SuperpowerPersistenceException("There is a problem connecting to the database");
        }
    }

    @Override
    public void removeSuperpower(int superpowerId) throws SuperpowerPersistenceException{
        try{
            jdbc.update(SQL_REMOVE_HERO_SUPERPOWER, superpowerId);
            jdbc.update(SQL_REMOVE_SUPERPOWER, superpowerId);
        }catch(DataAccessException e){
            throw new SuperpowerPersistenceException("There is a problem connecting to the database");
        }
    }

    @Override
    public Superpower editSuperPower(Superpower superpower) throws SuperpowerPersistenceException{
        try{
            jdbc.update(SQL_UPDATE_SUPERPOWER, superpower.getSuperPowerName(), superpower.getSuperPowerDescription());
            return superpower;
        }catch(DataAccessException e){
            throw new SuperpowerPersistenceException("There is a problem connecting to the database");
        }
    }
    
    @Override
    public List<Superpower> getAllSuperpowers(int limit, int offset){
        try{
            return jdbc.query(SQL_GET_ALL_SUPERPOWERS, new SuperPowerMapper(), limit, offset);
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public List<Superpower> getHeroesSuperPowers(Hero hero, int limit, int offset){
        try{
            List<Superpower> superpowers =  jdbc.query(SQL_SELECT_SUPERPOWERS_BY_HEROID, new SuperPowerMapper(),hero.getHeroId(), limit, offset);
            return superpowers;   
        }catch(EmptyResultDataAccessException e){
            return null;
        }    
    }
    
    @Override
    public void insertHeroSuperPower(Superpower sp, Hero hero) throws SuperpowerPersistenceException{
        try{
            jdbc.update(SQL_INSERT_HERO_SUPERPOWER, hero.getHeroId(), sp.getSuperPowerId());
        }catch(DataAccessException e){
            throw new SuperpowerPersistenceException("There is a problem connecting to the database");
        }
    }
    
}
