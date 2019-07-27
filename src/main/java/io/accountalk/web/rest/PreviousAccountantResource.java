package io.accountalk.web.rest;

import io.accountalk.domain.PreviousAccountant;
import io.accountalk.repository.PreviousAccountantRepository;
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
 * REST controller for managing {@link io.accountalk.domain.PreviousAccountant}.
 */
@RestController
@RequestMapping("/api")
public class PreviousAccountantResource {

    private final Logger log = LoggerFactory.getLogger(PreviousAccountantResource.class);

    private static final String ENTITY_NAME = "previousAccountant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PreviousAccountantRepository previousAccountantRepository;

    public PreviousAccountantResource(PreviousAccountantRepository previousAccountantRepository) {
        this.previousAccountantRepository = previousAccountantRepository;
    }

    /**
     * {@code POST  /previous-accountants} : Create a new previousAccountant.
     *
     * @param previousAccountant the previousAccountant to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new previousAccountant, or with status {@code 400 (Bad Request)} if the previousAccountant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/previous-accountants")
    public ResponseEntity<PreviousAccountant> createPreviousAccountant(@RequestBody PreviousAccountant previousAccountant) throws URISyntaxException {
        log.debug("REST request to save PreviousAccountant : {}", previousAccountant);
        if (previousAccountant.getId() != null) {
            throw new BadRequestAlertException("A new previousAccountant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PreviousAccountant result = previousAccountantRepository.save(previousAccountant);
        return ResponseEntity.created(new URI("/api/previous-accountants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /previous-accountants} : Updates an existing previousAccountant.
     *
     * @param previousAccountant the previousAccountant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated previousAccountant,
     * or with status {@code 400 (Bad Request)} if the previousAccountant is not valid,
     * or with status {@code 500 (Internal Server Error)} if the previousAccountant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/previous-accountants")
    public ResponseEntity<PreviousAccountant> updatePreviousAccountant(@RequestBody PreviousAccountant previousAccountant) throws URISyntaxException {
        log.debug("REST request to update PreviousAccountant : {}", previousAccountant);
        if (previousAccountant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PreviousAccountant result = previousAccountantRepository.save(previousAccountant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, previousAccountant.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /previous-accountants} : get all the previousAccountants.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of previousAccountants in body.
     */
    @GetMapping("/previous-accountants")
    public List<PreviousAccountant> getAllPreviousAccountants() {
        log.debug("REST request to get all PreviousAccountants");
        return previousAccountantRepository.findAll();
    }

    /**
     * {@code GET  /previous-accountants/:id} : get the "id" previousAccountant.
     *
     * @param id the id of the previousAccountant to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the previousAccountant, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/previous-accountants/{id}")
    public ResponseEntity<PreviousAccountant> getPreviousAccountant(@PathVariable Long id) {
        log.debug("REST request to get PreviousAccountant : {}", id);
        Optional<PreviousAccountant> previousAccountant = previousAccountantRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(previousAccountant);
    }

    /**
     * {@code DELETE  /previous-accountants/:id} : delete the "id" previousAccountant.
     *
     * @param id the id of the previousAccountant to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/previous-accountants/{id}")
    public ResponseEntity<Void> deletePreviousAccountant(@PathVariable Long id) {
        log.debug("REST request to delete PreviousAccountant : {}", id);
        previousAccountantRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
