package io.accountalk.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DividendsReceived.
 */
@Entity
@Table(name = "dividends_received")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DividendsReceived implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_date")
    private Instant paymentDate;

    @Column(name = "amount_received")
    private Double amountReceived;

    @ManyToOne
    @JsonIgnoreProperties("dividendsReceiveds")
    private TaxReturn taxReturn;

    @OneToOne
    @JoinColumn(unique = true)
    private Shares shares;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getPaymentDate() {
        return paymentDate;
    }

    public DividendsReceived paymentDate(Instant paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public void setPaymentDate(Instant paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getAmountReceived() {
        return amountReceived;
    }

    public DividendsReceived amountReceived(Double amountReceived) {
        this.amountReceived = amountReceived;
        return this;
    }

    public void setAmountReceived(Double amountReceived) {
        this.amountReceived = amountReceived;
    }

    public TaxReturn getTaxReturn() {
        return taxReturn;
    }

    public DividendsReceived taxReturn(TaxReturn taxReturn) {
        this.taxReturn = taxReturn;
        return this;
    }

    public void setTaxReturn(TaxReturn taxReturn) {
        this.taxReturn = taxReturn;
    }

    public Shares getShares() {
        return shares;
    }

    public DividendsReceived shares(Shares shares) {
        this.shares = shares;
        return this;
    }

    public void setShares(Shares shares) {
        this.shares = shares;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DividendsReceived)) {
            return false;
        }
        return id != null && id.equals(((DividendsReceived) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DividendsReceived{" +
            "id=" + getId() +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", amountReceived=" + getAmountReceived() +
            "}";
    }
}
