/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.webservice.interfaces;

import com.sg.superherosightingsspringmvc.commandmodel.organization.createorganization.CreateOrganizationCommandModel;
import com.sg.superherosightingsspringmvc.commandmodel.organization.editorganization.EditOrganizationCommandModel;
import com.sg.superherosightingsspringmvc.dao.LocationPersistenceException;
import com.sg.superherosightingsspringmvc.dao.MemberPersistenceException;
import com.sg.superherosightingsspringmvc.dao.OrganizationPersistenceException;
import com.sg.superherosightingsspringmvc.dto.Organization;
import com.sg.superherosightingsspringmvc.service.LocationDataValidationException;
import com.sg.superherosightingsspringmvc.service.OrganizationDataValidationException;
import com.sg.superherosightingsspringmvc.viewmodel.organization.createorganization.CreateOrganizationViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.organization.editorganization.EditOrganizationViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.organization.organizationdetails.OrganizationDetailsViewModel;
import com.sg.superherosightingsspringmvc.viewmodel.organization.organizationhome.OrganizationHomeViewModel;
import com.sg.superherosightingsspringmvc.webservice.interfaces.OrganizationWebServices;

/**
 *
 * @author Stephon
 */
public interface OrganizationWebServices {
    OrganizationHomeViewModel getOrganizationHomeViewModel(Integer limit, Integer offset, Integer pageNumbers) throws OrganizationPersistenceException, LocationPersistenceException, LocationDataValidationException;
    OrganizationDetailsViewModel getOrganizationDetailsViewModel(int organizationId)throws MemberPersistenceException, OrganizationPersistenceException, OrganizationDataValidationException, LocationPersistenceException, LocationDataValidationException;
    
    CreateOrganizationViewModel getCreateOrganizationViewModel()throws OrganizationPersistenceException, OrganizationDataValidationException, LocationPersistenceException, LocationDataValidationException;
    EditOrganizationViewModel getEditOrganizationViewModel(int organizationId)throws OrganizationPersistenceException, OrganizationDataValidationException, LocationPersistenceException, LocationDataValidationException;
    
    Organization saveCreateOrganizationCommandModel(CreateOrganizationCommandModel createOrganizationCommandModel)throws OrganizationPersistenceException, OrganizationDataValidationException, LocationPersistenceException, LocationDataValidationException;
    Organization saveEditOrganizationCommandModel(EditOrganizationCommandModel editOrganizationCommandModel)throws OrganizationPersistenceException, OrganizationDataValidationException, LocationPersistenceException, LocationDataValidationException;

}
