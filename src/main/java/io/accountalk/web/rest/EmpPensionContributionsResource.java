package io.accountalk.web.rest;

import io.accountalk.domain.EmpPensionContributions;
import io.accountalk.repository.EmpPensionContributionsRepository;
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
 * REST controller for managing {@link io.accountalk.domain.EmpPensionContributions}.
 */
@RestController
@RequestMapping("/api")
public class EmpPensionContributionsResource {

    private final Logger log = LoggerFactory.getLogger(EmpPensionContributionsResource.class);

    private static final String ENTITY_NAME = "empPensionContributions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmpPensionContributionsRepository empPensionContributionsRepository;

    public EmpPensionContributionsResource(EmpPensionContributionsRepository empPensionContributionsRepository) {
        this.empPensionContributionsRepository = empPensionContributionsRepository;
    }

    /**
     * {@code POST  /emp-pension-contributions} : Create a new empPensionContributions.
     *
     * @param empPensionContributions the empPensionContributions to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new empPensionContributions, or with status {@code 400 (Bad Request)} if the empPensionContributions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/emp-pension-contributions")
    public ResponseEntity<EmpPensionContributions> createEmpPensionContributions(@RequestBody EmpPensionContributions empPensionContributions) throws URISyntaxException {
        log.debug("REST request to save EmpPensionContributions : {}", empPensionContributions);
        if (empPensionContributions.getId() != null) {
            throw new BadRequestAlertException("A new empPensionContributions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmpPensionContributions result = empPensionContributionsRepository.save(empPensionContributions);
        return ResponseEntity.created(new URI("/api/emp-pension-contributions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /emp-pension-contributions} : Updates an existing empPensionContributions.
     *
     * @param empPensionContributions the empPensionContributions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empPensionContributions,
     * or with status {@code 400 (Bad Request)} if the empPensionContributions is not valid,
     * or with status {@code 500 (Internal Server Error)} if the empPensionContributions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/emp-pension-contributions")
    public ResponseEntity<EmpPensionContributions> updateEmpPensionContributions(@RequestBody EmpPensionContributions empPensionContributions) throws URISyntaxException {
        log.debug("REST request to update EmpPensionContributions : {}", empPensionContributions);
        if (empPensionContributions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmpPensionContributions result = empPensionContributionsRepository.save(empPensionContributions);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, empPensionContributions.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /emp-pension-contributions} : get all the empPensionContributions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of empPensionContributions in body.
     */
    @GetMapping("/emp-pension-contributions")
    public List<EmpPensionContributions> getAllEmpPensionContributions() {
        log.debug("REST request to get all EmpPensionContributions");
        return empPensionContributionsRepository.findAll();
    }

    /**
     * {@code GET  /emp-pension-contributions/:id} : get the "id" empPensionContributions.
     *
     * @param id the id of the empPensionContributions to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the empPensionContributions, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/emp-pension-contributions/{id}")
    public ResponseEntity<EmpPensionContributions> getEmpPensionContributions(@PathVariable Long id) {
        log.debug("REST request to get EmpPensionContributions : {}", id);
        Optional<EmpPensionContributions> empPensionContributions = empPensionContributionsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(empPensionContributions);
    }

    /**
     * {@code DELETE  /emp-pension-contributions/:id} : delete the "id" empPensionContributions.
     *
     * @param id the id of the empPensionContributions to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/emp-pension-contributions/{id}")
    public ResponseEntity<Void> deleteEmpPensionContributions(@PathVariable Long id) {
        log.debug("REST request to delete EmpPensionContributions : {}", id);
        empPensionContributionsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
