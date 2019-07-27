package io.accountalk.web.rest;

import io.accountalk.domain.EisInvestments;
import io.accountalk.repository.EisInvestmentsRepository;
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
 * REST controller for managing {@link io.accountalk.domain.EisInvestments}.
 */
@RestController
@RequestMapping("/api")
public class EisInvestmentsResource {

    private final Logger log = LoggerFactory.getLogger(EisInvestmentsResource.class);

    private static final String ENTITY_NAME = "eisInvestments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EisInvestmentsRepository eisInvestmentsRepository;

    public EisInvestmentsResource(EisInvestmentsRepository eisInvestmentsRepository) {
        this.eisInvestmentsRepository = eisInvestmentsRepository;
    }

    /**
     * {@code POST  /eis-investments} : Create a new eisInvestments.
     *
     * @param eisInvestments the eisInvestments to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eisInvestments, or with status {@code 400 (Bad Request)} if the eisInvestments has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/eis-investments")
    public ResponseEntity<EisInvestments> createEisInvestments(@RequestBody EisInvestments eisInvestments) throws URISyntaxException {
        log.debug("REST request to save EisInvestments : {}", eisInvestments);
        if (eisInvestments.getId() != null) {
            throw new BadRequestAlertException("A new eisInvestments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EisInvestments result = eisInvestmentsRepository.save(eisInvestments);
        return ResponseEntity.created(new URI("/api/eis-investments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /eis-investments} : Updates an existing eisInvestments.
     *
     * @param eisInvestments the eisInvestments to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eisInvestments,
     * or with status {@code 400 (Bad Request)} if the eisInvestments is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eisInvestments couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/eis-investments")
    public ResponseEntity<EisInvestments> updateEisInvestments(@RequestBody EisInvestments eisInvestments) throws URISyntaxException {
        log.debug("REST request to update EisInvestments : {}", eisInvestments);
        if (eisInvestments.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EisInvestments result = eisInvestmentsRepository.save(eisInvestments);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eisInvestments.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /eis-investments} : get all the eisInvestments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eisInvestments in body.
     */
    @GetMapping("/eis-investments")
    public List<EisInvestments> getAllEisInvestments() {
        log.debug("REST request to get all EisInvestments");
        return eisInvestmentsRepository.findAll();
    }

    /**
     * {@code GET  /eis-investments/:id} : get the "id" eisInvestments.
     *
     * @param id the id of the eisInvestments to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eisInvestments, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/eis-investments/{id}")
    public ResponseEntity<EisInvestments> getEisInvestments(@PathVariable Long id) {
        log.debug("REST request to get EisInvestments : {}", id);
        Optional<EisInvestments> eisInvestments = eisInvestmentsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(eisInvestments);
    }

    /**
     * {@code DELETE  /eis-investments/:id} : delete the "id" eisInvestments.
     *
     * @param id the id of the eisInvestments to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/eis-investments/{id}")
    public ResponseEntity<Void> deleteEisInvestments(@PathVariable Long id) {
        log.debug("REST request to delete EisInvestments : {}", id);
        eisInvestmentsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
