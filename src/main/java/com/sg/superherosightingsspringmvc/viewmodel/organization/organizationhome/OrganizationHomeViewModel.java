/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.viewmodel.organization.organizationhome;

import java.util.List;

/**
 *
 * @author Stephon
 */
public class OrganizationHomeViewModel {
    private List<OrganizationViewModel> orgs;
    private List<Integer> pageNumbers;
    private Integer pageNumber;

    public List<OrganizationViewModel> getOrgs() {
        return orgs;
    }

    public void setOrgs(List<OrganizationViewModel> orgs) {
        this.orgs = orgs;
    }

    public List<Integer> getPageNumbers() {
        return pageNumbers;
    }

    public void setPageNumbers(List<Integer> pageNumbers) {
        this.pageNumbers = pageNumbers;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
}
