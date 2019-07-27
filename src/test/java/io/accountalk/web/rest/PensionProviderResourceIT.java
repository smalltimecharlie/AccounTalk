package io.accountalk.web.rest;

import io.accountalk.AccounTalkApp;
import io.accountalk.domain.PensionProvider;
import io.accountalk.repository.PensionProviderRepository;
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
 * Integration tests for the {@Link PensionProviderResource} REST controller.
 */
@SpringBootTest(classes = AccounTalkApp.class)
public class PensionProviderResourceIT {

    private static final String DEFAULT_NAME_OF_PROVIDER = "AAAAAAAAAA";
    private static final String UPDATED_NAME_OF_PROVIDER = "BBBBBBBBBB";

    private static final String DEFAULT_MEMBERSHIP_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MEMBERSHIP_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_PAYE_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_PAYE_REFERENCE = "BBBBBBBBBB";

    @Autowired
    private PensionProviderRepository pensionProviderRepository;

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

    private MockMvc restPensionProviderMockMvc;

    private PensionProvider pensionProvider;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PensionProviderResource pensionProviderResource = new PensionProviderResource(pensionProviderRepository);
        this.restPensionProviderMockMvc = MockMvcBuilders.standaloneSetup(pensionProviderResource)
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
    public static PensionProvider createEntity(EntityManager em) {
        PensionProvider pensionProvider = new PensionProvider()
            .nameOfProvider(DEFAULT_NAME_OF_PROVIDER)
            .membershipNumber(DEFAULT_MEMBERSHIP_NUMBER)
            .payeReference(DEFAULT_PAYE_REFERENCE);
        return pensionProvider;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PensionProvider createUpdatedEntity(EntityManager em) {
        PensionProvider pensionProvider = new PensionProvider()
            .nameOfProvider(UPDATED_NAME_OF_PROVIDER)
            .membershipNumber(UPDATED_MEMBERSHIP_NUMBER)
            .payeReference(UPDATED_PAYE_REFERENCE);
        return pensionProvider;
    }

    @BeforeEach
    public void initTest() {
        pensionProvider = createEntity(em);
    }

    @Test
    @Transactional
    public void createPensionProvider() throws Exception {
        int databaseSizeBeforeCreate = pensionProviderRepository.findAll().size();

        // Create the PensionProvider
        restPensionProviderMockMvc.perform(post("/api/pension-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pensionProvider)))
            .andExpect(status().isCreated());

        // Validate the PensionProvider in the database
        List<PensionProvider> pensionProviderList = pensionProviderRepository.findAll();
        assertThat(pensionProviderList).hasSize(databaseSizeBeforeCreate + 1);
        PensionProvider testPensionProvider = pensionProviderList.get(pensionProviderList.size() - 1);
        assertThat(testPensionProvider.getNameOfProvider()).isEqualTo(DEFAULT_NAME_OF_PROVIDER);
        assertThat(testPensionProvider.getMembershipNumber()).isEqualTo(DEFAULT_MEMBERSHIP_NUMBER);
        assertThat(testPensionProvider.getPayeReference()).isEqualTo(DEFAULT_PAYE_REFERENCE);
    }

    @Test
    @Transactional
    public void createPensionProviderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pensionProviderRepository.findAll().size();

        // Create the PensionProvider with an existing ID
        pensionProvider.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPensionProviderMockMvc.perform(post("/api/pension-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pensionProvider)))
            .andExpect(status().isBadRequest());

        // Validate the PensionProvider in the database
        List<PensionProvider> pensionProviderList = pensionProviderRepository.findAll();
        assertThat(pensionProviderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPensionProviders() throws Exception {
        // Initialize the database
        pensionProviderRepository.saveAndFlush(pensionProvider);

        // Get all the pensionProviderList
        restPensionProviderMockMvc.perform(get("/api/pension-providers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pensionProvider.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameOfProvider").value(hasItem(DEFAULT_NAME_OF_PROVIDER.toString())))
            .andExpect(jsonPath("$.[*].membershipNumber").value(hasItem(DEFAULT_MEMBERSHIP_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].payeReference").value(hasItem(DEFAULT_PAYE_REFERENCE.toString())));
    }
    
    @Test
    @Transactional
    public void getPensionProvider() throws Exception {
        // Initialize the database
        pensionProviderRepository.saveAndFlush(pensionProvider);

        // Get the pensionProvider
        restPensionProviderMockMvc.perform(get("/api/pension-providers/{id}", pensionProvider.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pensionProvider.getId().intValue()))
            .andExpect(jsonPath("$.nameOfProvider").value(DEFAULT_NAME_OF_PROVIDER.toString()))
            .andExpect(jsonPath("$.membershipNumber").value(DEFAULT_MEMBERSHIP_NUMBER.toString()))
            .andExpect(jsonPath("$.payeReference").value(DEFAULT_PAYE_REFERENCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPensionProvider() throws Exception {
        // Get the pensionProvider
        restPensionProviderMockMvc.perform(get("/api/pension-providers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePensionProvider() throws Exception {
        // Initialize the database
        pensionProviderRepository.saveAndFlush(pensionProvider);

        int databaseSizeBeforeUpdate = pensionProviderRepository.findAll().size();

        // Update the pensionProvider
        PensionProvider updatedPensionProvider = pensionProviderRepository.findById(pensionProvider.getId()).get();
        // Disconnect from session so that the updates on updatedPensionProvider are not directly saved in db
        em.detach(updatedPensionProvider);
        updatedPensionProvider
            .nameOfProvider(UPDATED_NAME_OF_PROVIDER)
            .membershipNumber(UPDATED_MEMBERSHIP_NUMBER)
            .payeReference(UPDATED_PAYE_REFERENCE);

        restPensionProviderMockMvc.perform(put("/api/pension-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPensionProvider)))
            .andExpect(status().isOk());

        // Validate the PensionProvider in the database
        List<PensionProvider> pensionProviderList = pensionProviderRepository.findAll();
        assertThat(pensionProviderList).hasSize(databaseSizeBeforeUpdate);
        PensionProvider testPensionProvider = pensionProviderList.get(pensionProviderList.size() - 1);
        assertThat(testPensionProvider.getNameOfProvider()).isEqualTo(UPDATED_NAME_OF_PROVIDER);
        assertThat(testPensionProvider.getMembershipNumber()).isEqualTo(UPDATED_MEMBERSHIP_NUMBER);
        assertThat(testPensionProvider.getPayeReference()).isEqualTo(UPDATED_PAYE_REFERENCE);
    }

    @Test
    @Transactional
    public void updateNonExistingPensionProvider() throws Exception {
        int databaseSizeBeforeUpdate = pensionProviderRepository.findAll().size();

        // Create the PensionProvider

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPensionProviderMockMvc.perform(put("/api/pension-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pensionProvider)))
            .andExpect(status().isBadRequest());

        // Validate the PensionProvider in the database
        List<PensionProvider> pensionProviderList = pensionProviderRepository.findAll();
        assertThat(pensionProviderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePensionProvider() throws Exception {
        // Initialize the database
        pensionProviderRepository.saveAndFlush(pensionProvider);

        int databaseSizeBeforeDelete = pensionProviderRepository.findAll().size();

        // Delete the pensionProvider
        restPensionProviderMockMvc.perform(delete("/api/pension-providers/{id}", pensionProvider.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PensionProvider> pensionProviderList = pensionProviderRepository.findAll();
        assertThat(pensionProviderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PensionProvider.class);
        PensionProvider pensionProvider1 = new PensionProvider();
        pensionProvider1.setId(1L);
        PensionProvider pensionProvider2 = new PensionProvider();
        pensionProvider2.setId(pensionProvider1.getId());
        assertThat(pensionProvider1).isEqualTo(pensionProvider2);
        pensionProvider2.setId(2L);
        assertThat(pensionProvider1).isNotEqualTo(pensionProvider2);
        pensionProvider1.setId(null);
        assertThat(pensionProvider1).isNotEqualTo(pensionProvider2);
    }
}
