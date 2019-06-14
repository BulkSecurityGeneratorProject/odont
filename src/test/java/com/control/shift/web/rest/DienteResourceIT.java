package com.control.shift.web.rest;

import com.control.shift.OdontApp;
import com.control.shift.domain.Diente;
import com.control.shift.repository.DienteRepository;
import com.control.shift.service.DienteService;
import com.control.shift.service.dto.DienteDTO;
import com.control.shift.service.mapper.DienteMapper;
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

import com.control.shift.domain.enumeration.Cara;
/**
 * Integration tests for the {@Link DienteResource} REST controller.
 */
@SpringBootTest(classes = OdontApp.class)
public class DienteResourceIT {

    private static final Cara DEFAULT_CARA = Cara.VESTIBULAR;
    private static final Cara UPDATED_CARA = Cara.DISTAL;

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    @Autowired
    private DienteRepository dienteRepository;

    @Autowired
    private DienteMapper dienteMapper;

    @Autowired
    private DienteService dienteService;

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

    private MockMvc restDienteMockMvc;

    private Diente diente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DienteResource dienteResource = new DienteResource(dienteService);
        this.restDienteMockMvc = MockMvcBuilders.standaloneSetup(dienteResource)
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
    public static Diente createEntity(EntityManager em) {
        Diente diente = new Diente()
            .cara(DEFAULT_CARA)
            .numero(DEFAULT_NUMERO);
        return diente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Diente createUpdatedEntity(EntityManager em) {
        Diente diente = new Diente()
            .cara(UPDATED_CARA)
            .numero(UPDATED_NUMERO);
        return diente;
    }

    @BeforeEach
    public void initTest() {
        diente = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiente() throws Exception {
        int databaseSizeBeforeCreate = dienteRepository.findAll().size();

        // Create the Diente
        DienteDTO dienteDTO = dienteMapper.toDto(diente);
        restDienteMockMvc.perform(post("/api/dientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dienteDTO)))
            .andExpect(status().isCreated());

        // Validate the Diente in the database
        List<Diente> dienteList = dienteRepository.findAll();
        assertThat(dienteList).hasSize(databaseSizeBeforeCreate + 1);
        Diente testDiente = dienteList.get(dienteList.size() - 1);
        assertThat(testDiente.getCara()).isEqualTo(DEFAULT_CARA);
        assertThat(testDiente.getNumero()).isEqualTo(DEFAULT_NUMERO);
    }

    @Test
    @Transactional
    public void createDienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dienteRepository.findAll().size();

        // Create the Diente with an existing ID
        diente.setId(1L);
        DienteDTO dienteDTO = dienteMapper.toDto(diente);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDienteMockMvc.perform(post("/api/dientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Diente in the database
        List<Diente> dienteList = dienteRepository.findAll();
        assertThat(dienteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDientes() throws Exception {
        // Initialize the database
        dienteRepository.saveAndFlush(diente);

        // Get all the dienteList
        restDienteMockMvc.perform(get("/api/dientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diente.getId().intValue())))
            .andExpect(jsonPath("$.[*].cara").value(hasItem(DEFAULT_CARA.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)));
    }
    
    @Test
    @Transactional
    public void getDiente() throws Exception {
        // Initialize the database
        dienteRepository.saveAndFlush(diente);

        // Get the diente
        restDienteMockMvc.perform(get("/api/dientes/{id}", diente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(diente.getId().intValue()))
            .andExpect(jsonPath("$.cara").value(DEFAULT_CARA.toString()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO));
    }

    @Test
    @Transactional
    public void getNonExistingDiente() throws Exception {
        // Get the diente
        restDienteMockMvc.perform(get("/api/dientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiente() throws Exception {
        // Initialize the database
        dienteRepository.saveAndFlush(diente);

        int databaseSizeBeforeUpdate = dienteRepository.findAll().size();

        // Update the diente
        Diente updatedDiente = dienteRepository.findById(diente.getId()).get();
        // Disconnect from session so that the updates on updatedDiente are not directly saved in db
        em.detach(updatedDiente);
        updatedDiente
            .cara(UPDATED_CARA)
            .numero(UPDATED_NUMERO);
        DienteDTO dienteDTO = dienteMapper.toDto(updatedDiente);

        restDienteMockMvc.perform(put("/api/dientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dienteDTO)))
            .andExpect(status().isOk());

        // Validate the Diente in the database
        List<Diente> dienteList = dienteRepository.findAll();
        assertThat(dienteList).hasSize(databaseSizeBeforeUpdate);
        Diente testDiente = dienteList.get(dienteList.size() - 1);
        assertThat(testDiente.getCara()).isEqualTo(UPDATED_CARA);
        assertThat(testDiente.getNumero()).isEqualTo(UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void updateNonExistingDiente() throws Exception {
        int databaseSizeBeforeUpdate = dienteRepository.findAll().size();

        // Create the Diente
        DienteDTO dienteDTO = dienteMapper.toDto(diente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDienteMockMvc.perform(put("/api/dientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Diente in the database
        List<Diente> dienteList = dienteRepository.findAll();
        assertThat(dienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDiente() throws Exception {
        // Initialize the database
        dienteRepository.saveAndFlush(diente);

        int databaseSizeBeforeDelete = dienteRepository.findAll().size();

        // Delete the diente
        restDienteMockMvc.perform(delete("/api/dientes/{id}", diente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Diente> dienteList = dienteRepository.findAll();
        assertThat(dienteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Diente.class);
        Diente diente1 = new Diente();
        diente1.setId(1L);
        Diente diente2 = new Diente();
        diente2.setId(diente1.getId());
        assertThat(diente1).isEqualTo(diente2);
        diente2.setId(2L);
        assertThat(diente1).isNotEqualTo(diente2);
        diente1.setId(null);
        assertThat(diente1).isNotEqualTo(diente2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DienteDTO.class);
        DienteDTO dienteDTO1 = new DienteDTO();
        dienteDTO1.setId(1L);
        DienteDTO dienteDTO2 = new DienteDTO();
        assertThat(dienteDTO1).isNotEqualTo(dienteDTO2);
        dienteDTO2.setId(dienteDTO1.getId());
        assertThat(dienteDTO1).isEqualTo(dienteDTO2);
        dienteDTO2.setId(2L);
        assertThat(dienteDTO1).isNotEqualTo(dienteDTO2);
        dienteDTO1.setId(null);
        assertThat(dienteDTO1).isNotEqualTo(dienteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dienteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dienteMapper.fromId(null)).isNull();
    }
}
