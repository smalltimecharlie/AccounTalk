package io.accountalk.repository;

import io.accountalk.domain.Shares;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Shares entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SharesRepository extends JpaRepository<Shares, Long> {

}
