package com.busticket.mapper.response;

import com.busticket.dto.response.BusResponse;
import com.busticket.entity.Agency;
import com.busticket.entity.AgencyOffice;
import com.busticket.entity.Bus;

public class BusResponseMapper {

    public static BusResponse entityToResponse(Bus bus) {
        return BusResponse.builder()
                .busId(bus.getBusId())
                .officeId(bus.getOffice().getOfficeId())
                .officeMail(bus.getOffice().getOfficeMail())
                .officeContactPersonName(bus.getOffice().getOfficeContactPersonName())
                .agencyId(bus.getOffice().getAgency().getAgencyId())
                .agencyName(bus.getOffice().getAgency().getName())
                .registrationNumber(bus.getRegistrationNumber())
                .capacity(bus.getCapacity())
                .type(bus.getType())
                .build();
    }

    public static Bus responseToEntity(BusResponse response) {
        Bus bus = new Bus();
        bus.setBusId(response.getBusId());
        bus.setRegistrationNumber(response.getRegistrationNumber());
        bus.setCapacity(response.getCapacity());
        bus.setType(response.getType());

        Agency agency = new Agency();
        agency.setAgencyId(response.getAgencyId());
        agency.setName(response.getAgencyName());

        AgencyOffice office = new AgencyOffice();
        office.setOfficeId(response.getOfficeId());
        office.setOfficeMail(response.getOfficeMail());
        office.setOfficeContactPersonName(response.getOfficeContactPersonName());
        office.setAgency(agency);

        bus.setOffice(office);
        return bus;
    }
}
