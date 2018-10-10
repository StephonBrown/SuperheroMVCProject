/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.webservice.interfaces;

import com.sg.superherosightingsspringmvc.commandmodel.user.createuser.CreateUserCommandModel;
import com.sg.superherosightingsspringmvc.commandmodel.user.edituser.EditUserCommandModel;
import com.sg.superherosightingsspringmvc.dao.UserPersistenceException;
import com.sg.superherosightingsspringmvc.dto.User;
import com.sg.superherosightingsspringmvc.service.UserDataValidationException;
import com.sg.superherosightingsspringmvc.service.UserServiceLayer;
import com.sg.superherosightingsspringmvc.viewmodel.user.createuser.CreateUserViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.user.edituser.EditUserViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.user.userhome.UserHomeViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.user.userhome.UserViewModel;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sbrown6
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test-applicationContext.xml"})
@Rollback
@Transactional
public class UserWebServicesTest {
    @Inject
    UserWebServices userWebServices;
    @Inject
    UserServiceLayer userServiceLayer;

    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getUSerHomeViewModel method, of class UserWebServices.
     */
    @Test
    public void testGetUSerHomeViewModel() throws Exception{
        for(User currentUser: userServiceLayer.getAllUsers(Integer.MAX_VALUE, 0)){
            userServiceLayer.deleteUser(currentUser.getId());
        }
        
        List<User> users = createTestUsers(15);
        
        UserHomeViewModel userHomeViewModel = userWebServices.getUSerHomeViewModel(5, 0, 5);
        
        assert userHomeViewModel.getUsers().size()==5;
        assert userHomeViewModel.getPageNumber() == 1;
        
        assert userHomeViewModel.getPageNumbers().size() == 5;
        
        assert userHomeViewModel.getPageNumbers().get(1) == 2;
        assert userHomeViewModel.getPageNumbers().get(4) == 5;
        
        int count = 0;
        for(UserViewModel userViewModel: userHomeViewModel.getUsers()){
            assert("Tephon"+count).equals(userViewModel.getUsername());
            assert("password"+count).equals(userViewModel.getPassword()); 
            
            User sameUser = users.get(count);
            User user = userServiceLayer.getUser(sameUser.getId());
  
            assert user.getId() == userViewModel.getId();
            assert user.getUsername().equals(userViewModel.getUsername());
            assert user.getPassword().equals(userViewModel.getPassword());

            count++;                       
        }
    }

    /**
     * Test of getEditUserViewModel method, of class UserWebServices.
     */
    @Test
    public void testGetEditUserViewModel() throws Exception {
        
        User user = new User();
        user.setUsername("Tephon");
        user.setPassword("password");
        
        User existingUser = userServiceLayer.createUser(user);
        
        EditUserViewModel editUserViewModel = userWebServices.getEditUserViewModel(existingUser.getId()); 
        
        EditUserCommandModel commandModel = editUserViewModel.getEditUserCommandModel();
        
        assert existingUser.getId() == commandModel.getId();
        assert existingUser.getUsername().equals(commandModel.getUsername());
        assert existingUser.getPassword().equals(commandModel.getPassword());
    }

    /**
     * Test of saveCreateUserCommandModel method, of class UserWebServices.
     */
    @Test
    public void testSaveCreateUserCommandModel() throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        CreateUserCommandModel commandModel = new CreateUserCommandModel();
        commandModel.setUsername("Tephon");
        commandModel.setPassword("password");

        User user = userWebServices.saveCreateUserCommandModel(commandModel);
        
        assert user.getId() != 0;
        assert user.getUsername().equals("Tephon");
    }

    /**
     * Test of saveEditUserCommandModel method, of class UserWebServices.
     */
    @Test
    public void testSaveEditUserCommandModel() throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setUsername("Tephon");
        user.setPassword("password");
        
        
        ArrayList<String> authority1 = new ArrayList();
        authority1.add("ROLE_USER");
        
        User existingUser = userServiceLayer.createUser(user);
        
        EditUserCommandModel commandModel = new EditUserCommandModel();
        commandModel.setId(existingUser.getId());
        commandModel.setUsername("Cheesy");
        commandModel.setPassword("provolone1");
        commandModel.setAuthorities(authority1);
        
        
        User userFromModel = userWebServices.saveEditUserCommandModel(commandModel, "Tephon");
        
        assert userFromModel.getId() != 0;
        assert userFromModel.getUsername().equals("Cheesy");

    }
    
    private List<User> createTestUsers(int numberOfUsers) throws UserPersistenceException, UserDataValidationException{
        List<User> users = new ArrayList();
        
        for(int i=0;i< numberOfUsers; i ++){
            User user = new User();
            user.setUsername("Tephon"+i);
            user.setPassword("password"+i);
            
            userServiceLayer.createUser(user); 
            users.add(user);
            
        }
        
        return users;

    }
    
}
