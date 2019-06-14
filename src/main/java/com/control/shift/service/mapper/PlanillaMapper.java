package com.control.shift.service.mapper;

import com.control.shift.domain.*;
import com.control.shift.service.dto.PlanillaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Planilla} and its DTO {@link PlanillaDTO}.
 */
@Mapper(componentModel = "spring", uses = {ObraSocialMapper.class})
public interface PlanillaMapper extends EntityMapper<PlanillaDTO, Planilla> {

    @Mapping(source = "obraSocial.id", target = "obraSocialId")
    PlanillaDTO toDto(Planilla planilla);

    @Mapping(source = "obraSocialId", target = "obraSocial")
    @Mapping(target = "fichas", ignore = true)
    Planilla toEntity(PlanillaDTO planillaDTO);

    default Planilla fromId(Long id) {
        if (id == null) {
            return null;
        }
        Planilla planilla = new Planilla();
        planilla.setId(id);
        return planilla;
    }
}
