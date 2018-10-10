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

/**
 *
 * @author sbrown6
 */
public interface OrganizationDao {
    
    Organization getOrganization(int organizationId);
    
    Organization addOrganization(Organization organization)throws OrganizationPersistenceException;
    
    void removeOrganization(int organizationId)throws OrganizationPersistenceException;
    
    Organization editOrganization(Organization org)throws OrganizationPersistenceException;
    
    List<Organization> getAllOrganizations(int limit, int offset);
    
    List<Organization> getHeroOrganizations(Hero hero, int limit, int offset);
    
    List<Organization> getOrganizationsByLocationId(Location location, int limit, int offset);
    
    void insertOrganizationHero(Organization org, Hero hero) throws OrganizationPersistenceException;
}
