package io.accountalk.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A BankDetails.
 */
@Entity
@Table(name = "bank_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BankDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_holder_name")
    private String accountHolderName;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "sort_code")
    private String sortCode;

    @Column(name = "joint_account")
    private Boolean jointAccount;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "opening_date")
    private Instant openingDate;

    @Column(name = "closed_date")
    private Instant closedDate;

    @OneToOne(mappedBy = "bankRefundDetails")
    @JsonIgnore
    private TaxReturn taxReturn;

    @ManyToOne
    @JsonIgnoreProperties("bankDetails")
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public BankDetails accountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
        return this;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BankDetails accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getSortCode() {
        return sortCode;
    }

    public BankDetails sortCode(String sortCode) {
        this.sortCode = sortCode;
        return this;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public Boolean isJointAccount() {
        return jointAccount;
    }

    public BankDetails jointAccount(Boolean jointAccount) {
        this.jointAccount = jointAccount;
        return this;
    }

    public void setJointAccount(Boolean jointAccount) {
        this.jointAccount = jointAccount;
    }

    public String getBankName() {
        return bankName;
    }

    public BankDetails bankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Instant getOpeningDate() {
        return openingDate;
    }

    public BankDetails openingDate(Instant openingDate) {
        this.openingDate = openingDate;
        return this;
    }

    public void setOpeningDate(Instant openingDate) {
        this.openingDate = openingDate;
    }

    public Instant getClosedDate() {
        return closedDate;
    }

    public BankDetails closedDate(Instant closedDate) {
        this.closedDate = closedDate;
        return this;
    }

    public void setClosedDate(Instant closedDate) {
        this.closedDate = closedDate;
    }

    public TaxReturn getTaxReturn() {
        return taxReturn;
    }

    public BankDetails taxReturn(TaxReturn taxReturn) {
        this.taxReturn = taxReturn;
        return this;
    }

    public void setTaxReturn(TaxReturn taxReturn) {
        this.taxReturn = taxReturn;
    }

    public Client getClient() {
        return client;
    }

    public BankDetails client(Client client) {
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
        if (!(o instanceof BankDetails)) {
            return false;
        }
        return id != null && id.equals(((BankDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BankDetails{" +
            "id=" + getId() +
            ", accountHolderName='" + getAccountHolderName() + "'" +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", sortCode='" + getSortCode() + "'" +
            ", jointAccount='" + isJointAccount() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", openingDate='" + getOpeningDate() + "'" +
            ", closedDate='" + getClosedDate() + "'" +
            "}";
    }
}
