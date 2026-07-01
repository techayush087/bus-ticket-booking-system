package com.busticket.mapper.response;

import com.busticket.dto.response.RouteResponse;
import com.busticket.entity.Route;

public class RouteResponseMapper {

    public static RouteResponse entityToResponse(Route route) {
        return RouteResponse.builder()
                .routeId(route.getRouteId())
                .fromCity(route.getFromCity())
                .toCity(route.getToCity())
                .breakPoints(route.getBreakPoints())
                .duration(route.getDuration())
                .durationFormatted(formatDuration(route.getDuration()))
                .build();
    }

    public static Route responseToEntity(RouteResponse response) {
        Route route = new Route();
        route.setRouteId(response.getRouteId());
        route.setFromCity(response.getFromCity());
        route.setToCity(response.getToCity());
        route.setBreakPoints(response.getBreakPoints());
        route.setDuration(response.getDuration());
        return route;
    }

    private static String formatDuration(Integer hours) {
        if (hours == null) return null;
        return hours + "h 0m";
    }
}
