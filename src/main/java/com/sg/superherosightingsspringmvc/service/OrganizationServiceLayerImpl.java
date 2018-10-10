/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.service;

import com.sg.superherosightingsspringmvc.dao.HeroPersistenceException;
import com.sg.superherosightingsspringmvc.dao.LocationPersistenceException;
import com.sg.superherosightingsspringmvc.dao.OrganizationPersistenceException;
import com.sg.superherosightingsspringmvc.dao.OrganizationDao;
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
public class OrganizationServiceLayerImpl implements OrganizationServiceLayer{
    
    private OrganizationDao orgDao;
    private LocationServiceLayer locServiceLayer;
    private HeroServiceLayer heServiceLayer;
    
    public OrganizationServiceLayerImpl(OrganizationDao orgDao, LocationServiceLayer locServiceLayer, HeroServiceLayer heServiceLayer) {
        this.orgDao = orgDao;
        this.locServiceLayer = locServiceLayer;
        this.heServiceLayer = heServiceLayer;
    }
    
    @Override
    public Organization getOrganization(int organizationId) throws OrganizationPersistenceException,OrganizationDataValidationException {
        if(orgDao.getOrganization(organizationId) == null){
            throw new OrganizationDataValidationException("This is not a valid Organization ID");
        }
        return orgDao.getOrganization(organizationId);    
    }
    

    @Override
    public Organization createOrganization(Organization organization) throws OrganizationDataValidationException, OrganizationPersistenceException, LocationPersistenceException, LocationDataValidationException {
            Organization newOrg = validateOrganization(organization);
            return orgDao.addOrganization(newOrg);
    }

    @Override
    public void removeOrganization(int organizationId) throws OrganizationPersistenceException, OrganizationDataValidationException {
        getOrganization(organizationId);
        orgDao.removeOrganization(organizationId);
    }

    @Override
    public Organization editOrganization(Organization org) throws OrganizationDataValidationException, OrganizationPersistenceException, LocationPersistenceException, LocationDataValidationException {
        getOrganization(org.getOrganizationId());
        validateOrganization(org);
        return orgDao.editOrganization(org);
    }

    @Override
    public List<Organization> getAllOrganizations(int limit, int offset) throws OrganizationPersistenceException {
            return orgDao.getAllOrganizations( limit, offset);
    }
    
    @Override
    public List<Organization> getHeroOrganizations(Hero hero, int limit, int offset) throws OrganizationPersistenceException,HeroPersistenceException, HeroDataValidationException {
        heServiceLayer.getHero(hero.getHeroId());
        return orgDao.getHeroOrganizations(hero, limit, offset);
    }
    @Override
    public void insertOrganizationHero(Organization org, Hero hero) throws OrganizationPersistenceException, OrganizationDataValidationException, HeroPersistenceException, HeroDataValidationException {
        getOrganization(org.getOrganizationId());
        heServiceLayer.getHero(hero.getHeroId());
        orgDao.insertOrganizationHero(org, hero);
    }
    @Override
    public List<Organization> getOrganizationsByLocationId(Location location, int limit, int offset) throws OrganizationPersistenceException, OrganizationDataValidationException,LocationPersistenceException,LocationDataValidationException {     
        locServiceLayer.getLocation(location.getLocationId());
        return orgDao.getOrganizationsByLocationId(location, limit, offset);

    }
    private Organization validateOrganization(Organization org) throws OrganizationDataValidationException, LocationPersistenceException, LocationDataValidationException{
        if(org.getOrganizationName()==null || org.getOrganizationName().trim().length()==0 ||
                org.getOrganizationDescription()==null || org.getOrganizationDescription().trim().length()==0||
                org.getTelephoneNumber()==null|| org.getTelephoneNumber().trim().length()==0)
        {
            throw new OrganizationDataValidationException("ERROR: All fields [Organization Name, Organization Description,Telephone Number,LocationID] are required.");
        }else if(locServiceLayer.getLocation(org.getLocationId()) == null){
            throw new LocationDataValidationException("ERROR: LocationID is not valid.");
        }
        else{
            return org;
        }
    }


}
