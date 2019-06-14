package com.control.shift.service.mapper;

import com.control.shift.domain.*;
import com.control.shift.service.dto.PacienteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Paciente} and its DTO {@link PacienteDTO}.
 */
@Mapper(componentModel = "spring", uses = {ObraSocialMapper.class})
public interface PacienteMapper extends EntityMapper<PacienteDTO, Paciente> {

    @Mapping(source = "obraSocial.id", target = "obraSocialId")
    PacienteDTO toDto(Paciente paciente);

    @Mapping(target = "fichas", ignore = true)
    @Mapping(source = "obraSocialId", target = "obraSocial")
    Paciente toEntity(PacienteDTO pacienteDTO);

    default Paciente fromId(Long id) {
        if (id == null) {
            return null;
        }
        Paciente paciente = new Paciente();
        paciente.setId(id);
        return paciente;
    }
}
