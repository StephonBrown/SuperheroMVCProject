/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.viewmodel.location.locationhome;

import java.util.List;

/**
 *
 * @author Stephon
 */
public class LocationHomeViewModel {
    private List<LocationViewModel> locs;
    private List<Integer> pageNumbers;
    private Integer pageNumber;

    public List<LocationViewModel> getLocs() {
        return locs;
    }

    public void setLocs(List<LocationViewModel> locs) {
        this.locs = locs;
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
