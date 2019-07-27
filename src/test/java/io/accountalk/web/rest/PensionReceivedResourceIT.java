package io.accountalk.web.rest;

import io.accountalk.AccounTalkApp;
import io.accountalk.domain.PensionReceived;
import io.accountalk.repository.PensionReceivedRepository;
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
 * Integration tests for the {@Link PensionReceivedResource} REST controller.
 */
@SpringBootTest(classes = AccounTalkApp.class)
public class PensionReceivedResourceIT {

    private static final Double DEFAULT_GROSS_PENSION_RECEIVED = 1D;
    private static final Double UPDATED_GROSS_PENSION_RECEIVED = 2D;

    private static final Double DEFAULT_TAX_DEDUCTED = 1D;
    private static final Double UPDATED_TAX_DEDUCTED = 2D;

    @Autowired
    private PensionReceivedRepository pensionReceivedRepository;

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

    private MockMvc restPensionReceivedMockMvc;

    private PensionReceived pensionReceived;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PensionReceivedResource pensionReceivedResource = new PensionReceivedResource(pensionReceivedRepository);
        this.restPensionReceivedMockMvc = MockMvcBuilders.standaloneSetup(pensionReceivedResource)
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
    public static PensionReceived createEntity(EntityManager em) {
        PensionReceived pensionReceived = new PensionReceived()
            .grossPensionReceived(DEFAULT_GROSS_PENSION_RECEIVED)
            .taxDeducted(DEFAULT_TAX_DEDUCTED);
        return pensionReceived;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PensionReceived createUpdatedEntity(EntityManager em) {
        PensionReceived pensionReceived = new PensionReceived()
            .grossPensionReceived(UPDATED_GROSS_PENSION_RECEIVED)
            .taxDeducted(UPDATED_TAX_DEDUCTED);
        return pensionReceived;
    }

    @BeforeEach
    public void initTest() {
        pensionReceived = createEntity(em);
    }

    @Test
    @Transactional
    public void createPensionReceived() throws Exception {
        int databaseSizeBeforeCreate = pensionReceivedRepository.findAll().size();

        // Create the PensionReceived
        restPensionReceivedMockMvc.perform(post("/api/pension-receiveds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pensionReceived)))
            .andExpect(status().isCreated());

        // Validate the PensionReceived in the database
        List<PensionReceived> pensionReceivedList = pensionReceivedRepository.findAll();
        assertThat(pensionReceivedList).hasSize(databaseSizeBeforeCreate + 1);
        PensionReceived testPensionReceived = pensionReceivedList.get(pensionReceivedList.size() - 1);
        assertThat(testPensionReceived.getGrossPensionReceived()).isEqualTo(DEFAULT_GROSS_PENSION_RECEIVED);
        assertThat(testPensionReceived.getTaxDeducted()).isEqualTo(DEFAULT_TAX_DEDUCTED);
    }

    @Test
    @Transactional
    public void createPensionReceivedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pensionReceivedRepository.findAll().size();

        // Create the PensionReceived with an existing ID
        pensionReceived.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPensionReceivedMockMvc.perform(post("/api/pension-receiveds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pensionReceived)))
            .andExpect(status().isBadRequest());

        // Validate the PensionReceived in the database
        List<PensionReceived> pensionReceivedList = pensionReceivedRepository.findAll();
        assertThat(pensionReceivedList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPensionReceiveds() throws Exception {
        // Initialize the database
        pensionReceivedRepository.saveAndFlush(pensionReceived);

        // Get all the pensionReceivedList
        restPensionReceivedMockMvc.perform(get("/api/pension-receiveds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pensionReceived.getId().intValue())))
            .andExpect(jsonPath("$.[*].grossPensionReceived").value(hasItem(DEFAULT_GROSS_PENSION_RECEIVED.doubleValue())))
            .andExpect(jsonPath("$.[*].taxDeducted").value(hasItem(DEFAULT_TAX_DEDUCTED.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getPensionReceived() throws Exception {
        // Initialize the database
        pensionReceivedRepository.saveAndFlush(pensionReceived);

        // Get the pensionReceived
        restPensionReceivedMockMvc.perform(get("/api/pension-receiveds/{id}", pensionReceived.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pensionReceived.getId().intValue()))
            .andExpect(jsonPath("$.grossPensionReceived").value(DEFAULT_GROSS_PENSION_RECEIVED.doubleValue()))
            .andExpect(jsonPath("$.taxDeducted").value(DEFAULT_TAX_DEDUCTED.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPensionReceived() throws Exception {
        // Get the pensionReceived
        restPensionReceivedMockMvc.perform(get("/api/pension-receiveds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePensionReceived() throws Exception {
        // Initialize the database
        pensionReceivedRepository.saveAndFlush(pensionReceived);

        int databaseSizeBeforeUpdate = pensionReceivedRepository.findAll().size();

        // Update the pensionReceived
        PensionReceived updatedPensionReceived = pensionReceivedRepository.findById(pensionReceived.getId()).get();
        // Disconnect from session so that the updates on updatedPensionReceived are not directly saved in db
        em.detach(updatedPensionReceived);
        updatedPensionReceived
            .grossPensionReceived(UPDATED_GROSS_PENSION_RECEIVED)
            .taxDeducted(UPDATED_TAX_DEDUCTED);

        restPensionReceivedMockMvc.perform(put("/api/pension-receiveds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPensionReceived)))
            .andExpect(status().isOk());

        // Validate the PensionReceived in the database
        List<PensionReceived> pensionReceivedList = pensionReceivedRepository.findAll();
        assertThat(pensionReceivedList).hasSize(databaseSizeBeforeUpdate);
        PensionReceived testPensionReceived = pensionReceivedList.get(pensionReceivedList.size() - 1);
        assertThat(testPensionReceived.getGrossPensionReceived()).isEqualTo(UPDATED_GROSS_PENSION_RECEIVED);
        assertThat(testPensionReceived.getTaxDeducted()).isEqualTo(UPDATED_TAX_DEDUCTED);
    }

    @Test
    @Transactional
    public void updateNonExistingPensionReceived() throws Exception {
        int databaseSizeBeforeUpdate = pensionReceivedRepository.findAll().size();

        // Create the PensionReceived

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPensionReceivedMockMvc.perform(put("/api/pension-receiveds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pensionReceived)))
            .andExpect(status().isBadRequest());

        // Validate the PensionReceived in the database
        List<PensionReceived> pensionReceivedList = pensionReceivedRepository.findAll();
        assertThat(pensionReceivedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePensionReceived() throws Exception {
        // Initialize the database
        pensionReceivedRepository.saveAndFlush(pensionReceived);

        int databaseSizeBeforeDelete = pensionReceivedRepository.findAll().size();

        // Delete the pensionReceived
        restPensionReceivedMockMvc.perform(delete("/api/pension-receiveds/{id}", pensionReceived.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PensionReceived> pensionReceivedList = pensionReceivedRepository.findAll();
        assertThat(pensionReceivedList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PensionReceived.class);
        PensionReceived pensionReceived1 = new PensionReceived();
        pensionReceived1.setId(1L);
        PensionReceived pensionReceived2 = new PensionReceived();
        pensionReceived2.setId(pensionReceived1.getId());
        assertThat(pensionReceived1).isEqualTo(pensionReceived2);
        pensionReceived2.setId(2L);
        assertThat(pensionReceived1).isNotEqualTo(pensionReceived2);
        pensionReceived1.setId(null);
        assertThat(pensionReceived1).isNotEqualTo(pensionReceived2);
    }
}
