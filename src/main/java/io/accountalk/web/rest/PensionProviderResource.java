package io.accountalk.web.rest;

import io.accountalk.domain.PensionProvider;
import io.accountalk.repository.PensionProviderRepository;
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
 * REST controller for managing {@link io.accountalk.domain.PensionProvider}.
 */
@RestController
@RequestMapping("/api")
public class PensionProviderResource {

    private final Logger log = LoggerFactory.getLogger(PensionProviderResource.class);

    private static final String ENTITY_NAME = "pensionProvider";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PensionProviderRepository pensionProviderRepository;

    public PensionProviderResource(PensionProviderRepository pensionProviderRepository) {
        this.pensionProviderRepository = pensionProviderRepository;
    }

    /**
     * {@code POST  /pension-providers} : Create a new pensionProvider.
     *
     * @param pensionProvider the pensionProvider to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pensionProvider, or with status {@code 400 (Bad Request)} if the pensionProvider has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pension-providers")
    public ResponseEntity<PensionProvider> createPensionProvider(@RequestBody PensionProvider pensionProvider) throws URISyntaxException {
        log.debug("REST request to save PensionProvider : {}", pensionProvider);
        if (pensionProvider.getId() != null) {
            throw new BadRequestAlertException("A new pensionProvider cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PensionProvider result = pensionProviderRepository.save(pensionProvider);
        return ResponseEntity.created(new URI("/api/pension-providers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pension-providers} : Updates an existing pensionProvider.
     *
     * @param pensionProvider the pensionProvider to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pensionProvider,
     * or with status {@code 400 (Bad Request)} if the pensionProvider is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pensionProvider couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pension-providers")
    public ResponseEntity<PensionProvider> updatePensionProvider(@RequestBody PensionProvider pensionProvider) throws URISyntaxException {
        log.debug("REST request to update PensionProvider : {}", pensionProvider);
        if (pensionProvider.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PensionProvider result = pensionProviderRepository.save(pensionProvider);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pensionProvider.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pension-providers} : get all the pensionProviders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pensionProviders in body.
     */
    @GetMapping("/pension-providers")
    public List<PensionProvider> getAllPensionProviders() {
        log.debug("REST request to get all PensionProviders");
        return pensionProviderRepository.findAll();
    }

    /**
     * {@code GET  /pension-providers/:id} : get the "id" pensionProvider.
     *
     * @param id the id of the pensionProvider to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pensionProvider, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pension-providers/{id}")
    public ResponseEntity<PensionProvider> getPensionProvider(@PathVariable Long id) {
        log.debug("REST request to get PensionProvider : {}", id);
        Optional<PensionProvider> pensionProvider = pensionProviderRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pensionProvider);
    }

    /**
     * {@code DELETE  /pension-providers/:id} : delete the "id" pensionProvider.
     *
     * @param id the id of the pensionProvider to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pension-providers/{id}")
    public ResponseEntity<Void> deletePensionProvider(@PathVariable Long id) {
        log.debug("REST request to delete PensionProvider : {}", id);
        pensionProviderRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
