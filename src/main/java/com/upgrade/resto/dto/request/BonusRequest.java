package com.upgrade.resto.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BonusRequest {
    @NotBlank(message = "Bonus name data must be filled")
    private String bonusName;
    @NotNull(message = "Poin cost data must be filled")
    @Min(1)
    private Integer poinCost;
}
