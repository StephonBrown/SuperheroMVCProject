/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.dao;

import com.sg.superherosightingsspringmvc.dto.User;
import java.util.ArrayList;
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
public class UserDaoTest {
    private UserDao userDao;
    
    public UserDaoTest() {
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
        userDao = ctx.getBean("UserDao", UserDao.class);
        
        for(User currentUser: userDao.getAllUsers(Integer.MAX_VALUE, 0)){
            userDao.deleteUser(currentUser.getId());
        }
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addUser method, of class UserDao.
     */
    @Test
    public void testAddAndGetUser() throws Exception {
        ArrayList<String> authority1 = new ArrayList();
        
        authority1.add("ROLE_USER");
        
        User user = new User();
        user.setUsername("TephonBrown");
        user.setPassword("password");
        userDao.addUser(user);
        
        User fromDao = userDao.getUser(user.getId());

        assertEquals(user,fromDao);
        
        
        
    }

    /**
     * Test of deleteUser method, of class UserDao.
     */
    @Test
    public void testDeleteUser() throws Exception {
        ArrayList<String> authority1 = new ArrayList();
        ArrayList<String> authority2 = new ArrayList();
        
        authority1.add("ROLE_USER");
        authority2.add("ROLE_ADMIN");
        
        
        User user1 = new User();
        user1.setUsername("TephonBrown");
        user1.setPassword("password");
        user1.setAuthorities(authority1);
        
        User user2 = new User();
        user2.setUsername("TephonBrownie");
        user2.setPassword("passwords");
        user2.setAuthorities(authority2);
        
        
        userDao.addUser(user1);
        userDao.addUser(user2);
        
        userDao.deleteUser(user1.getId());
        assertEquals(1, userDao.getAllUsers(Integer.MAX_VALUE, 0).size());
        assertNull(userDao.getUser(user1.getId()));
        
        userDao.deleteUser(user2.getId());
        assertEquals(0, userDao.getAllUsers(Integer.MAX_VALUE, 0).size());
        assertNull(userDao.getUser(user2.getId()));
        
    }

    /**
     * Test of editUser method, of class UserDao.
     */
    @Test
    public void testEditUser() throws Exception {
        ArrayList<String> authority1 = new ArrayList();
        ArrayList<String> authority2 = new ArrayList();
        
        authority1.add("ROLE_USER");
        authority2.add("ROLE_ADMIN");
        
        
        User user1 = new User();
        user1.setUsername("TephonBrown");
        user1.setPassword("password");
        user1.setAuthorities(authority1);
        
        User user2 = new User();
        user2.setUsername("TephonBrownie");
        user2.setPassword("passwords");
        user2.setAuthorities(authority2);
        
        
        userDao.addUser(user1);
        userDao.addUser(user2);
        
        user1.setUsername("MYname");
        userDao.editUser(user1, "TephonBrown");
        
        User fromDao = userDao.getUser(user1.getId());
        assertEquals(user1,fromDao);
    }

    /**
     * Test of getAllUsers method, of class UserDao.
     */
    @Test
    public void testGetAllUsers() throws Exception{
        ArrayList<String> authority1 = new ArrayList();
        ArrayList<String> authority2 = new ArrayList();
        
        authority1.add("ROLE_USER");
        authority2.add("ROLE_ADMIN");
        
        
        User user1 = new User();
        user1.setUsername("TephonBrown");
        user1.setPassword("password");
        user1.setAuthorities(authority1);
        
        User user2 = new User();
        user2.setUsername("TephonBrownie");
        user2.setPassword("passwords");
        user2.setAuthorities(authority2);
        
        
        userDao.addUser(user1);
        userDao.addUser(user2);
        
        assertEquals(2, userDao.getAllUsers(Integer.MAX_VALUE, 0).size());
        
    }
    
}
