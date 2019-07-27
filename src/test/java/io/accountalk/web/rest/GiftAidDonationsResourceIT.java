package io.accountalk.web.rest;

import io.accountalk.AccounTalkApp;
import io.accountalk.domain.GiftAidDonations;
import io.accountalk.repository.GiftAidDonationsRepository;
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
 * Integration tests for the {@Link GiftAidDonationsResource} REST controller.
 */
@SpringBootTest(classes = AccounTalkApp.class)
public class GiftAidDonationsResourceIT {

    private static final String DEFAULT_CHARITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CHARITY_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_DONATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DONATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_DONATION_AMOUNT = 1D;
    private static final Double UPDATED_DONATION_AMOUNT = 2D;

    private static final Boolean DEFAULT_GIFT_AID_CLAIMED = false;
    private static final Boolean UPDATED_GIFT_AID_CLAIMED = true;

    @Autowired
    private GiftAidDonationsRepository giftAidDonationsRepository;

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

    private MockMvc restGiftAidDonationsMockMvc;

    private GiftAidDonations giftAidDonations;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GiftAidDonationsResource giftAidDonationsResource = new GiftAidDonationsResource(giftAidDonationsRepository);
        this.restGiftAidDonationsMockMvc = MockMvcBuilders.standaloneSetup(giftAidDonationsResource)
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
    public static GiftAidDonations createEntity(EntityManager em) {
        GiftAidDonations giftAidDonations = new GiftAidDonations()
            .charityName(DEFAULT_CHARITY_NAME)
            .donationDate(DEFAULT_DONATION_DATE)
            .donationAmount(DEFAULT_DONATION_AMOUNT)
            .giftAidClaimed(DEFAULT_GIFT_AID_CLAIMED);
        return giftAidDonations;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GiftAidDonations createUpdatedEntity(EntityManager em) {
        GiftAidDonations giftAidDonations = new GiftAidDonations()
            .charityName(UPDATED_CHARITY_NAME)
            .donationDate(UPDATED_DONATION_DATE)
            .donationAmount(UPDATED_DONATION_AMOUNT)
            .giftAidClaimed(UPDATED_GIFT_AID_CLAIMED);
        return giftAidDonations;
    }

    @BeforeEach
    public void initTest() {
        giftAidDonations = createEntity(em);
    }

    @Test
    @Transactional
    public void createGiftAidDonations() throws Exception {
        int databaseSizeBeforeCreate = giftAidDonationsRepository.findAll().size();

        // Create the GiftAidDonations
        restGiftAidDonationsMockMvc.perform(post("/api/gift-aid-donations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(giftAidDonations)))
            .andExpect(status().isCreated());

        // Validate the GiftAidDonations in the database
        List<GiftAidDonations> giftAidDonationsList = giftAidDonationsRepository.findAll();
        assertThat(giftAidDonationsList).hasSize(databaseSizeBeforeCreate + 1);
        GiftAidDonations testGiftAidDonations = giftAidDonationsList.get(giftAidDonationsList.size() - 1);
        assertThat(testGiftAidDonations.getCharityName()).isEqualTo(DEFAULT_CHARITY_NAME);
        assertThat(testGiftAidDonations.getDonationDate()).isEqualTo(DEFAULT_DONATION_DATE);
        assertThat(testGiftAidDonations.getDonationAmount()).isEqualTo(DEFAULT_DONATION_AMOUNT);
        assertThat(testGiftAidDonations.isGiftAidClaimed()).isEqualTo(DEFAULT_GIFT_AID_CLAIMED);
    }

    @Test
    @Transactional
    public void createGiftAidDonationsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = giftAidDonationsRepository.findAll().size();

        // Create the GiftAidDonations with an existing ID
        giftAidDonations.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGiftAidDonationsMockMvc.perform(post("/api/gift-aid-donations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(giftAidDonations)))
            .andExpect(status().isBadRequest());

        // Validate the GiftAidDonations in the database
        List<GiftAidDonations> giftAidDonationsList = giftAidDonationsRepository.findAll();
        assertThat(giftAidDonationsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGiftAidDonations() throws Exception {
        // Initialize the database
        giftAidDonationsRepository.saveAndFlush(giftAidDonations);

        // Get all the giftAidDonationsList
        restGiftAidDonationsMockMvc.perform(get("/api/gift-aid-donations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(giftAidDonations.getId().intValue())))
            .andExpect(jsonPath("$.[*].charityName").value(hasItem(DEFAULT_CHARITY_NAME.toString())))
            .andExpect(jsonPath("$.[*].donationDate").value(hasItem(DEFAULT_DONATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].donationAmount").value(hasItem(DEFAULT_DONATION_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].giftAidClaimed").value(hasItem(DEFAULT_GIFT_AID_CLAIMED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getGiftAidDonations() throws Exception {
        // Initialize the database
        giftAidDonationsRepository.saveAndFlush(giftAidDonations);

        // Get the giftAidDonations
        restGiftAidDonationsMockMvc.perform(get("/api/gift-aid-donations/{id}", giftAidDonations.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(giftAidDonations.getId().intValue()))
            .andExpect(jsonPath("$.charityName").value(DEFAULT_CHARITY_NAME.toString()))
            .andExpect(jsonPath("$.donationDate").value(DEFAULT_DONATION_DATE.toString()))
            .andExpect(jsonPath("$.donationAmount").value(DEFAULT_DONATION_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.giftAidClaimed").value(DEFAULT_GIFT_AID_CLAIMED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGiftAidDonations() throws Exception {
        // Get the giftAidDonations
        restGiftAidDonationsMockMvc.perform(get("/api/gift-aid-donations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGiftAidDonations() throws Exception {
        // Initialize the database
        giftAidDonationsRepository.saveAndFlush(giftAidDonations);

        int databaseSizeBeforeUpdate = giftAidDonationsRepository.findAll().size();

        // Update the giftAidDonations
        GiftAidDonations updatedGiftAidDonations = giftAidDonationsRepository.findById(giftAidDonations.getId()).get();
        // Disconnect from session so that the updates on updatedGiftAidDonations are not directly saved in db
        em.detach(updatedGiftAidDonations);
        updatedGiftAidDonations
            .charityName(UPDATED_CHARITY_NAME)
            .donationDate(UPDATED_DONATION_DATE)
            .donationAmount(UPDATED_DONATION_AMOUNT)
            .giftAidClaimed(UPDATED_GIFT_AID_CLAIMED);

        restGiftAidDonationsMockMvc.perform(put("/api/gift-aid-donations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGiftAidDonations)))
            .andExpect(status().isOk());

        // Validate the GiftAidDonations in the database
        List<GiftAidDonations> giftAidDonationsList = giftAidDonationsRepository.findAll();
        assertThat(giftAidDonationsList).hasSize(databaseSizeBeforeUpdate);
        GiftAidDonations testGiftAidDonations = giftAidDonationsList.get(giftAidDonationsList.size() - 1);
        assertThat(testGiftAidDonations.getCharityName()).isEqualTo(UPDATED_CHARITY_NAME);
        assertThat(testGiftAidDonations.getDonationDate()).isEqualTo(UPDATED_DONATION_DATE);
        assertThat(testGiftAidDonations.getDonationAmount()).isEqualTo(UPDATED_DONATION_AMOUNT);
        assertThat(testGiftAidDonations.isGiftAidClaimed()).isEqualTo(UPDATED_GIFT_AID_CLAIMED);
    }

    @Test
    @Transactional
    public void updateNonExistingGiftAidDonations() throws Exception {
        int databaseSizeBeforeUpdate = giftAidDonationsRepository.findAll().size();

        // Create the GiftAidDonations

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGiftAidDonationsMockMvc.perform(put("/api/gift-aid-donations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(giftAidDonations)))
            .andExpect(status().isBadRequest());

        // Validate the GiftAidDonations in the database
        List<GiftAidDonations> giftAidDonationsList = giftAidDonationsRepository.findAll();
        assertThat(giftAidDonationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGiftAidDonations() throws Exception {
        // Initialize the database
        giftAidDonationsRepository.saveAndFlush(giftAidDonations);

        int databaseSizeBeforeDelete = giftAidDonationsRepository.findAll().size();

        // Delete the giftAidDonations
        restGiftAidDonationsMockMvc.perform(delete("/api/gift-aid-donations/{id}", giftAidDonations.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GiftAidDonations> giftAidDonationsList = giftAidDonationsRepository.findAll();
        assertThat(giftAidDonationsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GiftAidDonations.class);
        GiftAidDonations giftAidDonations1 = new GiftAidDonations();
        giftAidDonations1.setId(1L);
        GiftAidDonations giftAidDonations2 = new GiftAidDonations();
        giftAidDonations2.setId(giftAidDonations1.getId());
        assertThat(giftAidDonations1).isEqualTo(giftAidDonations2);
        giftAidDonations2.setId(2L);
        assertThat(giftAidDonations1).isNotEqualTo(giftAidDonations2);
        giftAidDonations1.setId(null);
        assertThat(giftAidDonations1).isNotEqualTo(giftAidDonations2);
    }
}
