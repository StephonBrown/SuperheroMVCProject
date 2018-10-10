/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.dao;

import com.sg.superherosightingsspringmvc.dto.Hero;
import com.sg.superherosightingsspringmvc.dto.Location;
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
public class OrganizationDaoDbImpl implements OrganizationDao{
    JdbcTemplate jdbc;
    
    private final String SQL_SELECT_ORGANIZATION =
            "select * from Organization where OrganizationID = ?";
    private final String SQL_ADD_ORGANIZATION = 
            "insert into Organization (OrganizationName, OrganizationDescription, LocationId, TelephoneNumber)"+
            "values(?,?,?,?)";
    private final String SQL_REMOVE_ORGANIZATION=
            "delete from organization where organizationID = ?";
    private final String SQL_REMOVE_ORGANIZATION_HERO_ORGANIZATIONID=
            "delete from organization_hero where organizationID = ?";
    private final String SQL_REMOVE_MEMBER_ORGANIZATIONID=
            "delete from Member where organizationID = ?";
    private final String SQL_UPDATE_ORGANIZATION = 
            "update Organization set organizationName = ?, organizationDescription =?, locationId=?, TelephoneNumber=? where organizationID = ?";
    private final String SQL_SELECT_ALL_ORGANIZATIONS =
            "select * from Organization LIMIT ? OFFSET ?";
    private final String SQL_SELECT_ALL_ORGANIZATIONS_BY_LOCATIONID =
            "select * from Organization where LocationID = ? LIMIT ? OFFSET ?";
    private final String SQL_SELECT_ORGANIZATIONS_BY_HEROID = 
            "select og.OrganizationID, og.OrganizationName, og.OrganizationDescription, og.LocationID, og.TelephoneNumber from Organization og"+
            " join Organization_Hero oh on og.OrganizationID = oh.OrganizationID where oh.HeroID = ? LIMIT ? OFFSET ?";
    private final String SQL_INSERT_ORGANIZATION_HERO=
            "insert into Organization_Hero(OrganizationID, HeroID)"
            +"values(?,?)";
    
    public OrganizationDaoDbImpl(JdbcTemplate jdbc){
        this.jdbc=jdbc;
    }
    
    
    @Override
    public Organization getOrganization(int organizationId){
        try{
            return jdbc.queryForObject(SQL_SELECT_ORGANIZATION,new OrganizationMapper(), organizationId);     
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly=false)
    public Organization addOrganization(Organization organization) throws OrganizationPersistenceException{
        try{
            jdbc.update(SQL_ADD_ORGANIZATION,organization.getOrganizationName(),organization.getOrganizationDescription(),
                    organization.getLocationId(),organization.getTelephoneNumber());

            organization.setOrganizationId(jdbc.queryForObject("select LAST_INSERT_ID()", Integer.class));

            return organization;
        }catch(DataAccessException e){
            throw new OrganizationPersistenceException("There is a problem connecting to the database");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly=false)
    public void removeOrganization(int organizationId) throws OrganizationPersistenceException{
        try{
            jdbc.update(SQL_REMOVE_ORGANIZATION_HERO_ORGANIZATIONID, organizationId);
            jdbc.update(SQL_REMOVE_MEMBER_ORGANIZATIONID, organizationId);
            jdbc.update(SQL_REMOVE_ORGANIZATION, organizationId);
        }catch(DataAccessException e){
            throw new OrganizationPersistenceException("There is a problem connecting to the database");
        }
    }

        

    @Override
    public Organization editOrganization(Organization org) throws OrganizationPersistenceException{
        try{
            jdbc.update(SQL_UPDATE_ORGANIZATION, org.getOrganizationName(), org.getOrganizationDescription(),org.getLocationId(),
                    org.getTelephoneNumber(), org.getOrganizationId());  
            return org;
        }catch(DataAccessException e){
            throw new OrganizationPersistenceException("There is a problem connecting to the database");
        }
    }

    @Override
    public List<Organization> getAllOrganizations(int limit, int offset){
        try{
            return jdbc.query(SQL_SELECT_ALL_ORGANIZATIONS, new OrganizationMapper(),limit, offset);     
        }catch(EmptyResultDataAccessException e){
            return null;
        }    
    }
    
    @Override
    public List<Organization> getHeroOrganizations(Hero hero, int limit, int offset){
        try{
            List<Organization> orgs =  jdbc.query(SQL_SELECT_ORGANIZATIONS_BY_HEROID, new OrganizationMapper(), hero.getHeroId(), limit, offset);
            return orgs;   
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }
    @Override
    public List<Organization> getOrganizationsByLocationId(Location location, int limit, int offset){
        try{
            List<Organization> orgs =  jdbc.query(SQL_SELECT_ALL_ORGANIZATIONS_BY_LOCATIONID, new OrganizationMapper(), location.getLocationId(),  limit,  offset);
            return orgs;   
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }
    
    @Override
    public void insertOrganizationHero(Organization org, Hero hero) throws OrganizationPersistenceException{
        try{
            jdbc.update(SQL_INSERT_ORGANIZATION_HERO,org.getOrganizationId(), hero.getHeroId());
        }catch(DataAccessException e){
           throw new OrganizationPersistenceException("There is a problem connecting to the database");
        }
     }
}

