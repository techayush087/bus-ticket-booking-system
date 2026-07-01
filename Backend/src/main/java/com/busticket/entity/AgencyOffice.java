package com.busticket.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "agency_offices")
@Getter
@Setter
public class AgencyOffice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer officeId;

	@ManyToOne
	@JoinColumn(name = "agency_id")
	private Agency agency;
	@Email
	private String officeMail;
	@NotNull
	private String officeContactPersonName;
	@NotNull
	private String officeContactNumber;

	@ManyToOne
	@JoinColumn(name = "office_address_id")
	private Address address;
}