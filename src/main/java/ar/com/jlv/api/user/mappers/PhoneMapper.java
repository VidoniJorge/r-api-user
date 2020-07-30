package ar.com.jlv.api.user.mappers;

import java.util.List;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import ar.com.jlv.api.user.dtos.PhonesDTO;
import ar.com.jlv.api.user.entities.PhoneEntity;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface PhoneMapper {
	
	List<PhoneEntity> convertDtosToEntities(List<PhonesDTO> phones);
}
