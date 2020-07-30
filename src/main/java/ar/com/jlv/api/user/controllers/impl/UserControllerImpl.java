package ar.com.jlv.api.user.controllers.impl;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import ar.com.jlv.api.user.dtos.UserDTO;
import ar.com.jlv.api.user.services.UserService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "users")
@Slf4j
public class UserControllerImpl {
	@Autowired
	private UserService userService;

	@GetMapping(headers = "Accept=application/json")
	public ResponseEntity<List<UserDTO>> findAll() {
		log.info("Ejecución del proceso de búsqueda de usuarios");
		return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
	}

	@GetMapping(path = "{id}", headers = "Accept=application/json")
	public ResponseEntity<UserDTO> findById(@PathVariable final String id) {
		log.info("Ejecución del proceso de búsqueda de usuario por id : " + id);
		return new ResponseEntity<>(this.userService.findById(id), HttpStatus.OK);
	}

	@PostMapping(headers = "Accept=application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO user) {
		log.info("Ejecución del proceso de alta de usuario : " + user.toString());
		return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
	}

	@PutMapping(path = "{id}", headers = "Accept=application/json")
	@ResponseStatus(value = HttpStatus.OK)
	public void update(@PathVariable final String id, @Valid @RequestBody UserDTO user) {
		log.info("Ejecución del proceso de modificación  de usuario : " + user.toString());
		this.userService.update(id, user);

	}

	@DeleteMapping(path = "{id}", headers = "Accept=application/json")
	@ResponseStatus(value = HttpStatus.OK)
	public void delete(@PathVariable final String id) {
		log.info("Ejecución del proceso de baja de usuario : " + id);
		this.userService.delete(id);

	}
}
