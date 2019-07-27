package io.accountalk.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import io.accountalk.domain.enumeration.StudentLoan;

/**
 * A TaxReturn.
 */
@Entity
@Table(name = "tax_return")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TaxReturn implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "year")
    private Instant year;

    @Enumerated(EnumType.STRING)
    @Column(name = "student_loan")
    private StudentLoan studentLoan;

    @OneToOne
    @JoinColumn(unique = true)
    private BankDetails bankRefundDetails;

    @OneToOne
    @JoinColumn(unique = true)
    private StatePensionReceived statePensionDetails;

    @OneToMany(mappedBy = "taxReturn")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PensionReceived> pensionReceiveds = new HashSet<>();

    @OneToMany(mappedBy = "taxReturn")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PerPensionContributions> personalPensionContributions = new HashSet<>();

    @OneToMany(mappedBy = "taxReturn")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<GiftAidDonations> giftAidDonations = new HashSet<>();

    @OneToMany(mappedBy = "taxReturn")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BankInterest> bankInterests = new HashSet<>();

    @OneToMany(mappedBy = "taxReturn")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DividendsReceived> dividendsReceiveds = new HashSet<>();

    @OneToMany(mappedBy = "taxReturn")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EmploymentDetails> employmentDetails = new HashSet<>();

    @OneToMany(mappedBy = "taxReturn")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EisInvestments> eisInvestments = new HashSet<>();

    @OneToMany(mappedBy = "taxReturn")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BusinessProfit> businessProfits = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("taxReturns")
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getYear() {
        return year;
    }

    public TaxReturn year(Instant year) {
        this.year = year;
        return this;
    }

    public void setYear(Instant year) {
        this.year = year;
    }

    public StudentLoan getStudentLoan() {
        return studentLoan;
    }

    public TaxReturn studentLoan(StudentLoan studentLoan) {
        this.studentLoan = studentLoan;
        return this;
    }

    public void setStudentLoan(StudentLoan studentLoan) {
        this.studentLoan = studentLoan;
    }

    public BankDetails getBankRefundDetails() {
        return bankRefundDetails;
    }

    public TaxReturn bankRefundDetails(BankDetails bankDetails) {
        this.bankRefundDetails = bankDetails;
        return this;
    }

    public void setBankRefundDetails(BankDetails bankDetails) {
        this.bankRefundDetails = bankDetails;
    }

    public StatePensionReceived getStatePensionDetails() {
        return statePensionDetails;
    }

    public TaxReturn statePensionDetails(StatePensionReceived statePensionReceived) {
        this.statePensionDetails = statePensionReceived;
        return this;
    }

    public void setStatePensionDetails(StatePensionReceived statePensionReceived) {
        this.statePensionDetails = statePensionReceived;
    }

    public Set<PensionReceived> getPensionReceiveds() {
        return pensionReceiveds;
    }

    public TaxReturn pensionReceiveds(Set<PensionReceived> pensionReceiveds) {
        this.pensionReceiveds = pensionReceiveds;
        return this;
    }

    public TaxReturn addPensionReceived(PensionReceived pensionReceived) {
        this.pensionReceiveds.add(pensionReceived);
        pensionReceived.setTaxReturn(this);
        return this;
    }

    public TaxReturn removePensionReceived(PensionReceived pensionReceived) {
        this.pensionReceiveds.remove(pensionReceived);
        pensionReceived.setTaxReturn(null);
        return this;
    }

    public void setPensionReceiveds(Set<PensionReceived> pensionReceiveds) {
        this.pensionReceiveds = pensionReceiveds;
    }

    public Set<PerPensionContributions> getPersonalPensionContributions() {
        return personalPensionContributions;
    }

    public TaxReturn personalPensionContributions(Set<PerPensionContributions> perPensionContributions) {
        this.personalPensionContributions = perPensionContributions;
        return this;
    }

    public TaxReturn addPersonalPensionContributions(PerPensionContributions perPensionContributions) {
        this.personalPensionContributions.add(perPensionContributions);
        perPensionContributions.setTaxReturn(this);
        return this;
    }

    public TaxReturn removePersonalPensionContributions(PerPensionContributions perPensionContributions) {
        this.personalPensionContributions.remove(perPensionContributions);
        perPensionContributions.setTaxReturn(null);
        return this;
    }

    public void setPersonalPensionContributions(Set<PerPensionContributions> perPensionContributions) {
        this.personalPensionContributions = perPensionContributions;
    }

    public Set<GiftAidDonations> getGiftAidDonations() {
        return giftAidDonations;
    }

    public TaxReturn giftAidDonations(Set<GiftAidDonations> giftAidDonations) {
        this.giftAidDonations = giftAidDonations;
        return this;
    }

    public TaxReturn addGiftAidDonations(GiftAidDonations giftAidDonations) {
        this.giftAidDonations.add(giftAidDonations);
        giftAidDonations.setTaxReturn(this);
        return this;
    }

    public TaxReturn removeGiftAidDonations(GiftAidDonations giftAidDonations) {
        this.giftAidDonations.remove(giftAidDonations);
        giftAidDonations.setTaxReturn(null);
        return this;
    }

    public void setGiftAidDonations(Set<GiftAidDonations> giftAidDonations) {
        this.giftAidDonations = giftAidDonations;
    }

    public Set<BankInterest> getBankInterests() {
        return bankInterests;
    }

    public TaxReturn bankInterests(Set<BankInterest> bankInterests) {
        this.bankInterests = bankInterests;
        return this;
    }

    public TaxReturn addBankInterest(BankInterest bankInterest) {
        this.bankInterests.add(bankInterest);
        bankInterest.setTaxReturn(this);
        return this;
    }

    public TaxReturn removeBankInterest(BankInterest bankInterest) {
        this.bankInterests.remove(bankInterest);
        bankInterest.setTaxReturn(null);
        return this;
    }

    public void setBankInterests(Set<BankInterest> bankInterests) {
        this.bankInterests = bankInterests;
    }

    public Set<DividendsReceived> getDividendsReceiveds() {
        return dividendsReceiveds;
    }

    public TaxReturn dividendsReceiveds(Set<DividendsReceived> dividendsReceiveds) {
        this.dividendsReceiveds = dividendsReceiveds;
        return this;
    }

    public TaxReturn addDividendsReceived(DividendsReceived dividendsReceived) {
        this.dividendsReceiveds.add(dividendsReceived);
        dividendsReceived.setTaxReturn(this);
        return this;
    }

    public TaxReturn removeDividendsReceived(DividendsReceived dividendsReceived) {
        this.dividendsReceiveds.remove(dividendsReceived);
        dividendsReceived.setTaxReturn(null);
        return this;
    }

    public void setDividendsReceiveds(Set<DividendsReceived> dividendsReceiveds) {
        this.dividendsReceiveds = dividendsReceiveds;
    }

    public Set<EmploymentDetails> getEmploymentDetails() {
        return employmentDetails;
    }

    public TaxReturn employmentDetails(Set<EmploymentDetails> employmentDetails) {
        this.employmentDetails = employmentDetails;
        return this;
    }

    public TaxReturn addEmploymentDetails(EmploymentDetails employmentDetails) {
        this.employmentDetails.add(employmentDetails);
        employmentDetails.setTaxReturn(this);
        return this;
    }

    public TaxReturn removeEmploymentDetails(EmploymentDetails employmentDetails) {
        this.employmentDetails.remove(employmentDetails);
        employmentDetails.setTaxReturn(null);
        return this;
    }

    public void setEmploymentDetails(Set<EmploymentDetails> employmentDetails) {
        this.employmentDetails = employmentDetails;
    }

    public Set<EisInvestments> getEisInvestments() {
        return eisInvestments;
    }

    public TaxReturn eisInvestments(Set<EisInvestments> eisInvestments) {
        this.eisInvestments = eisInvestments;
        return this;
    }

    public TaxReturn addEisInvestments(EisInvestments eisInvestments) {
        this.eisInvestments.add(eisInvestments);
        eisInvestments.setTaxReturn(this);
        return this;
    }

    public TaxReturn removeEisInvestments(EisInvestments eisInvestments) {
        this.eisInvestments.remove(eisInvestments);
        eisInvestments.setTaxReturn(null);
        return this;
    }

    public void setEisInvestments(Set<EisInvestments> eisInvestments) {
        this.eisInvestments = eisInvestments;
    }

    public Set<BusinessProfit> getBusinessProfits() {
        return businessProfits;
    }

    public TaxReturn businessProfits(Set<BusinessProfit> businessProfits) {
        this.businessProfits = businessProfits;
        return this;
    }

    public TaxReturn addBusinessProfit(BusinessProfit businessProfit) {
        this.businessProfits.add(businessProfit);
        businessProfit.setTaxReturn(this);
        return this;
    }

    public TaxReturn removeBusinessProfit(BusinessProfit businessProfit) {
        this.businessProfits.remove(businessProfit);
        businessProfit.setTaxReturn(null);
        return this;
    }

    public void setBusinessProfits(Set<BusinessProfit> businessProfits) {
        this.businessProfits = businessProfits;
    }

    public Client getClient() {
        return client;
    }

    public TaxReturn client(Client client) {
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
        if (!(o instanceof TaxReturn)) {
            return false;
        }
        return id != null && id.equals(((TaxReturn) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TaxReturn{" +
            "id=" + getId() +
            ", year='" + getYear() + "'" +
            ", studentLoan='" + getStudentLoan() + "'" +
            "}";
    }
}
