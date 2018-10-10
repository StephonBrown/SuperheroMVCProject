/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.dao;

import com.sg.superherosightingsspringmvc.dto.Location;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author sbrown6
 */
public class LocationMapper implements RowMapper<Location>{

    @Override
    public Location mapRow(ResultSet rs, int i) throws SQLException {
        Location lo = new Location();
        lo.setLocationId(rs.getInt("LocationID"));
        lo.setLocationName(rs.getString("LocationName"));
        lo.setLocationDescription(rs.getString("LocationDescription"));
        lo.setStreet(rs.getString("Street"));
        lo.setCity(rs.getString("City"));
        lo.setState(rs.getString("State"));
        lo.setZipCode(rs.getString("ZipCode"));
        lo.setLongitude(new BigDecimal(rs.getString("Longitude")));
        lo.setLatitude(new BigDecimal(rs.getString("Latitude")));
        
        return lo;
    }
    
}
