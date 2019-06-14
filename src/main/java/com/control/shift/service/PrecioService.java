package com.control.shift.service;

import com.control.shift.service.dto.PrecioDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.control.shift.domain.Precio}.
 */
public interface PrecioService {

    /**
     * Save a precio.
     *
     * @param precioDTO the entity to save.
     * @return the persisted entity.
     */
    PrecioDTO save(PrecioDTO precioDTO);

    /**
     * Get all the precios.
     *
     * @return the list of entities.
     */
    List<PrecioDTO> findAll();


    /**
     * Get the "id" precio.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PrecioDTO> findOne(Long id);

    /**
     * Delete the "id" precio.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
