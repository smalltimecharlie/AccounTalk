package io.accountalk.web.rest;

import io.accountalk.AccounTalkApp;
import io.accountalk.domain.Shares;
import io.accountalk.repository.SharesRepository;
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
 * Integration tests for the {@Link SharesResource} REST controller.
 */
@SpringBootTest(classes = AccounTalkApp.class)
public class SharesResourceIT {

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_NUMBER_OF_SHARES = 1L;
    private static final Long UPDATED_NUMBER_OF_SHARES = 2L;

    @Autowired
    private SharesRepository sharesRepository;

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

    private MockMvc restSharesMockMvc;

    private Shares shares;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SharesResource sharesResource = new SharesResource(sharesRepository);
        this.restSharesMockMvc = MockMvcBuilders.standaloneSetup(sharesResource)
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
    public static Shares createEntity(EntityManager em) {
        Shares shares = new Shares()
            .companyName(DEFAULT_COMPANY_NAME)
            .numberOfShares(DEFAULT_NUMBER_OF_SHARES);
        return shares;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Shares createUpdatedEntity(EntityManager em) {
        Shares shares = new Shares()
            .companyName(UPDATED_COMPANY_NAME)
            .numberOfShares(UPDATED_NUMBER_OF_SHARES);
        return shares;
    }

    @BeforeEach
    public void initTest() {
        shares = createEntity(em);
    }

    @Test
    @Transactional
    public void createShares() throws Exception {
        int databaseSizeBeforeCreate = sharesRepository.findAll().size();

        // Create the Shares
        restSharesMockMvc.perform(post("/api/shares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shares)))
            .andExpect(status().isCreated());

        // Validate the Shares in the database
        List<Shares> sharesList = sharesRepository.findAll();
        assertThat(sharesList).hasSize(databaseSizeBeforeCreate + 1);
        Shares testShares = sharesList.get(sharesList.size() - 1);
        assertThat(testShares.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testShares.getNumberOfShares()).isEqualTo(DEFAULT_NUMBER_OF_SHARES);
    }

    @Test
    @Transactional
    public void createSharesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sharesRepository.findAll().size();

        // Create the Shares with an existing ID
        shares.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSharesMockMvc.perform(post("/api/shares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shares)))
            .andExpect(status().isBadRequest());

        // Validate the Shares in the database
        List<Shares> sharesList = sharesRepository.findAll();
        assertThat(sharesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllShares() throws Exception {
        // Initialize the database
        sharesRepository.saveAndFlush(shares);

        // Get all the sharesList
        restSharesMockMvc.perform(get("/api/shares?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shares.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].numberOfShares").value(hasItem(DEFAULT_NUMBER_OF_SHARES.intValue())));
    }
    
    @Test
    @Transactional
    public void getShares() throws Exception {
        // Initialize the database
        sharesRepository.saveAndFlush(shares);

        // Get the shares
        restSharesMockMvc.perform(get("/api/shares/{id}", shares.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shares.getId().intValue()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.numberOfShares").value(DEFAULT_NUMBER_OF_SHARES.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingShares() throws Exception {
        // Get the shares
        restSharesMockMvc.perform(get("/api/shares/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShares() throws Exception {
        // Initialize the database
        sharesRepository.saveAndFlush(shares);

        int databaseSizeBeforeUpdate = sharesRepository.findAll().size();

        // Update the shares
        Shares updatedShares = sharesRepository.findById(shares.getId()).get();
        // Disconnect from session so that the updates on updatedShares are not directly saved in db
        em.detach(updatedShares);
        updatedShares
            .companyName(UPDATED_COMPANY_NAME)
            .numberOfShares(UPDATED_NUMBER_OF_SHARES);

        restSharesMockMvc.perform(put("/api/shares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedShares)))
            .andExpect(status().isOk());

        // Validate the Shares in the database
        List<Shares> sharesList = sharesRepository.findAll();
        assertThat(sharesList).hasSize(databaseSizeBeforeUpdate);
        Shares testShares = sharesList.get(sharesList.size() - 1);
        assertThat(testShares.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testShares.getNumberOfShares()).isEqualTo(UPDATED_NUMBER_OF_SHARES);
    }

    @Test
    @Transactional
    public void updateNonExistingShares() throws Exception {
        int databaseSizeBeforeUpdate = sharesRepository.findAll().size();

        // Create the Shares

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSharesMockMvc.perform(put("/api/shares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shares)))
            .andExpect(status().isBadRequest());

        // Validate the Shares in the database
        List<Shares> sharesList = sharesRepository.findAll();
        assertThat(sharesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteShares() throws Exception {
        // Initialize the database
        sharesRepository.saveAndFlush(shares);

        int databaseSizeBeforeDelete = sharesRepository.findAll().size();

        // Delete the shares
        restSharesMockMvc.perform(delete("/api/shares/{id}", shares.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Shares> sharesList = sharesRepository.findAll();
        assertThat(sharesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Shares.class);
        Shares shares1 = new Shares();
        shares1.setId(1L);
        Shares shares2 = new Shares();
        shares2.setId(shares1.getId());
        assertThat(shares1).isEqualTo(shares2);
        shares2.setId(2L);
        assertThat(shares1).isNotEqualTo(shares2);
        shares1.setId(null);
        assertThat(shares1).isNotEqualTo(shares2);
    }
}
