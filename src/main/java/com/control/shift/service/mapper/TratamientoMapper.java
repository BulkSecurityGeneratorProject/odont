package com.control.shift.service.mapper;

import com.control.shift.domain.*;
import com.control.shift.service.dto.TratamientoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tratamiento} and its DTO {@link TratamientoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ObraSocialMapper.class})
public interface TratamientoMapper extends EntityMapper<TratamientoDTO, Tratamiento> {

    @Mapping(source = "obraSocial.id", target = "obraSocialId")
    TratamientoDTO toDto(Tratamiento tratamiento);

    @Mapping(source = "obraSocialId", target = "obraSocial")
    Tratamiento toEntity(TratamientoDTO tratamientoDTO);

    default Tratamiento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tratamiento tratamiento = new Tratamiento();
        tratamiento.setId(id);
        return tratamiento;
    }
}
