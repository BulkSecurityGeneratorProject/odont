package com.control.shift.web.rest;

import com.control.shift.OdontApp;
import com.control.shift.domain.Precio;
import com.control.shift.repository.PrecioRepository;
import com.control.shift.service.PrecioService;
import com.control.shift.service.dto.PrecioDTO;
import com.control.shift.service.mapper.PrecioMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.control.shift.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link PrecioResource} REST controller.
 */
@SpringBootTest(classes = OdontApp.class)
public class PrecioResourceIT {

    private static final Long DEFAULT_ID_TRATAMIENTO = 1L;
    private static final Long UPDATED_ID_TRATAMIENTO = 2L;

    private static final BigDecimal DEFAULT_PRECIO = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRECIO = new BigDecimal(2);

    private static final LocalDate DEFAULT_FECHA_DESDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_DESDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_HASTA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_HASTA = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PrecioRepository precioRepository;

    @Autowired
    private PrecioMapper precioMapper;

    @Autowired
    private PrecioService precioService;

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

    private MockMvc restPrecioMockMvc;

    private Precio precio;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrecioResource precioResource = new PrecioResource(precioService);
        this.restPrecioMockMvc = MockMvcBuilders.standaloneSetup(precioResource)
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
    public static Precio createEntity(EntityManager em) {
        Precio precio = new Precio()
            .idTratamiento(DEFAULT_ID_TRATAMIENTO)
            .precio(DEFAULT_PRECIO)
            .fechaDesde(DEFAULT_FECHA_DESDE)
            .fechaHasta(DEFAULT_FECHA_HASTA);
        return precio;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Precio createUpdatedEntity(EntityManager em) {
        Precio precio = new Precio()
            .idTratamiento(UPDATED_ID_TRATAMIENTO)
            .precio(UPDATED_PRECIO)
            .fechaDesde(UPDATED_FECHA_DESDE)
            .fechaHasta(UPDATED_FECHA_HASTA);
        return precio;
    }

    @BeforeEach
    public void initTest() {
        precio = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrecio() throws Exception {
        int databaseSizeBeforeCreate = precioRepository.findAll().size();

        // Create the Precio
        PrecioDTO precioDTO = precioMapper.toDto(precio);
        restPrecioMockMvc.perform(post("/api/precios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(precioDTO)))
            .andExpect(status().isCreated());

        // Validate the Precio in the database
        List<Precio> precioList = precioRepository.findAll();
        assertThat(precioList).hasSize(databaseSizeBeforeCreate + 1);
        Precio testPrecio = precioList.get(precioList.size() - 1);
        assertThat(testPrecio.getIdTratamiento()).isEqualTo(DEFAULT_ID_TRATAMIENTO);
        assertThat(testPrecio.getPrecio()).isEqualTo(DEFAULT_PRECIO);
        assertThat(testPrecio.getFechaDesde()).isEqualTo(DEFAULT_FECHA_DESDE);
        assertThat(testPrecio.getFechaHasta()).isEqualTo(DEFAULT_FECHA_HASTA);
    }

    @Test
    @Transactional
    public void createPrecioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = precioRepository.findAll().size();

        // Create the Precio with an existing ID
        precio.setId(1L);
        PrecioDTO precioDTO = precioMapper.toDto(precio);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrecioMockMvc.perform(post("/api/precios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(precioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Precio in the database
        List<Precio> precioList = precioRepository.findAll();
        assertThat(precioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPrecios() throws Exception {
        // Initialize the database
        precioRepository.saveAndFlush(precio);

        // Get all the precioList
        restPrecioMockMvc.perform(get("/api/precios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(precio.getId().intValue())))
            .andExpect(jsonPath("$.[*].idTratamiento").value(hasItem(DEFAULT_ID_TRATAMIENTO.intValue())))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.intValue())))
            .andExpect(jsonPath("$.[*].fechaDesde").value(hasItem(DEFAULT_FECHA_DESDE.toString())))
            .andExpect(jsonPath("$.[*].fechaHasta").value(hasItem(DEFAULT_FECHA_HASTA.toString())));
    }
    
    @Test
    @Transactional
    public void getPrecio() throws Exception {
        // Initialize the database
        precioRepository.saveAndFlush(precio);

        // Get the precio
        restPrecioMockMvc.perform(get("/api/precios/{id}", precio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(precio.getId().intValue()))
            .andExpect(jsonPath("$.idTratamiento").value(DEFAULT_ID_TRATAMIENTO.intValue()))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.intValue()))
            .andExpect(jsonPath("$.fechaDesde").value(DEFAULT_FECHA_DESDE.toString()))
            .andExpect(jsonPath("$.fechaHasta").value(DEFAULT_FECHA_HASTA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPrecio() throws Exception {
        // Get the precio
        restPrecioMockMvc.perform(get("/api/precios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrecio() throws Exception {
        // Initialize the database
        precioRepository.saveAndFlush(precio);

        int databaseSizeBeforeUpdate = precioRepository.findAll().size();

        // Update the precio
        Precio updatedPrecio = precioRepository.findById(precio.getId()).get();
        // Disconnect from session so that the updates on updatedPrecio are not directly saved in db
        em.detach(updatedPrecio);
        updatedPrecio
            .idTratamiento(UPDATED_ID_TRATAMIENTO)
            .precio(UPDATED_PRECIO)
            .fechaDesde(UPDATED_FECHA_DESDE)
            .fechaHasta(UPDATED_FECHA_HASTA);
        PrecioDTO precioDTO = precioMapper.toDto(updatedPrecio);

        restPrecioMockMvc.perform(put("/api/precios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(precioDTO)))
            .andExpect(status().isOk());

        // Validate the Precio in the database
        List<Precio> precioList = precioRepository.findAll();
        assertThat(precioList).hasSize(databaseSizeBeforeUpdate);
        Precio testPrecio = precioList.get(precioList.size() - 1);
        assertThat(testPrecio.getIdTratamiento()).isEqualTo(UPDATED_ID_TRATAMIENTO);
        assertThat(testPrecio.getPrecio()).isEqualTo(UPDATED_PRECIO);
        assertThat(testPrecio.getFechaDesde()).isEqualTo(UPDATED_FECHA_DESDE);
        assertThat(testPrecio.getFechaHasta()).isEqualTo(UPDATED_FECHA_HASTA);
    }

    @Test
    @Transactional
    public void updateNonExistingPrecio() throws Exception {
        int databaseSizeBeforeUpdate = precioRepository.findAll().size();

        // Create the Precio
        PrecioDTO precioDTO = precioMapper.toDto(precio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrecioMockMvc.perform(put("/api/precios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(precioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Precio in the database
        List<Precio> precioList = precioRepository.findAll();
        assertThat(precioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePrecio() throws Exception {
        // Initialize the database
        precioRepository.saveAndFlush(precio);

        int databaseSizeBeforeDelete = precioRepository.findAll().size();

        // Delete the precio
        restPrecioMockMvc.perform(delete("/api/precios/{id}", precio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Precio> precioList = precioRepository.findAll();
        assertThat(precioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Precio.class);
        Precio precio1 = new Precio();
        precio1.setId(1L);
        Precio precio2 = new Precio();
        precio2.setId(precio1.getId());
        assertThat(precio1).isEqualTo(precio2);
        precio2.setId(2L);
        assertThat(precio1).isNotEqualTo(precio2);
        precio1.setId(null);
        assertThat(precio1).isNotEqualTo(precio2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrecioDTO.class);
        PrecioDTO precioDTO1 = new PrecioDTO();
        precioDTO1.setId(1L);
        PrecioDTO precioDTO2 = new PrecioDTO();
        assertThat(precioDTO1).isNotEqualTo(precioDTO2);
        precioDTO2.setId(precioDTO1.getId());
        assertThat(precioDTO1).isEqualTo(precioDTO2);
        precioDTO2.setId(2L);
        assertThat(precioDTO1).isNotEqualTo(precioDTO2);
        precioDTO1.setId(null);
        assertThat(precioDTO1).isNotEqualTo(precioDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(precioMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(precioMapper.fromId(null)).isNull();
    }
}
