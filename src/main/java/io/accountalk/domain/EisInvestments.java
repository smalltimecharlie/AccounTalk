package io.accountalk.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

import io.accountalk.domain.enumeration.InvestmentScheme;

/**
 * A EisInvestments.
 */
@Entity
@Table(name = "eis_investments")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EisInvestments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "investment_scheme")
    private InvestmentScheme investmentScheme;

    @Column(name = "date_invested")
    private Instant dateInvested;

    @Column(name = "amount_paid")
    private Double amountPaid;

    @ManyToOne
    @JsonIgnoreProperties("eisInvestments")
    private TaxReturn taxReturn;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InvestmentScheme getInvestmentScheme() {
        return investmentScheme;
    }

    public EisInvestments investmentScheme(InvestmentScheme investmentScheme) {
        this.investmentScheme = investmentScheme;
        return this;
    }

    public void setInvestmentScheme(InvestmentScheme investmentScheme) {
        this.investmentScheme = investmentScheme;
    }

    public Instant getDateInvested() {
        return dateInvested;
    }

    public EisInvestments dateInvested(Instant dateInvested) {
        this.dateInvested = dateInvested;
        return this;
    }

    public void setDateInvested(Instant dateInvested) {
        this.dateInvested = dateInvested;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public EisInvestments amountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
        return this;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public TaxReturn getTaxReturn() {
        return taxReturn;
    }

    public EisInvestments taxReturn(TaxReturn taxReturn) {
        this.taxReturn = taxReturn;
        return this;
    }

    public void setTaxReturn(TaxReturn taxReturn) {
        this.taxReturn = taxReturn;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EisInvestments)) {
            return false;
        }
        return id != null && id.equals(((EisInvestments) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EisInvestments{" +
            "id=" + getId() +
            ", investmentScheme='" + getInvestmentScheme() + "'" +
            ", dateInvested='" + getDateInvested() + "'" +
            ", amountPaid=" + getAmountPaid() +
            "}";
    }
}
