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
public class SuperpowerPersistenceException extends Exception{

    public SuperpowerPersistenceException(String message) {
        super(message);
    }

    public SuperpowerPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
