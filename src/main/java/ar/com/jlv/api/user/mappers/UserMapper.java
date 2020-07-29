package ar.com.jlv.api.user.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import org.mapstruct.Mappings;

import ar.com.jlv.api.user.dtos.UserDTO;
import ar.com.jlv.api.user.entities.UserEntity;

import org.mapstruct.InjectionStrategy;

@Mapper(componentModel = "spring"/*, injectionStrategy = InjectionStrategy.FIELD*/)
public interface UserMapper {

	
	@Mapping(source = "name", target = "name")
	@Mapping(source = "password", target = "password")
	@Mapping(source = "email", target = "email")
	UserEntity convertDtoToEntity(UserDTO user);

	@Mapping(source = "name", target = "name")
	@Mapping(source = "password", target = "password")
	@Mapping(source = "email", target = "email")
	
	UserDTO convertEntityToDto(UserEntity user);

}
