package ar.com.jlv.api.user.dtos;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.Email;

import javax.validation.constraints.Pattern;

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
public class UserDTO {
	private String id;
	private String name;
	@Email(message = "Email should be valid")
	private String email;
	@Pattern(regexp = "^(?=.*[0-9]{2,})(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!$%^&+=])(?=\\S+$).{8,}$", message = "Error Pass Pattern")
	private String password;
	private List<PhonesDTO> phones;

	private LocalDateTime create;
	private LocalDateTime modified;
	private LocalDateTime lastLogin;
	private Boolean isActive;;
	private String token;
}
