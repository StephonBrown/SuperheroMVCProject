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
public class MemberPersistenceException extends Exception{

    public MemberPersistenceException(String message) {
        super(message);
    }

    public MemberPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
