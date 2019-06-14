package com.control.shift.service.mapper;

import com.control.shift.domain.*;
import com.control.shift.service.dto.FichaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ficha} and its DTO {@link FichaDTO}.
 */
@Mapper(componentModel = "spring", uses = {PacienteMapper.class, PlanillaMapper.class})
public interface FichaMapper extends EntityMapper<FichaDTO, Ficha> {

    @Mapping(source = "paciente.id", target = "pacienteId")
    @Mapping(source = "planilla.id", target = "planillaId")
    FichaDTO toDto(Ficha ficha);

    @Mapping(source = "pacienteId", target = "paciente")
    @Mapping(target = "detalles", ignore = true)
    @Mapping(source = "planillaId", target = "planilla")
    Ficha toEntity(FichaDTO fichaDTO);

    default Ficha fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ficha ficha = new Ficha();
        ficha.setId(id);
        return ficha;
    }
}
