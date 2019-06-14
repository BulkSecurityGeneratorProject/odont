package com.control.shift.service.mapper;

import com.control.shift.domain.*;
import com.control.shift.service.dto.DienteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Diente} and its DTO {@link DienteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DienteMapper extends EntityMapper<DienteDTO, Diente> {



    default Diente fromId(Long id) {
        if (id == null) {
            return null;
        }
        Diente diente = new Diente();
        diente.setId(id);
        return diente;
    }
}
