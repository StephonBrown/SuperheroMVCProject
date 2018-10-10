/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.service;

import com.sg.superherosightingsspringmvc.dao.UserDao;
import com.sg.superherosightingsspringmvc.dao.UserPersistenceException;
import com.sg.superherosightingsspringmvc.dto.User;
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
public class UserServiceLayerTest {
    private UserServiceLayer userServiceLayer;
    
    public UserServiceLayerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp()throws Exception{
        ApplicationContext ctx = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        userServiceLayer = ctx.getBean("UserServiceLayer", UserServiceLayer.class);
        
        for(User currentUser: userServiceLayer.getAllUsers(Integer.MAX_VALUE, 0)){
            userServiceLayer.deleteUser(currentUser.getId());
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createUser method, of class UserServiceLayer.
     */
    @Test
    public void testCreateAndGetUser() throws Exception {
        User user = new User();
        user.setUsername("TephonBrown");
        user.setPassword("password");
        
        userServiceLayer.createUser(user);
        
        User fromDao = userServiceLayer.getUser(user.getId());
        
        assertEquals(user,fromDao);
    }

    /**
     * Test of getUser method, of class UserServiceLayer.
     */
    @Test
    public void testCreateInvalidData() throws Exception {
        User user = new User();
        user.setUsername("TephonBrown");
        user.setPassword(" ");
        try{
           userServiceLayer.createUser(user);
           fail("Expected UserDataValidationException was not thrown");
        }catch(UserDataValidationException e){
            return;
        }

    }

    /**
     * Test of deleteUser method, of class UserServiceLayer.
     */
    @Test
    public void testDeleteUser() throws Exception {
        User user1 = new User();
        user1.setUsername("TephonBrown");
        user1.setPassword("password");
        
        User user2 = new User();
        user2.setUsername("TephonBrownie");
        user2.setPassword("passwords");
        
        userServiceLayer.createUser(user1);
        userServiceLayer.createUser(user2);
        
        userServiceLayer.deleteUser(user1.getId());
        assertEquals(1, userServiceLayer.getAllUsers(Integer.MAX_VALUE, 0).size());
        assertNull(userServiceLayer.getUser(user1.getId()));
        
        userServiceLayer.deleteUser(user2.getId());
        assertEquals(0, userServiceLayer.getAllUsers(Integer.MAX_VALUE, 0).size());
        assertNull(userServiceLayer.getUser(user2.getId()));
    }

    /**
     * Test of editUser method, of class UserServiceLayer.
     */
    @Test
    public void testEditUser() throws Exception {
        User user1 = new User();
        user1.setUsername("TephonBrown");
        user1.setPassword("password");
        
        User user2 = new User();
        user2.setUsername("TephonBrownie");
        user2.setPassword("passwords");
        
        userServiceLayer.createUser(user1);
        userServiceLayer.createUser(user2);
        
        user1.setUsername("MYname");
        userServiceLayer.editUser(user1, "TephonBrown");
        
        User fromDao = userServiceLayer.getUser(user1.getId());
        assertEquals(user1,fromDao);

    }
    
    /**
     * Test of editUser method, of class UserServiceLayer.
     */
    @Test
    public void testEditUserInvalidData() throws Exception {
      try{
            User user1 = new User();
            user1.setUsername("TephonBrown");
            user1.setPassword("password");

            User user2 = new User();
            user2.setUsername("TephonBrownie");
            user2.setPassword("passwords");

            userServiceLayer.createUser(user1);
            userServiceLayer.createUser(user2);

            user1.setUsername(" ");
            userServiceLayer.editUser(user1, "TephonBrown");
            fail("Expected UserDataValidationException was not thrown");
        }catch(UserDataValidationException e){
            return;
        }
    }

    /**
     * Test of getAllUsers method, of class UserServiceLayer.
     */
    @Test
    public void testGetAllUsers() throws Exception{
        User user1 = new User();
        user1.setUsername("TephonBrown");
        user1.setPassword("password");
        
        User user2 = new User();
        user2.setUsername("TephonBrownie");
        user2.setPassword("passwords");
        
        userServiceLayer.createUser(user1);
        userServiceLayer.createUser(user2);
        
        assertEquals(2, userServiceLayer.getAllUsers(Integer.MAX_VALUE, 0).size());
    }
    
}
