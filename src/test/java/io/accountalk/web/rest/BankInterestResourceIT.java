package io.accountalk.web.rest;

import io.accountalk.AccounTalkApp;
import io.accountalk.domain.BankInterest;
import io.accountalk.repository.BankInterestRepository;
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
 * Integration tests for the {@Link BankInterestResource} REST controller.
 */
@SpringBootTest(classes = AccounTalkApp.class)
public class BankInterestResourceIT {

    private static final Double DEFAULT_NET_INTEREST = 1D;
    private static final Double UPDATED_NET_INTEREST = 2D;

    private static final Double DEFAULT_TAX_DEDUCTED = 1D;
    private static final Double UPDATED_TAX_DEDUCTED = 2D;

    @Autowired
    private BankInterestRepository bankInterestRepository;

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

    private MockMvc restBankInterestMockMvc;

    private BankInterest bankInterest;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BankInterestResource bankInterestResource = new BankInterestResource(bankInterestRepository);
        this.restBankInterestMockMvc = MockMvcBuilders.standaloneSetup(bankInterestResource)
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
    public static BankInterest createEntity(EntityManager em) {
        BankInterest bankInterest = new BankInterest()
            .netInterest(DEFAULT_NET_INTEREST)
            .taxDeducted(DEFAULT_TAX_DEDUCTED);
        return bankInterest;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankInterest createUpdatedEntity(EntityManager em) {
        BankInterest bankInterest = new BankInterest()
            .netInterest(UPDATED_NET_INTEREST)
            .taxDeducted(UPDATED_TAX_DEDUCTED);
        return bankInterest;
    }

    @BeforeEach
    public void initTest() {
        bankInterest = createEntity(em);
    }

    @Test
    @Transactional
    public void createBankInterest() throws Exception {
        int databaseSizeBeforeCreate = bankInterestRepository.findAll().size();

        // Create the BankInterest
        restBankInterestMockMvc.perform(post("/api/bank-interests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankInterest)))
            .andExpect(status().isCreated());

        // Validate the BankInterest in the database
        List<BankInterest> bankInterestList = bankInterestRepository.findAll();
        assertThat(bankInterestList).hasSize(databaseSizeBeforeCreate + 1);
        BankInterest testBankInterest = bankInterestList.get(bankInterestList.size() - 1);
        assertThat(testBankInterest.getNetInterest()).isEqualTo(DEFAULT_NET_INTEREST);
        assertThat(testBankInterest.getTaxDeducted()).isEqualTo(DEFAULT_TAX_DEDUCTED);
    }

    @Test
    @Transactional
    public void createBankInterestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bankInterestRepository.findAll().size();

        // Create the BankInterest with an existing ID
        bankInterest.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankInterestMockMvc.perform(post("/api/bank-interests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankInterest)))
            .andExpect(status().isBadRequest());

        // Validate the BankInterest in the database
        List<BankInterest> bankInterestList = bankInterestRepository.findAll();
        assertThat(bankInterestList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBankInterests() throws Exception {
        // Initialize the database
        bankInterestRepository.saveAndFlush(bankInterest);

        // Get all the bankInterestList
        restBankInterestMockMvc.perform(get("/api/bank-interests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankInterest.getId().intValue())))
            .andExpect(jsonPath("$.[*].netInterest").value(hasItem(DEFAULT_NET_INTEREST.doubleValue())))
            .andExpect(jsonPath("$.[*].taxDeducted").value(hasItem(DEFAULT_TAX_DEDUCTED.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getBankInterest() throws Exception {
        // Initialize the database
        bankInterestRepository.saveAndFlush(bankInterest);

        // Get the bankInterest
        restBankInterestMockMvc.perform(get("/api/bank-interests/{id}", bankInterest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bankInterest.getId().intValue()))
            .andExpect(jsonPath("$.netInterest").value(DEFAULT_NET_INTEREST.doubleValue()))
            .andExpect(jsonPath("$.taxDeducted").value(DEFAULT_TAX_DEDUCTED.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBankInterest() throws Exception {
        // Get the bankInterest
        restBankInterestMockMvc.perform(get("/api/bank-interests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBankInterest() throws Exception {
        // Initialize the database
        bankInterestRepository.saveAndFlush(bankInterest);

        int databaseSizeBeforeUpdate = bankInterestRepository.findAll().size();

        // Update the bankInterest
        BankInterest updatedBankInterest = bankInterestRepository.findById(bankInterest.getId()).get();
        // Disconnect from session so that the updates on updatedBankInterest are not directly saved in db
        em.detach(updatedBankInterest);
        updatedBankInterest
            .netInterest(UPDATED_NET_INTEREST)
            .taxDeducted(UPDATED_TAX_DEDUCTED);

        restBankInterestMockMvc.perform(put("/api/bank-interests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBankInterest)))
            .andExpect(status().isOk());

        // Validate the BankInterest in the database
        List<BankInterest> bankInterestList = bankInterestRepository.findAll();
        assertThat(bankInterestList).hasSize(databaseSizeBeforeUpdate);
        BankInterest testBankInterest = bankInterestList.get(bankInterestList.size() - 1);
        assertThat(testBankInterest.getNetInterest()).isEqualTo(UPDATED_NET_INTEREST);
        assertThat(testBankInterest.getTaxDeducted()).isEqualTo(UPDATED_TAX_DEDUCTED);
    }

    @Test
    @Transactional
    public void updateNonExistingBankInterest() throws Exception {
        int databaseSizeBeforeUpdate = bankInterestRepository.findAll().size();

        // Create the BankInterest

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankInterestMockMvc.perform(put("/api/bank-interests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankInterest)))
            .andExpect(status().isBadRequest());

        // Validate the BankInterest in the database
        List<BankInterest> bankInterestList = bankInterestRepository.findAll();
        assertThat(bankInterestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBankInterest() throws Exception {
        // Initialize the database
        bankInterestRepository.saveAndFlush(bankInterest);

        int databaseSizeBeforeDelete = bankInterestRepository.findAll().size();

        // Delete the bankInterest
        restBankInterestMockMvc.perform(delete("/api/bank-interests/{id}", bankInterest.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankInterest> bankInterestList = bankInterestRepository.findAll();
        assertThat(bankInterestList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankInterest.class);
        BankInterest bankInterest1 = new BankInterest();
        bankInterest1.setId(1L);
        BankInterest bankInterest2 = new BankInterest();
        bankInterest2.setId(bankInterest1.getId());
        assertThat(bankInterest1).isEqualTo(bankInterest2);
        bankInterest2.setId(2L);
        assertThat(bankInterest1).isNotEqualTo(bankInterest2);
        bankInterest1.setId(null);
        assertThat(bankInterest1).isNotEqualTo(bankInterest2);
    }
}
