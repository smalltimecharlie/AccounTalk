package io.accountalk.web.rest;

import io.accountalk.AccounTalkApp;
import io.accountalk.domain.BankDetails;
import io.accountalk.repository.BankDetailsRepository;
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

/**
 * Integration tests for the {@Link BankDetailsResource} REST controller.
 */
@SpringBootTest(classes = AccounTalkApp.class)
public class BankDetailsResourceIT {

    private static final String DEFAULT_ACCOUNT_HOLDER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_HOLDER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_SORT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SORT_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_JOINT_ACCOUNT = false;
    private static final Boolean UPDATED_JOINT_ACCOUNT = true;

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_OPENING_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OPENING_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CLOSED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CLOSED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private BankDetailsRepository bankDetailsRepository;

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

    private MockMvc restBankDetailsMockMvc;

    private BankDetails bankDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BankDetailsResource bankDetailsResource = new BankDetailsResource(bankDetailsRepository);
        this.restBankDetailsMockMvc = MockMvcBuilders.standaloneSetup(bankDetailsResource)
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
    public static BankDetails createEntity(EntityManager em) {
        BankDetails bankDetails = new BankDetails()
            .accountHolderName(DEFAULT_ACCOUNT_HOLDER_NAME)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .sortCode(DEFAULT_SORT_CODE)
            .jointAccount(DEFAULT_JOINT_ACCOUNT)
            .bankName(DEFAULT_BANK_NAME)
            .openingDate(DEFAULT_OPENING_DATE)
            .closedDate(DEFAULT_CLOSED_DATE);
        return bankDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankDetails createUpdatedEntity(EntityManager em) {
        BankDetails bankDetails = new BankDetails()
            .accountHolderName(UPDATED_ACCOUNT_HOLDER_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .sortCode(UPDATED_SORT_CODE)
            .jointAccount(UPDATED_JOINT_ACCOUNT)
            .bankName(UPDATED_BANK_NAME)
            .openingDate(UPDATED_OPENING_DATE)
            .closedDate(UPDATED_CLOSED_DATE);
        return bankDetails;
    }

    @BeforeEach
    public void initTest() {
        bankDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createBankDetails() throws Exception {
        int databaseSizeBeforeCreate = bankDetailsRepository.findAll().size();

        // Create the BankDetails
        restBankDetailsMockMvc.perform(post("/api/bank-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankDetails)))
            .andExpect(status().isCreated());

        // Validate the BankDetails in the database
        List<BankDetails> bankDetailsList = bankDetailsRepository.findAll();
        assertThat(bankDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        BankDetails testBankDetails = bankDetailsList.get(bankDetailsList.size() - 1);
        assertThat(testBankDetails.getAccountHolderName()).isEqualTo(DEFAULT_ACCOUNT_HOLDER_NAME);
        assertThat(testBankDetails.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testBankDetails.getSortCode()).isEqualTo(DEFAULT_SORT_CODE);
        assertThat(testBankDetails.isJointAccount()).isEqualTo(DEFAULT_JOINT_ACCOUNT);
        assertThat(testBankDetails.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testBankDetails.getOpeningDate()).isEqualTo(DEFAULT_OPENING_DATE);
        assertThat(testBankDetails.getClosedDate()).isEqualTo(DEFAULT_CLOSED_DATE);
    }

    @Test
    @Transactional
    public void createBankDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bankDetailsRepository.findAll().size();

        // Create the BankDetails with an existing ID
        bankDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankDetailsMockMvc.perform(post("/api/bank-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankDetails)))
            .andExpect(status().isBadRequest());

        // Validate the BankDetails in the database
        List<BankDetails> bankDetailsList = bankDetailsRepository.findAll();
        assertThat(bankDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBankDetails() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList
        restBankDetailsMockMvc.perform(get("/api/bank-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountHolderName").value(hasItem(DEFAULT_ACCOUNT_HOLDER_NAME.toString())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].sortCode").value(hasItem(DEFAULT_SORT_CODE.toString())))
            .andExpect(jsonPath("$.[*].jointAccount").value(hasItem(DEFAULT_JOINT_ACCOUNT.booleanValue())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME.toString())))
            .andExpect(jsonPath("$.[*].openingDate").value(hasItem(DEFAULT_OPENING_DATE.toString())))
            .andExpect(jsonPath("$.[*].closedDate").value(hasItem(DEFAULT_CLOSED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getBankDetails() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get the bankDetails
        restBankDetailsMockMvc.perform(get("/api/bank-details/{id}", bankDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bankDetails.getId().intValue()))
            .andExpect(jsonPath("$.accountHolderName").value(DEFAULT_ACCOUNT_HOLDER_NAME.toString()))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.sortCode").value(DEFAULT_SORT_CODE.toString()))
            .andExpect(jsonPath("$.jointAccount").value(DEFAULT_JOINT_ACCOUNT.booleanValue()))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME.toString()))
            .andExpect(jsonPath("$.openingDate").value(DEFAULT_OPENING_DATE.toString()))
            .andExpect(jsonPath("$.closedDate").value(DEFAULT_CLOSED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBankDetails() throws Exception {
        // Get the bankDetails
        restBankDetailsMockMvc.perform(get("/api/bank-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBankDetails() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        int databaseSizeBeforeUpdate = bankDetailsRepository.findAll().size();

        // Update the bankDetails
        BankDetails updatedBankDetails = bankDetailsRepository.findById(bankDetails.getId()).get();
        // Disconnect from session so that the updates on updatedBankDetails are not directly saved in db
        em.detach(updatedBankDetails);
        updatedBankDetails
            .accountHolderName(UPDATED_ACCOUNT_HOLDER_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .sortCode(UPDATED_SORT_CODE)
            .jointAccount(UPDATED_JOINT_ACCOUNT)
            .bankName(UPDATED_BANK_NAME)
            .openingDate(UPDATED_OPENING_DATE)
            .closedDate(UPDATED_CLOSED_DATE);

        restBankDetailsMockMvc.perform(put("/api/bank-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBankDetails)))
            .andExpect(status().isOk());

        // Validate the BankDetails in the database
        List<BankDetails> bankDetailsList = bankDetailsRepository.findAll();
        assertThat(bankDetailsList).hasSize(databaseSizeBeforeUpdate);
        BankDetails testBankDetails = bankDetailsList.get(bankDetailsList.size() - 1);
        assertThat(testBankDetails.getAccountHolderName()).isEqualTo(UPDATED_ACCOUNT_HOLDER_NAME);
        assertThat(testBankDetails.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testBankDetails.getSortCode()).isEqualTo(UPDATED_SORT_CODE);
        assertThat(testBankDetails.isJointAccount()).isEqualTo(UPDATED_JOINT_ACCOUNT);
        assertThat(testBankDetails.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testBankDetails.getOpeningDate()).isEqualTo(UPDATED_OPENING_DATE);
        assertThat(testBankDetails.getClosedDate()).isEqualTo(UPDATED_CLOSED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingBankDetails() throws Exception {
        int databaseSizeBeforeUpdate = bankDetailsRepository.findAll().size();

        // Create the BankDetails

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankDetailsMockMvc.perform(put("/api/bank-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankDetails)))
            .andExpect(status().isBadRequest());

        // Validate the BankDetails in the database
        List<BankDetails> bankDetailsList = bankDetailsRepository.findAll();
        assertThat(bankDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBankDetails() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        int databaseSizeBeforeDelete = bankDetailsRepository.findAll().size();

        // Delete the bankDetails
        restBankDetailsMockMvc.perform(delete("/api/bank-details/{id}", bankDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankDetails> bankDetailsList = bankDetailsRepository.findAll();
        assertThat(bankDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankDetails.class);
        BankDetails bankDetails1 = new BankDetails();
        bankDetails1.setId(1L);
        BankDetails bankDetails2 = new BankDetails();
        bankDetails2.setId(bankDetails1.getId());
        assertThat(bankDetails1).isEqualTo(bankDetails2);
        bankDetails2.setId(2L);
        assertThat(bankDetails1).isNotEqualTo(bankDetails2);
        bankDetails1.setId(null);
        assertThat(bankDetails1).isNotEqualTo(bankDetails2);
    }
}
