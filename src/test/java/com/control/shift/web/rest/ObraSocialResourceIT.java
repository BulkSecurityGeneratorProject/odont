package com.control.shift.web.rest;

import com.control.shift.OdontApp;
import com.control.shift.domain.ObraSocial;
import com.control.shift.repository.ObraSocialRepository;
import com.control.shift.service.ObraSocialService;
import com.control.shift.service.dto.ObraSocialDTO;
import com.control.shift.service.mapper.ObraSocialMapper;
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
 * Integration tests for the {@Link ObraSocialResource} REST controller.
 */
@SpringBootTest(classes = OdontApp.class)
public class ObraSocialResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ObraSocialRepository obraSocialRepository;

    @Autowired
    private ObraSocialMapper obraSocialMapper;

    @Autowired
    private ObraSocialService obraSocialService;

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

    private MockMvc restObraSocialMockMvc;

    private ObraSocial obraSocial;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ObraSocialResource obraSocialResource = new ObraSocialResource(obraSocialService);
        this.restObraSocialMockMvc = MockMvcBuilders.standaloneSetup(obraSocialResource)
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
    public static ObraSocial createEntity(EntityManager em) {
        ObraSocial obraSocial = new ObraSocial()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return obraSocial;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ObraSocial createUpdatedEntity(EntityManager em) {
        ObraSocial obraSocial = new ObraSocial()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return obraSocial;
    }

    @BeforeEach
    public void initTest() {
        obraSocial = createEntity(em);
    }

    @Test
    @Transactional
    public void createObraSocial() throws Exception {
        int databaseSizeBeforeCreate = obraSocialRepository.findAll().size();

        // Create the ObraSocial
        ObraSocialDTO obraSocialDTO = obraSocialMapper.toDto(obraSocial);
        restObraSocialMockMvc.perform(post("/api/obra-socials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obraSocialDTO)))
            .andExpect(status().isCreated());

        // Validate the ObraSocial in the database
        List<ObraSocial> obraSocialList = obraSocialRepository.findAll();
        assertThat(obraSocialList).hasSize(databaseSizeBeforeCreate + 1);
        ObraSocial testObraSocial = obraSocialList.get(obraSocialList.size() - 1);
        assertThat(testObraSocial.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testObraSocial.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createObraSocialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = obraSocialRepository.findAll().size();

        // Create the ObraSocial with an existing ID
        obraSocial.setId(1L);
        ObraSocialDTO obraSocialDTO = obraSocialMapper.toDto(obraSocial);

        // An entity with an existing ID cannot be created, so this API call must fail
        restObraSocialMockMvc.perform(post("/api/obra-socials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obraSocialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ObraSocial in the database
        List<ObraSocial> obraSocialList = obraSocialRepository.findAll();
        assertThat(obraSocialList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = obraSocialRepository.findAll().size();
        // set the field null
        obraSocial.setName(null);

        // Create the ObraSocial, which fails.
        ObraSocialDTO obraSocialDTO = obraSocialMapper.toDto(obraSocial);

        restObraSocialMockMvc.perform(post("/api/obra-socials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obraSocialDTO)))
            .andExpect(status().isBadRequest());

        List<ObraSocial> obraSocialList = obraSocialRepository.findAll();
        assertThat(obraSocialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllObraSocials() throws Exception {
        // Initialize the database
        obraSocialRepository.saveAndFlush(obraSocial);

        // Get all the obraSocialList
        restObraSocialMockMvc.perform(get("/api/obra-socials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(obraSocial.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getObraSocial() throws Exception {
        // Initialize the database
        obraSocialRepository.saveAndFlush(obraSocial);

        // Get the obraSocial
        restObraSocialMockMvc.perform(get("/api/obra-socials/{id}", obraSocial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(obraSocial.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingObraSocial() throws Exception {
        // Get the obraSocial
        restObraSocialMockMvc.perform(get("/api/obra-socials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateObraSocial() throws Exception {
        // Initialize the database
        obraSocialRepository.saveAndFlush(obraSocial);

        int databaseSizeBeforeUpdate = obraSocialRepository.findAll().size();

        // Update the obraSocial
        ObraSocial updatedObraSocial = obraSocialRepository.findById(obraSocial.getId()).get();
        // Disconnect from session so that the updates on updatedObraSocial are not directly saved in db
        em.detach(updatedObraSocial);
        updatedObraSocial
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        ObraSocialDTO obraSocialDTO = obraSocialMapper.toDto(updatedObraSocial);

        restObraSocialMockMvc.perform(put("/api/obra-socials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obraSocialDTO)))
            .andExpect(status().isOk());

        // Validate the ObraSocial in the database
        List<ObraSocial> obraSocialList = obraSocialRepository.findAll();
        assertThat(obraSocialList).hasSize(databaseSizeBeforeUpdate);
        ObraSocial testObraSocial = obraSocialList.get(obraSocialList.size() - 1);
        assertThat(testObraSocial.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testObraSocial.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingObraSocial() throws Exception {
        int databaseSizeBeforeUpdate = obraSocialRepository.findAll().size();

        // Create the ObraSocial
        ObraSocialDTO obraSocialDTO = obraSocialMapper.toDto(obraSocial);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObraSocialMockMvc.perform(put("/api/obra-socials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obraSocialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ObraSocial in the database
        List<ObraSocial> obraSocialList = obraSocialRepository.findAll();
        assertThat(obraSocialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteObraSocial() throws Exception {
        // Initialize the database
        obraSocialRepository.saveAndFlush(obraSocial);

        int databaseSizeBeforeDelete = obraSocialRepository.findAll().size();

        // Delete the obraSocial
        restObraSocialMockMvc.perform(delete("/api/obra-socials/{id}", obraSocial.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ObraSocial> obraSocialList = obraSocialRepository.findAll();
        assertThat(obraSocialList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObraSocial.class);
        ObraSocial obraSocial1 = new ObraSocial();
        obraSocial1.setId(1L);
        ObraSocial obraSocial2 = new ObraSocial();
        obraSocial2.setId(obraSocial1.getId());
        assertThat(obraSocial1).isEqualTo(obraSocial2);
        obraSocial2.setId(2L);
        assertThat(obraSocial1).isNotEqualTo(obraSocial2);
        obraSocial1.setId(null);
        assertThat(obraSocial1).isNotEqualTo(obraSocial2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObraSocialDTO.class);
        ObraSocialDTO obraSocialDTO1 = new ObraSocialDTO();
        obraSocialDTO1.setId(1L);
        ObraSocialDTO obraSocialDTO2 = new ObraSocialDTO();
        assertThat(obraSocialDTO1).isNotEqualTo(obraSocialDTO2);
        obraSocialDTO2.setId(obraSocialDTO1.getId());
        assertThat(obraSocialDTO1).isEqualTo(obraSocialDTO2);
        obraSocialDTO2.setId(2L);
        assertThat(obraSocialDTO1).isNotEqualTo(obraSocialDTO2);
        obraSocialDTO1.setId(null);
        assertThat(obraSocialDTO1).isNotEqualTo(obraSocialDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(obraSocialMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(obraSocialMapper.fromId(null)).isNull();
    }
}
