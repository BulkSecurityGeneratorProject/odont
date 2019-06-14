package com.control.shift.service.impl;

import com.control.shift.service.PrecioService;
import com.control.shift.domain.Precio;
import com.control.shift.repository.PrecioRepository;
import com.control.shift.service.dto.PrecioDTO;
import com.control.shift.service.mapper.PrecioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Precio}.
 */
@Service
@Transactional
public class PrecioServiceImpl implements PrecioService {

    private final Logger log = LoggerFactory.getLogger(PrecioServiceImpl.class);

    private final PrecioRepository precioRepository;

    private final PrecioMapper precioMapper;

    public PrecioServiceImpl(PrecioRepository precioRepository, PrecioMapper precioMapper) {
        this.precioRepository = precioRepository;
        this.precioMapper = precioMapper;
    }

    /**
     * Save a precio.
     *
     * @param precioDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PrecioDTO save(PrecioDTO precioDTO) {
        log.debug("Request to save Precio : {}", precioDTO);
        Precio precio = precioMapper.toEntity(precioDTO);
        precio = precioRepository.save(precio);
        return precioMapper.toDto(precio);
    }

    /**
     * Get all the precios.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PrecioDTO> findAll() {
        log.debug("Request to get all Precios");
        return precioRepository.findAll().stream()
            .map(precioMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one precio by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PrecioDTO> findOne(Long id) {
        log.debug("Request to get Precio : {}", id);
        return precioRepository.findById(id)
            .map(precioMapper::toDto);
    }

    /**
     * Delete the precio by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Precio : {}", id);
        precioRepository.deleteById(id);
    }
}
