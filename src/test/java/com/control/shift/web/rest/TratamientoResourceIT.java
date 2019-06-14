package com.control.shift.web.rest;

import com.control.shift.OdontApp;
import com.control.shift.domain.Tratamiento;
import com.control.shift.repository.TratamientoRepository;
import com.control.shift.service.TratamientoService;
import com.control.shift.service.dto.TratamientoDTO;
import com.control.shift.service.mapper.TratamientoMapper;
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
import java.math.BigDecimal;
import java.util.List;

import static com.control.shift.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link TratamientoResource} REST controller.
 */
@SpringBootTest(classes = OdontApp.class)
public class TratamientoResourceIT {

    private static final Long DEFAULT_CODE = 1L;
    private static final Long UPDATED_CODE = 2L;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRECIO = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRECIO = new BigDecimal(2);

    @Autowired
    private TratamientoRepository tratamientoRepository;

    @Autowired
    private TratamientoMapper tratamientoMapper;

    @Autowired
    private TratamientoService tratamientoService;

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

    private MockMvc restTratamientoMockMvc;

    private Tratamiento tratamiento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TratamientoResource tratamientoResource = new TratamientoResource(tratamientoService);
        this.restTratamientoMockMvc = MockMvcBuilders.standaloneSetup(tratamientoResource)
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
    public static Tratamiento createEntity(EntityManager em) {
        Tratamiento tratamiento = new Tratamiento()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .precio(DEFAULT_PRECIO);
        return tratamiento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tratamiento createUpdatedEntity(EntityManager em) {
        Tratamiento tratamiento = new Tratamiento()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .precio(UPDATED_PRECIO);
        return tratamiento;
    }

    @BeforeEach
    public void initTest() {
        tratamiento = createEntity(em);
    }

    @Test
    @Transactional
    public void createTratamiento() throws Exception {
        int databaseSizeBeforeCreate = tratamientoRepository.findAll().size();

        // Create the Tratamiento
        TratamientoDTO tratamientoDTO = tratamientoMapper.toDto(tratamiento);
        restTratamientoMockMvc.perform(post("/api/tratamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tratamientoDTO)))
            .andExpect(status().isCreated());

        // Validate the Tratamiento in the database
        List<Tratamiento> tratamientoList = tratamientoRepository.findAll();
        assertThat(tratamientoList).hasSize(databaseSizeBeforeCreate + 1);
        Tratamiento testTratamiento = tratamientoList.get(tratamientoList.size() - 1);
        assertThat(testTratamiento.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTratamiento.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTratamiento.getPrecio()).isEqualTo(DEFAULT_PRECIO);
    }

    @Test
    @Transactional
    public void createTratamientoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tratamientoRepository.findAll().size();

        // Create the Tratamiento with an existing ID
        tratamiento.setId(1L);
        TratamientoDTO tratamientoDTO = tratamientoMapper.toDto(tratamiento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTratamientoMockMvc.perform(post("/api/tratamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tratamientoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tratamiento in the database
        List<Tratamiento> tratamientoList = tratamientoRepository.findAll();
        assertThat(tratamientoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tratamientoRepository.findAll().size();
        // set the field null
        tratamiento.setCode(null);

        // Create the Tratamiento, which fails.
        TratamientoDTO tratamientoDTO = tratamientoMapper.toDto(tratamiento);

        restTratamientoMockMvc.perform(post("/api/tratamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tratamientoDTO)))
            .andExpect(status().isBadRequest());

        List<Tratamiento> tratamientoList = tratamientoRepository.findAll();
        assertThat(tratamientoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = tratamientoRepository.findAll().size();
        // set the field null
        tratamiento.setDescription(null);

        // Create the Tratamiento, which fails.
        TratamientoDTO tratamientoDTO = tratamientoMapper.toDto(tratamiento);

        restTratamientoMockMvc.perform(post("/api/tratamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tratamientoDTO)))
            .andExpect(status().isBadRequest());

        List<Tratamiento> tratamientoList = tratamientoRepository.findAll();
        assertThat(tratamientoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTratamientos() throws Exception {
        // Initialize the database
        tratamientoRepository.saveAndFlush(tratamiento);

        // Get all the tratamientoList
        restTratamientoMockMvc.perform(get("/api/tratamientos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tratamiento.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.intValue())));
    }
    
    @Test
    @Transactional
    public void getTratamiento() throws Exception {
        // Initialize the database
        tratamientoRepository.saveAndFlush(tratamiento);

        // Get the tratamiento
        restTratamientoMockMvc.perform(get("/api/tratamientos/{id}", tratamiento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tratamiento.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTratamiento() throws Exception {
        // Get the tratamiento
        restTratamientoMockMvc.perform(get("/api/tratamientos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTratamiento() throws Exception {
        // Initialize the database
        tratamientoRepository.saveAndFlush(tratamiento);

        int databaseSizeBeforeUpdate = tratamientoRepository.findAll().size();

        // Update the tratamiento
        Tratamiento updatedTratamiento = tratamientoRepository.findById(tratamiento.getId()).get();
        // Disconnect from session so that the updates on updatedTratamiento are not directly saved in db
        em.detach(updatedTratamiento);
        updatedTratamiento
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .precio(UPDATED_PRECIO);
        TratamientoDTO tratamientoDTO = tratamientoMapper.toDto(updatedTratamiento);

        restTratamientoMockMvc.perform(put("/api/tratamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tratamientoDTO)))
            .andExpect(status().isOk());

        // Validate the Tratamiento in the database
        List<Tratamiento> tratamientoList = tratamientoRepository.findAll();
        assertThat(tratamientoList).hasSize(databaseSizeBeforeUpdate);
        Tratamiento testTratamiento = tratamientoList.get(tratamientoList.size() - 1);
        assertThat(testTratamiento.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTratamiento.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTratamiento.getPrecio()).isEqualTo(UPDATED_PRECIO);
    }

    @Test
    @Transactional
    public void updateNonExistingTratamiento() throws Exception {
        int databaseSizeBeforeUpdate = tratamientoRepository.findAll().size();

        // Create the Tratamiento
        TratamientoDTO tratamientoDTO = tratamientoMapper.toDto(tratamiento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTratamientoMockMvc.perform(put("/api/tratamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tratamientoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tratamiento in the database
        List<Tratamiento> tratamientoList = tratamientoRepository.findAll();
        assertThat(tratamientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTratamiento() throws Exception {
        // Initialize the database
        tratamientoRepository.saveAndFlush(tratamiento);

        int databaseSizeBeforeDelete = tratamientoRepository.findAll().size();

        // Delete the tratamiento
        restTratamientoMockMvc.perform(delete("/api/tratamientos/{id}", tratamiento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Tratamiento> tratamientoList = tratamientoRepository.findAll();
        assertThat(tratamientoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tratamiento.class);
        Tratamiento tratamiento1 = new Tratamiento();
        tratamiento1.setId(1L);
        Tratamiento tratamiento2 = new Tratamiento();
        tratamiento2.setId(tratamiento1.getId());
        assertThat(tratamiento1).isEqualTo(tratamiento2);
        tratamiento2.setId(2L);
        assertThat(tratamiento1).isNotEqualTo(tratamiento2);
        tratamiento1.setId(null);
        assertThat(tratamiento1).isNotEqualTo(tratamiento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TratamientoDTO.class);
        TratamientoDTO tratamientoDTO1 = new TratamientoDTO();
        tratamientoDTO1.setId(1L);
        TratamientoDTO tratamientoDTO2 = new TratamientoDTO();
        assertThat(tratamientoDTO1).isNotEqualTo(tratamientoDTO2);
        tratamientoDTO2.setId(tratamientoDTO1.getId());
        assertThat(tratamientoDTO1).isEqualTo(tratamientoDTO2);
        tratamientoDTO2.setId(2L);
        assertThat(tratamientoDTO1).isNotEqualTo(tratamientoDTO2);
        tratamientoDTO1.setId(null);
        assertThat(tratamientoDTO1).isNotEqualTo(tratamientoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tratamientoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tratamientoMapper.fromId(null)).isNull();
    }
}
