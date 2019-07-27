package io.accountalk.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A EmpPensionContributions.
 */
@Entity
@Table(name = "emp_pension_contributions")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EmpPensionContributions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount_paid")
    private Double amountPaid;

    @ManyToOne
    @JsonIgnoreProperties("empPensionContributions")
    private EmploymentDetails employmentDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public EmpPensionContributions amountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
        return this;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public EmploymentDetails getEmploymentDetails() {
        return employmentDetails;
    }

    public EmpPensionContributions employmentDetails(EmploymentDetails employmentDetails) {
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
        if (!(o instanceof EmpPensionContributions)) {
            return false;
        }
        return id != null && id.equals(((EmpPensionContributions) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EmpPensionContributions{" +
            "id=" + getId() +
            ", amountPaid=" + getAmountPaid() +
            "}";
    }
}
