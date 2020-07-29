package ar.com.jlv.api.user.controllers.impl;

import java.util.Collections;
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
import org.springframework.web.bind.annotation.Mapping;

import ar.com.jlv.api.user.dtos.UserDTO;
import ar.com.jlv.api.user.entities.UserEntity;
import ar.com.jlv.api.user.services.UserService;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(path = "/v1")
@Slf4j
public class UserControllerImpl {
	@Autowired
	private UserService userService;

	@GetMapping(headers = "Accept=application/json")
	public ResponseEntity<List<UserDTO>> findAll() {
		log.info("Inicio metodo find all user");
		return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
	}
	
	@GetMapping(path = "id", headers = "Accept=application/json")
	public void findById() {
		
	}

	@PostMapping(headers = "Accept=application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<UserDTO>  create(@Valid @RequestBody UserDTO user) {
	    log.info("Inicio metodo create user");
		return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
	}

	@PutMapping(path = "id", headers = "Accept=application/json")
	public void update() {
		
	}
	
	@DeleteMapping(path = "id", headers = "Accept=application/json")
	public void delete() {
		
	}
}
