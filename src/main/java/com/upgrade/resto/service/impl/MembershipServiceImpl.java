package com.upgrade.resto.service.impl;

import com.upgrade.resto.dto.request.MembershipRequest;
import com.upgrade.resto.dto.response.MembershipResponse;
import com.upgrade.resto.entity.Membership;
import com.upgrade.resto.repository.MembershipRepository;
import com.upgrade.resto.service.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipServiceImpl implements MembershipService {

    private final MembershipRepository membershipRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public MembershipResponse createNewMembership(MembershipRequest membershipRequest) {

        Membership membership = Membership.builder()
                .membershipName(membershipRequest.getMembershipName())
                .benefit(membershipRequest.getBenefit())
                .build();

        membershipRepository.saveAndFlush(membership);



        return parseMembershipToMembershipResponse(membership);
    }

    private MembershipResponse parseMembershipToMembershipResponse(Membership membership) {
        String id;
        if (membership.getMembershipId() == null){
            id = null;
        } else {
            id = membership.getMembershipId();
        }

        return MembershipResponse.builder()
                .membershipId(id)
                .membershipName(membership.getMembershipName())
                .benefit(membership.getBenefit())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public MembershipResponse getById(String membershipId) {
        Membership membership = membershipRepository.findById(membershipId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,membershipId + "'s data was not there!!!"));

        return parseMembershipToMembershipResponse(membership);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Membership> getAllMemberships() {
        return membershipRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String updateBenefitById(String membershipId, String newBenefit) {
        Membership membership = membershipRepository.findById(membershipId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,membershipId + "'s data was not there!!!"));

        membership.setBenefit(newBenefit);

        membershipRepository.saveAndFlush(membership);

        return membershipId + "'s data has been updated!!!";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String deleteById(String membershipId) {
        Membership membership = membershipRepository.findById(membershipId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,membershipId + "'s data was not there!!!"));

        membershipRepository.delete(membership);
        return membershipId + "'s data has been deleted!!!";
    }

    @Override
    public long count() {
        return membershipRepository.count();
    }
}
