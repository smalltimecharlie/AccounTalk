package io.accountalk.repository;

import io.accountalk.domain.StatePensionReceived;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StatePensionReceived entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatePensionReceivedRepository extends JpaRepository<StatePensionReceived, Long> {

}
