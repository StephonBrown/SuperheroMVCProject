/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.dao;

import com.sg.superherosightingsspringmvc.dto.Member;
import com.sg.superherosightingsspringmvc.dto.Organization;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sbrown6
 */
public interface MemberDao {
    
    Member getMember(int memberId) ;
    
    Member addMember(Member member)throws MemberPersistenceException;
    
    void removeMember(int memberId)throws MemberPersistenceException;
    
    Member editMember(Member member)throws MemberPersistenceException;
    
    List<Member> getAllMembers(int limit, int offset);
    
    List<Member> findAllMembersAtOrganization(Organization org, int limit, int offset);
    

}
