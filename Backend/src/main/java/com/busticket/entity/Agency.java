package com.busticket.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "agencies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agency {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer agencyId;
	@NotNull
	private String name;
	@NotNull
	private String contactPersonName;
	@Email
	private String email;
	@NotNull
	private String phone;

	
}