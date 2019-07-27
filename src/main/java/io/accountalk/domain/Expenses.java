package io.accountalk.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import io.accountalk.domain.enumeration.ExpenseType;

/**
 * A Expenses.
 */
@Entity
@Table(name = "expenses")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Expenses implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "expense_type")
    private ExpenseType expenseType;

    @Column(name = "description")
    private String description;

    @Column(name = "value")
    private Double value;

    @ManyToOne
    @JsonIgnoreProperties("expenses")
    private EmploymentDetails employmentDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public Expenses expenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
        return this;
    }

    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }

    public String getDescription() {
        return description;
    }

    public Expenses description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getValue() {
        return value;
    }

    public Expenses value(Double value) {
        this.value = value;
        return this;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public EmploymentDetails getEmploymentDetails() {
        return employmentDetails;
    }

    public Expenses employmentDetails(EmploymentDetails employmentDetails) {
        this.employmentDetails = employmentDetails;
        return this;
    }

    public void setEmploymentDetails(EmploymentDetails employmentDetails) {
        this.employmentDetails = employmentDetails;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Expenses)) {
            return false;
        }
        return id != null && id.equals(((Expenses) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Expenses{" +
            "id=" + getId() +
            ", expenseType='" + getExpenseType() + "'" +
            ", description='" + getDescription() + "'" +
            ", value=" + getValue() +
            "}";
    }
}
