package io.accountalk.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A EmploymentDetails.
 */
@Entity
@Table(name = "employment_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EmploymentDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("employmentDetails")
    private TaxReturn taxReturn;

    @OneToOne
    @JoinColumn(unique = true)
    private Employment employment;

    @OneToMany(mappedBy = "employmentDetails")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Earnings> earnings = new HashSet<>();

    @OneToMany(mappedBy = "employmentDetails")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Benefits> benefits = new HashSet<>();

    @OneToMany(mappedBy = "employmentDetails")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Expenses> expenses = new HashSet<>();

    @OneToMany(mappedBy = "employmentDetails")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EmpPensionContributions> employmentPensionContributions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaxReturn getTaxReturn() {
        return taxReturn;
    }

    public EmploymentDetails taxReturn(TaxReturn taxReturn) {
        this.taxReturn = taxReturn;
        return this;
    }

    public void setTaxReturn(TaxReturn taxReturn) {
        this.taxReturn = taxReturn;
    }

    public Employment getEmployment() {
        return employment;
    }

    public EmploymentDetails employment(Employment employment) {
        this.employment = employment;
        return this;
    }

    public void setEmployment(Employment employment) {
        this.employment = employment;
    }

    public Set<Earnings> getEarnings() {
        return earnings;
    }

    public EmploymentDetails earnings(Set<Earnings> earnings) {
        this.earnings = earnings;
        return this;
    }

    public EmploymentDetails addEarnings(Earnings earnings) {
        this.earnings.add(earnings);
        earnings.setEmploymentDetails(this);
        return this;
    }

    public EmploymentDetails removeEarnings(Earnings earnings) {
        this.earnings.remove(earnings);
        earnings.setEmploymentDetails(null);
        return this;
    }

    public void setEarnings(Set<Earnings> earnings) {
        this.earnings = earnings;
    }

    public Set<Benefits> getBenefits() {
        return benefits;
    }

    public EmploymentDetails benefits(Set<Benefits> benefits) {
        this.benefits = benefits;
        return this;
    }

    public EmploymentDetails addBenefits(Benefits benefits) {
        this.benefits.add(benefits);
        benefits.setEmploymentDetails(this);
        return this;
    }

    public EmploymentDetails removeBenefits(Benefits benefits) {
        this.benefits.remove(benefits);
        benefits.setEmploymentDetails(null);
        return this;
    }

    public void setBenefits(Set<Benefits> benefits) {
        this.benefits = benefits;
    }

    public Set<Expenses> getExpenses() {
        return expenses;
    }

    public EmploymentDetails expenses(Set<Expenses> expenses) {
        this.expenses = expenses;
        return this;
    }

    public EmploymentDetails addExpenses(Expenses expenses) {
        this.expenses.add(expenses);
        expenses.setEmploymentDetails(this);
        return this;
    }

    public EmploymentDetails removeExpenses(Expenses expenses) {
        this.expenses.remove(expenses);
        expenses.setEmploymentDetails(null);
        return this;
    }

    public void setExpenses(Set<Expenses> expenses) {
        this.expenses = expenses;
    }

    public Set<EmpPensionContributions> getEmploymentPensionContributions() {
        return employmentPensionContributions;
    }

    public EmploymentDetails employmentPensionContributions(Set<EmpPensionContributions> empPensionContributions) {
        this.employmentPensionContributions = empPensionContributions;
        return this;
    }

    public EmploymentDetails addEmploymentPensionContributions(EmpPensionContributions empPensionContributions) {
        this.employmentPensionContributions.add(empPensionContributions);
        empPensionContributions.setEmploymentDetails(this);
        return this;
    }

    public EmploymentDetails removeEmploymentPensionContributions(EmpPensionContributions empPensionContributions) {
        this.employmentPensionContributions.remove(empPensionContributions);
        empPensionContributions.setEmploymentDetails(null);
        return this;
    }

    public void setEmploymentPensionContributions(Set<EmpPensionContributions> empPensionContributions) {
        this.employmentPensionContributions = empPensionContributions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmploymentDetails)) {
            return false;
        }
        return id != null && id.equals(((EmploymentDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EmploymentDetails{" +
            "id=" + getId() +
            "}";
    }
}
