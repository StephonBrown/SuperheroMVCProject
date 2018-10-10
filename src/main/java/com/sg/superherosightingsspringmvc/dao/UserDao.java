/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.dao;

/**
 *
 * @author sbrown6
 */
import com.sg.superherosightingsspringmvc.dto.User;
import java.util.List;

public interface UserDao {

    User addUser(User newUser) throws UserPersistenceException;
    
    User getUser(int user_id) throws UserPersistenceException;
    
    void deleteUser(int user_id) throws UserPersistenceException;
    
    User editUser(User editedUser, String oldUsername) throws UserPersistenceException;

    List<User> getAllUsers(int limit, int offset);

}
