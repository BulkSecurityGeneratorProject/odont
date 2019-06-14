package com.control.shift.web.rest;

import com.control.shift.service.PlanillaService;
import com.control.shift.web.rest.errors.BadRequestAlertException;
import com.control.shift.service.dto.PlanillaDTO;

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
 * REST controller for managing {@link com.control.shift.domain.Planilla}.
 */
@RestController
@RequestMapping("/api")
public class PlanillaResource {

    private final Logger log = LoggerFactory.getLogger(PlanillaResource.class);

    private static final String ENTITY_NAME = "planilla";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanillaService planillaService;

    public PlanillaResource(PlanillaService planillaService) {
        this.planillaService = planillaService;
    }

    /**
     * {@code POST  /planillas} : Create a new planilla.
     *
     * @param planillaDTO the planillaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planillaDTO, or with status {@code 400 (Bad Request)} if the planilla has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/planillas")
    public ResponseEntity<PlanillaDTO> createPlanilla(@RequestBody PlanillaDTO planillaDTO) throws URISyntaxException {
        log.debug("REST request to save Planilla : {}", planillaDTO);
        if (planillaDTO.getId() != null) {
            throw new BadRequestAlertException("A new planilla cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanillaDTO result = planillaService.save(planillaDTO);
        return ResponseEntity.created(new URI("/api/planillas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /planillas} : Updates an existing planilla.
     *
     * @param planillaDTO the planillaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planillaDTO,
     * or with status {@code 400 (Bad Request)} if the planillaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the planillaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/planillas")
    public ResponseEntity<PlanillaDTO> updatePlanilla(@RequestBody PlanillaDTO planillaDTO) throws URISyntaxException {
        log.debug("REST request to update Planilla : {}", planillaDTO);
        if (planillaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlanillaDTO result = planillaService.save(planillaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planillaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /planillas} : get all the planillas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of planillas in body.
     */
    @GetMapping("/planillas")
    public List<PlanillaDTO> getAllPlanillas() {
        log.debug("REST request to get all Planillas");
        return planillaService.findAll();
    }

    /**
     * {@code GET  /planillas/:id} : get the "id" planilla.
     *
     * @param id the id of the planillaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planillaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/planillas/{id}")
    public ResponseEntity<PlanillaDTO> getPlanilla(@PathVariable Long id) {
        log.debug("REST request to get Planilla : {}", id);
        Optional<PlanillaDTO> planillaDTO = planillaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(planillaDTO);
    }

    /**
     * {@code DELETE  /planillas/:id} : delete the "id" planilla.
     *
     * @param id the id of the planillaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/planillas/{id}")
    public ResponseEntity<Void> deletePlanilla(@PathVariable Long id) {
        log.debug("REST request to delete Planilla : {}", id);
        planillaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
