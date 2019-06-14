package com.control.shift.service;

import com.control.shift.service.dto.TratamientoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.control.shift.domain.Tratamiento}.
 */
public interface TratamientoService {

    /**
     * Save a tratamiento.
     *
     * @param tratamientoDTO the entity to save.
     * @return the persisted entity.
     */
    TratamientoDTO save(TratamientoDTO tratamientoDTO);

    /**
     * Get all the tratamientos.
     *
     * @return the list of entities.
     */
    List<TratamientoDTO> findAll();


    /**
     * Get the "id" tratamiento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TratamientoDTO> findOne(Long id);

    /**
     * Delete the "id" tratamiento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
