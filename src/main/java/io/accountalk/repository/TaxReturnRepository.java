package io.accountalk.repository;

import io.accountalk.domain.TaxReturn;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TaxReturn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaxReturnRepository extends JpaRepository<TaxReturn, Long> {

}
