/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.dao;

import com.sg.superherosightingsspringmvc.dto.Superpower;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author sbrown6
 */
public class SuperPowerMapper implements RowMapper<Superpower>{
        
    @Override
        public Superpower mapRow(ResultSet rs, int i) throws SQLException {
            Superpower sp = new Superpower();
            sp.setSuperPowerId(rs.getInt("SuperPowerID"));
            sp.setSuperPowerName(rs.getString("SuperPowerName"));
            sp.setSuperPowerDescription(rs.getString("SuperPowerDescription"));
            
            return sp;
        }
        
}
