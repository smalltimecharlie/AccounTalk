package io.accountalk.web.rest;

import io.accountalk.AccounTalkApp;
import io.accountalk.domain.Benefits;
import io.accountalk.repository.BenefitsRepository;
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

import io.accountalk.domain.enumeration.BenefitType;
/**
 * Integration tests for the {@Link BenefitsResource} REST controller.
 */
@SpringBootTest(classes = AccounTalkApp.class)
public class BenefitsResourceIT {

    private static final BenefitType DEFAULT_BENEFIT_TYPE = BenefitType.UNSPECIFIED;
    private static final BenefitType UPDATED_BENEFIT_TYPE = BenefitType.COMPANY_VAN;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_VALUE = 1D;
    private static final Double UPDATED_VALUE = 2D;

    @Autowired
    private BenefitsRepository benefitsRepository;

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

    private MockMvc restBenefitsMockMvc;

    private Benefits benefits;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BenefitsResource benefitsResource = new BenefitsResource(benefitsRepository);
        this.restBenefitsMockMvc = MockMvcBuilders.standaloneSetup(benefitsResource)
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
    public static Benefits createEntity(EntityManager em) {
        Benefits benefits = new Benefits()
            .benefitType(DEFAULT_BENEFIT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .value(DEFAULT_VALUE);
        return benefits;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Benefits createUpdatedEntity(EntityManager em) {
        Benefits benefits = new Benefits()
            .benefitType(UPDATED_BENEFIT_TYPE)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE);
        return benefits;
    }

    @BeforeEach
    public void initTest() {
        benefits = createEntity(em);
    }

    @Test
    @Transactional
    public void createBenefits() throws Exception {
        int databaseSizeBeforeCreate = benefitsRepository.findAll().size();

        // Create the Benefits
        restBenefitsMockMvc.perform(post("/api/benefits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(benefits)))
            .andExpect(status().isCreated());

        // Validate the Benefits in the database
        List<Benefits> benefitsList = benefitsRepository.findAll();
        assertThat(benefitsList).hasSize(databaseSizeBeforeCreate + 1);
        Benefits testBenefits = benefitsList.get(benefitsList.size() - 1);
        assertThat(testBenefits.getBenefitType()).isEqualTo(DEFAULT_BENEFIT_TYPE);
        assertThat(testBenefits.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBenefits.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createBenefitsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = benefitsRepository.findAll().size();

        // Create the Benefits with an existing ID
        benefits.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBenefitsMockMvc.perform(post("/api/benefits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(benefits)))
            .andExpect(status().isBadRequest());

        // Validate the Benefits in the database
        List<Benefits> benefitsList = benefitsRepository.findAll();
        assertThat(benefitsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBenefits() throws Exception {
        // Initialize the database
        benefitsRepository.saveAndFlush(benefits);

        // Get all the benefitsList
        restBenefitsMockMvc.perform(get("/api/benefits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(benefits.getId().intValue())))
            .andExpect(jsonPath("$.[*].benefitType").value(hasItem(DEFAULT_BENEFIT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getBenefits() throws Exception {
        // Initialize the database
        benefitsRepository.saveAndFlush(benefits);

        // Get the benefits
        restBenefitsMockMvc.perform(get("/api/benefits/{id}", benefits.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(benefits.getId().intValue()))
            .andExpect(jsonPath("$.benefitType").value(DEFAULT_BENEFIT_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBenefits() throws Exception {
        // Get the benefits
        restBenefitsMockMvc.perform(get("/api/benefits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBenefits() throws Exception {
        // Initialize the database
        benefitsRepository.saveAndFlush(benefits);

        int databaseSizeBeforeUpdate = benefitsRepository.findAll().size();

        // Update the benefits
        Benefits updatedBenefits = benefitsRepository.findById(benefits.getId()).get();
        // Disconnect from session so that the updates on updatedBenefits are not directly saved in db
        em.detach(updatedBenefits);
        updatedBenefits
            .benefitType(UPDATED_BENEFIT_TYPE)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE);

        restBenefitsMockMvc.perform(put("/api/benefits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBenefits)))
            .andExpect(status().isOk());

        // Validate the Benefits in the database
        List<Benefits> benefitsList = benefitsRepository.findAll();
        assertThat(benefitsList).hasSize(databaseSizeBeforeUpdate);
        Benefits testBenefits = benefitsList.get(benefitsList.size() - 1);
        assertThat(testBenefits.getBenefitType()).isEqualTo(UPDATED_BENEFIT_TYPE);
        assertThat(testBenefits.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBenefits.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingBenefits() throws Exception {
        int databaseSizeBeforeUpdate = benefitsRepository.findAll().size();

        // Create the Benefits

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBenefitsMockMvc.perform(put("/api/benefits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(benefits)))
            .andExpect(status().isBadRequest());

        // Validate the Benefits in the database
        List<Benefits> benefitsList = benefitsRepository.findAll();
        assertThat(benefitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBenefits() throws Exception {
        // Initialize the database
        benefitsRepository.saveAndFlush(benefits);

        int databaseSizeBeforeDelete = benefitsRepository.findAll().size();

        // Delete the benefits
        restBenefitsMockMvc.perform(delete("/api/benefits/{id}", benefits.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Benefits> benefitsList = benefitsRepository.findAll();
        assertThat(benefitsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Benefits.class);
        Benefits benefits1 = new Benefits();
        benefits1.setId(1L);
        Benefits benefits2 = new Benefits();
        benefits2.setId(benefits1.getId());
        assertThat(benefits1).isEqualTo(benefits2);
        benefits2.setId(2L);
        assertThat(benefits1).isNotEqualTo(benefits2);
        benefits1.setId(null);
        assertThat(benefits1).isNotEqualTo(benefits2);
    }
}
