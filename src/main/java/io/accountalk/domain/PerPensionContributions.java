package io.accountalk.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A PerPensionContributions.
 */
@Entity
@Table(name = "per_pension_contributions")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PerPensionContributions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "net_amount_paid")
    private Double netAmountPaid;

    @ManyToOne
    @JsonIgnoreProperties("perPensionContributions")
    private TaxReturn taxReturn;

    @OneToOne
    @JoinColumn(unique = true)
    private PensionProvider pensionProvider;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getNetAmountPaid() {
        return netAmountPaid;
    }

    public PerPensionContributions netAmountPaid(Double netAmountPaid) {
        this.netAmountPaid = netAmountPaid;
        return this;
    }

    public void setNetAmountPaid(Double netAmountPaid) {
        this.netAmountPaid = netAmountPaid;
    }

    public TaxReturn getTaxReturn() {
        return taxReturn;
    }

    public PerPensionContributions taxReturn(TaxReturn taxReturn) {
        this.taxReturn = taxReturn;
        return this;
    }

    public void setTaxReturn(TaxReturn taxReturn) {
        this.taxReturn = taxReturn;
    }

    public PensionProvider getPensionProvider() {
        return pensionProvider;
    }

    public PerPensionContributions pensionProvider(PensionProvider pensionProvider) {
        this.pensionProvider = pensionProvider;
        return this;
    }

    public void setPensionProvider(PensionProvider pensionProvider) {
        this.pensionProvider = pensionProvider;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PerPensionContributions)) {
            return false;
        }
        return id != null && id.equals(((PerPensionContributions) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PerPensionContributions{" +
            "id=" + getId() +
            ", netAmountPaid=" + getNetAmountPaid() +
            "}";
    }
}
