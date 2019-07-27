package io.accountalk.web.rest;

import io.accountalk.AccounTalkApp;
import io.accountalk.domain.EisInvestments;
import io.accountalk.repository.EisInvestmentsRepository;
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

import io.accountalk.domain.enumeration.InvestmentScheme;
/**
 * Integration tests for the {@Link EisInvestmentsResource} REST controller.
 */
@SpringBootTest(classes = AccounTalkApp.class)
public class EisInvestmentsResourceIT {

    private static final InvestmentScheme DEFAULT_INVESTMENT_SCHEME = InvestmentScheme.EIS;
    private static final InvestmentScheme UPDATED_INVESTMENT_SCHEME = InvestmentScheme.SEIS;

    private static final Instant DEFAULT_DATE_INVESTED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_INVESTED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_AMOUNT_PAID = 1D;
    private static final Double UPDATED_AMOUNT_PAID = 2D;

    @Autowired
    private EisInvestmentsRepository eisInvestmentsRepository;

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

    private MockMvc restEisInvestmentsMockMvc;

    private EisInvestments eisInvestments;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EisInvestmentsResource eisInvestmentsResource = new EisInvestmentsResource(eisInvestmentsRepository);
        this.restEisInvestmentsMockMvc = MockMvcBuilders.standaloneSetup(eisInvestmentsResource)
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
    public static EisInvestments createEntity(EntityManager em) {
        EisInvestments eisInvestments = new EisInvestments()
            .investmentScheme(DEFAULT_INVESTMENT_SCHEME)
            .dateInvested(DEFAULT_DATE_INVESTED)
            .amountPaid(DEFAULT_AMOUNT_PAID);
        return eisInvestments;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EisInvestments createUpdatedEntity(EntityManager em) {
        EisInvestments eisInvestments = new EisInvestments()
            .investmentScheme(UPDATED_INVESTMENT_SCHEME)
            .dateInvested(UPDATED_DATE_INVESTED)
            .amountPaid(UPDATED_AMOUNT_PAID);
        return eisInvestments;
    }

    @BeforeEach
    public void initTest() {
        eisInvestments = createEntity(em);
    }

    @Test
    @Transactional
    public void createEisInvestments() throws Exception {
        int databaseSizeBeforeCreate = eisInvestmentsRepository.findAll().size();

        // Create the EisInvestments
        restEisInvestmentsMockMvc.perform(post("/api/eis-investments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eisInvestments)))
            .andExpect(status().isCreated());

        // Validate the EisInvestments in the database
        List<EisInvestments> eisInvestmentsList = eisInvestmentsRepository.findAll();
        assertThat(eisInvestmentsList).hasSize(databaseSizeBeforeCreate + 1);
        EisInvestments testEisInvestments = eisInvestmentsList.get(eisInvestmentsList.size() - 1);
        assertThat(testEisInvestments.getInvestmentScheme()).isEqualTo(DEFAULT_INVESTMENT_SCHEME);
        assertThat(testEisInvestments.getDateInvested()).isEqualTo(DEFAULT_DATE_INVESTED);
        assertThat(testEisInvestments.getAmountPaid()).isEqualTo(DEFAULT_AMOUNT_PAID);
    }

    @Test
    @Transactional
    public void createEisInvestmentsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eisInvestmentsRepository.findAll().size();

        // Create the EisInvestments with an existing ID
        eisInvestments.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEisInvestmentsMockMvc.perform(post("/api/eis-investments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eisInvestments)))
            .andExpect(status().isBadRequest());

        // Validate the EisInvestments in the database
        List<EisInvestments> eisInvestmentsList = eisInvestmentsRepository.findAll();
        assertThat(eisInvestmentsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEisInvestments() throws Exception {
        // Initialize the database
        eisInvestmentsRepository.saveAndFlush(eisInvestments);

        // Get all the eisInvestmentsList
        restEisInvestmentsMockMvc.perform(get("/api/eis-investments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eisInvestments.getId().intValue())))
            .andExpect(jsonPath("$.[*].investmentScheme").value(hasItem(DEFAULT_INVESTMENT_SCHEME.toString())))
            .andExpect(jsonPath("$.[*].dateInvested").value(hasItem(DEFAULT_DATE_INVESTED.toString())))
            .andExpect(jsonPath("$.[*].amountPaid").value(hasItem(DEFAULT_AMOUNT_PAID.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getEisInvestments() throws Exception {
        // Initialize the database
        eisInvestmentsRepository.saveAndFlush(eisInvestments);

        // Get the eisInvestments
        restEisInvestmentsMockMvc.perform(get("/api/eis-investments/{id}", eisInvestments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eisInvestments.getId().intValue()))
            .andExpect(jsonPath("$.investmentScheme").value(DEFAULT_INVESTMENT_SCHEME.toString()))
            .andExpect(jsonPath("$.dateInvested").value(DEFAULT_DATE_INVESTED.toString()))
            .andExpect(jsonPath("$.amountPaid").value(DEFAULT_AMOUNT_PAID.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEisInvestments() throws Exception {
        // Get the eisInvestments
        restEisInvestmentsMockMvc.perform(get("/api/eis-investments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEisInvestments() throws Exception {
        // Initialize the database
        eisInvestmentsRepository.saveAndFlush(eisInvestments);

        int databaseSizeBeforeUpdate = eisInvestmentsRepository.findAll().size();

        // Update the eisInvestments
        EisInvestments updatedEisInvestments = eisInvestmentsRepository.findById(eisInvestments.getId()).get();
        // Disconnect from session so that the updates on updatedEisInvestments are not directly saved in db
        em.detach(updatedEisInvestments);
        updatedEisInvestments
            .investmentScheme(UPDATED_INVESTMENT_SCHEME)
            .dateInvested(UPDATED_DATE_INVESTED)
            .amountPaid(UPDATED_AMOUNT_PAID);

        restEisInvestmentsMockMvc.perform(put("/api/eis-investments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEisInvestments)))
            .andExpect(status().isOk());

        // Validate the EisInvestments in the database
        List<EisInvestments> eisInvestmentsList = eisInvestmentsRepository.findAll();
        assertThat(eisInvestmentsList).hasSize(databaseSizeBeforeUpdate);
        EisInvestments testEisInvestments = eisInvestmentsList.get(eisInvestmentsList.size() - 1);
        assertThat(testEisInvestments.getInvestmentScheme()).isEqualTo(UPDATED_INVESTMENT_SCHEME);
        assertThat(testEisInvestments.getDateInvested()).isEqualTo(UPDATED_DATE_INVESTED);
        assertThat(testEisInvestments.getAmountPaid()).isEqualTo(UPDATED_AMOUNT_PAID);
    }

    @Test
    @Transactional
    public void updateNonExistingEisInvestments() throws Exception {
        int databaseSizeBeforeUpdate = eisInvestmentsRepository.findAll().size();

        // Create the EisInvestments

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEisInvestmentsMockMvc.perform(put("/api/eis-investments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eisInvestments)))
            .andExpect(status().isBadRequest());

        // Validate the EisInvestments in the database
        List<EisInvestments> eisInvestmentsList = eisInvestmentsRepository.findAll();
        assertThat(eisInvestmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEisInvestments() throws Exception {
        // Initialize the database
        eisInvestmentsRepository.saveAndFlush(eisInvestments);

        int databaseSizeBeforeDelete = eisInvestmentsRepository.findAll().size();

        // Delete the eisInvestments
        restEisInvestmentsMockMvc.perform(delete("/api/eis-investments/{id}", eisInvestments.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EisInvestments> eisInvestmentsList = eisInvestmentsRepository.findAll();
        assertThat(eisInvestmentsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EisInvestments.class);
        EisInvestments eisInvestments1 = new EisInvestments();
        eisInvestments1.setId(1L);
        EisInvestments eisInvestments2 = new EisInvestments();
        eisInvestments2.setId(eisInvestments1.getId());
        assertThat(eisInvestments1).isEqualTo(eisInvestments2);
        eisInvestments2.setId(2L);
        assertThat(eisInvestments1).isNotEqualTo(eisInvestments2);
        eisInvestments1.setId(null);
        assertThat(eisInvestments1).isNotEqualTo(eisInvestments2);
    }
}
