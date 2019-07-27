package io.accountalk.web.rest;

import io.accountalk.AccounTalkApp;
import io.accountalk.domain.Earnings;
import io.accountalk.repository.EarningsRepository;
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
import java.util.List;

import static io.accountalk.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link EarningsResource} REST controller.
 */
@SpringBootTest(classes = AccounTalkApp.class)
public class EarningsResourceIT {

    private static final Double DEFAULT_GROSS_PAY = 1D;
    private static final Double UPDATED_GROSS_PAY = 2D;

    private static final Double DEFAULT_TAX_DEDUCTED = 1D;
    private static final Double UPDATED_TAX_DEDUCTED = 2D;

    private static final Double DEFAULT_STUDENT_LOAN_DEDUCTIONS = 1D;
    private static final Double UPDATED_STUDENT_LOAN_DEDUCTIONS = 2D;

    @Autowired
    private EarningsRepository earningsRepository;

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

    private MockMvc restEarningsMockMvc;

    private Earnings earnings;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EarningsResource earningsResource = new EarningsResource(earningsRepository);
        this.restEarningsMockMvc = MockMvcBuilders.standaloneSetup(earningsResource)
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
    public static Earnings createEntity(EntityManager em) {
        Earnings earnings = new Earnings()
            .grossPay(DEFAULT_GROSS_PAY)
            .taxDeducted(DEFAULT_TAX_DEDUCTED)
            .studentLoanDeductions(DEFAULT_STUDENT_LOAN_DEDUCTIONS);
        return earnings;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Earnings createUpdatedEntity(EntityManager em) {
        Earnings earnings = new Earnings()
            .grossPay(UPDATED_GROSS_PAY)
            .taxDeducted(UPDATED_TAX_DEDUCTED)
            .studentLoanDeductions(UPDATED_STUDENT_LOAN_DEDUCTIONS);
        return earnings;
    }

    @BeforeEach
    public void initTest() {
        earnings = createEntity(em);
    }

    @Test
    @Transactional
    public void createEarnings() throws Exception {
        int databaseSizeBeforeCreate = earningsRepository.findAll().size();

        // Create the Earnings
        restEarningsMockMvc.perform(post("/api/earnings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(earnings)))
            .andExpect(status().isCreated());

        // Validate the Earnings in the database
        List<Earnings> earningsList = earningsRepository.findAll();
        assertThat(earningsList).hasSize(databaseSizeBeforeCreate + 1);
        Earnings testEarnings = earningsList.get(earningsList.size() - 1);
        assertThat(testEarnings.getGrossPay()).isEqualTo(DEFAULT_GROSS_PAY);
        assertThat(testEarnings.getTaxDeducted()).isEqualTo(DEFAULT_TAX_DEDUCTED);
        assertThat(testEarnings.getStudentLoanDeductions()).isEqualTo(DEFAULT_STUDENT_LOAN_DEDUCTIONS);
    }

    @Test
    @Transactional
    public void createEarningsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = earningsRepository.findAll().size();

        // Create the Earnings with an existing ID
        earnings.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEarningsMockMvc.perform(post("/api/earnings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(earnings)))
            .andExpect(status().isBadRequest());

        // Validate the Earnings in the database
        List<Earnings> earningsList = earningsRepository.findAll();
        assertThat(earningsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEarnings() throws Exception {
        // Initialize the database
        earningsRepository.saveAndFlush(earnings);

        // Get all the earningsList
        restEarningsMockMvc.perform(get("/api/earnings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(earnings.getId().intValue())))
            .andExpect(jsonPath("$.[*].grossPay").value(hasItem(DEFAULT_GROSS_PAY.doubleValue())))
            .andExpect(jsonPath("$.[*].taxDeducted").value(hasItem(DEFAULT_TAX_DEDUCTED.doubleValue())))
            .andExpect(jsonPath("$.[*].studentLoanDeductions").value(hasItem(DEFAULT_STUDENT_LOAN_DEDUCTIONS.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getEarnings() throws Exception {
        // Initialize the database
        earningsRepository.saveAndFlush(earnings);

        // Get the earnings
        restEarningsMockMvc.perform(get("/api/earnings/{id}", earnings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(earnings.getId().intValue()))
            .andExpect(jsonPath("$.grossPay").value(DEFAULT_GROSS_PAY.doubleValue()))
            .andExpect(jsonPath("$.taxDeducted").value(DEFAULT_TAX_DEDUCTED.doubleValue()))
            .andExpect(jsonPath("$.studentLoanDeductions").value(DEFAULT_STUDENT_LOAN_DEDUCTIONS.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEarnings() throws Exception {
        // Get the earnings
        restEarningsMockMvc.perform(get("/api/earnings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEarnings() throws Exception {
        // Initialize the database
        earningsRepository.saveAndFlush(earnings);

        int databaseSizeBeforeUpdate = earningsRepository.findAll().size();

        // Update the earnings
        Earnings updatedEarnings = earningsRepository.findById(earnings.getId()).get();
        // Disconnect from session so that the updates on updatedEarnings are not directly saved in db
        em.detach(updatedEarnings);
        updatedEarnings
            .grossPay(UPDATED_GROSS_PAY)
            .taxDeducted(UPDATED_TAX_DEDUCTED)
            .studentLoanDeductions(UPDATED_STUDENT_LOAN_DEDUCTIONS);

        restEarningsMockMvc.perform(put("/api/earnings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEarnings)))
            .andExpect(status().isOk());

        // Validate the Earnings in the database
        List<Earnings> earningsList = earningsRepository.findAll();
        assertThat(earningsList).hasSize(databaseSizeBeforeUpdate);
        Earnings testEarnings = earningsList.get(earningsList.size() - 1);
        assertThat(testEarnings.getGrossPay()).isEqualTo(UPDATED_GROSS_PAY);
        assertThat(testEarnings.getTaxDeducted()).isEqualTo(UPDATED_TAX_DEDUCTED);
        assertThat(testEarnings.getStudentLoanDeductions()).isEqualTo(UPDATED_STUDENT_LOAN_DEDUCTIONS);
    }

    @Test
    @Transactional
    public void updateNonExistingEarnings() throws Exception {
        int databaseSizeBeforeUpdate = earningsRepository.findAll().size();

        // Create the Earnings

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEarningsMockMvc.perform(put("/api/earnings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(earnings)))
            .andExpect(status().isBadRequest());

        // Validate the Earnings in the database
        List<Earnings> earningsList = earningsRepository.findAll();
        assertThat(earningsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEarnings() throws Exception {
        // Initialize the database
        earningsRepository.saveAndFlush(earnings);

        int databaseSizeBeforeDelete = earningsRepository.findAll().size();

        // Delete the earnings
        restEarningsMockMvc.perform(delete("/api/earnings/{id}", earnings.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Earnings> earningsList = earningsRepository.findAll();
        assertThat(earningsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Earnings.class);
        Earnings earnings1 = new Earnings();
        earnings1.setId(1L);
        Earnings earnings2 = new Earnings();
        earnings2.setId(earnings1.getId());
        assertThat(earnings1).isEqualTo(earnings2);
        earnings2.setId(2L);
        assertThat(earnings1).isNotEqualTo(earnings2);
        earnings1.setId(null);
        assertThat(earnings1).isNotEqualTo(earnings2);
    }
}
