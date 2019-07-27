package io.accountalk.web.rest;

import io.accountalk.domain.PensionReceived;
import io.accountalk.repository.PensionReceivedRepository;
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
 * REST controller for managing {@link io.accountalk.domain.PensionReceived}.
 */
@RestController
@RequestMapping("/api")
public class PensionReceivedResource {

    private final Logger log = LoggerFactory.getLogger(PensionReceivedResource.class);

    private static final String ENTITY_NAME = "pensionReceived";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PensionReceivedRepository pensionReceivedRepository;

    public PensionReceivedResource(PensionReceivedRepository pensionReceivedRepository) {
        this.pensionReceivedRepository = pensionReceivedRepository;
    }

    /**
     * {@code POST  /pension-receiveds} : Create a new pensionReceived.
     *
     * @param pensionReceived the pensionReceived to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pensionReceived, or with status {@code 400 (Bad Request)} if the pensionReceived has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pension-receiveds")
    public ResponseEntity<PensionReceived> createPensionReceived(@RequestBody PensionReceived pensionReceived) throws URISyntaxException {
        log.debug("REST request to save PensionReceived : {}", pensionReceived);
        if (pensionReceived.getId() != null) {
            throw new BadRequestAlertException("A new pensionReceived cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PensionReceived result = pensionReceivedRepository.save(pensionReceived);
        return ResponseEntity.created(new URI("/api/pension-receiveds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pension-receiveds} : Updates an existing pensionReceived.
     *
     * @param pensionReceived the pensionReceived to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pensionReceived,
     * or with status {@code 400 (Bad Request)} if the pensionReceived is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pensionReceived couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pension-receiveds")
    public ResponseEntity<PensionReceived> updatePensionReceived(@RequestBody PensionReceived pensionReceived) throws URISyntaxException {
        log.debug("REST request to update PensionReceived : {}", pensionReceived);
        if (pensionReceived.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PensionReceived result = pensionReceivedRepository.save(pensionReceived);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pensionReceived.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pension-receiveds} : get all the pensionReceiveds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pensionReceiveds in body.
     */
    @GetMapping("/pension-receiveds")
    public List<PensionReceived> getAllPensionReceiveds() {
        log.debug("REST request to get all PensionReceiveds");
        return pensionReceivedRepository.findAll();
    }

    /**
     * {@code GET  /pension-receiveds/:id} : get the "id" pensionReceived.
     *
     * @param id the id of the pensionReceived to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pensionReceived, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pension-receiveds/{id}")
    public ResponseEntity<PensionReceived> getPensionReceived(@PathVariable Long id) {
        log.debug("REST request to get PensionReceived : {}", id);
        Optional<PensionReceived> pensionReceived = pensionReceivedRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pensionReceived);
    }

    /**
     * {@code DELETE  /pension-receiveds/:id} : delete the "id" pensionReceived.
     *
     * @param id the id of the pensionReceived to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pension-receiveds/{id}")
    public ResponseEntity<Void> deletePensionReceived(@PathVariable Long id) {
        log.debug("REST request to delete PensionReceived : {}", id);
        pensionReceivedRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
