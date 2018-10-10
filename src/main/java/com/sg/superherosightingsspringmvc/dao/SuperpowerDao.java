/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.dao;

import com.sg.superherosightingsspringmvc.dto.Hero;
import com.sg.superherosightingsspringmvc.dto.Superpower;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sbrown6
 */
public interface SuperpowerDao {
    
    Superpower getSuperpower(int superpowerId) ; 
    
    Superpower addSuperpower(Superpower superPower)throws SuperpowerPersistenceException;
    
    void removeSuperpower(int superpowerId)throws SuperpowerPersistenceException;
    
    Superpower editSuperPower(Superpower superpower)throws SuperpowerPersistenceException;
    
    List<Superpower> getAllSuperpowers(int limit, int offset);
    
    List<Superpower> getHeroesSuperPowers(Hero hero, int limit, int offset);
    
    public void insertHeroSuperPower(Superpower sp, Hero hero) throws SuperpowerPersistenceException;


}
