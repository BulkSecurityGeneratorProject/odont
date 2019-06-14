package com.control.shift.service;

import com.control.shift.service.dto.PlanillaDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.control.shift.domain.Planilla}.
 */
public interface PlanillaService {

    /**
     * Save a planilla.
     *
     * @param planillaDTO the entity to save.
     * @return the persisted entity.
     */
    PlanillaDTO save(PlanillaDTO planillaDTO);

    /**
     * Get all the planillas.
     *
     * @return the list of entities.
     */
    List<PlanillaDTO> findAll();


    /**
     * Get the "id" planilla.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlanillaDTO> findOne(Long id);

    /**
     * Delete the "id" planilla.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
