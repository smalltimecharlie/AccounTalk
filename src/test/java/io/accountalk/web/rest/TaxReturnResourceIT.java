package io.accountalk.web.rest;

import io.accountalk.AccounTalkApp;
import io.accountalk.domain.TaxReturn;
import io.accountalk.repository.TaxReturnRepository;
import io.accountalk.web.rest.errors.ExceptionTranslator;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static io.accountalk.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.accountalk.domain.enumeration.StudentLoan;
/**
 * Integration tests for the {@Link TaxReturnResource} REST controller.
 */
@SpringBootTest(classes = AccounTalkApp.class)
public class TaxReturnResourceIT {

    private static final Instant DEFAULT_YEAR = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_YEAR = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final StudentLoan DEFAULT_STUDENT_LOAN = StudentLoan.NONE;
    private static final StudentLoan UPDATED_STUDENT_LOAN = StudentLoan.PLAN1;

    @Autowired
    private TaxReturnRepository taxReturnRepository;

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

    private MockMvc restTaxReturnMockMvc;

    private TaxReturn taxReturn;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TaxReturnResource taxReturnResource = new TaxReturnResource(taxReturnRepository);
        this.restTaxReturnMockMvc = MockMvcBuilders.standaloneSetup(taxReturnResource)
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
    public static TaxReturn createEntity(EntityManager em) {
        TaxReturn taxReturn = new TaxReturn()
            .year(DEFAULT_YEAR)
            .studentLoan(DEFAULT_STUDENT_LOAN);
        return taxReturn;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaxReturn createUpdatedEntity(EntityManager em) {
        TaxReturn taxReturn = new TaxReturn()
            .year(UPDATED_YEAR)
            .studentLoan(UPDATED_STUDENT_LOAN);
        return taxReturn;
    }

    @BeforeEach
    public void initTest() {
        taxReturn = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaxReturn() throws Exception {
        int databaseSizeBeforeCreate = taxReturnRepository.findAll().size();

        // Create the TaxReturn
        restTaxReturnMockMvc.perform(post("/api/tax-returns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxReturn)))
            .andExpect(status().isCreated());

        // Validate the TaxReturn in the database
        List<TaxReturn> taxReturnList = taxReturnRepository.findAll();
        assertThat(taxReturnList).hasSize(databaseSizeBeforeCreate + 1);
        TaxReturn testTaxReturn = taxReturnList.get(taxReturnList.size() - 1);
        assertThat(testTaxReturn.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testTaxReturn.getStudentLoan()).isEqualTo(DEFAULT_STUDENT_LOAN);
    }

    @Test
    @Transactional
    public void createTaxReturnWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taxReturnRepository.findAll().size();

        // Create the TaxReturn with an existing ID
        taxReturn.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaxReturnMockMvc.perform(post("/api/tax-returns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxReturn)))
            .andExpect(status().isBadRequest());

        // Validate the TaxReturn in the database
        List<TaxReturn> taxReturnList = taxReturnRepository.findAll();
        assertThat(taxReturnList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTaxReturns() throws Exception {
        // Initialize the database
        taxReturnRepository.saveAndFlush(taxReturn);

        // Get all the taxReturnList
        restTaxReturnMockMvc.perform(get("/api/tax-returns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taxReturn.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR.toString())))
            .andExpect(jsonPath("$.[*].studentLoan").value(hasItem(DEFAULT_STUDENT_LOAN.toString())));
    }
    
    @Test
    @Transactional
    public void getTaxReturn() throws Exception {
        // Initialize the database
        taxReturnRepository.saveAndFlush(taxReturn);

        // Get the taxReturn
        restTaxReturnMockMvc.perform(get("/api/tax-returns/{id}", taxReturn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(taxReturn.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR.toString()))
            .andExpect(jsonPath("$.studentLoan").value(DEFAULT_STUDENT_LOAN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTaxReturn() throws Exception {
        // Get the taxReturn
        restTaxReturnMockMvc.perform(get("/api/tax-returns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaxReturn() throws Exception {
        // Initialize the database
        taxReturnRepository.saveAndFlush(taxReturn);

        int databaseSizeBeforeUpdate = taxReturnRepository.findAll().size();

        // Update the taxReturn
        TaxReturn updatedTaxReturn = taxReturnRepository.findById(taxReturn.getId()).get();
        // Disconnect from session so that the updates on updatedTaxReturn are not directly saved in db
        em.detach(updatedTaxReturn);
        updatedTaxReturn
            .year(UPDATED_YEAR)
            .studentLoan(UPDATED_STUDENT_LOAN);

        restTaxReturnMockMvc.perform(put("/api/tax-returns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTaxReturn)))
            .andExpect(status().isOk());

        // Validate the TaxReturn in the database
        List<TaxReturn> taxReturnList = taxReturnRepository.findAll();
        assertThat(taxReturnList).hasSize(databaseSizeBeforeUpdate);
        TaxReturn testTaxReturn = taxReturnList.get(taxReturnList.size() - 1);
        assertThat(testTaxReturn.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testTaxReturn.getStudentLoan()).isEqualTo(UPDATED_STUDENT_LOAN);
    }

    @Test
    @Transactional
    public void updateNonExistingTaxReturn() throws Exception {
        int databaseSizeBeforeUpdate = taxReturnRepository.findAll().size();

        // Create the TaxReturn

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaxReturnMockMvc.perform(put("/api/tax-returns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxReturn)))
            .andExpect(status().isBadRequest());

        // Validate the TaxReturn in the database
        List<TaxReturn> taxReturnList = taxReturnRepository.findAll();
        assertThat(taxReturnList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTaxReturn() throws Exception {
        // Initialize the database
        taxReturnRepository.saveAndFlush(taxReturn);

        int databaseSizeBeforeDelete = taxReturnRepository.findAll().size();

        // Delete the taxReturn
        restTaxReturnMockMvc.perform(delete("/api/tax-returns/{id}", taxReturn.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaxReturn> taxReturnList = taxReturnRepository.findAll();
        assertThat(taxReturnList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaxReturn.class);
        TaxReturn taxReturn1 = new TaxReturn();
        taxReturn1.setId(1L);
        TaxReturn taxReturn2 = new TaxReturn();
        taxReturn2.setId(taxReturn1.getId());
        assertThat(taxReturn1).isEqualTo(taxReturn2);
        taxReturn2.setId(2L);
        assertThat(taxReturn1).isNotEqualTo(taxReturn2);
        taxReturn1.setId(null);
        assertThat(taxReturn1).isNotEqualTo(taxReturn2);
    }
}
