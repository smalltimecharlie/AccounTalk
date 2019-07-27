package io.accountalk.web.rest;

import io.accountalk.domain.GiftAidDonations;
import io.accountalk.repository.GiftAidDonationsRepository;
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
 * REST controller for managing {@link io.accountalk.domain.GiftAidDonations}.
 */
@RestController
@RequestMapping("/api")
public class GiftAidDonationsResource {

    private final Logger log = LoggerFactory.getLogger(GiftAidDonationsResource.class);

    private static final String ENTITY_NAME = "giftAidDonations";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GiftAidDonationsRepository giftAidDonationsRepository;

    public GiftAidDonationsResource(GiftAidDonationsRepository giftAidDonationsRepository) {
        this.giftAidDonationsRepository = giftAidDonationsRepository;
    }

    /**
     * {@code POST  /gift-aid-donations} : Create a new giftAidDonations.
     *
     * @param giftAidDonations the giftAidDonations to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new giftAidDonations, or with status {@code 400 (Bad Request)} if the giftAidDonations has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gift-aid-donations")
    public ResponseEntity<GiftAidDonations> createGiftAidDonations(@RequestBody GiftAidDonations giftAidDonations) throws URISyntaxException {
        log.debug("REST request to save GiftAidDonations : {}", giftAidDonations);
        if (giftAidDonations.getId() != null) {
            throw new BadRequestAlertException("A new giftAidDonations cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GiftAidDonations result = giftAidDonationsRepository.save(giftAidDonations);
        return ResponseEntity.created(new URI("/api/gift-aid-donations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gift-aid-donations} : Updates an existing giftAidDonations.
     *
     * @param giftAidDonations the giftAidDonations to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated giftAidDonations,
     * or with status {@code 400 (Bad Request)} if the giftAidDonations is not valid,
     * or with status {@code 500 (Internal Server Error)} if the giftAidDonations couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gift-aid-donations")
    public ResponseEntity<GiftAidDonations> updateGiftAidDonations(@RequestBody GiftAidDonations giftAidDonations) throws URISyntaxException {
        log.debug("REST request to update GiftAidDonations : {}", giftAidDonations);
        if (giftAidDonations.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GiftAidDonations result = giftAidDonationsRepository.save(giftAidDonations);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, giftAidDonations.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /gift-aid-donations} : get all the giftAidDonations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of giftAidDonations in body.
     */
    @GetMapping("/gift-aid-donations")
    public List<GiftAidDonations> getAllGiftAidDonations() {
        log.debug("REST request to get all GiftAidDonations");
        return giftAidDonationsRepository.findAll();
    }

    /**
     * {@code GET  /gift-aid-donations/:id} : get the "id" giftAidDonations.
     *
     * @param id the id of the giftAidDonations to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the giftAidDonations, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gift-aid-donations/{id}")
    public ResponseEntity<GiftAidDonations> getGiftAidDonations(@PathVariable Long id) {
        log.debug("REST request to get GiftAidDonations : {}", id);
        Optional<GiftAidDonations> giftAidDonations = giftAidDonationsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(giftAidDonations);
    }

    /**
     * {@code DELETE  /gift-aid-donations/:id} : delete the "id" giftAidDonations.
     *
     * @param id the id of the giftAidDonations to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gift-aid-donations/{id}")
    public ResponseEntity<Void> deleteGiftAidDonations(@PathVariable Long id) {
        log.debug("REST request to delete GiftAidDonations : {}", id);
        giftAidDonationsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
