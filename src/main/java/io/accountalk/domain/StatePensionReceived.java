package io.accountalk.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A StatePensionReceived.
 */
@Entity
@Table(name = "state_pension_received")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StatePensionReceived implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gross_pension_received")
    private Double grossPensionReceived;

    @Column(name = "tax_deducted")
    private Double taxDeducted;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getGrossPensionReceived() {
        return grossPensionReceived;
    }

    public StatePensionReceived grossPensionReceived(Double grossPensionReceived) {
        this.grossPensionReceived = grossPensionReceived;
        return this;
    }

    public void setGrossPensionReceived(Double grossPensionReceived) {
        this.grossPensionReceived = grossPensionReceived;
    }

    public Double getTaxDeducted() {
        return taxDeducted;
    }

    public StatePensionReceived taxDeducted(Double taxDeducted) {
        this.taxDeducted = taxDeducted;
        return this;
    }

    public void setTaxDeducted(Double taxDeducted) {
        this.taxDeducted = taxDeducted;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StatePensionReceived)) {
            return false;
        }
        return id != null && id.equals(((StatePensionReceived) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "StatePensionReceived{" +
            "id=" + getId() +
            ", grossPensionReceived=" + getGrossPensionReceived() +
            ", taxDeducted=" + getTaxDeducted() +
            "}";
    }
}
