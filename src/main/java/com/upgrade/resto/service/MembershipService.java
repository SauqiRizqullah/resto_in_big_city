package com.upgrade.resto.service;

import com.upgrade.resto.dto.request.MembershipRequest;
import com.upgrade.resto.dto.response.MembershipResponse;
import com.upgrade.resto.entity.Membership;

import java.util.List;

public interface MembershipService {
    MembershipResponse createNewMembership(MembershipRequest membershipRequest);

    MembershipResponse getById(String membershipId);

    List<Membership> getAllMemberships();

    String updateBenefitById(String membershipId, String newBenefit);

    String deleteById(String membershipId);

    long count();
}