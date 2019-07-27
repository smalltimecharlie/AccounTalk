package io.accountalk.repository;

import io.accountalk.domain.PerPensionContributions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PerPensionContributions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PerPensionContributionsRepository extends JpaRepository<PerPensionContributions, Long> {

}
