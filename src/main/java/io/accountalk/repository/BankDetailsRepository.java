package io.accountalk.repository;

import io.accountalk.domain.BankDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BankDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankDetailsRepository extends JpaRepository<BankDetails, Long> {

}
