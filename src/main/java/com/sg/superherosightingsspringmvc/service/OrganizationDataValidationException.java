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
public class OrganizationDataValidationException extends Exception{

    public OrganizationDataValidationException(String message) {
        super(message);
    }

    public OrganizationDataValidationException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
