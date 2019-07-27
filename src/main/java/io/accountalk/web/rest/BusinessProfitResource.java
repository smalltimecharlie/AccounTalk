package io.accountalk.web.rest;

import io.accountalk.domain.BusinessProfit;
import io.accountalk.repository.BusinessProfitRepository;
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
 * REST controller for managing {@link io.accountalk.domain.BusinessProfit}.
 */
@RestController
@RequestMapping("/api")
public class BusinessProfitResource {

    private final Logger log = LoggerFactory.getLogger(BusinessProfitResource.class);

    private static final String ENTITY_NAME = "businessProfit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BusinessProfitRepository businessProfitRepository;

    public BusinessProfitResource(BusinessProfitRepository businessProfitRepository) {
        this.businessProfitRepository = businessProfitRepository;
    }

    /**
     * {@code POST  /business-profits} : Create a new businessProfit.
     *
     * @param businessProfit the businessProfit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new businessProfit, or with status {@code 400 (Bad Request)} if the businessProfit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/business-profits")
    public ResponseEntity<BusinessProfit> createBusinessProfit(@RequestBody BusinessProfit businessProfit) throws URISyntaxException {
        log.debug("REST request to save BusinessProfit : {}", businessProfit);
        if (businessProfit.getId() != null) {
            throw new BadRequestAlertException("A new businessProfit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BusinessProfit result = businessProfitRepository.save(businessProfit);
        return ResponseEntity.created(new URI("/api/business-profits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /business-profits} : Updates an existing businessProfit.
     *
     * @param businessProfit the businessProfit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessProfit,
     * or with status {@code 400 (Bad Request)} if the businessProfit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the businessProfit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/business-profits")
    public ResponseEntity<BusinessProfit> updateBusinessProfit(@RequestBody BusinessProfit businessProfit) throws URISyntaxException {
        log.debug("REST request to update BusinessProfit : {}", businessProfit);
        if (businessProfit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BusinessProfit result = businessProfitRepository.save(businessProfit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, businessProfit.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /business-profits} : get all the businessProfits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of businessProfits in body.
     */
    @GetMapping("/business-profits")
    public List<BusinessProfit> getAllBusinessProfits() {
        log.debug("REST request to get all BusinessProfits");
        return businessProfitRepository.findAll();
    }

    /**
     * {@code GET  /business-profits/:id} : get the "id" businessProfit.
     *
     * @param id the id of the businessProfit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the businessProfit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/business-profits/{id}")
    public ResponseEntity<BusinessProfit> getBusinessProfit(@PathVariable Long id) {
        log.debug("REST request to get BusinessProfit : {}", id);
        Optional<BusinessProfit> businessProfit = businessProfitRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(businessProfit);
    }

    /**
     * {@code DELETE  /business-profits/:id} : delete the "id" businessProfit.
     *
     * @param id the id of the businessProfit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/business-profits/{id}")
    public ResponseEntity<Void> deleteBusinessProfit(@PathVariable Long id) {
        log.debug("REST request to delete BusinessProfit : {}", id);
        businessProfitRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
