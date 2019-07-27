package io.accountalk.repository;

import io.accountalk.domain.PensionProvider;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PensionProvider entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PensionProviderRepository extends JpaRepository<PensionProvider, Long> {

}
