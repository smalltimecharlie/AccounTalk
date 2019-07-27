package io.accountalk.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A GiftAidDonations.
 */
@Entity
@Table(name = "gift_aid_donations")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GiftAidDonations implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "charity_name")
    private String charityName;

    @Column(name = "donation_date")
    private Instant donationDate;

    @Column(name = "donation_amount")
    private Double donationAmount;

    @Column(name = "gift_aid_claimed")
    private Boolean giftAidClaimed;

    @ManyToOne
    @JsonIgnoreProperties("giftAidDonations")
    private TaxReturn taxReturn;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCharityName() {
        return charityName;
    }

    public GiftAidDonations charityName(String charityName) {
        this.charityName = charityName;
        return this;
    }

    public void setCharityName(String charityName) {
        this.charityName = charityName;
    }

    public Instant getDonationDate() {
        return donationDate;
    }

    public GiftAidDonations donationDate(Instant donationDate) {
        this.donationDate = donationDate;
        return this;
    }

    public void setDonationDate(Instant donationDate) {
        this.donationDate = donationDate;
    }

    public Double getDonationAmount() {
        return donationAmount;
    }

    public GiftAidDonations donationAmount(Double donationAmount) {
        this.donationAmount = donationAmount;
        return this;
    }

    public void setDonationAmount(Double donationAmount) {
        this.donationAmount = donationAmount;
    }

    public Boolean isGiftAidClaimed() {
        return giftAidClaimed;
    }

    public GiftAidDonations giftAidClaimed(Boolean giftAidClaimed) {
        this.giftAidClaimed = giftAidClaimed;
        return this;
    }

    public void setGiftAidClaimed(Boolean giftAidClaimed) {
        this.giftAidClaimed = giftAidClaimed;
    }

    public TaxReturn getTaxReturn() {
        return taxReturn;
    }

    public GiftAidDonations taxReturn(TaxReturn taxReturn) {
        this.taxReturn = taxReturn;
        return this;
    }

    public void setTaxReturn(TaxReturn taxReturn) {
        this.taxReturn = taxReturn;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GiftAidDonations)) {
            return false;
        }
        return id != null && id.equals(((GiftAidDonations) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "GiftAidDonations{" +
            "id=" + getId() +
            ", charityName='" + getCharityName() + "'" +
            ", donationDate='" + getDonationDate() + "'" +
            ", donationAmount=" + getDonationAmount() +
            ", giftAidClaimed='" + isGiftAidClaimed() + "'" +
            "}";
    }
}
