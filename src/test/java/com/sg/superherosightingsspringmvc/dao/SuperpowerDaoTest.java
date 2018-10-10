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
public class SuperpowerDaoTest {
    SuperpowerDao dao;
    HeroDao heroDao;
    
    public SuperpowerDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        dao = ctx.getBean("SuperpowerDao", SuperpowerDao.class);
        heroDao = ctx.getBean("HeroDao", HeroDao.class);
        
        List<Superpower> powers = dao.getAllSuperpowers(Integer.MAX_VALUE, 0);
        for (Superpower currentSuperpower: powers) {
            dao.removeSuperpower(currentSuperpower.getSuperPowerId());
        }
        List<Hero> heroes = heroDao.getAllHeroes(Integer.MAX_VALUE, 0);
        for (Hero currentHero: heroes) {
            heroDao.removeHero(currentHero.getHeroId());
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of AddAndGetSuperpower method, of class SuperpowerDao.
     */
    @Test
    public void testAddAndGetSuperpower() throws Exception {
        Superpower sp = new Superpower();
        sp.setSuperPowerName("Ice Power");
        sp.setSuperPowerDescription("Control the elements of ice");
        
        Superpower fromDb = dao.addSuperpower(sp);
        
        assertEquals(sp, fromDb);
        
    }

    /**
     * Test of removeSuperpower method, of class SuperpowerDao.
     */
    @Test
    public void testRemoveSuperpower() throws Exception {
        Superpower sp1 = new Superpower();
        sp1.setSuperPowerName("Ice Power");
        sp1.setSuperPowerDescription("Control the elements of ice"); 
        
        Superpower sp2 = new Superpower();
        sp2.setSuperPowerName("Fire Power");
        sp2.setSuperPowerDescription("Control the elements of fire");
        
        sp1 = dao.addSuperpower(sp1);
        sp2 = dao.addSuperpower(sp2);
        
        dao.removeSuperpower(sp1.getSuperPowerId());
        assertEquals(1, dao.getAllSuperpowers(Integer.MAX_VALUE, 0).size());
        assertNull(dao.getSuperpower(sp1.getSuperPowerId()));
        
        dao.removeSuperpower(sp2.getSuperPowerId());
        assertEquals(0, dao.getAllSuperpowers(Integer.MAX_VALUE, 0).size());
        assertNull(dao.getSuperpower(sp2.getSuperPowerId()));
    }

    /**
     * Test of editSuperPower method, of class SuperpowerDao.
     */
    @Test
    public void testEditSuperPower() throws Exception {
        Superpower sp1 = new Superpower();
        sp1.setSuperPowerName("Ice Power");
        sp1.setSuperPowerDescription("Control the elements of ice"); 
        
        Superpower sp2 = new Superpower();
        sp2.setSuperPowerName("Fire Power");
        sp2.setSuperPowerDescription("Control the elements of fire");
        
        Superpower sp1fromDao = dao.addSuperpower(sp1);
        Superpower sp2fromDao = dao.addSuperpower(sp2);
        
        assertEquals(sp1,sp1fromDao);
        assertEquals(sp2,sp2fromDao);
        
        sp2.setSuperPowerName("Earth Power");
        sp2fromDao = dao.editSuperPower(sp2);
        
        assertEquals(sp2,sp2fromDao);
          
    }
    @Test
    public void testGetAllSuperpowers() throws Exception {
        Superpower sp1 = new Superpower();
        sp1.setSuperPowerName("Ice Power");
        sp1.setSuperPowerDescription("Control the elements of ice"); 
        
        Superpower sp2 = new Superpower();
        sp2.setSuperPowerName("Fire Power");
        sp2.setSuperPowerDescription("Control the elements of fire");
        
        dao.addSuperpower(sp1);
        dao.addSuperpower(sp2);
        
        assertEquals(2, dao.getAllSuperpowers(Integer.MAX_VALUE, 0).size());
    }
    @Test
    public void testGetHeroesSuperPowers() throws Exception{
        Superpower sp1 = new Superpower();
        sp1.setSuperPowerName("Ice Power");
        sp1.setSuperPowerDescription("Control the elements of ice"); 
        
        Superpower sp2 = new Superpower();
        sp2.setSuperPowerName("Fire Power");
        sp2.setSuperPowerDescription("Control the elements of fire");
        
        dao.addSuperpower(sp1);
        dao.addSuperpower(sp2);
        
        Hero hero1 = new Hero();
        hero1.setHeroName("Ice Slider");
        hero1.setHeroDescription("A man that hails from the glacial islands");

        Hero hero2 = new Hero();
        hero2.setHeroName("BucketHead");
        hero2.setHeroDescription("A man with a buckethead");
        
        heroDao.addHero(hero1);
        heroDao.addHero(hero2);
        
        dao.insertHeroSuperPower(sp1, hero1);
        dao.insertHeroSuperPower(sp2, hero2);
        
        Superpower fromDao1 = dao.getHeroesSuperPowers(hero1, Integer.MAX_VALUE, 0).get(0);
        Superpower fromDao2 = dao.getHeroesSuperPowers(hero2, Integer.MAX_VALUE, 0).get(0);
        
        assertEquals(sp1,fromDao1);
        assertEquals(sp2,fromDao2);  
    }
    
}
