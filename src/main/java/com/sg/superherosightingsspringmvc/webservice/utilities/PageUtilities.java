/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.webservice.utilities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Stephon
 */
public class PageUtilities {

    public static List<Integer> getPageNumbers(int startPage, int numberOfPages) {
        List<Integer> pages = new ArrayList<>();

        for (int i = startPage; i < numberOfPages + startPage; i++ ) {
            pages.add(i);
        }
        return pages;
    }
    
    //calculates teh current page
    public static Integer calculatePageNumber(int limit, int offset) {
        return offset/limit + 1;
    }

}
