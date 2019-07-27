package io.accountalk.web.rest;

import io.accountalk.domain.BankDetails;
import io.accountalk.repository.BankDetailsRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link io.accountalk.domain.BankDetails}.
 */
@RestController
@RequestMapping("/api")
public class BankDetailsResource {

    private final Logger log = LoggerFactory.getLogger(BankDetailsResource.class);

    private static final String ENTITY_NAME = "bankDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankDetailsRepository bankDetailsRepository;

    public BankDetailsResource(BankDetailsRepository bankDetailsRepository) {
        this.bankDetailsRepository = bankDetailsRepository;
    }

    /**
     * {@code POST  /bank-details} : Create a new bankDetails.
     *
     * @param bankDetails the bankDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankDetails, or with status {@code 400 (Bad Request)} if the bankDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-details")
    public ResponseEntity<BankDetails> createBankDetails(@RequestBody BankDetails bankDetails) throws URISyntaxException {
        log.debug("REST request to save BankDetails : {}", bankDetails);
        if (bankDetails.getId() != null) {
            throw new BadRequestAlertException("A new bankDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankDetails result = bankDetailsRepository.save(bankDetails);
        return ResponseEntity.created(new URI("/api/bank-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bank-details} : Updates an existing bankDetails.
     *
     * @param bankDetails the bankDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankDetails,
     * or with status {@code 400 (Bad Request)} if the bankDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-details")
    public ResponseEntity<BankDetails> updateBankDetails(@RequestBody BankDetails bankDetails) throws URISyntaxException {
        log.debug("REST request to update BankDetails : {}", bankDetails);
        if (bankDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BankDetails result = bankDetailsRepository.save(bankDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bank-details} : get all the bankDetails.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankDetails in body.
     */
    @GetMapping("/bank-details")
    public List<BankDetails> getAllBankDetails(@RequestParam(required = false) String filter) {
        if ("taxreturn-is-null".equals(filter)) {
            log.debug("REST request to get all BankDetailss where taxReturn is null");
            return StreamSupport
                .stream(bankDetailsRepository.findAll().spliterator(), false)
                .filter(bankDetails -> bankDetails.getTaxReturn() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all BankDetails");
        return bankDetailsRepository.findAll();
    }

    /**
     * {@code GET  /bank-details/:id} : get the "id" bankDetails.
     *
     * @param id the id of the bankDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-details/{id}")
    public ResponseEntity<BankDetails> getBankDetails(@PathVariable Long id) {
        log.debug("REST request to get BankDetails : {}", id);
        Optional<BankDetails> bankDetails = bankDetailsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bankDetails);
    }

    /**
     * {@code DELETE  /bank-details/:id} : delete the "id" bankDetails.
     *
     * @param id the id of the bankDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-details/{id}")
    public ResponseEntity<Void> deleteBankDetails(@PathVariable Long id) {
        log.debug("REST request to delete BankDetails : {}", id);
        bankDetailsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
