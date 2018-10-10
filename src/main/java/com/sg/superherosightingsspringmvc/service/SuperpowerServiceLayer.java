/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.service;

import com.sg.superherosightingsspringmvc.dao.SuperpowerPersistenceException;
import com.sg.superherosightingsspringmvc.dao.HeroPersistenceException;
import com.sg.superherosightingsspringmvc.dto.Hero;
import com.sg.superherosightingsspringmvc.dto.Superpower;
import java.util.List;

/**
 *
 * @author sbrown6
 */
public interface SuperpowerServiceLayer {
    
    Superpower getSuperpower(int superpowerId) throws  SuperpowerPersistenceException, SuperpowerDataValidationException; 
    
    Superpower createSuperpower(Superpower superPower) throws SuperpowerDataValidationException, SuperpowerPersistenceException;
    
    void removeSuperpower(int superpowerId)throws SuperpowerPersistenceException;
    
    Superpower editSuperPower(Superpower superpower)throws SuperpowerDataValidationException, SuperpowerPersistenceException;
    
    List<Superpower> getAllSuperpowers(int limit, int offset) throws SuperpowerPersistenceException;
    
    List<Superpower> getHeroesSuperPowers(Hero hero, int limit, int offset) throws SuperpowerPersistenceException,HeroPersistenceException, HeroDataValidationException;
    
    public void insertHeroSuperPower(Superpower sp, Hero hero) throws SuperpowerPersistenceException,SuperpowerDataValidationException,HeroPersistenceException, HeroDataValidationException;    
}
