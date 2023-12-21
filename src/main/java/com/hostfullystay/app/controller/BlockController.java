package com.hostfullystay.app.controller;

import com.hostfullystay.app.domain.Block;
import com.hostfullystay.app.repository.BlockRepository;
import com.hostfullystay.app.service.BlockService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Controller class for handling HTTP requests related to Block entities.
 *
 * @author Sakthidharan Ashwin
 */
@RestController
@RequestMapping("/v1/api/blocks")
public class BlockController {

    /**
     * Service for handling business logic related to Block entities.
     */
    private final BlockService blockService;

    /**
     * Repository for accessing and manipulating Block entities in the database.
     */
    private final BlockRepository blockRepository;

    /**
     * Constructor for BlockController.
     *
     * @param blockService    Service for handling business logic related to Block entities.
     * @param blockRepository Repository for accessing and manipulating Block entities in the database.
     */
    public BlockController(BlockService blockService, BlockRepository blockRepository) {
        this.blockService = blockService;
        this.blockRepository = blockRepository;
    }

    /**
     * Handles HTTP POST requests to create a new Block entity.
     *
     * @param block The Block entity to be created. Must be a valid and non-existing entity.
     * @return ResponseEntity with the created Block entity and HTTP status 201 (Created).
     * @throws URISyntaxException If the URI syntax is incorrect.
     */
    @PostMapping
    public ResponseEntity<Block> createBlock(@Valid @RequestBody Block block) throws URISyntaxException {
        if (block.getBlockId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new block cannot already have an ID");
        }
        Block result = blockService.save(block);

        if (result == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "The block can't be scheduled in that date range");
        }

        return ResponseEntity
                .created(new URI("/v1/api/blocks/" + result.getBlockId()))
                .body(result);
    }

    /**
     * Handles HTTP PUT requests to update an existing Block entity.
     *
     * @param id    The ID of the Block entity to be updated.
     * @param block The updated Block entity.
     * @return ResponseEntity with the updated Block entity and HTTP status 200 (OK).
     * @throws URISyntaxException If the URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Block> updateBlock(
            @PathVariable final Long id,
            @Valid @RequestBody Block block
    ) throws URISyntaxException {
        if (block.getBlockId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!Objects.equals(id, block.getBlockId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!blockRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found");
        }

        Block result = blockService.update(block);
        if (result == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "The block can't be scheduled in that date range");
        }

        return ResponseEntity
                .ok()
                .body(result);
    }

    /**
     * Handles HTTP PATCH requests to partially update an existing Block entity.
     *
     * @param id    The ID of the Block entity to be partially updated.
     * @param block The partial update data for the Block entity.
     * @return ResponseEntity with the partially updated Block entity and HTTP status 200 (OK).
     * @throws URISyntaxException If the URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<Block> partialUpdateBlock(
            @PathVariable final Long id,
            @NotNull @RequestBody Block block
    ) throws URISyntaxException {
        if (block.getBlockId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!Objects.equals(id, block.getBlockId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!blockRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found");
        }

        Optional<Block> result = blockService.partialUpdate(block);

        return result
                .map((response) -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The block can't be scheduled in that date range"));
    }

    /**
     * Handles HTTP GET requests to retrieve all Block entities.
     *
     * @return ResponseEntity with a list of all Block entities and HTTP status 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<Block>> getAllBlocks() {
        List<Block> list = blockService.findAll();
        return ResponseEntity.ok().body(list);
    }

    /**
     * Handles HTTP GET requests to retrieve the count of Block entities.
     *
     * @return ResponseEntity with the count of Block entities and HTTP status 200 (OK).
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countBlocks() {
        return ResponseEntity.ok().body(blockService.count());
    }

    /**
     * Handles HTTP GET requests to retrieve a specific Block entity by ID.
     *
     * @param id The ID of the Block entity to be retrieved.
     * @return ResponseEntity with the retrieved Block entity and HTTP status 200 (OK).
     * @throws ResponseStatusException If the entity is not found, HTTP status 404 (Not Found) is returned.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Block> getBlock(@PathVariable Long id) {
        Optional<Block> block = blockService.findOne(id);
        return block
                .map((response) -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /**
     * Handles HTTP DELETE requests to delete a specific Block entity by ID.
     *
     * @param id The ID of the Block entity to be deleted.
     * @return ResponseEntity with no content and HTTP status 204 (No Content).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlock(@PathVariable Long id) {
        blockService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
