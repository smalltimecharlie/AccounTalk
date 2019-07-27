package io.accountalk.web.rest;

import io.accountalk.domain.DividendsReceived;
import io.accountalk.repository.DividendsReceivedRepository;
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
 * REST controller for managing {@link io.accountalk.domain.DividendsReceived}.
 */
@RestController
@RequestMapping("/api")
public class DividendsReceivedResource {

    private final Logger log = LoggerFactory.getLogger(DividendsReceivedResource.class);

    private static final String ENTITY_NAME = "dividendsReceived";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DividendsReceivedRepository dividendsReceivedRepository;

    public DividendsReceivedResource(DividendsReceivedRepository dividendsReceivedRepository) {
        this.dividendsReceivedRepository = dividendsReceivedRepository;
    }

    /**
     * {@code POST  /dividends-receiveds} : Create a new dividendsReceived.
     *
     * @param dividendsReceived the dividendsReceived to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dividendsReceived, or with status {@code 400 (Bad Request)} if the dividendsReceived has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dividends-receiveds")
    public ResponseEntity<DividendsReceived> createDividendsReceived(@RequestBody DividendsReceived dividendsReceived) throws URISyntaxException {
        log.debug("REST request to save DividendsReceived : {}", dividendsReceived);
        if (dividendsReceived.getId() != null) {
            throw new BadRequestAlertException("A new dividendsReceived cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DividendsReceived result = dividendsReceivedRepository.save(dividendsReceived);
        return ResponseEntity.created(new URI("/api/dividends-receiveds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dividends-receiveds} : Updates an existing dividendsReceived.
     *
     * @param dividendsReceived the dividendsReceived to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dividendsReceived,
     * or with status {@code 400 (Bad Request)} if the dividendsReceived is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dividendsReceived couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dividends-receiveds")
    public ResponseEntity<DividendsReceived> updateDividendsReceived(@RequestBody DividendsReceived dividendsReceived) throws URISyntaxException {
        log.debug("REST request to update DividendsReceived : {}", dividendsReceived);
        if (dividendsReceived.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DividendsReceived result = dividendsReceivedRepository.save(dividendsReceived);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dividendsReceived.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dividends-receiveds} : get all the dividendsReceiveds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dividendsReceiveds in body.
     */
    @GetMapping("/dividends-receiveds")
    public List<DividendsReceived> getAllDividendsReceiveds() {
        log.debug("REST request to get all DividendsReceiveds");
        return dividendsReceivedRepository.findAll();
    }

    /**
     * {@code GET  /dividends-receiveds/:id} : get the "id" dividendsReceived.
     *
     * @param id the id of the dividendsReceived to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dividendsReceived, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dividends-receiveds/{id}")
    public ResponseEntity<DividendsReceived> getDividendsReceived(@PathVariable Long id) {
        log.debug("REST request to get DividendsReceived : {}", id);
        Optional<DividendsReceived> dividendsReceived = dividendsReceivedRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dividendsReceived);
    }

    /**
     * {@code DELETE  /dividends-receiveds/:id} : delete the "id" dividendsReceived.
     *
     * @param id the id of the dividendsReceived to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dividends-receiveds/{id}")
    public ResponseEntity<Void> deleteDividendsReceived(@PathVariable Long id) {
        log.debug("REST request to delete DividendsReceived : {}", id);
        dividendsReceivedRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
