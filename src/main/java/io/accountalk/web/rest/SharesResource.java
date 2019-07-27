package io.accountalk.web.rest;

import io.accountalk.domain.Shares;
import io.accountalk.repository.SharesRepository;
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
 * REST controller for managing {@link io.accountalk.domain.Shares}.
 */
@RestController
@RequestMapping("/api")
public class SharesResource {

    private final Logger log = LoggerFactory.getLogger(SharesResource.class);

    private static final String ENTITY_NAME = "shares";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SharesRepository sharesRepository;

    public SharesResource(SharesRepository sharesRepository) {
        this.sharesRepository = sharesRepository;
    }

    /**
     * {@code POST  /shares} : Create a new shares.
     *
     * @param shares the shares to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shares, or with status {@code 400 (Bad Request)} if the shares has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shares")
    public ResponseEntity<Shares> createShares(@RequestBody Shares shares) throws URISyntaxException {
        log.debug("REST request to save Shares : {}", shares);
        if (shares.getId() != null) {
            throw new BadRequestAlertException("A new shares cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Shares result = sharesRepository.save(shares);
        return ResponseEntity.created(new URI("/api/shares/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shares} : Updates an existing shares.
     *
     * @param shares the shares to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shares,
     * or with status {@code 400 (Bad Request)} if the shares is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shares couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shares")
    public ResponseEntity<Shares> updateShares(@RequestBody Shares shares) throws URISyntaxException {
        log.debug("REST request to update Shares : {}", shares);
        if (shares.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Shares result = sharesRepository.save(shares);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shares.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /shares} : get all the shares.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shares in body.
     */
    @GetMapping("/shares")
    public List<Shares> getAllShares() {
        log.debug("REST request to get all Shares");
        return sharesRepository.findAll();
    }

    /**
     * {@code GET  /shares/:id} : get the "id" shares.
     *
     * @param id the id of the shares to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shares, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shares/{id}")
    public ResponseEntity<Shares> getShares(@PathVariable Long id) {
        log.debug("REST request to get Shares : {}", id);
        Optional<Shares> shares = sharesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(shares);
    }

    /**
     * {@code DELETE  /shares/:id} : delete the "id" shares.
     *
     * @param id the id of the shares to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shares/{id}")
    public ResponseEntity<Void> deleteShares(@PathVariable Long id) {
        log.debug("REST request to delete Shares : {}", id);
        sharesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
