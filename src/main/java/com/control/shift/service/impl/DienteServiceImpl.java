package com.control.shift.service.impl;

import com.control.shift.service.DienteService;
import com.control.shift.domain.Diente;
import com.control.shift.repository.DienteRepository;
import com.control.shift.service.dto.DienteDTO;
import com.control.shift.service.mapper.DienteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Diente}.
 */
@Service
@Transactional
public class DienteServiceImpl implements DienteService {

    private final Logger log = LoggerFactory.getLogger(DienteServiceImpl.class);

    private final DienteRepository dienteRepository;

    private final DienteMapper dienteMapper;

    public DienteServiceImpl(DienteRepository dienteRepository, DienteMapper dienteMapper) {
        this.dienteRepository = dienteRepository;
        this.dienteMapper = dienteMapper;
    }

    /**
     * Save a diente.
     *
     * @param dienteDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DienteDTO save(DienteDTO dienteDTO) {
        log.debug("Request to save Diente : {}", dienteDTO);
        Diente diente = dienteMapper.toEntity(dienteDTO);
        diente = dienteRepository.save(diente);
        return dienteMapper.toDto(diente);
    }

    /**
     * Get all the dientes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DienteDTO> findAll() {
        log.debug("Request to get all Dientes");
        return dienteRepository.findAll().stream()
            .map(dienteMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one diente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DienteDTO> findOne(Long id) {
        log.debug("Request to get Diente : {}", id);
        return dienteRepository.findById(id)
            .map(dienteMapper::toDto);
    }

    /**
     * Delete the diente by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Diente : {}", id);
        dienteRepository.deleteById(id);
    }
}
