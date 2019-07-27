package io.accountalk.web.rest;

import io.accountalk.AccounTalkApp;
import io.accountalk.domain.DividendsReceived;
import io.accountalk.repository.DividendsReceivedRepository;
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
 * Integration tests for the {@Link DividendsReceivedResource} REST controller.
 */
@SpringBootTest(classes = AccounTalkApp.class)
public class DividendsReceivedResourceIT {

    private static final Instant DEFAULT_PAYMENT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PAYMENT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_AMOUNT_RECEIVED = 1D;
    private static final Double UPDATED_AMOUNT_RECEIVED = 2D;

    @Autowired
    private DividendsReceivedRepository dividendsReceivedRepository;

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

    private MockMvc restDividendsReceivedMockMvc;

    private DividendsReceived dividendsReceived;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DividendsReceivedResource dividendsReceivedResource = new DividendsReceivedResource(dividendsReceivedRepository);
        this.restDividendsReceivedMockMvc = MockMvcBuilders.standaloneSetup(dividendsReceivedResource)
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
    public static DividendsReceived createEntity(EntityManager em) {
        DividendsReceived dividendsReceived = new DividendsReceived()
            .paymentDate(DEFAULT_PAYMENT_DATE)
            .amountReceived(DEFAULT_AMOUNT_RECEIVED);
        return dividendsReceived;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DividendsReceived createUpdatedEntity(EntityManager em) {
        DividendsReceived dividendsReceived = new DividendsReceived()
            .paymentDate(UPDATED_PAYMENT_DATE)
            .amountReceived(UPDATED_AMOUNT_RECEIVED);
        return dividendsReceived;
    }

    @BeforeEach
    public void initTest() {
        dividendsReceived = createEntity(em);
    }

    @Test
    @Transactional
    public void createDividendsReceived() throws Exception {
        int databaseSizeBeforeCreate = dividendsReceivedRepository.findAll().size();

        // Create the DividendsReceived
        restDividendsReceivedMockMvc.perform(post("/api/dividends-receiveds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dividendsReceived)))
            .andExpect(status().isCreated());

        // Validate the DividendsReceived in the database
        List<DividendsReceived> dividendsReceivedList = dividendsReceivedRepository.findAll();
        assertThat(dividendsReceivedList).hasSize(databaseSizeBeforeCreate + 1);
        DividendsReceived testDividendsReceived = dividendsReceivedList.get(dividendsReceivedList.size() - 1);
        assertThat(testDividendsReceived.getPaymentDate()).isEqualTo(DEFAULT_PAYMENT_DATE);
        assertThat(testDividendsReceived.getAmountReceived()).isEqualTo(DEFAULT_AMOUNT_RECEIVED);
    }

    @Test
    @Transactional
    public void createDividendsReceivedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dividendsReceivedRepository.findAll().size();

        // Create the DividendsReceived with an existing ID
        dividendsReceived.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDividendsReceivedMockMvc.perform(post("/api/dividends-receiveds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dividendsReceived)))
            .andExpect(status().isBadRequest());

        // Validate the DividendsReceived in the database
        List<DividendsReceived> dividendsReceivedList = dividendsReceivedRepository.findAll();
        assertThat(dividendsReceivedList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDividendsReceiveds() throws Exception {
        // Initialize the database
        dividendsReceivedRepository.saveAndFlush(dividendsReceived);

        // Get all the dividendsReceivedList
        restDividendsReceivedMockMvc.perform(get("/api/dividends-receiveds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dividendsReceived.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(DEFAULT_PAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].amountReceived").value(hasItem(DEFAULT_AMOUNT_RECEIVED.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getDividendsReceived() throws Exception {
        // Initialize the database
        dividendsReceivedRepository.saveAndFlush(dividendsReceived);

        // Get the dividendsReceived
        restDividendsReceivedMockMvc.perform(get("/api/dividends-receiveds/{id}", dividendsReceived.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dividendsReceived.getId().intValue()))
            .andExpect(jsonPath("$.paymentDate").value(DEFAULT_PAYMENT_DATE.toString()))
            .andExpect(jsonPath("$.amountReceived").value(DEFAULT_AMOUNT_RECEIVED.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDividendsReceived() throws Exception {
        // Get the dividendsReceived
        restDividendsReceivedMockMvc.perform(get("/api/dividends-receiveds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDividendsReceived() throws Exception {
        // Initialize the database
        dividendsReceivedRepository.saveAndFlush(dividendsReceived);

        int databaseSizeBeforeUpdate = dividendsReceivedRepository.findAll().size();

        // Update the dividendsReceived
        DividendsReceived updatedDividendsReceived = dividendsReceivedRepository.findById(dividendsReceived.getId()).get();
        // Disconnect from session so that the updates on updatedDividendsReceived are not directly saved in db
        em.detach(updatedDividendsReceived);
        updatedDividendsReceived
            .paymentDate(UPDATED_PAYMENT_DATE)
            .amountReceived(UPDATED_AMOUNT_RECEIVED);

        restDividendsReceivedMockMvc.perform(put("/api/dividends-receiveds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDividendsReceived)))
            .andExpect(status().isOk());

        // Validate the DividendsReceived in the database
        List<DividendsReceived> dividendsReceivedList = dividendsReceivedRepository.findAll();
        assertThat(dividendsReceivedList).hasSize(databaseSizeBeforeUpdate);
        DividendsReceived testDividendsReceived = dividendsReceivedList.get(dividendsReceivedList.size() - 1);
        assertThat(testDividendsReceived.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testDividendsReceived.getAmountReceived()).isEqualTo(UPDATED_AMOUNT_RECEIVED);
    }

    @Test
    @Transactional
    public void updateNonExistingDividendsReceived() throws Exception {
        int databaseSizeBeforeUpdate = dividendsReceivedRepository.findAll().size();

        // Create the DividendsReceived

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDividendsReceivedMockMvc.perform(put("/api/dividends-receiveds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dividendsReceived)))
            .andExpect(status().isBadRequest());

        // Validate the DividendsReceived in the database
        List<DividendsReceived> dividendsReceivedList = dividendsReceivedRepository.findAll();
        assertThat(dividendsReceivedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDividendsReceived() throws Exception {
        // Initialize the database
        dividendsReceivedRepository.saveAndFlush(dividendsReceived);

        int databaseSizeBeforeDelete = dividendsReceivedRepository.findAll().size();

        // Delete the dividendsReceived
        restDividendsReceivedMockMvc.perform(delete("/api/dividends-receiveds/{id}", dividendsReceived.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DividendsReceived> dividendsReceivedList = dividendsReceivedRepository.findAll();
        assertThat(dividendsReceivedList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DividendsReceived.class);
        DividendsReceived dividendsReceived1 = new DividendsReceived();
        dividendsReceived1.setId(1L);
        DividendsReceived dividendsReceived2 = new DividendsReceived();
        dividendsReceived2.setId(dividendsReceived1.getId());
        assertThat(dividendsReceived1).isEqualTo(dividendsReceived2);
        dividendsReceived2.setId(2L);
        assertThat(dividendsReceived1).isNotEqualTo(dividendsReceived2);
        dividendsReceived1.setId(null);
        assertThat(dividendsReceived1).isNotEqualTo(dividendsReceived2);
    }
}
