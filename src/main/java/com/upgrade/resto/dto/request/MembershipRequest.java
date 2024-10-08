package com.upgrade.resto.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MembershipRequest {
    @NotBlank(message = "Membership name data must be filled")
    private String membershipName;
    @NotBlank(message = "Benefit data must be filled")
    private String benefit;
}
