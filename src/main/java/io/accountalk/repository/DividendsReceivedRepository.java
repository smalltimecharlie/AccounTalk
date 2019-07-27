package io.accountalk.repository;

import io.accountalk.domain.DividendsReceived;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DividendsReceived entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DividendsReceivedRepository extends JpaRepository<DividendsReceived, Long> {

}
