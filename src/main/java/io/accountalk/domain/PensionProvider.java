package io.accountalk.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A PensionProvider.
 */
@Entity
@Table(name = "pension_provider")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PensionProvider implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_of_provider")
    private String nameOfProvider;

    @Column(name = "membership_number")
    private String membershipNumber;

    @Column(name = "paye_reference")
    private String payeReference;

    @ManyToOne
    @JsonIgnoreProperties("pensionProviders")
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameOfProvider() {
        return nameOfProvider;
    }

    public PensionProvider nameOfProvider(String nameOfProvider) {
        this.nameOfProvider = nameOfProvider;
        return this;
    }

    public void setNameOfProvider(String nameOfProvider) {
        this.nameOfProvider = nameOfProvider;
    }

    public String getMembershipNumber() {
        return membershipNumber;
    }

    public PensionProvider membershipNumber(String membershipNumber) {
        this.membershipNumber = membershipNumber;
        return this;
    }

    public void setMembershipNumber(String membershipNumber) {
        this.membershipNumber = membershipNumber;
    }

    public String getPayeReference() {
        return payeReference;
    }

    public PensionProvider payeReference(String payeReference) {
        this.payeReference = payeReference;
        return this;
    }

    public void setPayeReference(String payeReference) {
        this.payeReference = payeReference;
    }

    public Client getClient() {
        return client;
    }

    public PensionProvider client(Client client) {
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
        if (!(o instanceof PensionProvider)) {
            return false;
        }
        return id != null && id.equals(((PensionProvider) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PensionProvider{" +
            "id=" + getId() +
            ", nameOfProvider='" + getNameOfProvider() + "'" +
            ", membershipNumber='" + getMembershipNumber() + "'" +
            ", payeReference='" + getPayeReference() + "'" +
            "}";
    }
}
