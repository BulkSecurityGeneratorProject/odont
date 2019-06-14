package com.control.shift.service.mapper;

import com.control.shift.domain.*;
import com.control.shift.service.dto.ObraSocialDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ObraSocial} and its DTO {@link ObraSocialDTO}.
 */
@Mapper(componentModel = "spring", uses = {DentistaMapper.class})
public interface ObraSocialMapper extends EntityMapper<ObraSocialDTO, ObraSocial> {

    @Mapping(source = "dentista.id", target = "dentistaId")
    ObraSocialDTO toDto(ObraSocial obraSocial);

    @Mapping(source = "dentistaId", target = "dentista")
    @Mapping(target = "pacientes", ignore = true)
    @Mapping(target = "tratamientos", ignore = true)
    @Mapping(target = "planillas", ignore = true)
    ObraSocial toEntity(ObraSocialDTO obraSocialDTO);

    default ObraSocial fromId(Long id) {
        if (id == null) {
            return null;
        }
        ObraSocial obraSocial = new ObraSocial();
        obraSocial.setId(id);
        return obraSocial;
    }
}
