package io.accountalk.repository;

import io.accountalk.domain.PensionReceived;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PensionReceived entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PensionReceivedRepository extends JpaRepository<PensionReceived, Long> {

}
