package io.accountalk.repository;

import io.accountalk.domain.EmpPensionContributions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EmpPensionContributions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmpPensionContributionsRepository extends JpaRepository<EmpPensionContributions, Long> {

}
