package io.accountalk.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A BusinessProfit.
 */
@Entity
@Table(name = "business_profit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BusinessProfit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "turnover")
    private Double turnover;

    @Column(name = "expenses")
    private Double expenses;

    @Column(name = "capital_allowances")
    private Double capitalAllowances;

    @Column(name = "profit")
    private Double profit;

    @Column(name = "cis_tax_deducted")
    private Double cisTaxDeducted;

    @ManyToOne
    @JsonIgnoreProperties("businessProfits")
    private TaxReturn taxReturn;

    @OneToOne
    @JoinColumn(unique = true)
    private Business business;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTurnover() {
        return turnover;
    }

    public BusinessProfit turnover(Double turnover) {
        this.turnover = turnover;
        return this;
    }

    public void setTurnover(Double turnover) {
        this.turnover = turnover;
    }

    public Double getExpenses() {
        return expenses;
    }

    public BusinessProfit expenses(Double expenses) {
        this.expenses = expenses;
        return this;
    }

    public void setExpenses(Double expenses) {
        this.expenses = expenses;
    }

    public Double getCapitalAllowances() {
        return capitalAllowances;
    }

    public BusinessProfit capitalAllowances(Double capitalAllowances) {
        this.capitalAllowances = capitalAllowances;
        return this;
    }

    public void setCapitalAllowances(Double capitalAllowances) {
        this.capitalAllowances = capitalAllowances;
    }

    public Double getProfit() {
        return profit;
    }

    public BusinessProfit profit(Double profit) {
        this.profit = profit;
        return this;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public Double getCisTaxDeducted() {
        return cisTaxDeducted;
    }

    public BusinessProfit cisTaxDeducted(Double cisTaxDeducted) {
        this.cisTaxDeducted = cisTaxDeducted;
        return this;
    }

    public void setCisTaxDeducted(Double cisTaxDeducted) {
        this.cisTaxDeducted = cisTaxDeducted;
    }

    public TaxReturn getTaxReturn() {
        return taxReturn;
    }

    public BusinessProfit taxReturn(TaxReturn taxReturn) {
        this.taxReturn = taxReturn;
        return this;
    }

    public void setTaxReturn(TaxReturn taxReturn) {
        this.taxReturn = taxReturn;
    }

    public Business getBusiness() {
        return business;
    }

    public BusinessProfit business(Business business) {
        this.business = business;
        return this;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusinessProfit)) {
            return false;
        }
        return id != null && id.equals(((BusinessProfit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BusinessProfit{" +
            "id=" + getId() +
            ", turnover=" + getTurnover() +
            ", expenses=" + getExpenses() +
            ", capitalAllowances=" + getCapitalAllowances() +
            ", profit=" + getProfit() +
            ", cisTaxDeducted=" + getCisTaxDeducted() +
            "}";
    }
}
