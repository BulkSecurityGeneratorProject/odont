package com.control.shift.web.rest;

import com.control.shift.service.FichaService;
import com.control.shift.web.rest.errors.BadRequestAlertException;
import com.control.shift.service.dto.FichaDTO;

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
 * REST controller for managing {@link com.control.shift.domain.Ficha}.
 */
@RestController
@RequestMapping("/api")
public class FichaResource {

    private final Logger log = LoggerFactory.getLogger(FichaResource.class);

    private static final String ENTITY_NAME = "ficha";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FichaService fichaService;

    public FichaResource(FichaService fichaService) {
        this.fichaService = fichaService;
    }

    /**
     * {@code POST  /fichas} : Create a new ficha.
     *
     * @param fichaDTO the fichaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fichaDTO, or with status {@code 400 (Bad Request)} if the ficha has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fichas")
    public ResponseEntity<FichaDTO> createFicha(@RequestBody FichaDTO fichaDTO) throws URISyntaxException {
        log.debug("REST request to save Ficha : {}", fichaDTO);
        if (fichaDTO.getId() != null) {
            throw new BadRequestAlertException("A new ficha cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FichaDTO result = fichaService.save(fichaDTO);
        return ResponseEntity.created(new URI("/api/fichas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fichas} : Updates an existing ficha.
     *
     * @param fichaDTO the fichaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fichaDTO,
     * or with status {@code 400 (Bad Request)} if the fichaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fichaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fichas")
    public ResponseEntity<FichaDTO> updateFicha(@RequestBody FichaDTO fichaDTO) throws URISyntaxException {
        log.debug("REST request to update Ficha : {}", fichaDTO);
        if (fichaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FichaDTO result = fichaService.save(fichaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fichaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /fichas} : get all the fichas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fichas in body.
     */
    @GetMapping("/fichas")
    public List<FichaDTO> getAllFichas() {
        log.debug("REST request to get all Fichas");
        return fichaService.findAll();
    }

    /**
     * {@code GET  /fichas/:id} : get the "id" ficha.
     *
     * @param id the id of the fichaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fichaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fichas/{id}")
    public ResponseEntity<FichaDTO> getFicha(@PathVariable Long id) {
        log.debug("REST request to get Ficha : {}", id);
        Optional<FichaDTO> fichaDTO = fichaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fichaDTO);
    }

    /**
     * {@code DELETE  /fichas/:id} : delete the "id" ficha.
     *
     * @param id the id of the fichaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fichas/{id}")
    public ResponseEntity<Void> deleteFicha(@PathVariable Long id) {
        log.debug("REST request to delete Ficha : {}", id);
        fichaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
