/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.service;

import com.sg.superherosightingsspringmvc.dao.SuperpowerPersistenceException;
import com.sg.superherosightingsspringmvc.dao.HeroPersistenceException;
import com.sg.superherosightingsspringmvc.dao.SuperpowerDao;
import com.sg.superherosightingsspringmvc.dto.Hero;
import com.sg.superherosightingsspringmvc.dto.Superpower;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sbrown6
 */
public class SuperpowerServiceLayerImpl implements SuperpowerServiceLayer{
    SuperpowerDao superpowerDao;
    HeroServiceLayer heServiceLayer;

    public SuperpowerServiceLayerImpl(SuperpowerDao superpowerDao, HeroServiceLayer heServiceLayer) {
        this.superpowerDao = superpowerDao;
        this.heServiceLayer = heServiceLayer;
        
    }
    
    @Override
    public Superpower getSuperpower(int superpowerId) throws SuperpowerPersistenceException, SuperpowerDataValidationException {
        if(superpowerDao.getSuperpower(superpowerId) == null){
            throw new SuperpowerDataValidationException("Superpower ID does not exist");
        }
        return superpowerDao.getSuperpower(superpowerId);
   
    }

    @Override
    public Superpower createSuperpower(Superpower superPower) throws SuperpowerDataValidationException, SuperpowerPersistenceException {
        Superpower sp = validateSuperpower(superPower);
        return superpowerDao.addSuperpower(sp);
        
    }

    @Override
    public void removeSuperpower(int superpowerId) throws SuperpowerPersistenceException {
        superpowerDao.removeSuperpower(superpowerId);

    }

    @Override
    public Superpower editSuperPower(Superpower superpower) throws SuperpowerDataValidationException, SuperpowerPersistenceException{
        if(superpowerDao.getSuperpower(superpower.getSuperPowerId()) == null){
            throw new SuperpowerDataValidationException("Superpower ID does not exist");
        }
        validateSuperpower(superpower);
        return superpowerDao.editSuperPower(superpower);
    }

    @Override
    public List<Superpower> getAllSuperpowers(int limit, int offset) throws SuperpowerPersistenceException { 
        return superpowerDao.getAllSuperpowers(limit, offset);
    }
    @Override
    public List<Superpower> getHeroesSuperPowers(Hero hero, int limit, int offset) throws SuperpowerPersistenceException, HeroPersistenceException, HeroDataValidationException {
        heServiceLayer.getHero(hero.getHeroId());
        return superpowerDao.getHeroesSuperPowers(hero, limit, offset);

    }
    @Override
    public void insertHeroSuperPower(Superpower sp, Hero hero) throws SuperpowerPersistenceException, SuperpowerDataValidationException, HeroPersistenceException, HeroDataValidationException {
        getSuperpower(sp.getSuperPowerId());
        heServiceLayer.getHero(hero.getHeroId());
        superpowerDao.insertHeroSuperPower(sp, hero);   
    }

    private Superpower validateSuperpower(Superpower power) throws SuperpowerDataValidationException{
        if(power.getSuperPowerName()==null || power.getSuperPowerName().trim().length()==0||
                power.getSuperPowerDescription()==null || power.getSuperPowerDescription().trim().length()==0){
            throw new SuperpowerDataValidationException("ERROR: All fields [Superpower Name , Superpower Description] are required.");
                    
        }else{
            return power;
        }
    }
}
