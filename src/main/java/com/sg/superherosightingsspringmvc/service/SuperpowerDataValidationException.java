/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.service;

/**
 *
 * @author sbrown6
 */
public class SuperpowerDataValidationException extends Exception{

    public SuperpowerDataValidationException(String message) {
        super(message);
    }

    public SuperpowerDataValidationException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
