/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.service;

import com.sg.superherosightingsspringmvc.dto.Hero;
import com.sg.superherosightingsspringmvc.dto.Superpower;
import java.sql.SQLException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author sbrown6
 */
public class SuperpowerServiceLayerTest {
    private SuperpowerServiceLayer spServiceLayer;
    private HeroServiceLayer heServiceLayer;
    
    public SuperpowerServiceLayerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception{
        ApplicationContext ctx = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        spServiceLayer = ctx.getBean("SuperpowerServiceLayer", SuperpowerServiceLayer.class);
        heServiceLayer = ctx.getBean("HeroServiceLayer", HeroServiceLayer.class);
        
        List<Superpower> powers = spServiceLayer.getAllSuperpowers(Integer.MAX_VALUE, 0);
        for (Superpower currentSuperpower: powers) {
            spServiceLayer.removeSuperpower(currentSuperpower.getSuperPowerId());
        }
        
        List<Hero> heroes = heServiceLayer.getAllHeroes(Integer.MAX_VALUE, 0);
        for (Hero currentHero: heroes) {
            heServiceLayer.removeHero(currentHero.getHeroId());
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createSuperpower method, of class SuperpowerServiceLayer.
     */
    @Test
    public void testCreateAndGetSuperpower() throws Exception {
        Superpower sp = new Superpower();
        sp.setSuperPowerName("Ice");
        sp.setSuperPowerDescription("Freeze yourself or others");
        
        spServiceLayer.createSuperpower(sp);
        Superpower fromService = spServiceLayer.getSuperpower(sp.getSuperPowerId());
        
        assertEquals(sp,fromService);
        
    }
    
        /**
     * Test of createSuperpower method, of class SuperpowerServiceLayer.
     */
    
    @Test
    public void testCreateSuperpowerInvalidData() throws Exception {
        Superpower sp2 = new Superpower();
        sp2.setSuperPowerName("Ice");
        sp2.setSuperPowerDescription(" ");
        try{
            spServiceLayer.createSuperpower(sp2);
            fail("expected SuperpowerDataValiationException was not thrown");
        }catch(SuperpowerDataValidationException e){
            return;
        }
        
        
    }

    /**
     * Test of removeSuperpower method, of class SuperpowerServiceLayer.
     */
    @Test
    public void testRemoveSuperpower() throws Exception {
        Superpower sp2 = new Superpower();
        sp2.setSuperPowerName("Ice Power");
        sp2.setSuperPowerDescription("Control the elements of ice"); 
        
        sp2 = spServiceLayer.createSuperpower(sp2);
        
        spServiceLayer.removeSuperpower(sp2.getSuperPowerId());
        assertEquals(0, spServiceLayer.getAllSuperpowers(Integer.MAX_VALUE, 0).size());
        
    }

    /**
     * Test of editSuperPower method, of class SuperpowerServiceLayer.
     */
    @Test
    public void testEditSuperPower() throws Exception {
        Superpower sp1 = new Superpower();
        sp1.setSuperPowerName("Ice Power");
        sp1.setSuperPowerDescription("Control the elements of ice"); 
        
        Superpower sp2 = new Superpower();
        sp2.setSuperPowerName("Fire Power");
        sp2.setSuperPowerDescription("Control the elements of fire");
        
        Superpower sp1fromService = spServiceLayer.createSuperpower(sp1);
        Superpower sp2fromService = spServiceLayer.createSuperpower(sp2);
        
        assertEquals(sp1,sp1fromService);
        assertEquals(sp2,sp2fromService);
        
        sp2.setSuperPowerName("Earth Power");
        sp2fromService = spServiceLayer.editSuperPower(sp2);
        
        assertEquals(sp2,sp2fromService);
        

    }
    
    @Test
    public void testEditSuperPowerInvalidData() throws Exception {
        Superpower sp1 = new Superpower();
        sp1.setSuperPowerName("Ice Power");
        sp1.setSuperPowerDescription("Control the elements of ice"); 
        
        Superpower sp2 = new Superpower();
        sp2.setSuperPowerName("Fire Power");
        sp2.setSuperPowerDescription("Control the elements of fire");
        
        Superpower sp1fromService = spServiceLayer.createSuperpower(sp1);
        Superpower sp2fromService = spServiceLayer.createSuperpower(sp2);
        
        assertEquals(sp1,sp1fromService);
        assertEquals(sp2,sp2fromService);
        
        sp2.setSuperPowerName(" ");
       
       try{
        spServiceLayer.editSuperPower(sp2);
        fail("Expected SuperpowerDataValiationException was not thrown");
       }catch(SuperpowerDataValidationException e){
           return;
       }
        

    }

    /**
     * Test of getAllSuperpowers method, of class SuperpowerServiceLayer.
     */
    @Test
    public void testGetAllSuperpowers() throws Exception {
        Superpower sp1 = new Superpower();
        sp1.setSuperPowerName("Ice Power");
        sp1.setSuperPowerDescription("Control the elements of ice"); 
        
        Superpower sp2 = new Superpower();
        sp2.setSuperPowerName("Fire Power");
        sp2.setSuperPowerDescription("Control the elements of fire");
        
        spServiceLayer.createSuperpower(sp1);
        spServiceLayer.createSuperpower(sp2);
        
        assertEquals(2, spServiceLayer.getAllSuperpowers(Integer.MAX_VALUE, 0).size());
    }
    @Test
    public void testGetHeroesSuperPowers() throws Exception{
        Superpower sp1 = new Superpower();
        sp1.setSuperPowerName("Ice Power");
        sp1.setSuperPowerDescription("Control the elements of ice"); 
        
        Superpower sp2 = new Superpower();
        sp2.setSuperPowerName("Fire Power");
        sp2.setSuperPowerDescription("Control the elements of fire");
        
        spServiceLayer.createSuperpower(sp1);
        spServiceLayer.createSuperpower(sp2);
        
        Hero hero1 = new Hero();
        hero1.setHeroName("Ice Slider");
        hero1.setHeroDescription("A man that hails from the glacial islands");

        Hero hero2 = new Hero();
        hero2.setHeroName("BucketHead");
        hero2.setHeroDescription("A man with a buckethead");
        
        heServiceLayer.createHero(hero1);
        heServiceLayer.createHero(hero2);
        
        spServiceLayer.insertHeroSuperPower(sp1, hero1);
        spServiceLayer.insertHeroSuperPower(sp2, hero2);
        
        Superpower fromDao1 = spServiceLayer.getHeroesSuperPowers(hero1, Integer.MAX_VALUE, 0).get(0);
        Superpower fromDao2 = spServiceLayer.getHeroesSuperPowers(hero2, Integer.MAX_VALUE, 0).get(0);
        
        assertEquals(sp1,fromDao1);
        assertEquals(sp2,fromDao2);  
    }
    
}
