package com.control.shift.web.rest;

import com.control.shift.service.DienteService;
import com.control.shift.web.rest.errors.BadRequestAlertException;
import com.control.shift.service.dto.DienteDTO;

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
 * REST controller for managing {@link com.control.shift.domain.Diente}.
 */
@RestController
@RequestMapping("/api")
public class DienteResource {

    private final Logger log = LoggerFactory.getLogger(DienteResource.class);

    private static final String ENTITY_NAME = "diente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DienteService dienteService;

    public DienteResource(DienteService dienteService) {
        this.dienteService = dienteService;
    }

    /**
     * {@code POST  /dientes} : Create a new diente.
     *
     * @param dienteDTO the dienteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dienteDTO, or with status {@code 400 (Bad Request)} if the diente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dientes")
    public ResponseEntity<DienteDTO> createDiente(@RequestBody DienteDTO dienteDTO) throws URISyntaxException {
        log.debug("REST request to save Diente : {}", dienteDTO);
        if (dienteDTO.getId() != null) {
            throw new BadRequestAlertException("A new diente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DienteDTO result = dienteService.save(dienteDTO);
        return ResponseEntity.created(new URI("/api/dientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dientes} : Updates an existing diente.
     *
     * @param dienteDTO the dienteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dienteDTO,
     * or with status {@code 400 (Bad Request)} if the dienteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dienteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dientes")
    public ResponseEntity<DienteDTO> updateDiente(@RequestBody DienteDTO dienteDTO) throws URISyntaxException {
        log.debug("REST request to update Diente : {}", dienteDTO);
        if (dienteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DienteDTO result = dienteService.save(dienteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dienteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dientes} : get all the dientes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dientes in body.
     */
    @GetMapping("/dientes")
    public List<DienteDTO> getAllDientes() {
        log.debug("REST request to get all Dientes");
        return dienteService.findAll();
    }

    /**
     * {@code GET  /dientes/:id} : get the "id" diente.
     *
     * @param id the id of the dienteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dienteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dientes/{id}")
    public ResponseEntity<DienteDTO> getDiente(@PathVariable Long id) {
        log.debug("REST request to get Diente : {}", id);
        Optional<DienteDTO> dienteDTO = dienteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dienteDTO);
    }

    /**
     * {@code DELETE  /dientes/:id} : delete the "id" diente.
     *
     * @param id the id of the dienteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dientes/{id}")
    public ResponseEntity<Void> deleteDiente(@PathVariable Long id) {
        log.debug("REST request to delete Diente : {}", id);
        dienteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
