package io.accountalk.web.rest;

import io.accountalk.domain.BankInterest;
import io.accountalk.repository.BankInterestRepository;
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
 * REST controller for managing {@link io.accountalk.domain.BankInterest}.
 */
@RestController
@RequestMapping("/api")
public class BankInterestResource {

    private final Logger log = LoggerFactory.getLogger(BankInterestResource.class);

    private static final String ENTITY_NAME = "bankInterest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankInterestRepository bankInterestRepository;

    public BankInterestResource(BankInterestRepository bankInterestRepository) {
        this.bankInterestRepository = bankInterestRepository;
    }

    /**
     * {@code POST  /bank-interests} : Create a new bankInterest.
     *
     * @param bankInterest the bankInterest to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankInterest, or with status {@code 400 (Bad Request)} if the bankInterest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-interests")
    public ResponseEntity<BankInterest> createBankInterest(@RequestBody BankInterest bankInterest) throws URISyntaxException {
        log.debug("REST request to save BankInterest : {}", bankInterest);
        if (bankInterest.getId() != null) {
            throw new BadRequestAlertException("A new bankInterest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankInterest result = bankInterestRepository.save(bankInterest);
        return ResponseEntity.created(new URI("/api/bank-interests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bank-interests} : Updates an existing bankInterest.
     *
     * @param bankInterest the bankInterest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankInterest,
     * or with status {@code 400 (Bad Request)} if the bankInterest is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankInterest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-interests")
    public ResponseEntity<BankInterest> updateBankInterest(@RequestBody BankInterest bankInterest) throws URISyntaxException {
        log.debug("REST request to update BankInterest : {}", bankInterest);
        if (bankInterest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BankInterest result = bankInterestRepository.save(bankInterest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankInterest.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bank-interests} : get all the bankInterests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankInterests in body.
     */
    @GetMapping("/bank-interests")
    public List<BankInterest> getAllBankInterests() {
        log.debug("REST request to get all BankInterests");
        return bankInterestRepository.findAll();
    }

    /**
     * {@code GET  /bank-interests/:id} : get the "id" bankInterest.
     *
     * @param id the id of the bankInterest to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankInterest, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-interests/{id}")
    public ResponseEntity<BankInterest> getBankInterest(@PathVariable Long id) {
        log.debug("REST request to get BankInterest : {}", id);
        Optional<BankInterest> bankInterest = bankInterestRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bankInterest);
    }

    /**
     * {@code DELETE  /bank-interests/:id} : delete the "id" bankInterest.
     *
     * @param id the id of the bankInterest to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-interests/{id}")
    public ResponseEntity<Void> deleteBankInterest(@PathVariable Long id) {
        log.debug("REST request to delete BankInterest : {}", id);
        bankInterestRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
