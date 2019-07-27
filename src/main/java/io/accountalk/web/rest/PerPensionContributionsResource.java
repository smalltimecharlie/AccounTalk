package io.accountalk.web.rest;

import io.accountalk.domain.PerPensionContributions;
import io.accountalk.repository.PerPensionContributionsRepository;
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
 * REST controller for managing {@link io.accountalk.domain.PerPensionContributions}.
 */
@RestController
@RequestMapping("/api")
public class PerPensionContributionsResource {

    private final Logger log = LoggerFactory.getLogger(PerPensionContributionsResource.class);

    private static final String ENTITY_NAME = "perPensionContributions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PerPensionContributionsRepository perPensionContributionsRepository;

    public PerPensionContributionsResource(PerPensionContributionsRepository perPensionContributionsRepository) {
        this.perPensionContributionsRepository = perPensionContributionsRepository;
    }

    /**
     * {@code POST  /per-pension-contributions} : Create a new perPensionContributions.
     *
     * @param perPensionContributions the perPensionContributions to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new perPensionContributions, or with status {@code 400 (Bad Request)} if the perPensionContributions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/per-pension-contributions")
    public ResponseEntity<PerPensionContributions> createPerPensionContributions(@RequestBody PerPensionContributions perPensionContributions) throws URISyntaxException {
        log.debug("REST request to save PerPensionContributions : {}", perPensionContributions);
        if (perPensionContributions.getId() != null) {
            throw new BadRequestAlertException("A new perPensionContributions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PerPensionContributions result = perPensionContributionsRepository.save(perPensionContributions);
        return ResponseEntity.created(new URI("/api/per-pension-contributions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /per-pension-contributions} : Updates an existing perPensionContributions.
     *
     * @param perPensionContributions the perPensionContributions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated perPensionContributions,
     * or with status {@code 400 (Bad Request)} if the perPensionContributions is not valid,
     * or with status {@code 500 (Internal Server Error)} if the perPensionContributions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/per-pension-contributions")
    public ResponseEntity<PerPensionContributions> updatePerPensionContributions(@RequestBody PerPensionContributions perPensionContributions) throws URISyntaxException {
        log.debug("REST request to update PerPensionContributions : {}", perPensionContributions);
        if (perPensionContributions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PerPensionContributions result = perPensionContributionsRepository.save(perPensionContributions);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, perPensionContributions.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /per-pension-contributions} : get all the perPensionContributions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of perPensionContributions in body.
     */
    @GetMapping("/per-pension-contributions")
    public List<PerPensionContributions> getAllPerPensionContributions() {
        log.debug("REST request to get all PerPensionContributions");
        return perPensionContributionsRepository.findAll();
    }

    /**
     * {@code GET  /per-pension-contributions/:id} : get the "id" perPensionContributions.
     *
     * @param id the id of the perPensionContributions to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the perPensionContributions, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/per-pension-contributions/{id}")
    public ResponseEntity<PerPensionContributions> getPerPensionContributions(@PathVariable Long id) {
        log.debug("REST request to get PerPensionContributions : {}", id);
        Optional<PerPensionContributions> perPensionContributions = perPensionContributionsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(perPensionContributions);
    }

    /**
     * {@code DELETE  /per-pension-contributions/:id} : delete the "id" perPensionContributions.
     *
     * @param id the id of the perPensionContributions to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/per-pension-contributions/{id}")
    public ResponseEntity<Void> deletePerPensionContributions(@PathVariable Long id) {
        log.debug("REST request to delete PerPensionContributions : {}", id);
        perPensionContributionsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
