package com.control.shift.service;

import com.control.shift.service.dto.DienteDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.control.shift.domain.Diente}.
 */
public interface DienteService {

    /**
     * Save a diente.
     *
     * @param dienteDTO the entity to save.
     * @return the persisted entity.
     */
    DienteDTO save(DienteDTO dienteDTO);

    /**
     * Get all the dientes.
     *
     * @return the list of entities.
     */
    List<DienteDTO> findAll();


    /**
     * Get the "id" diente.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DienteDTO> findOne(Long id);

    /**
     * Delete the "id" diente.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
