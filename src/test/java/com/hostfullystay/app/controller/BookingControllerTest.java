package com.hostfullystay.app.controller;

import com.hostfullystay.app.domain.Booking;
import com.hostfullystay.app.repository.BookingRepository;
import com.hostfullystay.app.service.BookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingController bookingController;

    private Booking createValidBooking() {
        Booking validBooking = new Booking();
        validBooking.setGuestName("John Doe");
        validBooking.setDateFrom(LocalDate.of(2023, 1, 1));
        validBooking.setDateTo(LocalDate.of(2023, 2, 1));
        return validBooking;
    }

    @Test
    void createBooking() throws URISyntaxException {
        Booking booking = new Booking();

        when(bookingService.save(Mockito.any(Booking.class))).thenReturn(booking);

        ResponseEntity<Booking> responseEntity = bookingController.createBooking(booking);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void createBookingWithInvalidId() {
        Booking booking = new Booking();
        booking.setBookingId(1L);

        assertThrows(ResponseStatusException.class, () -> bookingController.createBooking(booking));
    }

    @Test
    void updateBooking() throws URISyntaxException {
        Booking booking = new Booking();
        booking.setBookingId(1L);

        when(bookingRepository.existsById(1L)).thenReturn(true);
        when(bookingService.update(Mockito.any(Booking.class))).thenReturn(booking);
        ResponseEntity<Booking> responseEntity = bookingController.updateBooking(1L, booking);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

    }

    @Test
    void updateBookingWithInvalidId() {
        Booking booking = new Booking();
        booking.setBookingId(1L);

        assertThrows(ResponseStatusException.class, () -> bookingController.updateBooking(2L, booking));
    }

    @Test
    void partialUpdateBooking() throws URISyntaxException {
        Booking booking = new Booking();
        booking.setBookingId(1L);

        when(bookingRepository.existsById(1L)).thenReturn(true);
        when(bookingService.partialUpdate(Mockito.any(Booking.class))).thenReturn(Optional.of(booking));

        ResponseEntity<Booking> responseEntity = bookingController.partialUpdateBooking(1L, booking);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void partialUpdateBookingWithInvalidId() {
        Booking booking = new Booking();
        booking.setBookingId(1L);

        assertThrows(ResponseStatusException.class, () -> bookingController.partialUpdateBooking(2L, booking));
    }

    @Test
    void getAllBookings() {
        List<Booking> bookingList = Arrays.asList(new Booking(), new Booking());
        when(bookingService.findAll()).thenReturn(bookingList);

        ResponseEntity<List<Booking>> responseEntity = bookingController.getAllBookings();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(bookingList, responseEntity.getBody());

    }

    @Test
    void countBookings() {
        when(bookingService.count()).thenReturn(5L);

        ResponseEntity<Long> responseEntity = bookingController.countBookings();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(5L, responseEntity.getBody());
    }

    @Test
    void getBooking() {
        Booking booking = new Booking();
        booking.setBookingId(1L);

        when(bookingService.findOne(1L)).thenReturn(Optional.of(booking));

        ResponseEntity<Booking> responseEntity = bookingController.getBooking(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void getBookingWithInvalidId() {
        when(bookingService.findOne(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> bookingController.getBooking(1L));
    }

    @Test
    void deleteBooking() {
        assertDoesNotThrow(() -> bookingController.deleteBooking(1L));
    }
}
