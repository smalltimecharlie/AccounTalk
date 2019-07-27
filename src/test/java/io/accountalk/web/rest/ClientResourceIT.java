package io.accountalk.web.rest;

import io.accountalk.AccounTalkApp;
import io.accountalk.domain.Client;
import io.accountalk.repository.ClientRepository;
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

import io.accountalk.domain.enumeration.Gender;
/**
 * Integration tests for the {@Link ClientResource} REST controller.
 */
@SpringBootTest(classes = AccounTalkApp.class)
public class ClientResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_FORENAME = "AAAAAAAAAA";
    private static final String UPDATED_FORENAME = "BBBBBBBBBB";

    private static final String DEFAULT_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_SURNAME = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_OF_BIRTH = "AAAAAAAAAA";
    private static final String UPDATED_DATE_OF_BIRTH = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_SELF_ASSESMENT_UTR_NO = "AAAAAAAAAA";
    private static final String UPDATED_SELF_ASSESMENT_UTR_NO = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final Boolean DEFAULT_STUDENT_LOAN = false;
    private static final Boolean UPDATED_STUDENT_LOAN = true;

    private static final Boolean DEFAULT_CHILD_BENEFIT = false;
    private static final Boolean UPDATED_CHILD_BENEFIT = true;

    private static final Boolean DEFAULT_SPOUSE = false;
    private static final Boolean UPDATED_SPOUSE = true;

    private static final String DEFAULT_FIND_OUT_ABOUT_US = "AAAAAAAAAA";
    private static final String UPDATED_FIND_OUT_ABOUT_US = "BBBBBBBBBB";

    private static final String DEFAULT_ADDITIONAL_INFORMATION = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_INFORMATION = "BBBBBBBBBB";

    @Autowired
    private ClientRepository clientRepository;

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

    private MockMvc restClientMockMvc;

    private Client client;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClientResource clientResource = new ClientResource(clientRepository);
        this.restClientMockMvc = MockMvcBuilders.standaloneSetup(clientResource)
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
    public static Client createEntity(EntityManager em) {
        Client client = new Client()
            .title(DEFAULT_TITLE)
            .forename(DEFAULT_FORENAME)
            .surname(DEFAULT_SURNAME)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .email(DEFAULT_EMAIL)
            .selfAssesmentUtrNo(DEFAULT_SELF_ASSESMENT_UTR_NO)
            .nationality(DEFAULT_NATIONALITY)
            .gender(DEFAULT_GENDER)
            .studentLoan(DEFAULT_STUDENT_LOAN)
            .childBenefit(DEFAULT_CHILD_BENEFIT)
            .spouse(DEFAULT_SPOUSE)
            .findOutAboutUs(DEFAULT_FIND_OUT_ABOUT_US)
            .additionalInformation(DEFAULT_ADDITIONAL_INFORMATION);
        return client;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createUpdatedEntity(EntityManager em) {
        Client client = new Client()
            .title(UPDATED_TITLE)
            .forename(UPDATED_FORENAME)
            .surname(UPDATED_SURNAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .email(UPDATED_EMAIL)
            .selfAssesmentUtrNo(UPDATED_SELF_ASSESMENT_UTR_NO)
            .nationality(UPDATED_NATIONALITY)
            .gender(UPDATED_GENDER)
            .studentLoan(UPDATED_STUDENT_LOAN)
            .childBenefit(UPDATED_CHILD_BENEFIT)
            .spouse(UPDATED_SPOUSE)
            .findOutAboutUs(UPDATED_FIND_OUT_ABOUT_US)
            .additionalInformation(UPDATED_ADDITIONAL_INFORMATION);
        return client;
    }

    @BeforeEach
    public void initTest() {
        client = createEntity(em);
    }

    @Test
    @Transactional
    public void createClient() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();

        // Create the Client
        restClientMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isCreated());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate + 1);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testClient.getForename()).isEqualTo(DEFAULT_FORENAME);
        assertThat(testClient.getSurname()).isEqualTo(DEFAULT_SURNAME);
        assertThat(testClient.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testClient.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testClient.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testClient.getSelfAssesmentUtrNo()).isEqualTo(DEFAULT_SELF_ASSESMENT_UTR_NO);
        assertThat(testClient.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testClient.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testClient.isStudentLoan()).isEqualTo(DEFAULT_STUDENT_LOAN);
        assertThat(testClient.isChildBenefit()).isEqualTo(DEFAULT_CHILD_BENEFIT);
        assertThat(testClient.isSpouse()).isEqualTo(DEFAULT_SPOUSE);
        assertThat(testClient.getFindOutAboutUs()).isEqualTo(DEFAULT_FIND_OUT_ABOUT_US);
        assertThat(testClient.getAdditionalInformation()).isEqualTo(DEFAULT_ADDITIONAL_INFORMATION);
    }

    @Test
    @Transactional
    public void createClientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();

        // Create the Client with an existing ID
        client.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setTitle(null);

        // Create the Client, which fails.

        restClientMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkForenameIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setForename(null);

        // Create the Client, which fails.

        restClientMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSurnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setSurname(null);

        // Create the Client, which fails.

        restClientMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateOfBirthIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setDateOfBirth(null);

        // Create the Client, which fails.

        restClientMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setEmail(null);

        // Create the Client, which fails.

        restClientMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClients() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList
        restClientMockMvc.perform(get("/api/clients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(client.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].forename").value(hasItem(DEFAULT_FORENAME.toString())))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME.toString())))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].selfAssesmentUtrNo").value(hasItem(DEFAULT_SELF_ASSESMENT_UTR_NO.toString())))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].studentLoan").value(hasItem(DEFAULT_STUDENT_LOAN.booleanValue())))
            .andExpect(jsonPath("$.[*].childBenefit").value(hasItem(DEFAULT_CHILD_BENEFIT.booleanValue())))
            .andExpect(jsonPath("$.[*].spouse").value(hasItem(DEFAULT_SPOUSE.booleanValue())))
            .andExpect(jsonPath("$.[*].findOutAboutUs").value(hasItem(DEFAULT_FIND_OUT_ABOUT_US.toString())))
            .andExpect(jsonPath("$.[*].additionalInformation").value(hasItem(DEFAULT_ADDITIONAL_INFORMATION.toString())));
    }
    
    @Test
    @Transactional
    public void getClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get the client
        restClientMockMvc.perform(get("/api/clients/{id}", client.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(client.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.forename").value(DEFAULT_FORENAME.toString()))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME.toString()))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.selfAssesmentUtrNo").value(DEFAULT_SELF_ASSESMENT_UTR_NO.toString()))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.studentLoan").value(DEFAULT_STUDENT_LOAN.booleanValue()))
            .andExpect(jsonPath("$.childBenefit").value(DEFAULT_CHILD_BENEFIT.booleanValue()))
            .andExpect(jsonPath("$.spouse").value(DEFAULT_SPOUSE.booleanValue()))
            .andExpect(jsonPath("$.findOutAboutUs").value(DEFAULT_FIND_OUT_ABOUT_US.toString()))
            .andExpect(jsonPath("$.additionalInformation").value(DEFAULT_ADDITIONAL_INFORMATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClient() throws Exception {
        // Get the client
        restClientMockMvc.perform(get("/api/clients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client
        Client updatedClient = clientRepository.findById(client.getId()).get();
        // Disconnect from session so that the updates on updatedClient are not directly saved in db
        em.detach(updatedClient);
        updatedClient
            .title(UPDATED_TITLE)
            .forename(UPDATED_FORENAME)
            .surname(UPDATED_SURNAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .email(UPDATED_EMAIL)
            .selfAssesmentUtrNo(UPDATED_SELF_ASSESMENT_UTR_NO)
            .nationality(UPDATED_NATIONALITY)
            .gender(UPDATED_GENDER)
            .studentLoan(UPDATED_STUDENT_LOAN)
            .childBenefit(UPDATED_CHILD_BENEFIT)
            .spouse(UPDATED_SPOUSE)
            .findOutAboutUs(UPDATED_FIND_OUT_ABOUT_US)
            .additionalInformation(UPDATED_ADDITIONAL_INFORMATION);

        restClientMockMvc.perform(put("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClient)))
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testClient.getForename()).isEqualTo(UPDATED_FORENAME);
        assertThat(testClient.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testClient.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testClient.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testClient.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClient.getSelfAssesmentUtrNo()).isEqualTo(UPDATED_SELF_ASSESMENT_UTR_NO);
        assertThat(testClient.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testClient.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testClient.isStudentLoan()).isEqualTo(UPDATED_STUDENT_LOAN);
        assertThat(testClient.isChildBenefit()).isEqualTo(UPDATED_CHILD_BENEFIT);
        assertThat(testClient.isSpouse()).isEqualTo(UPDATED_SPOUSE);
        assertThat(testClient.getFindOutAboutUs()).isEqualTo(UPDATED_FIND_OUT_ABOUT_US);
        assertThat(testClient.getAdditionalInformation()).isEqualTo(UPDATED_ADDITIONAL_INFORMATION);
    }

    @Test
    @Transactional
    public void updateNonExistingClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Create the Client

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientMockMvc.perform(put("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        int databaseSizeBeforeDelete = clientRepository.findAll().size();

        // Delete the client
        restClientMockMvc.perform(delete("/api/clients/{id}", client.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Client.class);
        Client client1 = new Client();
        client1.setId(1L);
        Client client2 = new Client();
        client2.setId(client1.getId());
        assertThat(client1).isEqualTo(client2);
        client2.setId(2L);
        assertThat(client1).isNotEqualTo(client2);
        client1.setId(null);
        assertThat(client1).isNotEqualTo(client2);
    }
}
