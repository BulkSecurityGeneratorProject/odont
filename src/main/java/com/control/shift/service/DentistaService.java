package com.control.shift.service;

import com.control.shift.service.dto.DentistaDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.control.shift.domain.Dentista}.
 */
public interface DentistaService {

    /**
     * Save a dentista.
     *
     * @param dentistaDTO the entity to save.
     * @return the persisted entity.
     */
    DentistaDTO save(DentistaDTO dentistaDTO);

    /**
     * Get all the dentistas.
     *
     * @return the list of entities.
     */
    List<DentistaDTO> findAll();


    /**
     * Get the "id" dentista.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DentistaDTO> findOne(Long id);

    /**
     * Delete the "id" dentista.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
