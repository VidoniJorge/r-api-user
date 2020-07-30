package ar.com.jlv.api.user.services;

import java.util.List;

import ar.com.jlv.api.user.dtos.UserDTO;

public interface UserService {
	
	public List<UserDTO> findAll();
	public UserDTO findById(final String id);
	public UserDTO create(final UserDTO user);
	public void update(final String id, final UserDTO user);
	public void delete(final String id);
}
