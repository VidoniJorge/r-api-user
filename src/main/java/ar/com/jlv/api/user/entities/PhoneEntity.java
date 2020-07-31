package ar.com.jlv.api.user.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "API_PHONE")
@ToString
public class PhoneEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PH_ID")
	private Integer id;
	@Column(name = "PH_NUMBER")
	private Integer number;
	@Column(name = "PH_CITY_CODE")
	private Integer cityCode;
	@Column(name = "PH_COUNTRY_CODE")
	private Integer countryCode;
}