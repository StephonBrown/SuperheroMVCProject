/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.service;

import com.sg.superherosightingsspringmvc.dao.UserPersistenceException;
import com.sg.superherosightingsspringmvc.dto.User;
import java.util.List;

/**
 *
 * @author sbrown6
 */
public interface UserServiceLayer {
    User createUser(User newUser) throws UserPersistenceException, UserDataValidationException;
    
    User getUser(int user_id) throws UserPersistenceException;

    void deleteUser(int user_id) throws UserPersistenceException, UserDataValidationException;
    
    User editUser(User editedUser, String oldUsername) throws UserPersistenceException, UserDataValidationException;

    List<User> getAllUsers(int limit, int offset);
}
