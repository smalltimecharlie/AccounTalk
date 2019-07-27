package io.accountalk.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import io.accountalk.domain.enumeration.BenefitType;

/**
 * A Benefits.
 */
@Entity
@Table(name = "benefits")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Benefits implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "benefit_type")
    private BenefitType benefitType;

    @Column(name = "description")
    private String description;

    @Column(name = "value")
    private Double value;

    @ManyToOne
    @JsonIgnoreProperties("benefits")
    private EmploymentDetails employmentDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BenefitType getBenefitType() {
        return benefitType;
    }

    public Benefits benefitType(BenefitType benefitType) {
        this.benefitType = benefitType;
        return this;
    }

    public void setBenefitType(BenefitType benefitType) {
        this.benefitType = benefitType;
    }

    public String getDescription() {
        return description;
    }

    public Benefits description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getValue() {
        return value;
    }

    public Benefits value(Double value) {
        this.value = value;
        return this;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public EmploymentDetails getEmploymentDetails() {
        return employmentDetails;
    }

    public Benefits employmentDetails(EmploymentDetails employmentDetails) {
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
        if (!(o instanceof Benefits)) {
            return false;
        }
        return id != null && id.equals(((Benefits) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Benefits{" +
            "id=" + getId() +
            ", benefitType='" + getBenefitType() + "'" +
            ", description='" + getDescription() + "'" +
            ", value=" + getValue() +
            "}";
    }
}
