package com.control.shift.web.rest;

import com.control.shift.service.TratamientoService;
import com.control.shift.web.rest.errors.BadRequestAlertException;
import com.control.shift.service.dto.TratamientoDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.control.shift.domain.Tratamiento}.
 */
@RestController
@RequestMapping("/api")
public class TratamientoResource {

    private final Logger log = LoggerFactory.getLogger(TratamientoResource.class);

    private static final String ENTITY_NAME = "tratamiento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TratamientoService tratamientoService;

    public TratamientoResource(TratamientoService tratamientoService) {
        this.tratamientoService = tratamientoService;
    }

    /**
     * {@code POST  /tratamientos} : Create a new tratamiento.
     *
     * @param tratamientoDTO the tratamientoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tratamientoDTO, or with status {@code 400 (Bad Request)} if the tratamiento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tratamientos")
    public ResponseEntity<TratamientoDTO> createTratamiento(@Valid @RequestBody TratamientoDTO tratamientoDTO) throws URISyntaxException {
        log.debug("REST request to save Tratamiento : {}", tratamientoDTO);
        if (tratamientoDTO.getId() != null) {
            throw new BadRequestAlertException("A new tratamiento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TratamientoDTO result = tratamientoService.save(tratamientoDTO);
        return ResponseEntity.created(new URI("/api/tratamientos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tratamientos} : Updates an existing tratamiento.
     *
     * @param tratamientoDTO the tratamientoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tratamientoDTO,
     * or with status {@code 400 (Bad Request)} if the tratamientoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tratamientoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tratamientos")
    public ResponseEntity<TratamientoDTO> updateTratamiento(@Valid @RequestBody TratamientoDTO tratamientoDTO) throws URISyntaxException {
        log.debug("REST request to update Tratamiento : {}", tratamientoDTO);
        if (tratamientoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TratamientoDTO result = tratamientoService.save(tratamientoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tratamientoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tratamientos} : get all the tratamientos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tratamientos in body.
     */
    @GetMapping("/tratamientos")
    public List<TratamientoDTO> getAllTratamientos() {
        log.debug("REST request to get all Tratamientos");
        return tratamientoService.findAll();
    }

    /**
     * {@code GET  /tratamientos/:id} : get the "id" tratamiento.
     *
     * @param id the id of the tratamientoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tratamientoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tratamientos/{id}")
    public ResponseEntity<TratamientoDTO> getTratamiento(@PathVariable Long id) {
        log.debug("REST request to get Tratamiento : {}", id);
        Optional<TratamientoDTO> tratamientoDTO = tratamientoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tratamientoDTO);
    }

    /**
     * {@code DELETE  /tratamientos/:id} : delete the "id" tratamiento.
     *
     * @param id the id of the tratamientoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tratamientos/{id}")
    public ResponseEntity<Void> deleteTratamiento(@PathVariable Long id) {
        log.debug("REST request to delete Tratamiento : {}", id);
        tratamientoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
