package com.busticket.mapper.response;

import com.busticket.dto.response.DriverResponse;
import com.busticket.entity.Agency;
import com.busticket.entity.AgencyOffice;
import com.busticket.entity.Driver;

public class DriverResponseMapper {

    public static DriverResponse entityToResponse(Driver driver) {
        return DriverResponse.builder()
                .driverId(driver.getDriverId())
                .licenseNumber(driver.getLicenseNumber())
                .name(driver.getName())
                .phone(driver.getPhone())
                .officeId(driver.getAgencyOffice().getOfficeId())
                .officeMail(driver.getAgencyOffice().getOfficeMail())
                .officeContactPersonName(driver.getAgencyOffice().getOfficeContactPersonName())
                .agencyId(driver.getAgencyOffice().getAgency().getAgencyId())
                .agencyName(driver.getAgencyOffice().getAgency().getName())
                .address(AddressResponseMapper.entityToResponse(driver.getAddress()))
                .build();
    }

    public static Driver responseToEntity(DriverResponse response) {
        Driver driver = new Driver();
        driver.setDriverId(response.getDriverId());
        driver.setLicenseNumber(response.getLicenseNumber());
        driver.setName(response.getName());
        driver.setPhone(response.getPhone());
        driver.setAddress(AddressResponseMapper.responseToEntity(response.getAddress()));

        Agency agency = new Agency();
        agency.setAgencyId(response.getAgencyId());
        agency.setName(response.getAgencyName());

        AgencyOffice office = new AgencyOffice();
        office.setOfficeId(response.getOfficeId());
        office.setOfficeMail(response.getOfficeMail());
        office.setOfficeContactPersonName(response.getOfficeContactPersonName());
        office.setAgency(agency);

        driver.setAgencyOffice(office);
        return driver;
    }
}
