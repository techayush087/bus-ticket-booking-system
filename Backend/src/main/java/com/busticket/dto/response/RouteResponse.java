// RouteResponse.java
package com.busticket.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteResponse {

    private Integer routeId;
    private String fromCity;
    private String toCity;
    private Integer breakPoints;
    private Integer duration;           // in minutes
    private String durationFormatted;   // e.g. "14h 30m" — derived, not stored
}