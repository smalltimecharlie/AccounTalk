package io.accountalk.web.rest;

import io.accountalk.AccounTalkApp;
import io.accountalk.domain.PreviousAccountant;
import io.accountalk.repository.PreviousAccountantRepository;
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
 * Integration tests for the {@Link PreviousAccountantResource} REST controller.
 */
@SpringBootTest(classes = AccounTalkApp.class)
public class PreviousAccountantResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    @Autowired
    private PreviousAccountantRepository previousAccountantRepository;

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

    private MockMvc restPreviousAccountantMockMvc;

    private PreviousAccountant previousAccountant;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PreviousAccountantResource previousAccountantResource = new PreviousAccountantResource(previousAccountantRepository);
        this.restPreviousAccountantMockMvc = MockMvcBuilders.standaloneSetup(previousAccountantResource)
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
    public static PreviousAccountant createEntity(EntityManager em) {
        PreviousAccountant previousAccountant = new PreviousAccountant()
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER);
        return previousAccountant;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PreviousAccountant createUpdatedEntity(EntityManager em) {
        PreviousAccountant previousAccountant = new PreviousAccountant()
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER);
        return previousAccountant;
    }

    @BeforeEach
    public void initTest() {
        previousAccountant = createEntity(em);
    }

    @Test
    @Transactional
    public void createPreviousAccountant() throws Exception {
        int databaseSizeBeforeCreate = previousAccountantRepository.findAll().size();

        // Create the PreviousAccountant
        restPreviousAccountantMockMvc.perform(post("/api/previous-accountants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(previousAccountant)))
            .andExpect(status().isCreated());

        // Validate the PreviousAccountant in the database
        List<PreviousAccountant> previousAccountantList = previousAccountantRepository.findAll();
        assertThat(previousAccountantList).hasSize(databaseSizeBeforeCreate + 1);
        PreviousAccountant testPreviousAccountant = previousAccountantList.get(previousAccountantList.size() - 1);
        assertThat(testPreviousAccountant.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPreviousAccountant.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPreviousAccountant.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void createPreviousAccountantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = previousAccountantRepository.findAll().size();

        // Create the PreviousAccountant with an existing ID
        previousAccountant.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPreviousAccountantMockMvc.perform(post("/api/previous-accountants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(previousAccountant)))
            .andExpect(status().isBadRequest());

        // Validate the PreviousAccountant in the database
        List<PreviousAccountant> previousAccountantList = previousAccountantRepository.findAll();
        assertThat(previousAccountantList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPreviousAccountants() throws Exception {
        // Initialize the database
        previousAccountantRepository.saveAndFlush(previousAccountant);

        // Get all the previousAccountantList
        restPreviousAccountantMockMvc.perform(get("/api/previous-accountants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(previousAccountant.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())));
    }
    
    @Test
    @Transactional
    public void getPreviousAccountant() throws Exception {
        // Initialize the database
        previousAccountantRepository.saveAndFlush(previousAccountant);

        // Get the previousAccountant
        restPreviousAccountantMockMvc.perform(get("/api/previous-accountants/{id}", previousAccountant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(previousAccountant.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPreviousAccountant() throws Exception {
        // Get the previousAccountant
        restPreviousAccountantMockMvc.perform(get("/api/previous-accountants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePreviousAccountant() throws Exception {
        // Initialize the database
        previousAccountantRepository.saveAndFlush(previousAccountant);

        int databaseSizeBeforeUpdate = previousAccountantRepository.findAll().size();

        // Update the previousAccountant
        PreviousAccountant updatedPreviousAccountant = previousAccountantRepository.findById(previousAccountant.getId()).get();
        // Disconnect from session so that the updates on updatedPreviousAccountant are not directly saved in db
        em.detach(updatedPreviousAccountant);
        updatedPreviousAccountant
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER);

        restPreviousAccountantMockMvc.perform(put("/api/previous-accountants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPreviousAccountant)))
            .andExpect(status().isOk());

        // Validate the PreviousAccountant in the database
        List<PreviousAccountant> previousAccountantList = previousAccountantRepository.findAll();
        assertThat(previousAccountantList).hasSize(databaseSizeBeforeUpdate);
        PreviousAccountant testPreviousAccountant = previousAccountantList.get(previousAccountantList.size() - 1);
        assertThat(testPreviousAccountant.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPreviousAccountant.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPreviousAccountant.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingPreviousAccountant() throws Exception {
        int databaseSizeBeforeUpdate = previousAccountantRepository.findAll().size();

        // Create the PreviousAccountant

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPreviousAccountantMockMvc.perform(put("/api/previous-accountants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(previousAccountant)))
            .andExpect(status().isBadRequest());

        // Validate the PreviousAccountant in the database
        List<PreviousAccountant> previousAccountantList = previousAccountantRepository.findAll();
        assertThat(previousAccountantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePreviousAccountant() throws Exception {
        // Initialize the database
        previousAccountantRepository.saveAndFlush(previousAccountant);

        int databaseSizeBeforeDelete = previousAccountantRepository.findAll().size();

        // Delete the previousAccountant
        restPreviousAccountantMockMvc.perform(delete("/api/previous-accountants/{id}", previousAccountant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PreviousAccountant> previousAccountantList = previousAccountantRepository.findAll();
        assertThat(previousAccountantList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PreviousAccountant.class);
        PreviousAccountant previousAccountant1 = new PreviousAccountant();
        previousAccountant1.setId(1L);
        PreviousAccountant previousAccountant2 = new PreviousAccountant();
        previousAccountant2.setId(previousAccountant1.getId());
        assertThat(previousAccountant1).isEqualTo(previousAccountant2);
        previousAccountant2.setId(2L);
        assertThat(previousAccountant1).isNotEqualTo(previousAccountant2);
        previousAccountant1.setId(null);
        assertThat(previousAccountant1).isNotEqualTo(previousAccountant2);
    }
}
