/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsspringmvc.service;

import com.sg.superherosightingsspringmvc.dao.OrganizationPersistenceException;
import com.sg.superherosightingsspringmvc.dao.MemberPersistenceException;
import com.sg.superherosightingsspringmvc.dao.MemberDao;
import com.sg.superherosightingsspringmvc.dto.Member;
import com.sg.superherosightingsspringmvc.dto.Organization;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sbrown6
 */
public class MemberServiceLayerImpl implements MemberServiceLayer{
    private MemberDao memberDao;
    private OrganizationServiceLayer orgServiceLayer;

    public MemberServiceLayerImpl(MemberDao memberDao, OrganizationServiceLayer orgServiceLayer) {
        this.memberDao = memberDao;
        this.orgServiceLayer = orgServiceLayer;
    }
    
    @Override
    public Member getMember(int memberId) throws  MemberPersistenceException, MemberDataValidationException {
            if(memberDao.getMember(memberId) == null){
                throw new MemberDataValidationException("Member ID does not exist");
            }
            return memberDao.getMember(memberId);
    }

    @Override
    public Member createMember(Member member) throws MemberDataValidationException, MemberPersistenceException {
        Member newMember = validateMember(member);
        return memberDao.addMember(newMember);


    }

    @Override
    public void removeMember(int memberId) throws MemberPersistenceException, MemberDataValidationException {
        getMember(memberId);
        memberDao.removeMember(memberId);
    }
    
    @Override
    public Member editMember(Member member) throws MemberDataValidationException, MemberPersistenceException {
        getMember(member.getMemberId());
        validateMember(member);
        return memberDao.editMember(member);    
    }

    @Override
    public List<Member> getAllMembers(int limit, int offset) throws  MemberPersistenceException {
            return memberDao.getAllMembers(limit, offset);
    }
    
    @Override
    public List<Member> findAllMembersAtOrganization(Organization org, int limit, int offset) throws MemberPersistenceException, OrganizationPersistenceException, OrganizationDataValidationException {
        orgServiceLayer.getOrganization(org.getOrganizationId());
        return memberDao.findAllMembersAtOrganization(org, limit, offset);
    }
    private Member validateMember(Member member) throws MemberDataValidationException{
        if(member.getFirstName()==null || member.getFirstName().trim().length()==0
                || member.getLastName()== null || member.getLastName().trim().length()==0
                ||member.getOrganizationID()==0){
                throw new MemberDataValidationException("ERROR: All fields [First Name, Last Name, OrganizationID] are required.");    
        }else{
            return member;
        }
    }

 
}
