package com.hostfullystay.app.controller;

import com.hostfullystay.app.domain.Booking;
import com.hostfullystay.app.repository.BookingRepository;
import com.hostfullystay.app.service.BookingService;
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
 * Controller class for handling HTTP requests related to Booking entities.
 *
 * @author Sakthidharan Ashwin
 */
@RestController
@RequestMapping("/v1/api/bookings")
public class BookingController {

    /**
     * Service for handling business logic related to Booking entities.
     */
    private final BookingService bookingService;

    /**
     * Repository for accessing and manipulating Booking entities in the database.
     */
    private final BookingRepository bookingRepository;

    /**
     * Constructor for BookingController.
     *
     * @param bookingService    Service for handling business logic related to Booking entities.
     * @param bookingRepository Repository for accessing and manipulating Booking entities in the database.
     */
    public BookingController(BookingService bookingService, BookingRepository bookingRepository) {
        this.bookingService = bookingService;
        this.bookingRepository = bookingRepository;
    }

    /**
     * Handles HTTP POST requests to create a new Booking entity.
     *
     * @param booking The Booking entity to be created. Must be a valid and non-existing entity.
     * @return ResponseEntity with the created Booking entity and HTTP status 201 (Created).
     * @throws URISyntaxException If the URI syntax is incorrect.
     */
    @PostMapping
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody Booking booking) throws URISyntaxException {
        if (booking.getBookingId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new block cannot already have an ID");
        }

        Booking result = bookingService.save(booking);

        if (result == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "The booking can't be scheduled in that date range");
        }

        return ResponseEntity
                .created(new URI("/v1/api/bookings/" + result.getBookingId()))
                .body(result);
    }

    /**
     * Handles HTTP PUT requests to update an existing Booking entity.
     *
     * @param id      The ID of the Booking entity to be updated.
     * @param booking The updated Booking entity.
     * @return ResponseEntity with the updated Booking entity and HTTP status 200 (OK).
     * @throws URISyntaxException If the URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody Booking booking
    ) throws URISyntaxException {
        if (booking.getBookingId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!Objects.equals(id, booking.getBookingId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!bookingRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found");
        }

        Booking result = bookingService.update(booking);
        if (result == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "The booking can't be scheduled in that date range");
        }

        return ResponseEntity
                .ok()
                .body(result);
    }

    /**
     * Handles HTTP PATCH requests to partially update an existing Booking entity.
     *
     * @param id      The ID of the Booking entity to be partially updated.
     * @param booking The partial update data for the Booking entity.
     * @return ResponseEntity with the partially updated Booking entity and HTTP status 200 (OK).
     * @throws URISyntaxException If the URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<Booking> partialUpdateBooking(
            @PathVariable(value = "id", required = false) final Long id,
            @NotNull @RequestBody Booking booking
    ) throws URISyntaxException {
        if (booking.getBookingId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!Objects.equals(id, booking.getBookingId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!bookingRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found");
        }

        Optional<Booking> result = bookingService.partialUpdate(booking);

        return result
                .map((response) -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The booking can't be scheduled in that date range"));
    }

    /**
     * Handles HTTP GET requests to retrieve all Booking entities.
     *
     * @return ResponseEntity with a list of all Booking entities and HTTP status 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> list = bookingService.findAll();
        return ResponseEntity.ok().body(list);
    }

    /**
     * Handles HTTP GET requests to retrieve the count of Booking entities.
     *
     * @return ResponseEntity with the count of Booking entities and HTTP status 200 (OK).
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countBookings() {
        return ResponseEntity.ok().body(bookingService.count());
    }

    /**
     * Handles HTTP GET requests to retrieve a specific Booking entity by ID.
     *
     * @param id The ID of the Booking entity to be retrieved.
     * @return ResponseEntity with the retrieved Booking entity and HTTP status 200 (OK).
     * @throws ResponseStatusException If the entity is not found, HTTP status 404 (Not Found) is returned.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable Long id) {
        Optional<Booking> booking = bookingService.findOne(id);
        return booking
                .map((response) -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /**
     * Handles HTTP DELETE requests to delete a specific Booking entity by ID.
     *
     * @param id The ID of the Booking entity to be deleted.
     * @return ResponseEntity with no content and HTTP status 204 (No Content).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
