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
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sbrown6
 */
public class HeroDaoDbImpl implements HeroDao{
    JdbcTemplate jdbc;
    
    public HeroDaoDbImpl(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }
    
    private final String SQL_SELECT_HERO = 
            "select * from Hero where HeroID = ?";
    private final String SQL_ADD_HERO = 
            "insert into hero(HeroName, HeroDescription)"+
            "values(?,?)";
    private final String SQL_REMOVE_HERO = 
            "delete from hero where HeroID = ?";   
    private final String SQL_UPDATE_HERO = 
            "update Hero set HeroName = ?, HeroDescription=? where HeroID=?"; 
    private final String SQL_SELECT_ALL_HEROES = 
            "select * from Hero LIMIT ? OFFSET ?";
    private final String SQL_DELETE_HERO_SUPERPOWER_HEROID =
            "delete from Hero_SuperPower where HeroID = ?";
    private final String SQL_REMOVE_ORGANIZATION_HERO_HeroID = 
            "delete from Organization_Hero where HeroID = ?";
    private final String SQL_DELETE_HERO_SIGHTING_HERO_ID =
            "delete from Hero_Sighting where HeroID = ?";
    private final String SQL_SELECT_HEROES_BY_LOCATIONID = 
            "select he.HeroID, he.HeroName, he.HeroDescription from Hero he"+
            " join Hero_Sighting hs on he.HeroID = hs.HeroID"+
            " join Sighting st on hs.SightingID = st.SightingID where LocationID = ? LIMIT ? OFFSET ?";
   private final String SQL_SELECT_ALL_HEROES_AT_SIGHTING=
            "select * from Hero he join hero_sighting hs on he.heroID = hs.heroId where SightingId =? ";
    
    @Override
    public Hero getHero(int heroId){
        try{
            Hero hero = jdbc.queryForObject(SQL_SELECT_HERO, new HeroMapper(),heroId);
            return hero;
        }catch(EmptyResultDataAccessException e ){
            return null;
        }
        
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Hero addHero(Hero hero)throws HeroPersistenceException {
        try{
            jdbc.update(SQL_ADD_HERO, hero.getHeroName(),hero.getHeroDescription());
            hero.setHeroId(jdbc.queryForObject("select LAST_INSERT_ID()", Integer.class));
            return hero;
        }catch(DataAccessException e){
            throw new HeroPersistenceException("There is a problem connecting to the database");
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void removeHero(int heroId) throws HeroPersistenceException{
       try{
            jdbc.update(SQL_REMOVE_ORGANIZATION_HERO_HeroID, heroId);
            jdbc.update(SQL_DELETE_HERO_SUPERPOWER_HEROID, heroId);
            jdbc.update(SQL_DELETE_HERO_SIGHTING_HERO_ID, heroId);
            jdbc.update(SQL_REMOVE_HERO, heroId);
        }catch(DataAccessException e){
            throw new HeroPersistenceException("There is a problem connecting to the database");
        }
    }

    @Override
    public Hero editHero(Hero hero) throws HeroPersistenceException{
        try{
            jdbc.update(SQL_UPDATE_HERO, hero.getHeroName(),hero.getHeroDescription(),hero.getHeroId());
            jdbc.update(SQL_REMOVE_ORGANIZATION_HERO_HeroID, hero.getHeroId());
            jdbc.update(SQL_DELETE_HERO_SUPERPOWER_HEROID, hero.getHeroId());
            jdbc.update(SQL_DELETE_HERO_SIGHTING_HERO_ID, hero.getHeroId());
            return hero;
        }catch(DataAccessException e){
            throw new HeroPersistenceException("There is a problem connecting to the database");
        }
        
    }

    @Override
    public List<Hero> getAllHeroes(int limit, int offset) {
        try{
            List<Hero> heroes =  jdbc.query(SQL_SELECT_ALL_HEROES, new HeroMapper(),limit,offset);
            return heroes;
        }catch(EmptyResultDataAccessException e){
            return null;
        }

    }
    
    @Override
    public Hero getHeroAtSighting(Sighting sighting){
        try{
            return jdbc.queryForObject(SQL_SELECT_ALL_HEROES_AT_SIGHTING, new HeroMapper(),sighting.getSightingId());
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public List<Hero> getAllHerosSightedAtLocation(Location location, int limit, int offset){
        try{
            return jdbc.query(SQL_SELECT_HEROES_BY_LOCATIONID, new HeroMapper(), location.getLocationId(),limit, offset);
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }
        
}
