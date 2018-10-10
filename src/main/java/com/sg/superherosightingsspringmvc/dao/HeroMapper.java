/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.dao;

import com.sg.superherosightingsspringmvc.dto.Hero;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author sbrown6
 */
public class HeroMapper implements RowMapper<Hero>{
    
        @Override
        public Hero mapRow(ResultSet rs, int i) throws SQLException {
            Hero h = new Hero();
            h.setHeroId(rs.getInt("HeroID"));
            h.setHeroName(rs.getString("HeroName"));
            h.setHeroDescription(rs.getString("HeroDescription"));
            return h;
        }
}
