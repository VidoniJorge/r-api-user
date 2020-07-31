package ar.com.jlv.api.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PhoneDTO {
	private Integer number;
	private Integer cityCode;
	private Integer countryCode;
}
