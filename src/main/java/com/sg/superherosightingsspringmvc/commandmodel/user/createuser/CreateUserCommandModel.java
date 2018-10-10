/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.commandmodel.user.createuser;

import java.util.ArrayList;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author sbrown6
 */
public class CreateUserCommandModel {
    @NotNull
    @NotEmpty(message = "You must supply a value for username.")
    @Length(max = 20, message = "Username must be no more than 20 characters")
    private String username;
    
    @NotNull
    @NotEmpty(message = "You must supply a value for password.")
    @Length(max = 20, message = "Password must be no more than 100 characters")
    private String password;
    
    private ArrayList<String> authorities = new ArrayList<>();

    public CreateUserCommandModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(ArrayList<String> authorities) {
        this.authorities = authorities;
    }

   
    
}
