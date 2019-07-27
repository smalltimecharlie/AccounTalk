package io.accountalk.repository;

import io.accountalk.domain.EisInvestments;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EisInvestments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EisInvestmentsRepository extends JpaRepository<EisInvestments, Long> {

}
