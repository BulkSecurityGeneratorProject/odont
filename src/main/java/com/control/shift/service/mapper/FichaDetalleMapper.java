package com.control.shift.service.mapper;

import com.control.shift.domain.*;
import com.control.shift.service.dto.FichaDetalleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FichaDetalle} and its DTO {@link FichaDetalleDTO}.
 */
@Mapper(componentModel = "spring", uses = {FichaMapper.class, TratamientoMapper.class, DienteMapper.class})
public interface FichaDetalleMapper extends EntityMapper<FichaDetalleDTO, FichaDetalle> {

    @Mapping(source = "ficha.id", target = "fichaId")
    @Mapping(source = "tratamiento.id", target = "tratamientoId")
    @Mapping(source = "diente.id", target = "dienteId")
    FichaDetalleDTO toDto(FichaDetalle fichaDetalle);

    @Mapping(source = "fichaId", target = "ficha")
    @Mapping(source = "tratamientoId", target = "tratamiento")
    @Mapping(source = "dienteId", target = "diente")
    FichaDetalle toEntity(FichaDetalleDTO fichaDetalleDTO);

    default FichaDetalle fromId(Long id) {
        if (id == null) {
            return null;
        }
        FichaDetalle fichaDetalle = new FichaDetalle();
        fichaDetalle.setId(id);
        return fichaDetalle;
    }
}
