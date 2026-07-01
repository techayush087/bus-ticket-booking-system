package com.busticket.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "drivers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Driver {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "driver_id")
	private Integer driverId;

	@Column(name = "license_number", nullable = false, unique=true)
	@NotNull
	private String licenseNumber;

	@Column(name = "name", nullable = false)
	@NotNull
	private String name;

	@Column(name = "phone", nullable = false)
	@NotNull
	private String phone;

	@ManyToOne
	@JoinColumn(name = "office_id")
	@NotNull
	private AgencyOffice agencyOffice;

	@ManyToOne
	@JoinColumn(name = "address_id")
	@NotNull
	private Address address;
}