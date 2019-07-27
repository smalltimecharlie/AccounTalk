package io.accountalk.web.rest;

import io.accountalk.domain.Employment;
import io.accountalk.repository.EmploymentRepository;
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
 * REST controller for managing {@link io.accountalk.domain.Employment}.
 */
@RestController
@RequestMapping("/api")
public class EmploymentResource {

    private final Logger log = LoggerFactory.getLogger(EmploymentResource.class);

    private static final String ENTITY_NAME = "employment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmploymentRepository employmentRepository;

    public EmploymentResource(EmploymentRepository employmentRepository) {
        this.employmentRepository = employmentRepository;
    }

    /**
     * {@code POST  /employments} : Create a new employment.
     *
     * @param employment the employment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employment, or with status {@code 400 (Bad Request)} if the employment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employments")
    public ResponseEntity<Employment> createEmployment(@RequestBody Employment employment) throws URISyntaxException {
        log.debug("REST request to save Employment : {}", employment);
        if (employment.getId() != null) {
            throw new BadRequestAlertException("A new employment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Employment result = employmentRepository.save(employment);
        return ResponseEntity.created(new URI("/api/employments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employments} : Updates an existing employment.
     *
     * @param employment the employment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employment,
     * or with status {@code 400 (Bad Request)} if the employment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employments")
    public ResponseEntity<Employment> updateEmployment(@RequestBody Employment employment) throws URISyntaxException {
        log.debug("REST request to update Employment : {}", employment);
        if (employment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Employment result = employmentRepository.save(employment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employment.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employments} : get all the employments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employments in body.
     */
    @GetMapping("/employments")
    public List<Employment> getAllEmployments() {
        log.debug("REST request to get all Employments");
        return employmentRepository.findAll();
    }

    /**
     * {@code GET  /employments/:id} : get the "id" employment.
     *
     * @param id the id of the employment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employments/{id}")
    public ResponseEntity<Employment> getEmployment(@PathVariable Long id) {
        log.debug("REST request to get Employment : {}", id);
        Optional<Employment> employment = employmentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(employment);
    }

    /**
     * {@code DELETE  /employments/:id} : delete the "id" employment.
     *
     * @param id the id of the employment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employments/{id}")
    public ResponseEntity<Void> deleteEmployment(@PathVariable Long id) {
        log.debug("REST request to delete Employment : {}", id);
        employmentRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
