package io.accountalk.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Employment.
 */
@Entity
@Table(name = "employment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Employment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "paye_reference")
    private String payeReference;

    @Column(name = "employment_end_date")
    private Instant employmentEndDate;

    @ManyToOne
    @JsonIgnoreProperties("employments")
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public Employment businessName(String businessName) {
        this.businessName = businessName;
        return this;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getPayeReference() {
        return payeReference;
    }

    public Employment payeReference(String payeReference) {
        this.payeReference = payeReference;
        return this;
    }

    public void setPayeReference(String payeReference) {
        this.payeReference = payeReference;
    }

    public Instant getEmploymentEndDate() {
        return employmentEndDate;
    }

    public Employment employmentEndDate(Instant employmentEndDate) {
        this.employmentEndDate = employmentEndDate;
        return this;
    }

    public void setEmploymentEndDate(Instant employmentEndDate) {
        this.employmentEndDate = employmentEndDate;
    }

    public Client getClient() {
        return client;
    }

    public Employment client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employment)) {
            return false;
        }
        return id != null && id.equals(((Employment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Employment{" +
            "id=" + getId() +
            ", businessName='" + getBusinessName() + "'" +
            ", payeReference='" + getPayeReference() + "'" +
            ", employmentEndDate='" + getEmploymentEndDate() + "'" +
            "}";
    }
}
