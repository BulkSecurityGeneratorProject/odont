package com.control.shift.web.rest;

import com.control.shift.service.DentistaService;
import com.control.shift.web.rest.errors.BadRequestAlertException;
import com.control.shift.service.dto.DentistaDTO;

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
 * REST controller for managing {@link com.control.shift.domain.Dentista}.
 */
@RestController
@RequestMapping("/api")
public class DentistaResource {

    private final Logger log = LoggerFactory.getLogger(DentistaResource.class);

    private static final String ENTITY_NAME = "dentista";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DentistaService dentistaService;

    public DentistaResource(DentistaService dentistaService) {
        this.dentistaService = dentistaService;
    }

    /**
     * {@code POST  /dentistas} : Create a new dentista.
     *
     * @param dentistaDTO the dentistaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dentistaDTO, or with status {@code 400 (Bad Request)} if the dentista has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dentistas")
    public ResponseEntity<DentistaDTO> createDentista(@RequestBody DentistaDTO dentistaDTO) throws URISyntaxException {
        log.debug("REST request to save Dentista : {}", dentistaDTO);
        if (dentistaDTO.getId() != null) {
            throw new BadRequestAlertException("A new dentista cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DentistaDTO result = dentistaService.save(dentistaDTO);
        return ResponseEntity.created(new URI("/api/dentistas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dentistas} : Updates an existing dentista.
     *
     * @param dentistaDTO the dentistaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dentistaDTO,
     * or with status {@code 400 (Bad Request)} if the dentistaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dentistaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dentistas")
    public ResponseEntity<DentistaDTO> updateDentista(@RequestBody DentistaDTO dentistaDTO) throws URISyntaxException {
        log.debug("REST request to update Dentista : {}", dentistaDTO);
        if (dentistaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DentistaDTO result = dentistaService.save(dentistaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dentistaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dentistas} : get all the dentistas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dentistas in body.
     */
    @GetMapping("/dentistas")
    public List<DentistaDTO> getAllDentistas() {
        log.debug("REST request to get all Dentistas");
        return dentistaService.findAll();
    }

    /**
     * {@code GET  /dentistas/:id} : get the "id" dentista.
     *
     * @param id the id of the dentistaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dentistaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dentistas/{id}")
    public ResponseEntity<DentistaDTO> getDentista(@PathVariable Long id) {
        log.debug("REST request to get Dentista : {}", id);
        Optional<DentistaDTO> dentistaDTO = dentistaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dentistaDTO);
    }

    /**
     * {@code DELETE  /dentistas/:id} : delete the "id" dentista.
     *
     * @param id the id of the dentistaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dentistas/{id}")
    public ResponseEntity<Void> deleteDentista(@PathVariable Long id) {
        log.debug("REST request to delete Dentista : {}", id);
        dentistaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
