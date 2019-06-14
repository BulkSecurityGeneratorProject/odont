package com.control.shift.web.rest;

import com.control.shift.OdontApp;
import com.control.shift.domain.Ficha;
import com.control.shift.repository.FichaRepository;
import com.control.shift.service.FichaService;
import com.control.shift.service.dto.FichaDTO;
import com.control.shift.service.mapper.FichaMapper;
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
 * Integration tests for the {@Link FichaResource} REST controller.
 */
@SpringBootTest(classes = OdontApp.class)
public class FichaResourceIT {

    private static final String DEFAULT_MONTH = "AAAAAAAAAA";
    private static final String UPDATED_MONTH = "BBBBBBBBBB";

    private static final Boolean DEFAULT_URGENCY = false;
    private static final Boolean UPDATED_URGENCY = true;

    @Autowired
    private FichaRepository fichaRepository;

    @Autowired
    private FichaMapper fichaMapper;

    @Autowired
    private FichaService fichaService;

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

    private MockMvc restFichaMockMvc;

    private Ficha ficha;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FichaResource fichaResource = new FichaResource(fichaService);
        this.restFichaMockMvc = MockMvcBuilders.standaloneSetup(fichaResource)
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
    public static Ficha createEntity(EntityManager em) {
        Ficha ficha = new Ficha()
            .month(DEFAULT_MONTH)
            .urgency(DEFAULT_URGENCY);
        return ficha;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ficha createUpdatedEntity(EntityManager em) {
        Ficha ficha = new Ficha()
            .month(UPDATED_MONTH)
            .urgency(UPDATED_URGENCY);
        return ficha;
    }

    @BeforeEach
    public void initTest() {
        ficha = createEntity(em);
    }

    @Test
    @Transactional
    public void createFicha() throws Exception {
        int databaseSizeBeforeCreate = fichaRepository.findAll().size();

        // Create the Ficha
        FichaDTO fichaDTO = fichaMapper.toDto(ficha);
        restFichaMockMvc.perform(post("/api/fichas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichaDTO)))
            .andExpect(status().isCreated());

        // Validate the Ficha in the database
        List<Ficha> fichaList = fichaRepository.findAll();
        assertThat(fichaList).hasSize(databaseSizeBeforeCreate + 1);
        Ficha testFicha = fichaList.get(fichaList.size() - 1);
        assertThat(testFicha.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testFicha.isUrgency()).isEqualTo(DEFAULT_URGENCY);
    }

    @Test
    @Transactional
    public void createFichaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fichaRepository.findAll().size();

        // Create the Ficha with an existing ID
        ficha.setId(1L);
        FichaDTO fichaDTO = fichaMapper.toDto(ficha);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFichaMockMvc.perform(post("/api/fichas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ficha in the database
        List<Ficha> fichaList = fichaRepository.findAll();
        assertThat(fichaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFichas() throws Exception {
        // Initialize the database
        fichaRepository.saveAndFlush(ficha);

        // Get all the fichaList
        restFichaMockMvc.perform(get("/api/fichas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ficha.getId().intValue())))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].urgency").value(hasItem(DEFAULT_URGENCY.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getFicha() throws Exception {
        // Initialize the database
        fichaRepository.saveAndFlush(ficha);

        // Get the ficha
        restFichaMockMvc.perform(get("/api/fichas/{id}", ficha.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ficha.getId().intValue()))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH.toString()))
            .andExpect(jsonPath("$.urgency").value(DEFAULT_URGENCY.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFicha() throws Exception {
        // Get the ficha
        restFichaMockMvc.perform(get("/api/fichas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFicha() throws Exception {
        // Initialize the database
        fichaRepository.saveAndFlush(ficha);

        int databaseSizeBeforeUpdate = fichaRepository.findAll().size();

        // Update the ficha
        Ficha updatedFicha = fichaRepository.findById(ficha.getId()).get();
        // Disconnect from session so that the updates on updatedFicha are not directly saved in db
        em.detach(updatedFicha);
        updatedFicha
            .month(UPDATED_MONTH)
            .urgency(UPDATED_URGENCY);
        FichaDTO fichaDTO = fichaMapper.toDto(updatedFicha);

        restFichaMockMvc.perform(put("/api/fichas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichaDTO)))
            .andExpect(status().isOk());

        // Validate the Ficha in the database
        List<Ficha> fichaList = fichaRepository.findAll();
        assertThat(fichaList).hasSize(databaseSizeBeforeUpdate);
        Ficha testFicha = fichaList.get(fichaList.size() - 1);
        assertThat(testFicha.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testFicha.isUrgency()).isEqualTo(UPDATED_URGENCY);
    }

    @Test
    @Transactional
    public void updateNonExistingFicha() throws Exception {
        int databaseSizeBeforeUpdate = fichaRepository.findAll().size();

        // Create the Ficha
        FichaDTO fichaDTO = fichaMapper.toDto(ficha);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFichaMockMvc.perform(put("/api/fichas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ficha in the database
        List<Ficha> fichaList = fichaRepository.findAll();
        assertThat(fichaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFicha() throws Exception {
        // Initialize the database
        fichaRepository.saveAndFlush(ficha);

        int databaseSizeBeforeDelete = fichaRepository.findAll().size();

        // Delete the ficha
        restFichaMockMvc.perform(delete("/api/fichas/{id}", ficha.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Ficha> fichaList = fichaRepository.findAll();
        assertThat(fichaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ficha.class);
        Ficha ficha1 = new Ficha();
        ficha1.setId(1L);
        Ficha ficha2 = new Ficha();
        ficha2.setId(ficha1.getId());
        assertThat(ficha1).isEqualTo(ficha2);
        ficha2.setId(2L);
        assertThat(ficha1).isNotEqualTo(ficha2);
        ficha1.setId(null);
        assertThat(ficha1).isNotEqualTo(ficha2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FichaDTO.class);
        FichaDTO fichaDTO1 = new FichaDTO();
        fichaDTO1.setId(1L);
        FichaDTO fichaDTO2 = new FichaDTO();
        assertThat(fichaDTO1).isNotEqualTo(fichaDTO2);
        fichaDTO2.setId(fichaDTO1.getId());
        assertThat(fichaDTO1).isEqualTo(fichaDTO2);
        fichaDTO2.setId(2L);
        assertThat(fichaDTO1).isNotEqualTo(fichaDTO2);
        fichaDTO1.setId(null);
        assertThat(fichaDTO1).isNotEqualTo(fichaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(fichaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(fichaMapper.fromId(null)).isNull();
    }
}
