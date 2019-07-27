package io.accountalk.repository;

import io.accountalk.domain.BankInterest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BankInterest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankInterestRepository extends JpaRepository<BankInterest, Long> {

}
