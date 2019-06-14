package com.control.shift.service;

import com.control.shift.service.dto.ObraSocialDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.control.shift.domain.ObraSocial}.
 */
public interface ObraSocialService {

    /**
     * Save a obraSocial.
     *
     * @param obraSocialDTO the entity to save.
     * @return the persisted entity.
     */
    ObraSocialDTO save(ObraSocialDTO obraSocialDTO);

    /**
     * Get all the obraSocials.
     *
     * @return the list of entities.
     */
    List<ObraSocialDTO> findAll();


    /**
     * Get the "id" obraSocial.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ObraSocialDTO> findOne(Long id);

    /**
     * Delete the "id" obraSocial.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
