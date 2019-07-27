package io.accountalk.repository;

import io.accountalk.domain.Benefits;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Benefits entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BenefitsRepository extends JpaRepository<Benefits, Long> {

}
