/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.dao;

import com.sg.superherosightingsspringmvc.dto.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author sbrown6
 */
public class UserDaoDbImpl implements UserDao{

    private static final String SQL_INSERT_USER
        = "insert into users (username, password, enabled) values (?, ?, 1)";
    private static final String SQL_INSERT_AUTHORITY
        = "insert into authorities (username, authority) values (?, ?)";
    private final String SQL_UPDATE_USER =
            "update users set username=?, password=? where user_id = ? ";
    private static final String SQL_DELETE_USER
        = "delete from users where user_id = ?";
    private static final String SQL_DELETE_AUTHORITIES
        = "delete from authorities where username= ?";
    private static final String SQL_GET_ALL_USERS
        = "select * from users LIMIT ? OFFSET ?";
    private static final String SQL_GET_USER
        = "select * from users where user_id=?";

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User addUser(User newUser)throws UserPersistenceException{
        try{   
            jdbcTemplate.update(SQL_INSERT_USER, newUser.getUsername(), newUser.getPassword());
            newUser.setId(jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class));
            // now insert user's roles
            ArrayList<String> authorities = newUser.getAuthorities();
            for (String authority : authorities) {
                jdbcTemplate.update(SQL_INSERT_AUTHORITY, newUser.getUsername(), authority);
            }
            return newUser;
        }catch(DataAccessException e){
            throw new UserPersistenceException("There is a problem connecting to the database");
        }
        
    }

    @Override
    public void deleteUser(int user_id) throws UserPersistenceException{
        try{
            User user = getUser(user_id);
            // first delete all authorities for this user
            jdbcTemplate.update(SQL_DELETE_AUTHORITIES, user.getUsername());
            // second delete the user
            jdbcTemplate.update(SQL_DELETE_USER, user_id);
        }catch(DataAccessException e){
            throw new UserPersistenceException("There is a problem connecting to the database");
        }
       
    }

    @Override
    public List<User> getAllUsers(int limit, int offset) {
        try{
            return jdbcTemplate.query(SQL_GET_ALL_USERS, new UserMapper(), limit, offset);
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }
    @Override
    public User editUser(User editedUser, String oldUsername) throws UserPersistenceException{
        try{
            jdbcTemplate.update(SQL_DELETE_AUTHORITIES, oldUsername);
            jdbcTemplate.update(SQL_UPDATE_USER,editedUser.getUsername(), editedUser.getPassword(), editedUser.getId());
               
        ArrayList<String> authorities = editedUser.getAuthorities();              
        for (String authority : authorities) {
            jdbcTemplate.update(SQL_INSERT_AUTHORITY, 
                                editedUser.getUsername(), 
                                authority);
        }
        
        return editedUser;
        
        }catch(DataAccessException e){
            throw new UserPersistenceException("There is a problem connecting to the database");
        }


    }

    @Override
    public User getUser(int user_id) throws UserPersistenceException {
        try{
            return jdbcTemplate.queryForObject(SQL_GET_USER, new UserMapper(),user_id);
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }
    
    private static final class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("user_id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            return user;
        }

    }

}
