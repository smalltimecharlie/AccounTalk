package io.accountalk.repository;

import io.accountalk.domain.GiftAidDonations;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the GiftAidDonations entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GiftAidDonationsRepository extends JpaRepository<GiftAidDonations, Long> {

}
