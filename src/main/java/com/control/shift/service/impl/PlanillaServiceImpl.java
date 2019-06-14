package com.control.shift.service.impl;

import com.control.shift.service.PlanillaService;
import com.control.shift.domain.Planilla;
import com.control.shift.repository.PlanillaRepository;
import com.control.shift.service.dto.PlanillaDTO;
import com.control.shift.service.mapper.PlanillaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Planilla}.
 */
@Service
@Transactional
public class PlanillaServiceImpl implements PlanillaService {

    private final Logger log = LoggerFactory.getLogger(PlanillaServiceImpl.class);

    private final PlanillaRepository planillaRepository;

    private final PlanillaMapper planillaMapper;

    public PlanillaServiceImpl(PlanillaRepository planillaRepository, PlanillaMapper planillaMapper) {
        this.planillaRepository = planillaRepository;
        this.planillaMapper = planillaMapper;
    }

    /**
     * Save a planilla.
     *
     * @param planillaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PlanillaDTO save(PlanillaDTO planillaDTO) {
        log.debug("Request to save Planilla : {}", planillaDTO);
        Planilla planilla = planillaMapper.toEntity(planillaDTO);
        planilla = planillaRepository.save(planilla);
        return planillaMapper.toDto(planilla);
    }

    /**
     * Get all the planillas.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PlanillaDTO> findAll() {
        log.debug("Request to get all Planillas");
        return planillaRepository.findAll().stream()
            .map(planillaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one planilla by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PlanillaDTO> findOne(Long id) {
        log.debug("Request to get Planilla : {}", id);
        return planillaRepository.findById(id)
            .map(planillaMapper::toDto);
    }

    /**
     * Delete the planilla by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Planilla : {}", id);
        planillaRepository.deleteById(id);
    }
}
