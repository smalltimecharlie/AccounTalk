package io.accountalk.web.rest;

import io.accountalk.AccounTalkApp;
import io.accountalk.domain.Expenses;
import io.accountalk.repository.ExpensesRepository;
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

import io.accountalk.domain.enumeration.ExpenseType;
/**
 * Integration tests for the {@Link ExpensesResource} REST controller.
 */
@SpringBootTest(classes = AccounTalkApp.class)
public class ExpensesResourceIT {

    private static final ExpenseType DEFAULT_EXPENSE_TYPE = ExpenseType.UNSPECIFIED;
    private static final ExpenseType UPDATED_EXPENSE_TYPE = ExpenseType.SUBSCRIPTIONS;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_VALUE = 1D;
    private static final Double UPDATED_VALUE = 2D;

    @Autowired
    private ExpensesRepository expensesRepository;

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

    private MockMvc restExpensesMockMvc;

    private Expenses expenses;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExpensesResource expensesResource = new ExpensesResource(expensesRepository);
        this.restExpensesMockMvc = MockMvcBuilders.standaloneSetup(expensesResource)
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
    public static Expenses createEntity(EntityManager em) {
        Expenses expenses = new Expenses()
            .expenseType(DEFAULT_EXPENSE_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .value(DEFAULT_VALUE);
        return expenses;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Expenses createUpdatedEntity(EntityManager em) {
        Expenses expenses = new Expenses()
            .expenseType(UPDATED_EXPENSE_TYPE)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE);
        return expenses;
    }

    @BeforeEach
    public void initTest() {
        expenses = createEntity(em);
    }

    @Test
    @Transactional
    public void createExpenses() throws Exception {
        int databaseSizeBeforeCreate = expensesRepository.findAll().size();

        // Create the Expenses
        restExpensesMockMvc.perform(post("/api/expenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expenses)))
            .andExpect(status().isCreated());

        // Validate the Expenses in the database
        List<Expenses> expensesList = expensesRepository.findAll();
        assertThat(expensesList).hasSize(databaseSizeBeforeCreate + 1);
        Expenses testExpenses = expensesList.get(expensesList.size() - 1);
        assertThat(testExpenses.getExpenseType()).isEqualTo(DEFAULT_EXPENSE_TYPE);
        assertThat(testExpenses.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testExpenses.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createExpensesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = expensesRepository.findAll().size();

        // Create the Expenses with an existing ID
        expenses.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExpensesMockMvc.perform(post("/api/expenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expenses)))
            .andExpect(status().isBadRequest());

        // Validate the Expenses in the database
        List<Expenses> expensesList = expensesRepository.findAll();
        assertThat(expensesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllExpenses() throws Exception {
        // Initialize the database
        expensesRepository.saveAndFlush(expenses);

        // Get all the expensesList
        restExpensesMockMvc.perform(get("/api/expenses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expenses.getId().intValue())))
            .andExpect(jsonPath("$.[*].expenseType").value(hasItem(DEFAULT_EXPENSE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getExpenses() throws Exception {
        // Initialize the database
        expensesRepository.saveAndFlush(expenses);

        // Get the expenses
        restExpensesMockMvc.perform(get("/api/expenses/{id}", expenses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(expenses.getId().intValue()))
            .andExpect(jsonPath("$.expenseType").value(DEFAULT_EXPENSE_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingExpenses() throws Exception {
        // Get the expenses
        restExpensesMockMvc.perform(get("/api/expenses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExpenses() throws Exception {
        // Initialize the database
        expensesRepository.saveAndFlush(expenses);

        int databaseSizeBeforeUpdate = expensesRepository.findAll().size();

        // Update the expenses
        Expenses updatedExpenses = expensesRepository.findById(expenses.getId()).get();
        // Disconnect from session so that the updates on updatedExpenses are not directly saved in db
        em.detach(updatedExpenses);
        updatedExpenses
            .expenseType(UPDATED_EXPENSE_TYPE)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE);

        restExpensesMockMvc.perform(put("/api/expenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedExpenses)))
            .andExpect(status().isOk());

        // Validate the Expenses in the database
        List<Expenses> expensesList = expensesRepository.findAll();
        assertThat(expensesList).hasSize(databaseSizeBeforeUpdate);
        Expenses testExpenses = expensesList.get(expensesList.size() - 1);
        assertThat(testExpenses.getExpenseType()).isEqualTo(UPDATED_EXPENSE_TYPE);
        assertThat(testExpenses.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testExpenses.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingExpenses() throws Exception {
        int databaseSizeBeforeUpdate = expensesRepository.findAll().size();

        // Create the Expenses

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExpensesMockMvc.perform(put("/api/expenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expenses)))
            .andExpect(status().isBadRequest());

        // Validate the Expenses in the database
        List<Expenses> expensesList = expensesRepository.findAll();
        assertThat(expensesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExpenses() throws Exception {
        // Initialize the database
        expensesRepository.saveAndFlush(expenses);

        int databaseSizeBeforeDelete = expensesRepository.findAll().size();

        // Delete the expenses
        restExpensesMockMvc.perform(delete("/api/expenses/{id}", expenses.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Expenses> expensesList = expensesRepository.findAll();
        assertThat(expensesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Expenses.class);
        Expenses expenses1 = new Expenses();
        expenses1.setId(1L);
        Expenses expenses2 = new Expenses();
        expenses2.setId(expenses1.getId());
        assertThat(expenses1).isEqualTo(expenses2);
        expenses2.setId(2L);
        assertThat(expenses1).isNotEqualTo(expenses2);
        expenses1.setId(null);
        assertThat(expenses1).isNotEqualTo(expenses2);
    }
}
