package com.control.shift.service;

import com.control.shift.service.dto.FichaDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.control.shift.domain.Ficha}.
 */
public interface FichaService {

    /**
     * Save a ficha.
     *
     * @param fichaDTO the entity to save.
     * @return the persisted entity.
     */
    FichaDTO save(FichaDTO fichaDTO);

    /**
     * Get all the fichas.
     *
     * @return the list of entities.
     */
    List<FichaDTO> findAll();


    /**
     * Get the "id" ficha.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FichaDTO> findOne(Long id);

    /**
     * Delete the "id" ficha.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
