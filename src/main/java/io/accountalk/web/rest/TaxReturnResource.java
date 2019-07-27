package io.accountalk.web.rest;

import io.accountalk.domain.TaxReturn;
import io.accountalk.repository.TaxReturnRepository;
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
 * REST controller for managing {@link io.accountalk.domain.TaxReturn}.
 */
@RestController
@RequestMapping("/api")
public class TaxReturnResource {

    private final Logger log = LoggerFactory.getLogger(TaxReturnResource.class);

    private static final String ENTITY_NAME = "taxReturn";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaxReturnRepository taxReturnRepository;

    public TaxReturnResource(TaxReturnRepository taxReturnRepository) {
        this.taxReturnRepository = taxReturnRepository;
    }

    /**
     * {@code POST  /tax-returns} : Create a new taxReturn.
     *
     * @param taxReturn the taxReturn to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taxReturn, or with status {@code 400 (Bad Request)} if the taxReturn has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tax-returns")
    public ResponseEntity<TaxReturn> createTaxReturn(@RequestBody TaxReturn taxReturn) throws URISyntaxException {
        log.debug("REST request to save TaxReturn : {}", taxReturn);
        if (taxReturn.getId() != null) {
            throw new BadRequestAlertException("A new taxReturn cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaxReturn result = taxReturnRepository.save(taxReturn);
        return ResponseEntity.created(new URI("/api/tax-returns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tax-returns} : Updates an existing taxReturn.
     *
     * @param taxReturn the taxReturn to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taxReturn,
     * or with status {@code 400 (Bad Request)} if the taxReturn is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taxReturn couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tax-returns")
    public ResponseEntity<TaxReturn> updateTaxReturn(@RequestBody TaxReturn taxReturn) throws URISyntaxException {
        log.debug("REST request to update TaxReturn : {}", taxReturn);
        if (taxReturn.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaxReturn result = taxReturnRepository.save(taxReturn);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taxReturn.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tax-returns} : get all the taxReturns.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taxReturns in body.
     */
    @GetMapping("/tax-returns")
    public List<TaxReturn> getAllTaxReturns() {
        log.debug("REST request to get all TaxReturns");
        return taxReturnRepository.findAll();
    }

    /**
     * {@code GET  /tax-returns/:id} : get the "id" taxReturn.
     *
     * @param id the id of the taxReturn to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taxReturn, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tax-returns/{id}")
    public ResponseEntity<TaxReturn> getTaxReturn(@PathVariable Long id) {
        log.debug("REST request to get TaxReturn : {}", id);
        Optional<TaxReturn> taxReturn = taxReturnRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(taxReturn);
    }

    /**
     * {@code DELETE  /tax-returns/:id} : delete the "id" taxReturn.
     *
     * @param id the id of the taxReturn to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tax-returns/{id}")
    public ResponseEntity<Void> deleteTaxReturn(@PathVariable Long id) {
        log.debug("REST request to delete TaxReturn : {}", id);
        taxReturnRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
