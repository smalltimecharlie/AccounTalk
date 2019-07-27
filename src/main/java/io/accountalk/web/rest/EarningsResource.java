package io.accountalk.web.rest;

import io.accountalk.domain.Earnings;
import io.accountalk.repository.EarningsRepository;
import io.accountalk.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.accountalk.domain.Earnings}.
 */
@RestController
@RequestMapping("/api")
public class EarningsResource {

    private final Logger log = LoggerFactory.getLogger(EarningsResource.class);

    private static final String ENTITY_NAME = "earnings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EarningsRepository earningsRepository;

    public EarningsResource(EarningsRepository earningsRepository) {
        this.earningsRepository = earningsRepository;
    }

    /**
     * {@code POST  /earnings} : Create a new earnings.
     *
     * @param earnings the earnings to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new earnings, or with status {@code 400 (Bad Request)} if the earnings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/earnings")
    public ResponseEntity<Earnings> createEarnings(@RequestBody Earnings earnings) throws URISyntaxException {
        log.debug("REST request to save Earnings : {}", earnings);
        if (earnings.getId() != null) {
            throw new BadRequestAlertException("A new earnings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Earnings result = earningsRepository.save(earnings);
        return ResponseEntity.created(new URI("/api/earnings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /earnings} : Updates an existing earnings.
     *
     * @param earnings the earnings to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated earnings,
     * or with status {@code 400 (Bad Request)} if the earnings is not valid,
     * or with status {@code 500 (Internal Server Error)} if the earnings couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/earnings")
    public ResponseEntity<Earnings> updateEarnings(@RequestBody Earnings earnings) throws URISyntaxException {
        log.debug("REST request to update Earnings : {}", earnings);
        if (earnings.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Earnings result = earningsRepository.save(earnings);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, earnings.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /earnings} : get all the earnings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of earnings in body.
     */
    @GetMapping("/earnings")
    public List<Earnings> getAllEarnings() {
        log.debug("REST request to get all Earnings");
        return earningsRepository.findAll();
    }

    /**
     * {@code GET  /earnings/:id} : get the "id" earnings.
     *
     * @param id the id of the earnings to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the earnings, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/earnings/{id}")
    public ResponseEntity<Earnings> getEarnings(@PathVariable Long id) {
        log.debug("REST request to get Earnings : {}", id);
        Optional<Earnings> earnings = earningsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(earnings);
    }

    /**
     * {@code DELETE  /earnings/:id} : delete the "id" earnings.
     *
     * @param id the id of the earnings to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/earnings/{id}")
    public ResponseEntity<Void> deleteEarnings(@PathVariable Long id) {
        log.debug("REST request to delete Earnings : {}", id);
        earningsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
