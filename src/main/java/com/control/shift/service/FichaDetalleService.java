package com.control.shift.service;

import com.control.shift.service.dto.FichaDetalleDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.control.shift.domain.FichaDetalle}.
 */
public interface FichaDetalleService {

    /**
     * Save a fichaDetalle.
     *
     * @param fichaDetalleDTO the entity to save.
     * @return the persisted entity.
     */
    FichaDetalleDTO save(FichaDetalleDTO fichaDetalleDTO);

    /**
     * Get all the fichaDetalles.
     *
     * @return the list of entities.
     */
    List<FichaDetalleDTO> findAll();


    /**
     * Get the "id" fichaDetalle.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FichaDetalleDTO> findOne(Long id);

    /**
     * Delete the "id" fichaDetalle.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
