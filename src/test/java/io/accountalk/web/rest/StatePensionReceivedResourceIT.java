package io.accountalk.web.rest;

import io.accountalk.AccounTalkApp;
import io.accountalk.domain.StatePensionReceived;
import io.accountalk.repository.StatePensionReceivedRepository;
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
 * Integration tests for the {@Link StatePensionReceivedResource} REST controller.
 */
@SpringBootTest(classes = AccounTalkApp.class)
public class StatePensionReceivedResourceIT {

    private static final Double DEFAULT_GROSS_PENSION_RECEIVED = 1D;
    private static final Double UPDATED_GROSS_PENSION_RECEIVED = 2D;

    private static final Double DEFAULT_TAX_DEDUCTED = 1D;
    private static final Double UPDATED_TAX_DEDUCTED = 2D;

    @Autowired
    private StatePensionReceivedRepository statePensionReceivedRepository;

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

    private MockMvc restStatePensionReceivedMockMvc;

    private StatePensionReceived statePensionReceived;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StatePensionReceivedResource statePensionReceivedResource = new StatePensionReceivedResource(statePensionReceivedRepository);
        this.restStatePensionReceivedMockMvc = MockMvcBuilders.standaloneSetup(statePensionReceivedResource)
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
    public static StatePensionReceived createEntity(EntityManager em) {
        StatePensionReceived statePensionReceived = new StatePensionReceived()
            .grossPensionReceived(DEFAULT_GROSS_PENSION_RECEIVED)
            .taxDeducted(DEFAULT_TAX_DEDUCTED);
        return statePensionReceived;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StatePensionReceived createUpdatedEntity(EntityManager em) {
        StatePensionReceived statePensionReceived = new StatePensionReceived()
            .grossPensionReceived(UPDATED_GROSS_PENSION_RECEIVED)
            .taxDeducted(UPDATED_TAX_DEDUCTED);
        return statePensionReceived;
    }

    @BeforeEach
    public void initTest() {
        statePensionReceived = createEntity(em);
    }

    @Test
    @Transactional
    public void createStatePensionReceived() throws Exception {
        int databaseSizeBeforeCreate = statePensionReceivedRepository.findAll().size();

        // Create the StatePensionReceived
        restStatePensionReceivedMockMvc.perform(post("/api/state-pension-receiveds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statePensionReceived)))
            .andExpect(status().isCreated());

        // Validate the StatePensionReceived in the database
        List<StatePensionReceived> statePensionReceivedList = statePensionReceivedRepository.findAll();
        assertThat(statePensionReceivedList).hasSize(databaseSizeBeforeCreate + 1);
        StatePensionReceived testStatePensionReceived = statePensionReceivedList.get(statePensionReceivedList.size() - 1);
        assertThat(testStatePensionReceived.getGrossPensionReceived()).isEqualTo(DEFAULT_GROSS_PENSION_RECEIVED);
        assertThat(testStatePensionReceived.getTaxDeducted()).isEqualTo(DEFAULT_TAX_DEDUCTED);
    }

    @Test
    @Transactional
    public void createStatePensionReceivedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = statePensionReceivedRepository.findAll().size();

        // Create the StatePensionReceived with an existing ID
        statePensionReceived.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatePensionReceivedMockMvc.perform(post("/api/state-pension-receiveds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statePensionReceived)))
            .andExpect(status().isBadRequest());

        // Validate the StatePensionReceived in the database
        List<StatePensionReceived> statePensionReceivedList = statePensionReceivedRepository.findAll();
        assertThat(statePensionReceivedList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStatePensionReceiveds() throws Exception {
        // Initialize the database
        statePensionReceivedRepository.saveAndFlush(statePensionReceived);

        // Get all the statePensionReceivedList
        restStatePensionReceivedMockMvc.perform(get("/api/state-pension-receiveds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statePensionReceived.getId().intValue())))
            .andExpect(jsonPath("$.[*].grossPensionReceived").value(hasItem(DEFAULT_GROSS_PENSION_RECEIVED.doubleValue())))
            .andExpect(jsonPath("$.[*].taxDeducted").value(hasItem(DEFAULT_TAX_DEDUCTED.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getStatePensionReceived() throws Exception {
        // Initialize the database
        statePensionReceivedRepository.saveAndFlush(statePensionReceived);

        // Get the statePensionReceived
        restStatePensionReceivedMockMvc.perform(get("/api/state-pension-receiveds/{id}", statePensionReceived.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(statePensionReceived.getId().intValue()))
            .andExpect(jsonPath("$.grossPensionReceived").value(DEFAULT_GROSS_PENSION_RECEIVED.doubleValue()))
            .andExpect(jsonPath("$.taxDeducted").value(DEFAULT_TAX_DEDUCTED.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStatePensionReceived() throws Exception {
        // Get the statePensionReceived
        restStatePensionReceivedMockMvc.perform(get("/api/state-pension-receiveds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatePensionReceived() throws Exception {
        // Initialize the database
        statePensionReceivedRepository.saveAndFlush(statePensionReceived);

        int databaseSizeBeforeUpdate = statePensionReceivedRepository.findAll().size();

        // Update the statePensionReceived
        StatePensionReceived updatedStatePensionReceived = statePensionReceivedRepository.findById(statePensionReceived.getId()).get();
        // Disconnect from session so that the updates on updatedStatePensionReceived are not directly saved in db
        em.detach(updatedStatePensionReceived);
        updatedStatePensionReceived
            .grossPensionReceived(UPDATED_GROSS_PENSION_RECEIVED)
            .taxDeducted(UPDATED_TAX_DEDUCTED);

        restStatePensionReceivedMockMvc.perform(put("/api/state-pension-receiveds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStatePensionReceived)))
            .andExpect(status().isOk());

        // Validate the StatePensionReceived in the database
        List<StatePensionReceived> statePensionReceivedList = statePensionReceivedRepository.findAll();
        assertThat(statePensionReceivedList).hasSize(databaseSizeBeforeUpdate);
        StatePensionReceived testStatePensionReceived = statePensionReceivedList.get(statePensionReceivedList.size() - 1);
        assertThat(testStatePensionReceived.getGrossPensionReceived()).isEqualTo(UPDATED_GROSS_PENSION_RECEIVED);
        assertThat(testStatePensionReceived.getTaxDeducted()).isEqualTo(UPDATED_TAX_DEDUCTED);
    }

    @Test
    @Transactional
    public void updateNonExistingStatePensionReceived() throws Exception {
        int databaseSizeBeforeUpdate = statePensionReceivedRepository.findAll().size();

        // Create the StatePensionReceived

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatePensionReceivedMockMvc.perform(put("/api/state-pension-receiveds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statePensionReceived)))
            .andExpect(status().isBadRequest());

        // Validate the StatePensionReceived in the database
        List<StatePensionReceived> statePensionReceivedList = statePensionReceivedRepository.findAll();
        assertThat(statePensionReceivedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStatePensionReceived() throws Exception {
        // Initialize the database
        statePensionReceivedRepository.saveAndFlush(statePensionReceived);

        int databaseSizeBeforeDelete = statePensionReceivedRepository.findAll().size();

        // Delete the statePensionReceived
        restStatePensionReceivedMockMvc.perform(delete("/api/state-pension-receiveds/{id}", statePensionReceived.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StatePensionReceived> statePensionReceivedList = statePensionReceivedRepository.findAll();
        assertThat(statePensionReceivedList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatePensionReceived.class);
        StatePensionReceived statePensionReceived1 = new StatePensionReceived();
        statePensionReceived1.setId(1L);
        StatePensionReceived statePensionReceived2 = new StatePensionReceived();
        statePensionReceived2.setId(statePensionReceived1.getId());
        assertThat(statePensionReceived1).isEqualTo(statePensionReceived2);
        statePensionReceived2.setId(2L);
        assertThat(statePensionReceived1).isNotEqualTo(statePensionReceived2);
        statePensionReceived1.setId(null);
        assertThat(statePensionReceived1).isNotEqualTo(statePensionReceived2);
    }
}
