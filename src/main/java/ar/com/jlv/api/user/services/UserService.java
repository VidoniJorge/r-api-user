package ar.com.jlv.api.user.services;

import java.util.List;

import ar.com.jlv.api.user.dtos.UserDTO;

public interface UserService {

	public List<UserDTO> findAll();
	public void create(UserDTO user);
}
