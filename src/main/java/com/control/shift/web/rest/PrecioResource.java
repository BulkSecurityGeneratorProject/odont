package com.control.shift.web.rest;

import com.control.shift.service.PrecioService;
import com.control.shift.web.rest.errors.BadRequestAlertException;
import com.control.shift.service.dto.PrecioDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.control.shift.domain.Precio}.
 */
@RestController
@RequestMapping("/api")
public class PrecioResource {

    private final Logger log = LoggerFactory.getLogger(PrecioResource.class);

    private static final String ENTITY_NAME = "precio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrecioService precioService;

    public PrecioResource(PrecioService precioService) {
        this.precioService = precioService;
    }

    /**
     * {@code POST  /precios} : Create a new precio.
     *
     * @param precioDTO the precioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new precioDTO, or with status {@code 400 (Bad Request)} if the precio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/precios")
    public ResponseEntity<PrecioDTO> createPrecio(@RequestBody PrecioDTO precioDTO) throws URISyntaxException {
        log.debug("REST request to save Precio : {}", precioDTO);
        if (precioDTO.getId() != null) {
            throw new BadRequestAlertException("A new precio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrecioDTO result = precioService.save(precioDTO);
        return ResponseEntity.created(new URI("/api/precios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /precios} : Updates an existing precio.
     *
     * @param precioDTO the precioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated precioDTO,
     * or with status {@code 400 (Bad Request)} if the precioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the precioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/precios")
    public ResponseEntity<PrecioDTO> updatePrecio(@RequestBody PrecioDTO precioDTO) throws URISyntaxException {
        log.debug("REST request to update Precio : {}", precioDTO);
        if (precioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PrecioDTO result = precioService.save(precioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, precioDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /precios} : get all the precios.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of precios in body.
     */
    @GetMapping("/precios")
    public List<PrecioDTO> getAllPrecios() {
        log.debug("REST request to get all Precios");
        return precioService.findAll();
    }

    /**
     * {@code GET  /precios/:id} : get the "id" precio.
     *
     * @param id the id of the precioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the precioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/precios/{id}")
    public ResponseEntity<PrecioDTO> getPrecio(@PathVariable Long id) {
        log.debug("REST request to get Precio : {}", id);
        Optional<PrecioDTO> precioDTO = precioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(precioDTO);
    }

    /**
     * {@code DELETE  /precios/:id} : delete the "id" precio.
     *
     * @param id the id of the precioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/precios/{id}")
    public ResponseEntity<Void> deletePrecio(@PathVariable Long id) {
        log.debug("REST request to delete Precio : {}", id);
        precioService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
