/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.viewmodel.sighting.sightinghome;

import java.util.List;

/**
 *
 * @author Stephon
 */
public class SightingHomeViewModel {
    private List<SightingViewModel> sts;
    private List<Integer> pageNumbers;
    private Integer pageNumber;

    public List<SightingViewModel> getSts() {
        return sts;
    }

    public void setSts(List<SightingViewModel> sts) {
        this.sts = sts;
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
