package com.control.shift.web.rest;

import com.control.shift.OdontApp;
import com.control.shift.domain.FichaDetalle;
import com.control.shift.repository.FichaDetalleRepository;
import com.control.shift.service.FichaDetalleService;
import com.control.shift.service.dto.FichaDetalleDTO;
import com.control.shift.service.mapper.FichaDetalleMapper;
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
import java.util.List;

import static com.control.shift.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link FichaDetalleResource} REST controller.
 */
@SpringBootTest(classes = OdontApp.class)
public class FichaDetalleResourceIT {

    @Autowired
    private FichaDetalleRepository fichaDetalleRepository;

    @Autowired
    private FichaDetalleMapper fichaDetalleMapper;

    @Autowired
    private FichaDetalleService fichaDetalleService;

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

    private MockMvc restFichaDetalleMockMvc;

    private FichaDetalle fichaDetalle;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FichaDetalleResource fichaDetalleResource = new FichaDetalleResource(fichaDetalleService);
        this.restFichaDetalleMockMvc = MockMvcBuilders.standaloneSetup(fichaDetalleResource)
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
    public static FichaDetalle createEntity(EntityManager em) {
        FichaDetalle fichaDetalle = new FichaDetalle();
        return fichaDetalle;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FichaDetalle createUpdatedEntity(EntityManager em) {
        FichaDetalle fichaDetalle = new FichaDetalle();
        return fichaDetalle;
    }

    @BeforeEach
    public void initTest() {
        fichaDetalle = createEntity(em);
    }

    @Test
    @Transactional
    public void createFichaDetalle() throws Exception {
        int databaseSizeBeforeCreate = fichaDetalleRepository.findAll().size();

        // Create the FichaDetalle
        FichaDetalleDTO fichaDetalleDTO = fichaDetalleMapper.toDto(fichaDetalle);
        restFichaDetalleMockMvc.perform(post("/api/ficha-detalles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichaDetalleDTO)))
            .andExpect(status().isCreated());

        // Validate the FichaDetalle in the database
        List<FichaDetalle> fichaDetalleList = fichaDetalleRepository.findAll();
        assertThat(fichaDetalleList).hasSize(databaseSizeBeforeCreate + 1);
        FichaDetalle testFichaDetalle = fichaDetalleList.get(fichaDetalleList.size() - 1);
    }

    @Test
    @Transactional
    public void createFichaDetalleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fichaDetalleRepository.findAll().size();

        // Create the FichaDetalle with an existing ID
        fichaDetalle.setId(1L);
        FichaDetalleDTO fichaDetalleDTO = fichaDetalleMapper.toDto(fichaDetalle);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFichaDetalleMockMvc.perform(post("/api/ficha-detalles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichaDetalleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FichaDetalle in the database
        List<FichaDetalle> fichaDetalleList = fichaDetalleRepository.findAll();
        assertThat(fichaDetalleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFichaDetalles() throws Exception {
        // Initialize the database
        fichaDetalleRepository.saveAndFlush(fichaDetalle);

        // Get all the fichaDetalleList
        restFichaDetalleMockMvc.perform(get("/api/ficha-detalles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fichaDetalle.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getFichaDetalle() throws Exception {
        // Initialize the database
        fichaDetalleRepository.saveAndFlush(fichaDetalle);

        // Get the fichaDetalle
        restFichaDetalleMockMvc.perform(get("/api/ficha-detalles/{id}", fichaDetalle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fichaDetalle.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFichaDetalle() throws Exception {
        // Get the fichaDetalle
        restFichaDetalleMockMvc.perform(get("/api/ficha-detalles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFichaDetalle() throws Exception {
        // Initialize the database
        fichaDetalleRepository.saveAndFlush(fichaDetalle);

        int databaseSizeBeforeUpdate = fichaDetalleRepository.findAll().size();

        // Update the fichaDetalle
        FichaDetalle updatedFichaDetalle = fichaDetalleRepository.findById(fichaDetalle.getId()).get();
        // Disconnect from session so that the updates on updatedFichaDetalle are not directly saved in db
        em.detach(updatedFichaDetalle);
        FichaDetalleDTO fichaDetalleDTO = fichaDetalleMapper.toDto(updatedFichaDetalle);

        restFichaDetalleMockMvc.perform(put("/api/ficha-detalles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichaDetalleDTO)))
            .andExpect(status().isOk());

        // Validate the FichaDetalle in the database
        List<FichaDetalle> fichaDetalleList = fichaDetalleRepository.findAll();
        assertThat(fichaDetalleList).hasSize(databaseSizeBeforeUpdate);
        FichaDetalle testFichaDetalle = fichaDetalleList.get(fichaDetalleList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingFichaDetalle() throws Exception {
        int databaseSizeBeforeUpdate = fichaDetalleRepository.findAll().size();

        // Create the FichaDetalle
        FichaDetalleDTO fichaDetalleDTO = fichaDetalleMapper.toDto(fichaDetalle);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFichaDetalleMockMvc.perform(put("/api/ficha-detalles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichaDetalleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FichaDetalle in the database
        List<FichaDetalle> fichaDetalleList = fichaDetalleRepository.findAll();
        assertThat(fichaDetalleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFichaDetalle() throws Exception {
        // Initialize the database
        fichaDetalleRepository.saveAndFlush(fichaDetalle);

        int databaseSizeBeforeDelete = fichaDetalleRepository.findAll().size();

        // Delete the fichaDetalle
        restFichaDetalleMockMvc.perform(delete("/api/ficha-detalles/{id}", fichaDetalle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<FichaDetalle> fichaDetalleList = fichaDetalleRepository.findAll();
        assertThat(fichaDetalleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FichaDetalle.class);
        FichaDetalle fichaDetalle1 = new FichaDetalle();
        fichaDetalle1.setId(1L);
        FichaDetalle fichaDetalle2 = new FichaDetalle();
        fichaDetalle2.setId(fichaDetalle1.getId());
        assertThat(fichaDetalle1).isEqualTo(fichaDetalle2);
        fichaDetalle2.setId(2L);
        assertThat(fichaDetalle1).isNotEqualTo(fichaDetalle2);
        fichaDetalle1.setId(null);
        assertThat(fichaDetalle1).isNotEqualTo(fichaDetalle2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FichaDetalleDTO.class);
        FichaDetalleDTO fichaDetalleDTO1 = new FichaDetalleDTO();
        fichaDetalleDTO1.setId(1L);
        FichaDetalleDTO fichaDetalleDTO2 = new FichaDetalleDTO();
        assertThat(fichaDetalleDTO1).isNotEqualTo(fichaDetalleDTO2);
        fichaDetalleDTO2.setId(fichaDetalleDTO1.getId());
        assertThat(fichaDetalleDTO1).isEqualTo(fichaDetalleDTO2);
        fichaDetalleDTO2.setId(2L);
        assertThat(fichaDetalleDTO1).isNotEqualTo(fichaDetalleDTO2);
        fichaDetalleDTO1.setId(null);
        assertThat(fichaDetalleDTO1).isNotEqualTo(fichaDetalleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(fichaDetalleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(fichaDetalleMapper.fromId(null)).isNull();
    }
}
