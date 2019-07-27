package io.accountalk.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Earnings.
 */
@Entity
@Table(name = "earnings")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Earnings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gross_pay")
    private Double grossPay;

    @Column(name = "tax_deducted")
    private Double taxDeducted;

    @Column(name = "student_loan_deductions")
    private Double studentLoanDeductions;

    @ManyToOne
    @JsonIgnoreProperties("earnings")
    private EmploymentDetails employmentDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getGrossPay() {
        return grossPay;
    }

    public Earnings grossPay(Double grossPay) {
        this.grossPay = grossPay;
        return this;
    }

    public void setGrossPay(Double grossPay) {
        this.grossPay = grossPay;
    }

    public Double getTaxDeducted() {
        return taxDeducted;
    }

    public Earnings taxDeducted(Double taxDeducted) {
        this.taxDeducted = taxDeducted;
        return this;
    }

    public void setTaxDeducted(Double taxDeducted) {
        this.taxDeducted = taxDeducted;
    }

    public Double getStudentLoanDeductions() {
        return studentLoanDeductions;
    }

    public Earnings studentLoanDeductions(Double studentLoanDeductions) {
        this.studentLoanDeductions = studentLoanDeductions;
        return this;
    }

    public void setStudentLoanDeductions(Double studentLoanDeductions) {
        this.studentLoanDeductions = studentLoanDeductions;
    }

    public EmploymentDetails getEmploymentDetails() {
        return employmentDetails;
    }

    public Earnings employmentDetails(EmploymentDetails employmentDetails) {
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
        if (!(o instanceof Earnings)) {
            return false;
        }
        return id != null && id.equals(((Earnings) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Earnings{" +
            "id=" + getId() +
            ", grossPay=" + getGrossPay() +
            ", taxDeducted=" + getTaxDeducted() +
            ", studentLoanDeductions=" + getStudentLoanDeductions() +
            "}";
    }
}
