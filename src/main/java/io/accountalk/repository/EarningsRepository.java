package io.accountalk.repository;

import io.accountalk.domain.Earnings;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Earnings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EarningsRepository extends JpaRepository<Earnings, Long> {

}
