package com.busticket.dto.response;

import java.util.List;

import com.busticket.entity.Bus;
import com.busticket.entity.Driver;
import lombok.Data;

@Data

public class AgencyOfficeWithResourcesDTO {

    private Integer officeId;
    private String officeName;
    private List<Bus> buses;
    private List<Driver> drivers;

  
    public AgencyOfficeWithResourcesDTO(Integer officeId,
                                        String officeName,
                                        List<Bus> buses,
                                        List<Driver> drivers) {
        this.officeId = officeId;
        this.officeName = officeName;
        this.buses = buses;
        this.drivers = drivers;
    }

}
