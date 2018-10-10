/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.dao;

import com.sg.superherosightingsspringmvc.dto.Member;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author sbrown6
 */
public class MemberMapper implements RowMapper<Member>{

    @Override
    public Member mapRow(ResultSet rs, int i) throws SQLException {
        Member mem = new Member();
        mem.setMemberId(rs.getInt("MemberID"));
        mem.setFirstName(rs.getString("FirstName"));
        mem.setLastName(rs.getString("LastName"));
        mem.setOrganizationID(rs.getInt("OrganizationID"));
        
        return mem;
    }
    
}
