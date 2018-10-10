/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.dao;

import com.sg.superherosightingsspringmvc.dto.Member;
import com.sg.superherosightingsspringmvc.dto.Organization;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sbrown6
 */
public class MemberDaoDbImpl implements MemberDao{
    JdbcTemplate jdbc;
    
    private final String SQL_SELECT_MEMBER= 
            "select * from Member where MemberID = ?";
    private final String SQL_ADD_MEMBER = 
            "insert into Member (FirstName, LastName, OrganizationID)"+
            "values(?,?,?)";
    private final String SQL_REMOVE_MEMBER = 
            "delete from Member where MemberID = ?";
    private final String SQL_UPDATE_MEMBER =
            "update Member set FirstName=?, LastName=?, OrganizationID=? where MemberID = ? ";
    private final String SQL_SELECT_ALL_MEMBERS = 
            "select * from Member LIMIT ? OFFSET ?";
    private final String SQL_SELECT_ALL_MEMBERS_OF_ORGANIZATION=
            " select me.memberId, me.FirstName, me.LastName, me.OrganizationID from Member me"+
            " join Organization org on me.OrganizationID = org.OrganizationID where org.OrganizationID = ? LIMIT ? OFFSET ?";
  
    public MemberDaoDbImpl(JdbcTemplate jdbc){
        this.jdbc=jdbc;
    }
    
    @Override
    public Member getMember(int memberId){
        try{
            return jdbc.queryForObject(SQL_SELECT_MEMBER,new MemberMapper(), memberId);     
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly=false)
    public Member addMember(Member member) throws MemberPersistenceException{
        try{
            jdbc.update(SQL_ADD_MEMBER, member.getFirstName(), member.getLastName(), member.getOrganizationID());
            member.setMemberId(jdbc.queryForObject("select LAST_INSERT_ID()", Integer.class));
            return member; 
        }catch(DataAccessException e){
            throw new MemberPersistenceException("There is a problem connecting to the database");
        }

    }

    @Override
    public void removeMember(int memberId) throws MemberPersistenceException{
        try{
            jdbc.update(SQL_REMOVE_MEMBER, memberId);
        }catch(DataAccessException e){
            throw new MemberPersistenceException("There is a problem connecting to the database");
        }
    }

    @Override
    public Member editMember(Member member) throws MemberPersistenceException{
        try{
            jdbc.update(SQL_UPDATE_MEMBER, member.getFirstName(), member.getLastName(), member.getOrganizationID(), member.getMemberId());
            return member;
        }catch(DataAccessException e){
            throw new MemberPersistenceException("There is a problem connecting to the database");
        }
    }
    
    @Override
    public List<Member> getAllMembers(int limit, int offset){
        try{
            return jdbc.query(SQL_SELECT_ALL_MEMBERS, new MemberMapper(), limit, offset);     
        }catch(EmptyResultDataAccessException e){
            return null;
        }       
    }

    @Override
    public List<Member> findAllMembersAtOrganization(Organization organization, int limit, int offset){
        try{
            return jdbc.query(SQL_SELECT_ALL_MEMBERS_OF_ORGANIZATION, new MemberMapper(), organization.getOrganizationId(),limit,  offset);     
        }catch(EmptyResultDataAccessException e){
            return null;
        }       
    }
    
}
