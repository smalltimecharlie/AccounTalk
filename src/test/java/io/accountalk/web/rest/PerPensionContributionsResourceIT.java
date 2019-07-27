package io.accountalk.web.rest;

import io.accountalk.AccounTalkApp;
import io.accountalk.domain.PerPensionContributions;
import io.accountalk.repository.PerPensionContributionsRepository;
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
 * Integration tests for the {@Link PerPensionContributionsResource} REST controller.
 */
@SpringBootTest(classes = AccounTalkApp.class)
public class PerPensionContributionsResourceIT {

    private static final Double DEFAULT_NET_AMOUNT_PAID = 1D;
    private static final Double UPDATED_NET_AMOUNT_PAID = 2D;

    @Autowired
    private PerPensionContributionsRepository perPensionContributionsRepository;

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

    private MockMvc restPerPensionContributionsMockMvc;

    private PerPensionContributions perPensionContributions;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PerPensionContributionsResource perPensionContributionsResource = new PerPensionContributionsResource(perPensionContributionsRepository);
        this.restPerPensionContributionsMockMvc = MockMvcBuilders.standaloneSetup(perPensionContributionsResource)
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
    public static PerPensionContributions createEntity(EntityManager em) {
        PerPensionContributions perPensionContributions = new PerPensionContributions()
            .netAmountPaid(DEFAULT_NET_AMOUNT_PAID);
        return perPensionContributions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerPensionContributions createUpdatedEntity(EntityManager em) {
        PerPensionContributions perPensionContributions = new PerPensionContributions()
            .netAmountPaid(UPDATED_NET_AMOUNT_PAID);
        return perPensionContributions;
    }

    @BeforeEach
    public void initTest() {
        perPensionContributions = createEntity(em);
    }

    @Test
    @Transactional
    public void createPerPensionContributions() throws Exception {
        int databaseSizeBeforeCreate = perPensionContributionsRepository.findAll().size();

        // Create the PerPensionContributions
        restPerPensionContributionsMockMvc.perform(post("/api/per-pension-contributions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perPensionContributions)))
            .andExpect(status().isCreated());

        // Validate the PerPensionContributions in the database
        List<PerPensionContributions> perPensionContributionsList = perPensionContributionsRepository.findAll();
        assertThat(perPensionContributionsList).hasSize(databaseSizeBeforeCreate + 1);
        PerPensionContributions testPerPensionContributions = perPensionContributionsList.get(perPensionContributionsList.size() - 1);
        assertThat(testPerPensionContributions.getNetAmountPaid()).isEqualTo(DEFAULT_NET_AMOUNT_PAID);
    }

    @Test
    @Transactional
    public void createPerPensionContributionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = perPensionContributionsRepository.findAll().size();

        // Create the PerPensionContributions with an existing ID
        perPensionContributions.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerPensionContributionsMockMvc.perform(post("/api/per-pension-contributions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perPensionContributions)))
            .andExpect(status().isBadRequest());

        // Validate the PerPensionContributions in the database
        List<PerPensionContributions> perPensionContributionsList = perPensionContributionsRepository.findAll();
        assertThat(perPensionContributionsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPerPensionContributions() throws Exception {
        // Initialize the database
        perPensionContributionsRepository.saveAndFlush(perPensionContributions);

        // Get all the perPensionContributionsList
        restPerPensionContributionsMockMvc.perform(get("/api/per-pension-contributions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perPensionContributions.getId().intValue())))
            .andExpect(jsonPath("$.[*].netAmountPaid").value(hasItem(DEFAULT_NET_AMOUNT_PAID.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getPerPensionContributions() throws Exception {
        // Initialize the database
        perPensionContributionsRepository.saveAndFlush(perPensionContributions);

        // Get the perPensionContributions
        restPerPensionContributionsMockMvc.perform(get("/api/per-pension-contributions/{id}", perPensionContributions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(perPensionContributions.getId().intValue()))
            .andExpect(jsonPath("$.netAmountPaid").value(DEFAULT_NET_AMOUNT_PAID.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPerPensionContributions() throws Exception {
        // Get the perPensionContributions
        restPerPensionContributionsMockMvc.perform(get("/api/per-pension-contributions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerPensionContributions() throws Exception {
        // Initialize the database
        perPensionContributionsRepository.saveAndFlush(perPensionContributions);

        int databaseSizeBeforeUpdate = perPensionContributionsRepository.findAll().size();

        // Update the perPensionContributions
        PerPensionContributions updatedPerPensionContributions = perPensionContributionsRepository.findById(perPensionContributions.getId()).get();
        // Disconnect from session so that the updates on updatedPerPensionContributions are not directly saved in db
        em.detach(updatedPerPensionContributions);
        updatedPerPensionContributions
            .netAmountPaid(UPDATED_NET_AMOUNT_PAID);

        restPerPensionContributionsMockMvc.perform(put("/api/per-pension-contributions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPerPensionContributions)))
            .andExpect(status().isOk());

        // Validate the PerPensionContributions in the database
        List<PerPensionContributions> perPensionContributionsList = perPensionContributionsRepository.findAll();
        assertThat(perPensionContributionsList).hasSize(databaseSizeBeforeUpdate);
        PerPensionContributions testPerPensionContributions = perPensionContributionsList.get(perPensionContributionsList.size() - 1);
        assertThat(testPerPensionContributions.getNetAmountPaid()).isEqualTo(UPDATED_NET_AMOUNT_PAID);
    }

    @Test
    @Transactional
    public void updateNonExistingPerPensionContributions() throws Exception {
        int databaseSizeBeforeUpdate = perPensionContributionsRepository.findAll().size();

        // Create the PerPensionContributions

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerPensionContributionsMockMvc.perform(put("/api/per-pension-contributions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perPensionContributions)))
            .andExpect(status().isBadRequest());

        // Validate the PerPensionContributions in the database
        List<PerPensionContributions> perPensionContributionsList = perPensionContributionsRepository.findAll();
        assertThat(perPensionContributionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePerPensionContributions() throws Exception {
        // Initialize the database
        perPensionContributionsRepository.saveAndFlush(perPensionContributions);

        int databaseSizeBeforeDelete = perPensionContributionsRepository.findAll().size();

        // Delete the perPensionContributions
        restPerPensionContributionsMockMvc.perform(delete("/api/per-pension-contributions/{id}", perPensionContributions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PerPensionContributions> perPensionContributionsList = perPensionContributionsRepository.findAll();
        assertThat(perPensionContributionsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerPensionContributions.class);
        PerPensionContributions perPensionContributions1 = new PerPensionContributions();
        perPensionContributions1.setId(1L);
        PerPensionContributions perPensionContributions2 = new PerPensionContributions();
        perPensionContributions2.setId(perPensionContributions1.getId());
        assertThat(perPensionContributions1).isEqualTo(perPensionContributions2);
        perPensionContributions2.setId(2L);
        assertThat(perPensionContributions1).isNotEqualTo(perPensionContributions2);
        perPensionContributions1.setId(null);
        assertThat(perPensionContributions1).isNotEqualTo(perPensionContributions2);
    }
}
