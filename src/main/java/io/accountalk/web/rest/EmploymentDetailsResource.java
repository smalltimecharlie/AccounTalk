package io.accountalk.web.rest;

import io.accountalk.domain.EmploymentDetails;
import io.accountalk.repository.EmploymentDetailsRepository;
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
 * REST controller for managing {@link io.accountalk.domain.EmploymentDetails}.
 */
@RestController
@RequestMapping("/api")
public class EmploymentDetailsResource {

    private final Logger log = LoggerFactory.getLogger(EmploymentDetailsResource.class);

    private static final String ENTITY_NAME = "employmentDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmploymentDetailsRepository employmentDetailsRepository;

    public EmploymentDetailsResource(EmploymentDetailsRepository employmentDetailsRepository) {
        this.employmentDetailsRepository = employmentDetailsRepository;
    }

    /**
     * {@code POST  /employment-details} : Create a new employmentDetails.
     *
     * @param employmentDetails the employmentDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employmentDetails, or with status {@code 400 (Bad Request)} if the employmentDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employment-details")
    public ResponseEntity<EmploymentDetails> createEmploymentDetails(@RequestBody EmploymentDetails employmentDetails) throws URISyntaxException {
        log.debug("REST request to save EmploymentDetails : {}", employmentDetails);
        if (employmentDetails.getId() != null) {
            throw new BadRequestAlertException("A new employmentDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmploymentDetails result = employmentDetailsRepository.save(employmentDetails);
        return ResponseEntity.created(new URI("/api/employment-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employment-details} : Updates an existing employmentDetails.
     *
     * @param employmentDetails the employmentDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employmentDetails,
     * or with status {@code 400 (Bad Request)} if the employmentDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employmentDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employment-details")
    public ResponseEntity<EmploymentDetails> updateEmploymentDetails(@RequestBody EmploymentDetails employmentDetails) throws URISyntaxException {
        log.debug("REST request to update EmploymentDetails : {}", employmentDetails);
        if (employmentDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmploymentDetails result = employmentDetailsRepository.save(employmentDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employmentDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employment-details} : get all the employmentDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employmentDetails in body.
     */
    @GetMapping("/employment-details")
    public List<EmploymentDetails> getAllEmploymentDetails() {
        log.debug("REST request to get all EmploymentDetails");
        return employmentDetailsRepository.findAll();
    }

    /**
     * {@code GET  /employment-details/:id} : get the "id" employmentDetails.
     *
     * @param id the id of the employmentDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employmentDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employment-details/{id}")
    public ResponseEntity<EmploymentDetails> getEmploymentDetails(@PathVariable Long id) {
        log.debug("REST request to get EmploymentDetails : {}", id);
        Optional<EmploymentDetails> employmentDetails = employmentDetailsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(employmentDetails);
    }

    /**
     * {@code DELETE  /employment-details/:id} : delete the "id" employmentDetails.
     *
     * @param id the id of the employmentDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employment-details/{id}")
    public ResponseEntity<Void> deleteEmploymentDetails(@PathVariable Long id) {
        log.debug("REST request to delete EmploymentDetails : {}", id);
        employmentDetailsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
