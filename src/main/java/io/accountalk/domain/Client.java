package io.accountalk.domain;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import io.accountalk.domain.enumeration.Gender;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Clients.
     */
    @NotNull
    @ApiModelProperty(value = "Clients.", required = true)
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "forename", nullable = false)
    private String forename;

    @NotNull
    @Column(name = "surname", nullable = false)
    private String surname;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private String dateOfBirth;

    @Column(name = "phone_number")
    private String phoneNumber;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Size(min = 10, max = 10)
    @Column(name = "self_assesment_utr_no", length = 10)
    private String selfAssesmentUtrNo;

    @Column(name = "nationality")
    private String nationality;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "student_loan")
    private Boolean studentLoan;

    @Column(name = "child_benefit")
    private Boolean childBenefit;

    @Column(name = "spouse")
    private Boolean spouse;

    @Column(name = "find_out_about_us")
    private String findOutAboutUs;

    @Column(name = "additional_information")
    private String additionalInformation;

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Business> businesses = new HashSet<>();

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PreviousAccountant> previousAccountants = new HashSet<>();

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TaxReturn> taxReturns = new HashSet<>();

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BankDetails> bankDetails = new HashSet<>();

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Shares> shares = new HashSet<>();

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PensionProvider> pensionProviders = new HashSet<>();

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Employment> employments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Client title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getForename() {
        return forename;
    }

    public Client forename(String forename) {
        this.forename = forename;
        return this;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public Client surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public Client dateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Client phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public Client email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSelfAssesmentUtrNo() {
        return selfAssesmentUtrNo;
    }

    public Client selfAssesmentUtrNo(String selfAssesmentUtrNo) {
        this.selfAssesmentUtrNo = selfAssesmentUtrNo;
        return this;
    }

    public void setSelfAssesmentUtrNo(String selfAssesmentUtrNo) {
        this.selfAssesmentUtrNo = selfAssesmentUtrNo;
    }

    public String getNationality() {
        return nationality;
    }

    public Client nationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Gender getGender() {
        return gender;
    }

    public Client gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Boolean isStudentLoan() {
        return studentLoan;
    }

    public Client studentLoan(Boolean studentLoan) {
        this.studentLoan = studentLoan;
        return this;
    }

    public void setStudentLoan(Boolean studentLoan) {
        this.studentLoan = studentLoan;
    }

    public Boolean isChildBenefit() {
        return childBenefit;
    }

    public Client childBenefit(Boolean childBenefit) {
        this.childBenefit = childBenefit;
        return this;
    }

    public void setChildBenefit(Boolean childBenefit) {
        this.childBenefit = childBenefit;
    }

    public Boolean isSpouse() {
        return spouse;
    }

    public Client spouse(Boolean spouse) {
        this.spouse = spouse;
        return this;
    }

    public void setSpouse(Boolean spouse) {
        this.spouse = spouse;
    }

    public String getFindOutAboutUs() {
        return findOutAboutUs;
    }

    public Client findOutAboutUs(String findOutAboutUs) {
        this.findOutAboutUs = findOutAboutUs;
        return this;
    }

    public void setFindOutAboutUs(String findOutAboutUs) {
        this.findOutAboutUs = findOutAboutUs;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public Client additionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
        return this;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public Client addresses(Set<Address> addresses) {
        this.addresses = addresses;
        return this;
    }

    public Client addAddress(Address address) {
        this.addresses.add(address);
        address.setClient(this);
        return this;
    }

    public Client removeAddress(Address address) {
        this.addresses.remove(address);
        address.setClient(null);
        return this;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Set<Business> getBusinesses() {
        return businesses;
    }

    public Client businesses(Set<Business> businesses) {
        this.businesses = businesses;
        return this;
    }

    public Client addBusiness(Business business) {
        this.businesses.add(business);
        business.setClient(this);
        return this;
    }

    public Client removeBusiness(Business business) {
        this.businesses.remove(business);
        business.setClient(null);
        return this;
    }

    public void setBusinesses(Set<Business> businesses) {
        this.businesses = businesses;
    }

    public Set<PreviousAccountant> getPreviousAccountants() {
        return previousAccountants;
    }

    public Client previousAccountants(Set<PreviousAccountant> previousAccountants) {
        this.previousAccountants = previousAccountants;
        return this;
    }

    public Client addPreviousAccountant(PreviousAccountant previousAccountant) {
        this.previousAccountants.add(previousAccountant);
        previousAccountant.setClient(this);
        return this;
    }

    public Client removePreviousAccountant(PreviousAccountant previousAccountant) {
        this.previousAccountants.remove(previousAccountant);
        previousAccountant.setClient(null);
        return this;
    }

    public void setPreviousAccountants(Set<PreviousAccountant> previousAccountants) {
        this.previousAccountants = previousAccountants;
    }

    public Set<TaxReturn> getTaxReturns() {
        return taxReturns;
    }

    public Client taxReturns(Set<TaxReturn> taxReturns) {
        this.taxReturns = taxReturns;
        return this;
    }

    public Client addTaxReturn(TaxReturn taxReturn) {
        this.taxReturns.add(taxReturn);
        taxReturn.setClient(this);
        return this;
    }

    public Client removeTaxReturn(TaxReturn taxReturn) {
        this.taxReturns.remove(taxReturn);
        taxReturn.setClient(null);
        return this;
    }

    public void setTaxReturns(Set<TaxReturn> taxReturns) {
        this.taxReturns = taxReturns;
    }

    public Set<BankDetails> getBankDetails() {
        return bankDetails;
    }

    public Client bankDetails(Set<BankDetails> bankDetails) {
        this.bankDetails = bankDetails;
        return this;
    }

    public Client addBankDetails(BankDetails bankDetails) {
        this.bankDetails.add(bankDetails);
        bankDetails.setClient(this);
        return this;
    }

    public Client removeBankDetails(BankDetails bankDetails) {
        this.bankDetails.remove(bankDetails);
        bankDetails.setClient(null);
        return this;
    }

    public void setBankDetails(Set<BankDetails> bankDetails) {
        this.bankDetails = bankDetails;
    }

    public Set<Shares> getShares() {
        return shares;
    }

    public Client shares(Set<Shares> shares) {
        this.shares = shares;
        return this;
    }

    public Client addShares(Shares shares) {
        this.shares.add(shares);
        shares.setClient(this);
        return this;
    }

    public Client removeShares(Shares shares) {
        this.shares.remove(shares);
        shares.setClient(null);
        return this;
    }

    public void setShares(Set<Shares> shares) {
        this.shares = shares;
    }

    public Set<PensionProvider> getPensionProviders() {
        return pensionProviders;
    }

    public Client pensionProviders(Set<PensionProvider> pensionProviders) {
        this.pensionProviders = pensionProviders;
        return this;
    }

    public Client addPensionProvider(PensionProvider pensionProvider) {
        this.pensionProviders.add(pensionProvider);
        pensionProvider.setClient(this);
        return this;
    }

    public Client removePensionProvider(PensionProvider pensionProvider) {
        this.pensionProviders.remove(pensionProvider);
        pensionProvider.setClient(null);
        return this;
    }

    public void setPensionProviders(Set<PensionProvider> pensionProviders) {
        this.pensionProviders = pensionProviders;
    }

    public Set<Employment> getEmployments() {
        return employments;
    }

    public Client employments(Set<Employment> employments) {
        this.employments = employments;
        return this;
    }

    public Client addEmployment(Employment employment) {
        this.employments.add(employment);
        employment.setClient(this);
        return this;
    }

    public Client removeEmployment(Employment employment) {
        this.employments.remove(employment);
        employment.setClient(null);
        return this;
    }

    public void setEmployments(Set<Employment> employments) {
        this.employments = employments;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", forename='" + getForename() + "'" +
            ", surname='" + getSurname() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", email='" + getEmail() + "'" +
            ", selfAssesmentUtrNo='" + getSelfAssesmentUtrNo() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", gender='" + getGender() + "'" +
            ", studentLoan='" + isStudentLoan() + "'" +
            ", childBenefit='" + isChildBenefit() + "'" +
            ", spouse='" + isSpouse() + "'" +
            ", findOutAboutUs='" + getFindOutAboutUs() + "'" +
            ", additionalInformation='" + getAdditionalInformation() + "'" +
            "}";
    }
}
