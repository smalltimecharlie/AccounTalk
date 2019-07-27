package io.accountalk.web.rest;

import io.accountalk.domain.Benefits;
import io.accountalk.repository.BenefitsRepository;
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
 * REST controller for managing {@link io.accountalk.domain.Benefits}.
 */
@RestController
@RequestMapping("/api")
public class BenefitsResource {

    private final Logger log = LoggerFactory.getLogger(BenefitsResource.class);

    private static final String ENTITY_NAME = "benefits";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BenefitsRepository benefitsRepository;

    public BenefitsResource(BenefitsRepository benefitsRepository) {
        this.benefitsRepository = benefitsRepository;
    }

    /**
     * {@code POST  /benefits} : Create a new benefits.
     *
     * @param benefits the benefits to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new benefits, or with status {@code 400 (Bad Request)} if the benefits has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/benefits")
    public ResponseEntity<Benefits> createBenefits(@RequestBody Benefits benefits) throws URISyntaxException {
        log.debug("REST request to save Benefits : {}", benefits);
        if (benefits.getId() != null) {
            throw new BadRequestAlertException("A new benefits cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Benefits result = benefitsRepository.save(benefits);
        return ResponseEntity.created(new URI("/api/benefits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /benefits} : Updates an existing benefits.
     *
     * @param benefits the benefits to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated benefits,
     * or with status {@code 400 (Bad Request)} if the benefits is not valid,
     * or with status {@code 500 (Internal Server Error)} if the benefits couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/benefits")
    public ResponseEntity<Benefits> updateBenefits(@RequestBody Benefits benefits) throws URISyntaxException {
        log.debug("REST request to update Benefits : {}", benefits);
        if (benefits.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Benefits result = benefitsRepository.save(benefits);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, benefits.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /benefits} : get all the benefits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of benefits in body.
     */
    @GetMapping("/benefits")
    public List<Benefits> getAllBenefits() {
        log.debug("REST request to get all Benefits");
        return benefitsRepository.findAll();
    }

    /**
     * {@code GET  /benefits/:id} : get the "id" benefits.
     *
     * @param id the id of the benefits to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the benefits, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/benefits/{id}")
    public ResponseEntity<Benefits> getBenefits(@PathVariable Long id) {
        log.debug("REST request to get Benefits : {}", id);
        Optional<Benefits> benefits = benefitsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(benefits);
    }

    /**
     * {@code DELETE  /benefits/:id} : delete the "id" benefits.
     *
     * @param id the id of the benefits to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/benefits/{id}")
    public ResponseEntity<Void> deleteBenefits(@PathVariable Long id) {
        log.debug("REST request to delete Benefits : {}", id);
        benefitsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
