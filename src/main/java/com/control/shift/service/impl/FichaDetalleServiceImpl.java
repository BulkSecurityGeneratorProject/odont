package com.control.shift.service.impl;

import com.control.shift.service.FichaDetalleService;
import com.control.shift.domain.FichaDetalle;
import com.control.shift.repository.FichaDetalleRepository;
import com.control.shift.service.dto.FichaDetalleDTO;
import com.control.shift.service.mapper.FichaDetalleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link FichaDetalle}.
 */
@Service
@Transactional
public class FichaDetalleServiceImpl implements FichaDetalleService {

    private final Logger log = LoggerFactory.getLogger(FichaDetalleServiceImpl.class);

    private final FichaDetalleRepository fichaDetalleRepository;

    private final FichaDetalleMapper fichaDetalleMapper;

    public FichaDetalleServiceImpl(FichaDetalleRepository fichaDetalleRepository, FichaDetalleMapper fichaDetalleMapper) {
        this.fichaDetalleRepository = fichaDetalleRepository;
        this.fichaDetalleMapper = fichaDetalleMapper;
    }

    /**
     * Save a fichaDetalle.
     *
     * @param fichaDetalleDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FichaDetalleDTO save(FichaDetalleDTO fichaDetalleDTO) {
        log.debug("Request to save FichaDetalle : {}", fichaDetalleDTO);
        FichaDetalle fichaDetalle = fichaDetalleMapper.toEntity(fichaDetalleDTO);
        fichaDetalle = fichaDetalleRepository.save(fichaDetalle);
        return fichaDetalleMapper.toDto(fichaDetalle);
    }

    /**
     * Get all the fichaDetalles.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<FichaDetalleDTO> findAll() {
        log.debug("Request to get all FichaDetalles");
        return fichaDetalleRepository.findAll().stream()
            .map(fichaDetalleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one fichaDetalle by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FichaDetalleDTO> findOne(Long id) {
        log.debug("Request to get FichaDetalle : {}", id);
        return fichaDetalleRepository.findById(id)
            .map(fichaDetalleMapper::toDto);
    }

    /**
     * Delete the fichaDetalle by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FichaDetalle : {}", id);
        fichaDetalleRepository.deleteById(id);
    }
}
