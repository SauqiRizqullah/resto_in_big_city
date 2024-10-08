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
public class MenuRequest {
    @NotBlank(message = "Menu name can't empty")
    private String menuName;

    @NotNull(message = "Price is a one of important aspect when you sell your product in a business. You must FILL IT!!!")
    @Min(value = 0, message = "Are sure want to sell your product with 0 or less price?, REPEAT YOUR STEP!!!")
    private Long menuPrice;
}
