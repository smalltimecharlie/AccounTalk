package io.accountalk.web.rest;

import io.accountalk.AccounTalkApp;
import io.accountalk.domain.EmploymentDetails;
import io.accountalk.repository.EmploymentDetailsRepository;
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
 * Integration tests for the {@Link EmploymentDetailsResource} REST controller.
 */
@SpringBootTest(classes = AccounTalkApp.class)
public class EmploymentDetailsResourceIT {

    @Autowired
    private EmploymentDetailsRepository employmentDetailsRepository;

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

    private MockMvc restEmploymentDetailsMockMvc;

    private EmploymentDetails employmentDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmploymentDetailsResource employmentDetailsResource = new EmploymentDetailsResource(employmentDetailsRepository);
        this.restEmploymentDetailsMockMvc = MockMvcBuilders.standaloneSetup(employmentDetailsResource)
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
    public static EmploymentDetails createEntity(EntityManager em) {
        EmploymentDetails employmentDetails = new EmploymentDetails();
        return employmentDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmploymentDetails createUpdatedEntity(EntityManager em) {
        EmploymentDetails employmentDetails = new EmploymentDetails();
        return employmentDetails;
    }

    @BeforeEach
    public void initTest() {
        employmentDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmploymentDetails() throws Exception {
        int databaseSizeBeforeCreate = employmentDetailsRepository.findAll().size();

        // Create the EmploymentDetails
        restEmploymentDetailsMockMvc.perform(post("/api/employment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employmentDetails)))
            .andExpect(status().isCreated());

        // Validate the EmploymentDetails in the database
        List<EmploymentDetails> employmentDetailsList = employmentDetailsRepository.findAll();
        assertThat(employmentDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        EmploymentDetails testEmploymentDetails = employmentDetailsList.get(employmentDetailsList.size() - 1);
    }

    @Test
    @Transactional
    public void createEmploymentDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employmentDetailsRepository.findAll().size();

        // Create the EmploymentDetails with an existing ID
        employmentDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmploymentDetailsMockMvc.perform(post("/api/employment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employmentDetails)))
            .andExpect(status().isBadRequest());

        // Validate the EmploymentDetails in the database
        List<EmploymentDetails> employmentDetailsList = employmentDetailsRepository.findAll();
        assertThat(employmentDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmploymentDetails() throws Exception {
        // Initialize the database
        employmentDetailsRepository.saveAndFlush(employmentDetails);

        // Get all the employmentDetailsList
        restEmploymentDetailsMockMvc.perform(get("/api/employment-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employmentDetails.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getEmploymentDetails() throws Exception {
        // Initialize the database
        employmentDetailsRepository.saveAndFlush(employmentDetails);

        // Get the employmentDetails
        restEmploymentDetailsMockMvc.perform(get("/api/employment-details/{id}", employmentDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employmentDetails.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEmploymentDetails() throws Exception {
        // Get the employmentDetails
        restEmploymentDetailsMockMvc.perform(get("/api/employment-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmploymentDetails() throws Exception {
        // Initialize the database
        employmentDetailsRepository.saveAndFlush(employmentDetails);

        int databaseSizeBeforeUpdate = employmentDetailsRepository.findAll().size();

        // Update the employmentDetails
        EmploymentDetails updatedEmploymentDetails = employmentDetailsRepository.findById(employmentDetails.getId()).get();
        // Disconnect from session so that the updates on updatedEmploymentDetails are not directly saved in db
        em.detach(updatedEmploymentDetails);

        restEmploymentDetailsMockMvc.perform(put("/api/employment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmploymentDetails)))
            .andExpect(status().isOk());

        // Validate the EmploymentDetails in the database
        List<EmploymentDetails> employmentDetailsList = employmentDetailsRepository.findAll();
        assertThat(employmentDetailsList).hasSize(databaseSizeBeforeUpdate);
        EmploymentDetails testEmploymentDetails = employmentDetailsList.get(employmentDetailsList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingEmploymentDetails() throws Exception {
        int databaseSizeBeforeUpdate = employmentDetailsRepository.findAll().size();

        // Create the EmploymentDetails

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmploymentDetailsMockMvc.perform(put("/api/employment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employmentDetails)))
            .andExpect(status().isBadRequest());

        // Validate the EmploymentDetails in the database
        List<EmploymentDetails> employmentDetailsList = employmentDetailsRepository.findAll();
        assertThat(employmentDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmploymentDetails() throws Exception {
        // Initialize the database
        employmentDetailsRepository.saveAndFlush(employmentDetails);

        int databaseSizeBeforeDelete = employmentDetailsRepository.findAll().size();

        // Delete the employmentDetails
        restEmploymentDetailsMockMvc.perform(delete("/api/employment-details/{id}", employmentDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmploymentDetails> employmentDetailsList = employmentDetailsRepository.findAll();
        assertThat(employmentDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmploymentDetails.class);
        EmploymentDetails employmentDetails1 = new EmploymentDetails();
        employmentDetails1.setId(1L);
        EmploymentDetails employmentDetails2 = new EmploymentDetails();
        employmentDetails2.setId(employmentDetails1.getId());
        assertThat(employmentDetails1).isEqualTo(employmentDetails2);
        employmentDetails2.setId(2L);
        assertThat(employmentDetails1).isNotEqualTo(employmentDetails2);
        employmentDetails1.setId(null);
        assertThat(employmentDetails1).isNotEqualTo(employmentDetails2);
    }
}
