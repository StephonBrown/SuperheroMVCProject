/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.dao;

import com.sg.superherosightingsspringmvc.dto.Sighting;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author sbrown6
 */
public class SightingMapper implements RowMapper<Sighting>{

    @Override
    public Sighting mapRow(ResultSet rs, int i) throws SQLException {
        Sighting sg = new Sighting();
        sg.setSightingId(rs.getInt("SightingID"));
        sg.setSightingDate(rs.getTimestamp("SightingDate").toLocalDateTime().toLocalDate());
        sg.setLocationID(rs.getInt("LocationID"));
        return sg;
    }
    
}
