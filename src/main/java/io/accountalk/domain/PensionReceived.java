package io.accountalk.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A PensionReceived.
 */
@Entity
@Table(name = "pension_received")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PensionReceived implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gross_pension_received")
    private Double grossPensionReceived;

    @Column(name = "tax_deducted")
    private Double taxDeducted;

    @ManyToOne
    @JsonIgnoreProperties("pensionReceiveds")
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

    public Double getGrossPensionReceived() {
        return grossPensionReceived;
    }

    public PensionReceived grossPensionReceived(Double grossPensionReceived) {
        this.grossPensionReceived = grossPensionReceived;
        return this;
    }

    public void setGrossPensionReceived(Double grossPensionReceived) {
        this.grossPensionReceived = grossPensionReceived;
    }

    public Double getTaxDeducted() {
        return taxDeducted;
    }

    public PensionReceived taxDeducted(Double taxDeducted) {
        this.taxDeducted = taxDeducted;
        return this;
    }

    public void setTaxDeducted(Double taxDeducted) {
        this.taxDeducted = taxDeducted;
    }

    public TaxReturn getTaxReturn() {
        return taxReturn;
    }

    public PensionReceived taxReturn(TaxReturn taxReturn) {
        this.taxReturn = taxReturn;
        return this;
    }

    public void setTaxReturn(TaxReturn taxReturn) {
        this.taxReturn = taxReturn;
    }

    public PensionProvider getPensionProvider() {
        return pensionProvider;
    }

    public PensionReceived pensionProvider(PensionProvider pensionProvider) {
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
        if (!(o instanceof PensionReceived)) {
            return false;
        }
        return id != null && id.equals(((PensionReceived) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PensionReceived{" +
            "id=" + getId() +
            ", grossPensionReceived=" + getGrossPensionReceived() +
            ", taxDeducted=" + getTaxDeducted() +
            "}";
    }
}
