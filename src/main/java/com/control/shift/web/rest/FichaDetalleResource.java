package com.control.shift.web.rest;

import com.control.shift.service.FichaDetalleService;
import com.control.shift.web.rest.errors.BadRequestAlertException;
import com.control.shift.service.dto.FichaDetalleDTO;

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
 * REST controller for managing {@link com.control.shift.domain.FichaDetalle}.
 */
@RestController
@RequestMapping("/api")
public class FichaDetalleResource {

    private final Logger log = LoggerFactory.getLogger(FichaDetalleResource.class);

    private static final String ENTITY_NAME = "fichaDetalle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FichaDetalleService fichaDetalleService;

    public FichaDetalleResource(FichaDetalleService fichaDetalleService) {
        this.fichaDetalleService = fichaDetalleService;
    }

    /**
     * {@code POST  /ficha-detalles} : Create a new fichaDetalle.
     *
     * @param fichaDetalleDTO the fichaDetalleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fichaDetalleDTO, or with status {@code 400 (Bad Request)} if the fichaDetalle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ficha-detalles")
    public ResponseEntity<FichaDetalleDTO> createFichaDetalle(@RequestBody FichaDetalleDTO fichaDetalleDTO) throws URISyntaxException {
        log.debug("REST request to save FichaDetalle : {}", fichaDetalleDTO);
        if (fichaDetalleDTO.getId() != null) {
            throw new BadRequestAlertException("A new fichaDetalle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FichaDetalleDTO result = fichaDetalleService.save(fichaDetalleDTO);
        return ResponseEntity.created(new URI("/api/ficha-detalles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ficha-detalles} : Updates an existing fichaDetalle.
     *
     * @param fichaDetalleDTO the fichaDetalleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fichaDetalleDTO,
     * or with status {@code 400 (Bad Request)} if the fichaDetalleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fichaDetalleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ficha-detalles")
    public ResponseEntity<FichaDetalleDTO> updateFichaDetalle(@RequestBody FichaDetalleDTO fichaDetalleDTO) throws URISyntaxException {
        log.debug("REST request to update FichaDetalle : {}", fichaDetalleDTO);
        if (fichaDetalleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FichaDetalleDTO result = fichaDetalleService.save(fichaDetalleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fichaDetalleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ficha-detalles} : get all the fichaDetalles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fichaDetalles in body.
     */
    @GetMapping("/ficha-detalles")
    public List<FichaDetalleDTO> getAllFichaDetalles() {
        log.debug("REST request to get all FichaDetalles");
        return fichaDetalleService.findAll();
    }

    /**
     * {@code GET  /ficha-detalles/:id} : get the "id" fichaDetalle.
     *
     * @param id the id of the fichaDetalleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fichaDetalleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ficha-detalles/{id}")
    public ResponseEntity<FichaDetalleDTO> getFichaDetalle(@PathVariable Long id) {
        log.debug("REST request to get FichaDetalle : {}", id);
        Optional<FichaDetalleDTO> fichaDetalleDTO = fichaDetalleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fichaDetalleDTO);
    }

    /**
     * {@code DELETE  /ficha-detalles/:id} : delete the "id" fichaDetalle.
     *
     * @param id the id of the fichaDetalleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ficha-detalles/{id}")
    public ResponseEntity<Void> deleteFichaDetalle(@PathVariable Long id) {
        log.debug("REST request to delete FichaDetalle : {}", id);
        fichaDetalleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
