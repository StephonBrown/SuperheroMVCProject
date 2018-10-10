/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.service;

import com.sg.superherosightingsspringmvc.dao.OrganizationPersistenceException;
import com.sg.superherosightingsspringmvc.dao.MemberPersistenceException;
import com.sg.superherosightingsspringmvc.dto.Member;
import com.sg.superherosightingsspringmvc.dto.Organization;
import java.util.List;

/**
 *
 * @author sbrown6
 */
public interface MemberServiceLayer {
    
   Member getMember(int memberId) throws MemberPersistenceException, MemberDataValidationException;
   
   Member createMember(Member member) throws MemberDataValidationException, MemberPersistenceException;
   
   void removeMember(int memberId) throws MemberPersistenceException, MemberDataValidationException;
   
   Member editMember(Member member) throws MemberDataValidationException, MemberPersistenceException;
   
   List<Member> getAllMembers(int limit, int offset) throws MemberPersistenceException;
    
   List<Member> findAllMembersAtOrganization(Organization org, int limit, int offset) throws MemberPersistenceException, OrganizationPersistenceException, OrganizationDataValidationException;

}
