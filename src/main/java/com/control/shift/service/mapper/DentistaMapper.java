package com.control.shift.service.mapper;

import com.control.shift.domain.*;
import com.control.shift.service.dto.DentistaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Dentista} and its DTO {@link DentistaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DentistaMapper extends EntityMapper<DentistaDTO, Dentista> {


    @Mapping(target = "obrasSociales", ignore = true)
    Dentista toEntity(DentistaDTO dentistaDTO);

    default Dentista fromId(Long id) {
        if (id == null) {
            return null;
        }
        Dentista dentista = new Dentista();
        dentista.setId(id);
        return dentista;
    }
}
