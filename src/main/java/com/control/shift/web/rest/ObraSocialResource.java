package com.control.shift.web.rest;

import com.control.shift.service.ObraSocialService;
import com.control.shift.web.rest.errors.BadRequestAlertException;
import com.control.shift.service.dto.ObraSocialDTO;

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
 * REST controller for managing {@link com.control.shift.domain.ObraSocial}.
 */
@RestController
@RequestMapping("/api")
public class ObraSocialResource {

    private final Logger log = LoggerFactory.getLogger(ObraSocialResource.class);

    private static final String ENTITY_NAME = "obraSocial";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ObraSocialService obraSocialService;

    public ObraSocialResource(ObraSocialService obraSocialService) {
        this.obraSocialService = obraSocialService;
    }

    /**
     * {@code POST  /obra-socials} : Create a new obraSocial.
     *
     * @param obraSocialDTO the obraSocialDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new obraSocialDTO, or with status {@code 400 (Bad Request)} if the obraSocial has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/obra-socials")
    public ResponseEntity<ObraSocialDTO> createObraSocial(@Valid @RequestBody ObraSocialDTO obraSocialDTO) throws URISyntaxException {
        log.debug("REST request to save ObraSocial : {}", obraSocialDTO);
        if (obraSocialDTO.getId() != null) {
            throw new BadRequestAlertException("A new obraSocial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ObraSocialDTO result = obraSocialService.save(obraSocialDTO);
        return ResponseEntity.created(new URI("/api/obra-socials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /obra-socials} : Updates an existing obraSocial.
     *
     * @param obraSocialDTO the obraSocialDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated obraSocialDTO,
     * or with status {@code 400 (Bad Request)} if the obraSocialDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the obraSocialDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/obra-socials")
    public ResponseEntity<ObraSocialDTO> updateObraSocial(@Valid @RequestBody ObraSocialDTO obraSocialDTO) throws URISyntaxException {
        log.debug("REST request to update ObraSocial : {}", obraSocialDTO);
        if (obraSocialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ObraSocialDTO result = obraSocialService.save(obraSocialDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, obraSocialDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /obra-socials} : get all the obraSocials.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of obraSocials in body.
     */
    @GetMapping("/obra-socials")
    public List<ObraSocialDTO> getAllObraSocials() {
        log.debug("REST request to get all ObraSocials");
        return obraSocialService.findAll();
    }

    /**
     * {@code GET  /obra-socials/:id} : get the "id" obraSocial.
     *
     * @param id the id of the obraSocialDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the obraSocialDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/obra-socials/{id}")
    public ResponseEntity<ObraSocialDTO> getObraSocial(@PathVariable Long id) {
        log.debug("REST request to get ObraSocial : {}", id);
        Optional<ObraSocialDTO> obraSocialDTO = obraSocialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(obraSocialDTO);
    }

    /**
     * {@code DELETE  /obra-socials/:id} : delete the "id" obraSocial.
     *
     * @param id the id of the obraSocialDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/obra-socials/{id}")
    public ResponseEntity<Void> deleteObraSocial(@PathVariable Long id) {
        log.debug("REST request to delete ObraSocial : {}", id);
        obraSocialService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
