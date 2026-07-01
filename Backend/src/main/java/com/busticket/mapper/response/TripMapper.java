package com.busticket.mapper.response;

import com.busticket.dto.response.BusResponse;
import com.busticket.dto.response.DriverResponse;
import com.busticket.dto.response.RouteResponse;
import com.busticket.dto.response.TripFullDetailsResponse;
import com.busticket.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.apache.commons.lang3.time.DurationFormatUtils.formatDuration;

@Component
public class TripMapper {

    public TripFullDetailsResponse tripToFullDetailsResponse(
            Trip trip,
            Route route,
            Bus bus,
            List<Driver> drivers
    ) {
        return TripFullDetailsResponse.builder()
                .tripId(trip.getTripId())
                .route(mapRoute(route))
                .bus(mapBus(bus))
                .drivers(mapDrivers(drivers))
                .build();
    }

    private BusResponse mapBus(Bus bus) {
        if (bus == null) return null;

        AgencyOffice office = bus.getOffice();
        Agency agency = (office != null) ? office.getAgency() : null;

        return BusResponse.builder()
                .busId(bus.getBusId())

                // Office
                .officeId(office != null ? office.getOfficeId() : null)
                .officeMail(office != null ? office.getOfficeMail() : null)
                .officeContactPersonName(
                        office != null ? office.getOfficeContactPersonName() : null
                )

                // Agency (via office)
                .agencyId(agency != null ? agency.getAgencyId() : null)
                .agencyName(agency != null ? agency.getName() : null)

                .registrationNumber(bus.getRegistrationNumber())
                .capacity(bus.getCapacity())
                .type(bus.getType())
                .build();
    }

    private RouteResponse mapRoute(Route route) {
        if (route == null) return null;

        return RouteResponse.builder()
                .routeId(route.getRouteId())
                .fromCity(route.getFromCity())
                .toCity(route.getToCity())
                .breakPoints(route.getBreakPoints())
                .duration(route.getDuration())
                .durationFormatted(formatDuration(route.getDuration()))
                .build();
    }

    private String formatDuration(Integer minutes) {
        if (minutes == null) return null;

        int hours = minutes / 60;
        int mins = minutes % 60;

        return hours + "h " + mins + "m";
    }

    private List<DriverResponse> mapDrivers(List<Driver> drivers) {

        return drivers.stream().map(driver -> {

            AgencyOffice office = driver.getAgencyOffice();
            Agency agency = driver.getAgencyOffice().getAgency();

            return DriverResponse.builder()
                    .driverId(driver.getDriverId())
                    .licenseNumber(driver.getLicenseNumber())
                    .name(driver.getName())
                    .phone(driver.getPhone())

                    .officeId(office != null ? office.getOfficeId() : null)
                    .officeMail(office != null ? office.getOfficeMail() : null)
                    .officeContactPersonName(office != null ? office.getOfficeContactPersonName() : null)

                    .agencyId(agency != null ? agency.getAgencyId() : null)
                    .agencyName(agency != null ? agency.getName() : null)

                    .address(AddressResponseMapper.entityToResponse(driver.getAddress()))
                    .build();

        }).toList();
    }
    
    
    
}