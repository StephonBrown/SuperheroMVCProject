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

/**
 *
 * @author sbrown6
 */
public class UserServiceLayerImpl implements UserServiceLayer {
    private UserDao userDao;

    public UserServiceLayerImpl(UserDao userDao) {
        this.userDao = userDao;
    }
    
    @Override
    public User createUser(User newUser) throws UserPersistenceException, UserDataValidationException {
        if(userDao.getUser(newUser.getId()) != null){
            throw new UserDataValidationException("This username already exists");
        }else{
            return validateUser(userDao.addUser(newUser));
        }
        
    }

    @Override
    public void deleteUser(int user_id) throws UserPersistenceException, UserDataValidationException {
        if(userDao.getUser(user_id) == null){
            throw new UserDataValidationException("This user does not exists");
        }else{
            userDao.deleteUser(user_id);
        }
    }
    

    @Override
    public User editUser(User editedUser, String oldUsername) throws UserPersistenceException, UserDataValidationException {
        if(userDao.getUser(editedUser.getId()) == null){
            throw new UserDataValidationException("This username does not exists");
        }else{
            return validateUser(userDao.editUser(editedUser, oldUsername));
        }
    }

    @Override
    public List<User> getAllUsers(int limit, int offset) {
        return userDao.getAllUsers(limit, offset);
    }

    @Override
    public User getUser(int user_id) throws UserPersistenceException {
        return userDao.getUser(user_id);
    }
    
    private User validateUser(User user) throws UserDataValidationException{
        if(user.getUsername()==null || user.getUsername().trim().length()==0||
                user.getPassword()==null || user.getPassword().trim().length()==0){
            throw new UserDataValidationException("ERROR: All fields [User Username  , User Password] are required.");
                    
        }else{
            return user;
        }
    }
    
}
