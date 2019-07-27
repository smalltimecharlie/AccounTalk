package io.accountalk.web.rest;

import io.accountalk.AccounTalkApp;
import io.accountalk.domain.BusinessProfit;
import io.accountalk.repository.BusinessProfitRepository;
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
 * Integration tests for the {@Link BusinessProfitResource} REST controller.
 */
@SpringBootTest(classes = AccounTalkApp.class)
public class BusinessProfitResourceIT {

    private static final Double DEFAULT_TURNOVER = 1D;
    private static final Double UPDATED_TURNOVER = 2D;

    private static final Double DEFAULT_EXPENSES = 1D;
    private static final Double UPDATED_EXPENSES = 2D;

    private static final Double DEFAULT_CAPITAL_ALLOWANCES = 1D;
    private static final Double UPDATED_CAPITAL_ALLOWANCES = 2D;

    private static final Double DEFAULT_PROFIT = 1D;
    private static final Double UPDATED_PROFIT = 2D;

    private static final Double DEFAULT_CIS_TAX_DEDUCTED = 1D;
    private static final Double UPDATED_CIS_TAX_DEDUCTED = 2D;

    @Autowired
    private BusinessProfitRepository businessProfitRepository;

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

    private MockMvc restBusinessProfitMockMvc;

    private BusinessProfit businessProfit;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BusinessProfitResource businessProfitResource = new BusinessProfitResource(businessProfitRepository);
        this.restBusinessProfitMockMvc = MockMvcBuilders.standaloneSetup(businessProfitResource)
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
    public static BusinessProfit createEntity(EntityManager em) {
        BusinessProfit businessProfit = new BusinessProfit()
            .turnover(DEFAULT_TURNOVER)
            .expenses(DEFAULT_EXPENSES)
            .capitalAllowances(DEFAULT_CAPITAL_ALLOWANCES)
            .profit(DEFAULT_PROFIT)
            .cisTaxDeducted(DEFAULT_CIS_TAX_DEDUCTED);
        return businessProfit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessProfit createUpdatedEntity(EntityManager em) {
        BusinessProfit businessProfit = new BusinessProfit()
            .turnover(UPDATED_TURNOVER)
            .expenses(UPDATED_EXPENSES)
            .capitalAllowances(UPDATED_CAPITAL_ALLOWANCES)
            .profit(UPDATED_PROFIT)
            .cisTaxDeducted(UPDATED_CIS_TAX_DEDUCTED);
        return businessProfit;
    }

    @BeforeEach
    public void initTest() {
        businessProfit = createEntity(em);
    }

    @Test
    @Transactional
    public void createBusinessProfit() throws Exception {
        int databaseSizeBeforeCreate = businessProfitRepository.findAll().size();

        // Create the BusinessProfit
        restBusinessProfitMockMvc.perform(post("/api/business-profits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessProfit)))
            .andExpect(status().isCreated());

        // Validate the BusinessProfit in the database
        List<BusinessProfit> businessProfitList = businessProfitRepository.findAll();
        assertThat(businessProfitList).hasSize(databaseSizeBeforeCreate + 1);
        BusinessProfit testBusinessProfit = businessProfitList.get(businessProfitList.size() - 1);
        assertThat(testBusinessProfit.getTurnover()).isEqualTo(DEFAULT_TURNOVER);
        assertThat(testBusinessProfit.getExpenses()).isEqualTo(DEFAULT_EXPENSES);
        assertThat(testBusinessProfit.getCapitalAllowances()).isEqualTo(DEFAULT_CAPITAL_ALLOWANCES);
        assertThat(testBusinessProfit.getProfit()).isEqualTo(DEFAULT_PROFIT);
        assertThat(testBusinessProfit.getCisTaxDeducted()).isEqualTo(DEFAULT_CIS_TAX_DEDUCTED);
    }

    @Test
    @Transactional
    public void createBusinessProfitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = businessProfitRepository.findAll().size();

        // Create the BusinessProfit with an existing ID
        businessProfit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusinessProfitMockMvc.perform(post("/api/business-profits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessProfit)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessProfit in the database
        List<BusinessProfit> businessProfitList = businessProfitRepository.findAll();
        assertThat(businessProfitList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBusinessProfits() throws Exception {
        // Initialize the database
        businessProfitRepository.saveAndFlush(businessProfit);

        // Get all the businessProfitList
        restBusinessProfitMockMvc.perform(get("/api/business-profits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessProfit.getId().intValue())))
            .andExpect(jsonPath("$.[*].turnover").value(hasItem(DEFAULT_TURNOVER.doubleValue())))
            .andExpect(jsonPath("$.[*].expenses").value(hasItem(DEFAULT_EXPENSES.doubleValue())))
            .andExpect(jsonPath("$.[*].capitalAllowances").value(hasItem(DEFAULT_CAPITAL_ALLOWANCES.doubleValue())))
            .andExpect(jsonPath("$.[*].profit").value(hasItem(DEFAULT_PROFIT.doubleValue())))
            .andExpect(jsonPath("$.[*].cisTaxDeducted").value(hasItem(DEFAULT_CIS_TAX_DEDUCTED.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getBusinessProfit() throws Exception {
        // Initialize the database
        businessProfitRepository.saveAndFlush(businessProfit);

        // Get the businessProfit
        restBusinessProfitMockMvc.perform(get("/api/business-profits/{id}", businessProfit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(businessProfit.getId().intValue()))
            .andExpect(jsonPath("$.turnover").value(DEFAULT_TURNOVER.doubleValue()))
            .andExpect(jsonPath("$.expenses").value(DEFAULT_EXPENSES.doubleValue()))
            .andExpect(jsonPath("$.capitalAllowances").value(DEFAULT_CAPITAL_ALLOWANCES.doubleValue()))
            .andExpect(jsonPath("$.profit").value(DEFAULT_PROFIT.doubleValue()))
            .andExpect(jsonPath("$.cisTaxDeducted").value(DEFAULT_CIS_TAX_DEDUCTED.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBusinessProfit() throws Exception {
        // Get the businessProfit
        restBusinessProfitMockMvc.perform(get("/api/business-profits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBusinessProfit() throws Exception {
        // Initialize the database
        businessProfitRepository.saveAndFlush(businessProfit);

        int databaseSizeBeforeUpdate = businessProfitRepository.findAll().size();

        // Update the businessProfit
        BusinessProfit updatedBusinessProfit = businessProfitRepository.findById(businessProfit.getId()).get();
        // Disconnect from session so that the updates on updatedBusinessProfit are not directly saved in db
        em.detach(updatedBusinessProfit);
        updatedBusinessProfit
            .turnover(UPDATED_TURNOVER)
            .expenses(UPDATED_EXPENSES)
            .capitalAllowances(UPDATED_CAPITAL_ALLOWANCES)
            .profit(UPDATED_PROFIT)
            .cisTaxDeducted(UPDATED_CIS_TAX_DEDUCTED);

        restBusinessProfitMockMvc.perform(put("/api/business-profits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBusinessProfit)))
            .andExpect(status().isOk());

        // Validate the BusinessProfit in the database
        List<BusinessProfit> businessProfitList = businessProfitRepository.findAll();
        assertThat(businessProfitList).hasSize(databaseSizeBeforeUpdate);
        BusinessProfit testBusinessProfit = businessProfitList.get(businessProfitList.size() - 1);
        assertThat(testBusinessProfit.getTurnover()).isEqualTo(UPDATED_TURNOVER);
        assertThat(testBusinessProfit.getExpenses()).isEqualTo(UPDATED_EXPENSES);
        assertThat(testBusinessProfit.getCapitalAllowances()).isEqualTo(UPDATED_CAPITAL_ALLOWANCES);
        assertThat(testBusinessProfit.getProfit()).isEqualTo(UPDATED_PROFIT);
        assertThat(testBusinessProfit.getCisTaxDeducted()).isEqualTo(UPDATED_CIS_TAX_DEDUCTED);
    }

    @Test
    @Transactional
    public void updateNonExistingBusinessProfit() throws Exception {
        int databaseSizeBeforeUpdate = businessProfitRepository.findAll().size();

        // Create the BusinessProfit

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessProfitMockMvc.perform(put("/api/business-profits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessProfit)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessProfit in the database
        List<BusinessProfit> businessProfitList = businessProfitRepository.findAll();
        assertThat(businessProfitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBusinessProfit() throws Exception {
        // Initialize the database
        businessProfitRepository.saveAndFlush(businessProfit);

        int databaseSizeBeforeDelete = businessProfitRepository.findAll().size();

        // Delete the businessProfit
        restBusinessProfitMockMvc.perform(delete("/api/business-profits/{id}", businessProfit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BusinessProfit> businessProfitList = businessProfitRepository.findAll();
        assertThat(businessProfitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessProfit.class);
        BusinessProfit businessProfit1 = new BusinessProfit();
        businessProfit1.setId(1L);
        BusinessProfit businessProfit2 = new BusinessProfit();
        businessProfit2.setId(businessProfit1.getId());
        assertThat(businessProfit1).isEqualTo(businessProfit2);
        businessProfit2.setId(2L);
        assertThat(businessProfit1).isNotEqualTo(businessProfit2);
        businessProfit1.setId(null);
        assertThat(businessProfit1).isNotEqualTo(businessProfit2);
    }
}
