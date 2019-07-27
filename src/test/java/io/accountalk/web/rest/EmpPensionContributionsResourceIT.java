package io.accountalk.web.rest;

import io.accountalk.AccounTalkApp;
import io.accountalk.domain.EmpPensionContributions;
import io.accountalk.repository.EmpPensionContributionsRepository;
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
 * Integration tests for the {@Link EmpPensionContributionsResource} REST controller.
 */
@SpringBootTest(classes = AccounTalkApp.class)
public class EmpPensionContributionsResourceIT {

    private static final Double DEFAULT_AMOUNT_PAID = 1D;
    private static final Double UPDATED_AMOUNT_PAID = 2D;

    @Autowired
    private EmpPensionContributionsRepository empPensionContributionsRepository;

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

    private MockMvc restEmpPensionContributionsMockMvc;

    private EmpPensionContributions empPensionContributions;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmpPensionContributionsResource empPensionContributionsResource = new EmpPensionContributionsResource(empPensionContributionsRepository);
        this.restEmpPensionContributionsMockMvc = MockMvcBuilders.standaloneSetup(empPensionContributionsResource)
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
    public static EmpPensionContributions createEntity(EntityManager em) {
        EmpPensionContributions empPensionContributions = new EmpPensionContributions()
            .amountPaid(DEFAULT_AMOUNT_PAID);
        return empPensionContributions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmpPensionContributions createUpdatedEntity(EntityManager em) {
        EmpPensionContributions empPensionContributions = new EmpPensionContributions()
            .amountPaid(UPDATED_AMOUNT_PAID);
        return empPensionContributions;
    }

    @BeforeEach
    public void initTest() {
        empPensionContributions = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmpPensionContributions() throws Exception {
        int databaseSizeBeforeCreate = empPensionContributionsRepository.findAll().size();

        // Create the EmpPensionContributions
        restEmpPensionContributionsMockMvc.perform(post("/api/emp-pension-contributions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empPensionContributions)))
            .andExpect(status().isCreated());

        // Validate the EmpPensionContributions in the database
        List<EmpPensionContributions> empPensionContributionsList = empPensionContributionsRepository.findAll();
        assertThat(empPensionContributionsList).hasSize(databaseSizeBeforeCreate + 1);
        EmpPensionContributions testEmpPensionContributions = empPensionContributionsList.get(empPensionContributionsList.size() - 1);
        assertThat(testEmpPensionContributions.getAmountPaid()).isEqualTo(DEFAULT_AMOUNT_PAID);
    }

    @Test
    @Transactional
    public void createEmpPensionContributionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = empPensionContributionsRepository.findAll().size();

        // Create the EmpPensionContributions with an existing ID
        empPensionContributions.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpPensionContributionsMockMvc.perform(post("/api/emp-pension-contributions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empPensionContributions)))
            .andExpect(status().isBadRequest());

        // Validate the EmpPensionContributions in the database
        List<EmpPensionContributions> empPensionContributionsList = empPensionContributionsRepository.findAll();
        assertThat(empPensionContributionsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmpPensionContributions() throws Exception {
        // Initialize the database
        empPensionContributionsRepository.saveAndFlush(empPensionContributions);

        // Get all the empPensionContributionsList
        restEmpPensionContributionsMockMvc.perform(get("/api/emp-pension-contributions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empPensionContributions.getId().intValue())))
            .andExpect(jsonPath("$.[*].amountPaid").value(hasItem(DEFAULT_AMOUNT_PAID.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getEmpPensionContributions() throws Exception {
        // Initialize the database
        empPensionContributionsRepository.saveAndFlush(empPensionContributions);

        // Get the empPensionContributions
        restEmpPensionContributionsMockMvc.perform(get("/api/emp-pension-contributions/{id}", empPensionContributions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(empPensionContributions.getId().intValue()))
            .andExpect(jsonPath("$.amountPaid").value(DEFAULT_AMOUNT_PAID.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEmpPensionContributions() throws Exception {
        // Get the empPensionContributions
        restEmpPensionContributionsMockMvc.perform(get("/api/emp-pension-contributions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmpPensionContributions() throws Exception {
        // Initialize the database
        empPensionContributionsRepository.saveAndFlush(empPensionContributions);

        int databaseSizeBeforeUpdate = empPensionContributionsRepository.findAll().size();

        // Update the empPensionContributions
        EmpPensionContributions updatedEmpPensionContributions = empPensionContributionsRepository.findById(empPensionContributions.getId()).get();
        // Disconnect from session so that the updates on updatedEmpPensionContributions are not directly saved in db
        em.detach(updatedEmpPensionContributions);
        updatedEmpPensionContributions
            .amountPaid(UPDATED_AMOUNT_PAID);

        restEmpPensionContributionsMockMvc.perform(put("/api/emp-pension-contributions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmpPensionContributions)))
            .andExpect(status().isOk());

        // Validate the EmpPensionContributions in the database
        List<EmpPensionContributions> empPensionContributionsList = empPensionContributionsRepository.findAll();
        assertThat(empPensionContributionsList).hasSize(databaseSizeBeforeUpdate);
        EmpPensionContributions testEmpPensionContributions = empPensionContributionsList.get(empPensionContributionsList.size() - 1);
        assertThat(testEmpPensionContributions.getAmountPaid()).isEqualTo(UPDATED_AMOUNT_PAID);
    }

    @Test
    @Transactional
    public void updateNonExistingEmpPensionContributions() throws Exception {
        int databaseSizeBeforeUpdate = empPensionContributionsRepository.findAll().size();

        // Create the EmpPensionContributions

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpPensionContributionsMockMvc.perform(put("/api/emp-pension-contributions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empPensionContributions)))
            .andExpect(status().isBadRequest());

        // Validate the EmpPensionContributions in the database
        List<EmpPensionContributions> empPensionContributionsList = empPensionContributionsRepository.findAll();
        assertThat(empPensionContributionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmpPensionContributions() throws Exception {
        // Initialize the database
        empPensionContributionsRepository.saveAndFlush(empPensionContributions);

        int databaseSizeBeforeDelete = empPensionContributionsRepository.findAll().size();

        // Delete the empPensionContributions
        restEmpPensionContributionsMockMvc.perform(delete("/api/emp-pension-contributions/{id}", empPensionContributions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmpPensionContributions> empPensionContributionsList = empPensionContributionsRepository.findAll();
        assertThat(empPensionContributionsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmpPensionContributions.class);
        EmpPensionContributions empPensionContributions1 = new EmpPensionContributions();
        empPensionContributions1.setId(1L);
        EmpPensionContributions empPensionContributions2 = new EmpPensionContributions();
        empPensionContributions2.setId(empPensionContributions1.getId());
        assertThat(empPensionContributions1).isEqualTo(empPensionContributions2);
        empPensionContributions2.setId(2L);
        assertThat(empPensionContributions1).isNotEqualTo(empPensionContributions2);
        empPensionContributions1.setId(null);
        assertThat(empPensionContributions1).isNotEqualTo(empPensionContributions2);
    }
}
