package com.control.shift.service.impl;

import com.control.shift.service.DentistaService;
import com.control.shift.domain.Dentista;
import com.control.shift.repository.DentistaRepository;
import com.control.shift.service.dto.DentistaDTO;
import com.control.shift.service.mapper.DentistaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Dentista}.
 */
@Service
@Transactional
public class DentistaServiceImpl implements DentistaService {

    private final Logger log = LoggerFactory.getLogger(DentistaServiceImpl.class);

    private final DentistaRepository dentistaRepository;

    private final DentistaMapper dentistaMapper;

    public DentistaServiceImpl(DentistaRepository dentistaRepository, DentistaMapper dentistaMapper) {
        this.dentistaRepository = dentistaRepository;
        this.dentistaMapper = dentistaMapper;
    }

    /**
     * Save a dentista.
     *
     * @param dentistaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DentistaDTO save(DentistaDTO dentistaDTO) {
        log.debug("Request to save Dentista : {}", dentistaDTO);
        Dentista dentista = dentistaMapper.toEntity(dentistaDTO);
        dentista = dentistaRepository.save(dentista);
        return dentistaMapper.toDto(dentista);
    }

    /**
     * Get all the dentistas.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DentistaDTO> findAll() {
        log.debug("Request to get all Dentistas");
        return dentistaRepository.findAll().stream()
            .map(dentistaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one dentista by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DentistaDTO> findOne(Long id) {
        log.debug("Request to get Dentista : {}", id);
        return dentistaRepository.findById(id)
            .map(dentistaMapper::toDto);
    }

    /**
     * Delete the dentista by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dentista : {}", id);
        dentistaRepository.deleteById(id);
    }
}
