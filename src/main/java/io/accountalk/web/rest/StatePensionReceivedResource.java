package io.accountalk.web.rest;

import io.accountalk.domain.StatePensionReceived;
import io.accountalk.repository.StatePensionReceivedRepository;
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
 * REST controller for managing {@link io.accountalk.domain.StatePensionReceived}.
 */
@RestController
@RequestMapping("/api")
public class StatePensionReceivedResource {

    private final Logger log = LoggerFactory.getLogger(StatePensionReceivedResource.class);

    private static final String ENTITY_NAME = "statePensionReceived";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatePensionReceivedRepository statePensionReceivedRepository;

    public StatePensionReceivedResource(StatePensionReceivedRepository statePensionReceivedRepository) {
        this.statePensionReceivedRepository = statePensionReceivedRepository;
    }

    /**
     * {@code POST  /state-pension-receiveds} : Create a new statePensionReceived.
     *
     * @param statePensionReceived the statePensionReceived to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new statePensionReceived, or with status {@code 400 (Bad Request)} if the statePensionReceived has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/state-pension-receiveds")
    public ResponseEntity<StatePensionReceived> createStatePensionReceived(@RequestBody StatePensionReceived statePensionReceived) throws URISyntaxException {
        log.debug("REST request to save StatePensionReceived : {}", statePensionReceived);
        if (statePensionReceived.getId() != null) {
            throw new BadRequestAlertException("A new statePensionReceived cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StatePensionReceived result = statePensionReceivedRepository.save(statePensionReceived);
        return ResponseEntity.created(new URI("/api/state-pension-receiveds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /state-pension-receiveds} : Updates an existing statePensionReceived.
     *
     * @param statePensionReceived the statePensionReceived to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statePensionReceived,
     * or with status {@code 400 (Bad Request)} if the statePensionReceived is not valid,
     * or with status {@code 500 (Internal Server Error)} if the statePensionReceived couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/state-pension-receiveds")
    public ResponseEntity<StatePensionReceived> updateStatePensionReceived(@RequestBody StatePensionReceived statePensionReceived) throws URISyntaxException {
        log.debug("REST request to update StatePensionReceived : {}", statePensionReceived);
        if (statePensionReceived.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StatePensionReceived result = statePensionReceivedRepository.save(statePensionReceived);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statePensionReceived.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /state-pension-receiveds} : get all the statePensionReceiveds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of statePensionReceiveds in body.
     */
    @GetMapping("/state-pension-receiveds")
    public List<StatePensionReceived> getAllStatePensionReceiveds() {
        log.debug("REST request to get all StatePensionReceiveds");
        return statePensionReceivedRepository.findAll();
    }

    /**
     * {@code GET  /state-pension-receiveds/:id} : get the "id" statePensionReceived.
     *
     * @param id the id of the statePensionReceived to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the statePensionReceived, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/state-pension-receiveds/{id}")
    public ResponseEntity<StatePensionReceived> getStatePensionReceived(@PathVariable Long id) {
        log.debug("REST request to get StatePensionReceived : {}", id);
        Optional<StatePensionReceived> statePensionReceived = statePensionReceivedRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(statePensionReceived);
    }

    /**
     * {@code DELETE  /state-pension-receiveds/:id} : delete the "id" statePensionReceived.
     *
     * @param id the id of the statePensionReceived to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/state-pension-receiveds/{id}")
    public ResponseEntity<Void> deleteStatePensionReceived(@PathVariable Long id) {
        log.debug("REST request to delete StatePensionReceived : {}", id);
        statePensionReceivedRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
