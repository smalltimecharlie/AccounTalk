package io.accountalk.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Shares.
 */
@Entity
@Table(name = "shares")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Shares implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "number_of_shares")
    private Long numberOfShares;

    @ManyToOne
    @JsonIgnoreProperties("shares")
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Shares companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getNumberOfShares() {
        return numberOfShares;
    }

    public Shares numberOfShares(Long numberOfShares) {
        this.numberOfShares = numberOfShares;
        return this;
    }

    public void setNumberOfShares(Long numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    public Client getClient() {
        return client;
    }

    public Shares client(Client client) {
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
        if (!(o instanceof Shares)) {
            return false;
        }
        return id != null && id.equals(((Shares) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Shares{" +
            "id=" + getId() +
            ", companyName='" + getCompanyName() + "'" +
            ", numberOfShares=" + getNumberOfShares() +
            "}";
    }
}
