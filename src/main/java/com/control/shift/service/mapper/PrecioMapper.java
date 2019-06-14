package com.control.shift.service.mapper;

import com.control.shift.domain.*;
import com.control.shift.service.dto.PrecioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Precio} and its DTO {@link PrecioDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PrecioMapper extends EntityMapper<PrecioDTO, Precio> {



    default Precio fromId(Long id) {
        if (id == null) {
            return null;
        }
        Precio precio = new Precio();
        precio.setId(id);
        return precio;
    }
}
