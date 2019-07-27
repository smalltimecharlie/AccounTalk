package io.accountalk.repository;

import io.accountalk.domain.BusinessProfit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BusinessProfit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusinessProfitRepository extends JpaRepository<BusinessProfit, Long> {

}
