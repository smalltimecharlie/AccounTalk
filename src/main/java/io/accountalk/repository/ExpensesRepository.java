package io.accountalk.repository;

import io.accountalk.domain.Expenses;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Expenses entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExpensesRepository extends JpaRepository<Expenses, Long> {

}
