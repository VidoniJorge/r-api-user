package ar.com.jlv.api.user.entities;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "API_USER")
@ToString
public class UserEntity {
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "US_ID")
	private String id;
	@Column(name = "US_NAME")
	private String name;
	@Column(name = "US_EMAIL")
	private String email;
	@Column(name = "US_PASSWORD")
	private String password;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "US_ID")
	private List<PhoneEntity> phones;

	@Column(name = "US_CREATE")
	private LocalDateTime create;
	@Column(name = "US_MODIFIED")
	private LocalDateTime modified;
	@Column(name = "US_LAST_LOGIN")
	private LocalDateTime lastLogin;
	@Column(name = "US_IS_ACTIVE")
	private Boolean isActive;

}
