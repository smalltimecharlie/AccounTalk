package io.accountalk.web.rest;

import io.accountalk.AccounTalkApp;
import io.accountalk.domain.Employment;
import io.accountalk.repository.EmploymentRepository;
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
 * Integration tests for the {@Link EmploymentResource} REST controller.
 */
@SpringBootTest(classes = AccounTalkApp.class)
public class EmploymentResourceIT {

    private static final String DEFAULT_BUSINESS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PAYE_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_PAYE_REFERENCE = "BBBBBBBBBB";

    private static final Instant DEFAULT_EMPLOYMENT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EMPLOYMENT_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private EmploymentRepository employmentRepository;

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

    private MockMvc restEmploymentMockMvc;

    private Employment employment;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmploymentResource employmentResource = new EmploymentResource(employmentRepository);
        this.restEmploymentMockMvc = MockMvcBuilders.standaloneSetup(employmentResource)
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
    public static Employment createEntity(EntityManager em) {
        Employment employment = new Employment()
            .businessName(DEFAULT_BUSINESS_NAME)
            .payeReference(DEFAULT_PAYE_REFERENCE)
            .employmentEndDate(DEFAULT_EMPLOYMENT_END_DATE);
        return employment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employment createUpdatedEntity(EntityManager em) {
        Employment employment = new Employment()
            .businessName(UPDATED_BUSINESS_NAME)
            .payeReference(UPDATED_PAYE_REFERENCE)
            .employmentEndDate(UPDATED_EMPLOYMENT_END_DATE);
        return employment;
    }

    @BeforeEach
    public void initTest() {
        employment = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployment() throws Exception {
        int databaseSizeBeforeCreate = employmentRepository.findAll().size();

        // Create the Employment
        restEmploymentMockMvc.perform(post("/api/employments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employment)))
            .andExpect(status().isCreated());

        // Validate the Employment in the database
        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeCreate + 1);
        Employment testEmployment = employmentList.get(employmentList.size() - 1);
        assertThat(testEmployment.getBusinessName()).isEqualTo(DEFAULT_BUSINESS_NAME);
        assertThat(testEmployment.getPayeReference()).isEqualTo(DEFAULT_PAYE_REFERENCE);
        assertThat(testEmployment.getEmploymentEndDate()).isEqualTo(DEFAULT_EMPLOYMENT_END_DATE);
    }

    @Test
    @Transactional
    public void createEmploymentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employmentRepository.findAll().size();

        // Create the Employment with an existing ID
        employment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmploymentMockMvc.perform(post("/api/employments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employment)))
            .andExpect(status().isBadRequest());

        // Validate the Employment in the database
        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmployments() throws Exception {
        // Initialize the database
        employmentRepository.saveAndFlush(employment);

        // Get all the employmentList
        restEmploymentMockMvc.perform(get("/api/employments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employment.getId().intValue())))
            .andExpect(jsonPath("$.[*].businessName").value(hasItem(DEFAULT_BUSINESS_NAME.toString())))
            .andExpect(jsonPath("$.[*].payeReference").value(hasItem(DEFAULT_PAYE_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].employmentEndDate").value(hasItem(DEFAULT_EMPLOYMENT_END_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getEmployment() throws Exception {
        // Initialize the database
        employmentRepository.saveAndFlush(employment);

        // Get the employment
        restEmploymentMockMvc.perform(get("/api/employments/{id}", employment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employment.getId().intValue()))
            .andExpect(jsonPath("$.businessName").value(DEFAULT_BUSINESS_NAME.toString()))
            .andExpect(jsonPath("$.payeReference").value(DEFAULT_PAYE_REFERENCE.toString()))
            .andExpect(jsonPath("$.employmentEndDate").value(DEFAULT_EMPLOYMENT_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployment() throws Exception {
        // Get the employment
        restEmploymentMockMvc.perform(get("/api/employments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployment() throws Exception {
        // Initialize the database
        employmentRepository.saveAndFlush(employment);

        int databaseSizeBeforeUpdate = employmentRepository.findAll().size();

        // Update the employment
        Employment updatedEmployment = employmentRepository.findById(employment.getId()).get();
        // Disconnect from session so that the updates on updatedEmployment are not directly saved in db
        em.detach(updatedEmployment);
        updatedEmployment
            .businessName(UPDATED_BUSINESS_NAME)
            .payeReference(UPDATED_PAYE_REFERENCE)
            .employmentEndDate(UPDATED_EMPLOYMENT_END_DATE);

        restEmploymentMockMvc.perform(put("/api/employments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployment)))
            .andExpect(status().isOk());

        // Validate the Employment in the database
        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeUpdate);
        Employment testEmployment = employmentList.get(employmentList.size() - 1);
        assertThat(testEmployment.getBusinessName()).isEqualTo(UPDATED_BUSINESS_NAME);
        assertThat(testEmployment.getPayeReference()).isEqualTo(UPDATED_PAYE_REFERENCE);
        assertThat(testEmployment.getEmploymentEndDate()).isEqualTo(UPDATED_EMPLOYMENT_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployment() throws Exception {
        int databaseSizeBeforeUpdate = employmentRepository.findAll().size();

        // Create the Employment

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmploymentMockMvc.perform(put("/api/employments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employment)))
            .andExpect(status().isBadRequest());

        // Validate the Employment in the database
        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployment() throws Exception {
        // Initialize the database
        employmentRepository.saveAndFlush(employment);

        int databaseSizeBeforeDelete = employmentRepository.findAll().size();

        // Delete the employment
        restEmploymentMockMvc.perform(delete("/api/employments/{id}", employment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Employment.class);
        Employment employment1 = new Employment();
        employment1.setId(1L);
        Employment employment2 = new Employment();
        employment2.setId(employment1.getId());
        assertThat(employment1).isEqualTo(employment2);
        employment2.setId(2L);
        assertThat(employment1).isNotEqualTo(employment2);
        employment1.setId(null);
        assertThat(employment1).isNotEqualTo(employment2);
    }
}
