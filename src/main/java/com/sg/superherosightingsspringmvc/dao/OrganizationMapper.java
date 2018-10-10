/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.dao;

import com.sg.superherosightingsspringmvc.dto.Organization;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author sbrown6
 */
public class OrganizationMapper implements RowMapper<Organization>{

    @Override
    public Organization mapRow(ResultSet rs, int i) throws SQLException {
        Organization org = new Organization();
        org.setOrganizationId(rs.getInt("OrganizationID"));
        org.setOrganizationName(rs.getString("OrganizationName"));
        org.setOrganizationDescription(rs.getString("OrganizationDescription"));
        org.setLocationId(rs.getInt("LocationID"));
        org.setTelephoneNumber(rs.getString("TelephoneNumber"));        
        return org;
    }
    
}
