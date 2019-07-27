package io.accountalk.repository;

import io.accountalk.domain.PreviousAccountant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PreviousAccountant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PreviousAccountantRepository extends JpaRepository<PreviousAccountant, Long> {

}
