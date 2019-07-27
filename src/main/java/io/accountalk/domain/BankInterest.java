package io.accountalk.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A BankInterest.
 */
@Entity
@Table(name = "bank_interest")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BankInterest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "net_interest")
    private Double netInterest;

    @Column(name = "tax_deducted")
    private Double taxDeducted;

    @ManyToOne
    @JsonIgnoreProperties("bankInterests")
    private TaxReturn taxReturn;

    @OneToOne
    @JoinColumn(unique = true)
    private BankDetails bankdetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getNetInterest() {
        return netInterest;
    }

    public BankInterest netInterest(Double netInterest) {
        this.netInterest = netInterest;
        return this;
    }

    public void setNetInterest(Double netInterest) {
        this.netInterest = netInterest;
    }

    public Double getTaxDeducted() {
        return taxDeducted;
    }

    public BankInterest taxDeducted(Double taxDeducted) {
        this.taxDeducted = taxDeducted;
        return this;
    }

    public void setTaxDeducted(Double taxDeducted) {
        this.taxDeducted = taxDeducted;
    }

    public TaxReturn getTaxReturn() {
        return taxReturn;
    }

    public BankInterest taxReturn(TaxReturn taxReturn) {
        this.taxReturn = taxReturn;
        return this;
    }

    public void setTaxReturn(TaxReturn taxReturn) {
        this.taxReturn = taxReturn;
    }

    public BankDetails getBankdetails() {
        return bankdetails;
    }

    public BankInterest bankdetails(BankDetails bankDetails) {
        this.bankdetails = bankDetails;
        return this;
    }

    public void setBankdetails(BankDetails bankDetails) {
        this.bankdetails = bankDetails;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankInterest)) {
            return false;
        }
        return id != null && id.equals(((BankInterest) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BankInterest{" +
            "id=" + getId() +
            ", netInterest=" + getNetInterest() +
            ", taxDeducted=" + getTaxDeducted() +
            "}";
    }
}
