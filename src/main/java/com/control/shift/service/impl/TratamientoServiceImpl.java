package com.control.shift.service.impl;

import com.control.shift.service.TratamientoService;
import com.control.shift.domain.Tratamiento;
import com.control.shift.repository.TratamientoRepository;
import com.control.shift.service.dto.TratamientoDTO;
import com.control.shift.service.mapper.TratamientoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Tratamiento}.
 */
@Service
@Transactional
public class TratamientoServiceImpl implements TratamientoService {

    private final Logger log = LoggerFactory.getLogger(TratamientoServiceImpl.class);

    private final TratamientoRepository tratamientoRepository;

    private final TratamientoMapper tratamientoMapper;

    public TratamientoServiceImpl(TratamientoRepository tratamientoRepository, TratamientoMapper tratamientoMapper) {
        this.tratamientoRepository = tratamientoRepository;
        this.tratamientoMapper = tratamientoMapper;
    }

    /**
     * Save a tratamiento.
     *
     * @param tratamientoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TratamientoDTO save(TratamientoDTO tratamientoDTO) {
        log.debug("Request to save Tratamiento : {}", tratamientoDTO);
        Tratamiento tratamiento = tratamientoMapper.toEntity(tratamientoDTO);
        tratamiento = tratamientoRepository.save(tratamiento);
        return tratamientoMapper.toDto(tratamiento);
    }

    /**
     * Get all the tratamientos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TratamientoDTO> findAll() {
        log.debug("Request to get all Tratamientos");
        return tratamientoRepository.findAll().stream()
            .map(tratamientoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one tratamiento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TratamientoDTO> findOne(Long id) {
        log.debug("Request to get Tratamiento : {}", id);
        return tratamientoRepository.findById(id)
            .map(tratamientoMapper::toDto);
    }

    /**
     * Delete the tratamiento by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tratamiento : {}", id);
        tratamientoRepository.deleteById(id);
    }
}
