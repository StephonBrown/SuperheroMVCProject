/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.service;

import com.sg.superherosightingsspringmvc.dao.HeroPersistenceException;
import com.sg.superherosightingsspringmvc.dao.LocationPersistenceException;
import com.sg.superherosightingsspringmvc.dao.OrganizationPersistenceException;
import com.sg.superherosightingsspringmvc.dto.Hero;
import com.sg.superherosightingsspringmvc.dto.Location;
import com.sg.superherosightingsspringmvc.dto.Member;
import com.sg.superherosightingsspringmvc.dto.Organization;
import java.util.List;

/**
 *
 * @author sbrown6
 */
public interface OrganizationServiceLayer {
    Organization getOrganization(int organizationId) throws OrganizationPersistenceException, OrganizationDataValidationException;
    
    Organization createOrganization(Organization organization) throws OrganizationDataValidationException, OrganizationPersistenceException, LocationPersistenceException, LocationDataValidationException;
    
    void removeOrganization(int organizationId)throws OrganizationPersistenceException, OrganizationDataValidationException;
    
    Organization editOrganization(Organization org)throws OrganizationDataValidationException, OrganizationPersistenceException, LocationPersistenceException, LocationDataValidationException;
    
    List<Organization> getAllOrganizations(int limit, int offset)throws OrganizationPersistenceException;
    
    List<Organization> getHeroOrganizations(Hero hero, int limit, int offset) throws OrganizationPersistenceException, HeroPersistenceException, HeroDataValidationException;
    
    List<Organization> getOrganizationsByLocationId(Location location, int limit, int offset) throws OrganizationPersistenceException, OrganizationDataValidationException,LocationPersistenceException,LocationDataValidationException;

    void insertOrganizationHero(Organization org, Hero hero) throws OrganizationPersistenceException,OrganizationDataValidationException, HeroPersistenceException, HeroDataValidationException;

}
