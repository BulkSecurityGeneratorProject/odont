package com.control.shift.web.rest;

import com.control.shift.OdontApp;
import com.control.shift.domain.Planilla;
import com.control.shift.repository.PlanillaRepository;
import com.control.shift.service.PlanillaService;
import com.control.shift.service.dto.PlanillaDTO;
import com.control.shift.service.mapper.PlanillaMapper;
import com.control.shift.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.control.shift.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link PlanillaResource} REST controller.
 */
@SpringBootTest(classes = OdontApp.class)
public class PlanillaResourceIT {

    private static final LocalDate DEFAULT_FECHA_DESDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_DESDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_HASTA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_HASTA = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PlanillaRepository planillaRepository;

    @Autowired
    private PlanillaMapper planillaMapper;

    @Autowired
    private PlanillaService planillaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPlanillaMockMvc;

    private Planilla planilla;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlanillaResource planillaResource = new PlanillaResource(planillaService);
        this.restPlanillaMockMvc = MockMvcBuilders.standaloneSetup(planillaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Planilla createEntity(EntityManager em) {
        Planilla planilla = new Planilla()
            .fechaDesde(DEFAULT_FECHA_DESDE)
            .fechaHasta(DEFAULT_FECHA_HASTA);
        return planilla;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Planilla createUpdatedEntity(EntityManager em) {
        Planilla planilla = new Planilla()
            .fechaDesde(UPDATED_FECHA_DESDE)
            .fechaHasta(UPDATED_FECHA_HASTA);
        return planilla;
    }

    @BeforeEach
    public void initTest() {
        planilla = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlanilla() throws Exception {
        int databaseSizeBeforeCreate = planillaRepository.findAll().size();

        // Create the Planilla
        PlanillaDTO planillaDTO = planillaMapper.toDto(planilla);
        restPlanillaMockMvc.perform(post("/api/planillas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planillaDTO)))
            .andExpect(status().isCreated());

        // Validate the Planilla in the database
        List<Planilla> planillaList = planillaRepository.findAll();
        assertThat(planillaList).hasSize(databaseSizeBeforeCreate + 1);
        Planilla testPlanilla = planillaList.get(planillaList.size() - 1);
        assertThat(testPlanilla.getFechaDesde()).isEqualTo(DEFAULT_FECHA_DESDE);
        assertThat(testPlanilla.getFechaHasta()).isEqualTo(DEFAULT_FECHA_HASTA);
    }

    @Test
    @Transactional
    public void createPlanillaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planillaRepository.findAll().size();

        // Create the Planilla with an existing ID
        planilla.setId(1L);
        PlanillaDTO planillaDTO = planillaMapper.toDto(planilla);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanillaMockMvc.perform(post("/api/planillas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planillaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Planilla in the database
        List<Planilla> planillaList = planillaRepository.findAll();
        assertThat(planillaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPlanillas() throws Exception {
        // Initialize the database
        planillaRepository.saveAndFlush(planilla);

        // Get all the planillaList
        restPlanillaMockMvc.perform(get("/api/planillas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planilla.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaDesde").value(hasItem(DEFAULT_FECHA_DESDE.toString())))
            .andExpect(jsonPath("$.[*].fechaHasta").value(hasItem(DEFAULT_FECHA_HASTA.toString())));
    }
    
    @Test
    @Transactional
    public void getPlanilla() throws Exception {
        // Initialize the database
        planillaRepository.saveAndFlush(planilla);

        // Get the planilla
        restPlanillaMockMvc.perform(get("/api/planillas/{id}", planilla.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(planilla.getId().intValue()))
            .andExpect(jsonPath("$.fechaDesde").value(DEFAULT_FECHA_DESDE.toString()))
            .andExpect(jsonPath("$.fechaHasta").value(DEFAULT_FECHA_HASTA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlanilla() throws Exception {
        // Get the planilla
        restPlanillaMockMvc.perform(get("/api/planillas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlanilla() throws Exception {
        // Initialize the database
        planillaRepository.saveAndFlush(planilla);

        int databaseSizeBeforeUpdate = planillaRepository.findAll().size();

        // Update the planilla
        Planilla updatedPlanilla = planillaRepository.findById(planilla.getId()).get();
        // Disconnect from session so that the updates on updatedPlanilla are not directly saved in db
        em.detach(updatedPlanilla);
        updatedPlanilla
            .fechaDesde(UPDATED_FECHA_DESDE)
            .fechaHasta(UPDATED_FECHA_HASTA);
        PlanillaDTO planillaDTO = planillaMapper.toDto(updatedPlanilla);

        restPlanillaMockMvc.perform(put("/api/planillas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planillaDTO)))
            .andExpect(status().isOk());

        // Validate the Planilla in the database
        List<Planilla> planillaList = planillaRepository.findAll();
        assertThat(planillaList).hasSize(databaseSizeBeforeUpdate);
        Planilla testPlanilla = planillaList.get(planillaList.size() - 1);
        assertThat(testPlanilla.getFechaDesde()).isEqualTo(UPDATED_FECHA_DESDE);
        assertThat(testPlanilla.getFechaHasta()).isEqualTo(UPDATED_FECHA_HASTA);
    }

    @Test
    @Transactional
    public void updateNonExistingPlanilla() throws Exception {
        int databaseSizeBeforeUpdate = planillaRepository.findAll().size();

        // Create the Planilla
        PlanillaDTO planillaDTO = planillaMapper.toDto(planilla);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanillaMockMvc.perform(put("/api/planillas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planillaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Planilla in the database
        List<Planilla> planillaList = planillaRepository.findAll();
        assertThat(planillaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlanilla() throws Exception {
        // Initialize the database
        planillaRepository.saveAndFlush(planilla);

        int databaseSizeBeforeDelete = planillaRepository.findAll().size();

        // Delete the planilla
        restPlanillaMockMvc.perform(delete("/api/planillas/{id}", planilla.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Planilla> planillaList = planillaRepository.findAll();
        assertThat(planillaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Planilla.class);
        Planilla planilla1 = new Planilla();
        planilla1.setId(1L);
        Planilla planilla2 = new Planilla();
        planilla2.setId(planilla1.getId());
        assertThat(planilla1).isEqualTo(planilla2);
        planilla2.setId(2L);
        assertThat(planilla1).isNotEqualTo(planilla2);
        planilla1.setId(null);
        assertThat(planilla1).isNotEqualTo(planilla2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanillaDTO.class);
        PlanillaDTO planillaDTO1 = new PlanillaDTO();
        planillaDTO1.setId(1L);
        PlanillaDTO planillaDTO2 = new PlanillaDTO();
        assertThat(planillaDTO1).isNotEqualTo(planillaDTO2);
        planillaDTO2.setId(planillaDTO1.getId());
        assertThat(planillaDTO1).isEqualTo(planillaDTO2);
        planillaDTO2.setId(2L);
        assertThat(planillaDTO1).isNotEqualTo(planillaDTO2);
        planillaDTO1.setId(null);
        assertThat(planillaDTO1).isNotEqualTo(planillaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(planillaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(planillaMapper.fromId(null)).isNull();
    }
}
