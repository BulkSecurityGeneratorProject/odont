package com.control.shift.web.rest;

import com.control.shift.OdontApp;
import com.control.shift.domain.Dentista;
import com.control.shift.repository.DentistaRepository;
import com.control.shift.service.DentistaService;
import com.control.shift.service.dto.DentistaDTO;
import com.control.shift.service.mapper.DentistaMapper;
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
 * Integration tests for the {@Link DentistaResource} REST controller.
 */
@SpringBootTest(classes = OdontApp.class)
public class DentistaResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_MATRICULA = 1L;
    private static final Long UPDATED_MATRICULA = 2L;

    @Autowired
    private DentistaRepository dentistaRepository;

    @Autowired
    private DentistaMapper dentistaMapper;

    @Autowired
    private DentistaService dentistaService;

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

    private MockMvc restDentistaMockMvc;

    private Dentista dentista;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DentistaResource dentistaResource = new DentistaResource(dentistaService);
        this.restDentistaMockMvc = MockMvcBuilders.standaloneSetup(dentistaResource)
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
    public static Dentista createEntity(EntityManager em) {
        Dentista dentista = new Dentista()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .matricula(DEFAULT_MATRICULA);
        return dentista;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dentista createUpdatedEntity(EntityManager em) {
        Dentista dentista = new Dentista()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .matricula(UPDATED_MATRICULA);
        return dentista;
    }

    @BeforeEach
    public void initTest() {
        dentista = createEntity(em);
    }

    @Test
    @Transactional
    public void createDentista() throws Exception {
        int databaseSizeBeforeCreate = dentistaRepository.findAll().size();

        // Create the Dentista
        DentistaDTO dentistaDTO = dentistaMapper.toDto(dentista);
        restDentistaMockMvc.perform(post("/api/dentistas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dentistaDTO)))
            .andExpect(status().isCreated());

        // Validate the Dentista in the database
        List<Dentista> dentistaList = dentistaRepository.findAll();
        assertThat(dentistaList).hasSize(databaseSizeBeforeCreate + 1);
        Dentista testDentista = dentistaList.get(dentistaList.size() - 1);
        assertThat(testDentista.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testDentista.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testDentista.getMatricula()).isEqualTo(DEFAULT_MATRICULA);
    }

    @Test
    @Transactional
    public void createDentistaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dentistaRepository.findAll().size();

        // Create the Dentista with an existing ID
        dentista.setId(1L);
        DentistaDTO dentistaDTO = dentistaMapper.toDto(dentista);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDentistaMockMvc.perform(post("/api/dentistas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dentistaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dentista in the database
        List<Dentista> dentistaList = dentistaRepository.findAll();
        assertThat(dentistaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDentistas() throws Exception {
        // Initialize the database
        dentistaRepository.saveAndFlush(dentista);

        // Get all the dentistaList
        restDentistaMockMvc.perform(get("/api/dentistas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dentista.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].matricula").value(hasItem(DEFAULT_MATRICULA.intValue())));
    }
    
    @Test
    @Transactional
    public void getDentista() throws Exception {
        // Initialize the database
        dentistaRepository.saveAndFlush(dentista);

        // Get the dentista
        restDentistaMockMvc.perform(get("/api/dentistas/{id}", dentista.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dentista.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.matricula").value(DEFAULT_MATRICULA.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDentista() throws Exception {
        // Get the dentista
        restDentistaMockMvc.perform(get("/api/dentistas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDentista() throws Exception {
        // Initialize the database
        dentistaRepository.saveAndFlush(dentista);

        int databaseSizeBeforeUpdate = dentistaRepository.findAll().size();

        // Update the dentista
        Dentista updatedDentista = dentistaRepository.findById(dentista.getId()).get();
        // Disconnect from session so that the updates on updatedDentista are not directly saved in db
        em.detach(updatedDentista);
        updatedDentista
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .matricula(UPDATED_MATRICULA);
        DentistaDTO dentistaDTO = dentistaMapper.toDto(updatedDentista);

        restDentistaMockMvc.perform(put("/api/dentistas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dentistaDTO)))
            .andExpect(status().isOk());

        // Validate the Dentista in the database
        List<Dentista> dentistaList = dentistaRepository.findAll();
        assertThat(dentistaList).hasSize(databaseSizeBeforeUpdate);
        Dentista testDentista = dentistaList.get(dentistaList.size() - 1);
        assertThat(testDentista.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testDentista.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testDentista.getMatricula()).isEqualTo(UPDATED_MATRICULA);
    }

    @Test
    @Transactional
    public void updateNonExistingDentista() throws Exception {
        int databaseSizeBeforeUpdate = dentistaRepository.findAll().size();

        // Create the Dentista
        DentistaDTO dentistaDTO = dentistaMapper.toDto(dentista);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDentistaMockMvc.perform(put("/api/dentistas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dentistaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dentista in the database
        List<Dentista> dentistaList = dentistaRepository.findAll();
        assertThat(dentistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDentista() throws Exception {
        // Initialize the database
        dentistaRepository.saveAndFlush(dentista);

        int databaseSizeBeforeDelete = dentistaRepository.findAll().size();

        // Delete the dentista
        restDentistaMockMvc.perform(delete("/api/dentistas/{id}", dentista.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Dentista> dentistaList = dentistaRepository.findAll();
        assertThat(dentistaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dentista.class);
        Dentista dentista1 = new Dentista();
        dentista1.setId(1L);
        Dentista dentista2 = new Dentista();
        dentista2.setId(dentista1.getId());
        assertThat(dentista1).isEqualTo(dentista2);
        dentista2.setId(2L);
        assertThat(dentista1).isNotEqualTo(dentista2);
        dentista1.setId(null);
        assertThat(dentista1).isNotEqualTo(dentista2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DentistaDTO.class);
        DentistaDTO dentistaDTO1 = new DentistaDTO();
        dentistaDTO1.setId(1L);
        DentistaDTO dentistaDTO2 = new DentistaDTO();
        assertThat(dentistaDTO1).isNotEqualTo(dentistaDTO2);
        dentistaDTO2.setId(dentistaDTO1.getId());
        assertThat(dentistaDTO1).isEqualTo(dentistaDTO2);
        dentistaDTO2.setId(2L);
        assertThat(dentistaDTO1).isNotEqualTo(dentistaDTO2);
        dentistaDTO1.setId(null);
        assertThat(dentistaDTO1).isNotEqualTo(dentistaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dentistaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dentistaMapper.fromId(null)).isNull();
    }
}
