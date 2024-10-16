package com.upgrade.resto.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class RestaurantResponse {
    private String restoId;
    private String outlet;
    private String city;
    private String province;
    private String contact;
    private List<WaiterResponse> waiterResponses;
}
