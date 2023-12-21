package com.hostfullystay.app.service;

import com.hostfullystay.app.domain.Booking;
import com.hostfullystay.app.repository.BlockRepository;
import com.hostfullystay.app.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository mockBookingRepository;
    @Mock
    private BlockRepository mockBlockRepository;

    private BookingService bookingServiceUnderTest;

    @BeforeEach
    void setUp() {
        bookingServiceUnderTest = new BookingService(mockBookingRepository, mockBlockRepository);
    }

    @Test
    void testSave() {
        // Setup
        final Booking booking = new Booking();
        booking.setBookingId(0L);
        booking.setDateFrom(LocalDate.of(2020, 1, 1));
        booking.setDateTo(LocalDate.of(2020, 1, 1));
        booking.setGuestName("guestName");
        booking.setState(Booking.State.BOOKED);

        when(mockBookingRepository.existsByDateFromGreaterThanEqualAndDateFromLessThanEqualAndState(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), Booking.State.BOOKED)).thenReturn(false);
        when(mockBookingRepository.existsByDateToGreaterThanEqualAndDateToLessThanEqualAndState(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), Booking.State.BOOKED)).thenReturn(false);
        when(mockBookingRepository.existsByDateFromLessThanEqualAndDateToGreaterThanEqualAndState(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), Booking.State.BOOKED)).thenReturn(false);

        // Configure BookingRepository.save(...).
        final Booking booking1 = new Booking();
        booking1.setBookingId(0L);
        booking1.setDateFrom(LocalDate.of(2020, 1, 1));
        booking1.setDateTo(LocalDate.of(2020, 1, 1));
        booking1.setGuestName("guestName");
        booking1.setState(Booking.State.BOOKED);
        when(mockBookingRepository.save(any(Booking.class))).thenReturn(booking1);

        // Run the test
        final Booking result = bookingServiceUnderTest.save(booking);

        // Verify the results
    }

    @Test
    void testSave_BookingRepositoryExistsByDateFromLessThanEqualAndDateToGreaterThanEqualAndStateReturnsTrue() {
        // Setup
        final Booking booking = new Booking();
        booking.setBookingId(0L);
        booking.setDateFrom(LocalDate.of(2020, 1, 1));
        booking.setDateTo(LocalDate.of(2020, 1, 1));
        booking.setGuestName("guestName");
        booking.setState(Booking.State.BOOKED);

        when(mockBookingRepository.existsByDateFromGreaterThanEqualAndDateFromLessThanEqualAndState(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), Booking.State.BOOKED)).thenReturn(false);
        when(mockBookingRepository.existsByDateToGreaterThanEqualAndDateToLessThanEqualAndState(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), Booking.State.BOOKED)).thenReturn(false);
        when(mockBookingRepository.existsByDateFromLessThanEqualAndDateToGreaterThanEqualAndState(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), Booking.State.BOOKED)).thenReturn(true);

        // Run the test
        final Booking result = bookingServiceUnderTest.save(booking);

        // Verify the results
        assertNull(result);
    }

    @Test
    void testSave_BlockRepositoryExistsByDateFromLessThanEqualAndDateToGreaterThanEqualReturnsTrue() {
        // Setup
        final Booking booking = new Booking();
        booking.setBookingId(0L);
        booking.setDateFrom(LocalDate.of(2020, 1, 1));
        booking.setDateTo(LocalDate.of(2020, 1, 1));
        booking.setGuestName("guestName");
        booking.setState(Booking.State.BOOKED);

        when(mockBlockRepository.existsByDateFromGreaterThanEqualAndDateFromLessThanEqual(LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 1))).thenReturn(false);
        when(mockBlockRepository.existsByDateToGreaterThanEqualAndDateToLessThanEqual(LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 1))).thenReturn(false);
        when(mockBlockRepository.existsByDateFromLessThanEqualAndDateToGreaterThanEqual(LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 1))).thenReturn(true);

        // Run the test
        final Booking result = bookingServiceUnderTest.save(booking);

        // Verify the results
        assertNull(result);
    }

    @Test
    void testUpdate() {
        // Setup
        final Booking booking = new Booking();
        booking.setBookingId(0L);
        booking.setDateFrom(LocalDate.of(2020, 1, 1));
        booking.setDateTo(LocalDate.of(2020, 1, 1));
        booking.setGuestName("guestName");
        booking.setState(Booking.State.BOOKED);

        when(mockBookingRepository.existsByDateFromGreaterThanEqualAndDateFromLessThanEqualAndState(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), Booking.State.BOOKED)).thenReturn(false);
        when(mockBookingRepository.existsByDateToGreaterThanEqualAndDateToLessThanEqualAndState(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), Booking.State.BOOKED)).thenReturn(false);
        when(mockBookingRepository.existsByDateFromLessThanEqualAndDateToGreaterThanEqualAndState(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), Booking.State.BOOKED)).thenReturn(false);

        // Configure BookingRepository.save(...).
        final Booking booking1 = new Booking();
        booking1.setBookingId(0L);
        booking1.setDateFrom(LocalDate.of(2020, 1, 1));
        booking1.setDateTo(LocalDate.of(2020, 1, 1));
        booking1.setGuestName("guestName");
        booking1.setState(Booking.State.BOOKED);
        when(mockBookingRepository.save(any(Booking.class))).thenReturn(booking1);

        // Run the test
        final Booking result = bookingServiceUnderTest.update(booking);

        // Verify the results
    }

    @Test
    void testUpdate_BookingRepositoryExistsByDateFromLessThanEqualAndDateToGreaterThanEqualAndStateReturnsTrue() {
        // Setup
        final Booking booking = new Booking();
        booking.setBookingId(0L);
        booking.setDateFrom(LocalDate.of(2020, 1, 1));
        booking.setDateTo(LocalDate.of(2020, 1, 1));
        booking.setGuestName("guestName");
        booking.setState(Booking.State.BOOKED);

        when(mockBookingRepository.existsByDateFromGreaterThanEqualAndDateFromLessThanEqualAndState(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), Booking.State.BOOKED)).thenReturn(false);
        when(mockBookingRepository.existsByDateToGreaterThanEqualAndDateToLessThanEqualAndState(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), Booking.State.BOOKED)).thenReturn(false);
        when(mockBookingRepository.existsByDateFromLessThanEqualAndDateToGreaterThanEqualAndState(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), Booking.State.BOOKED)).thenReturn(true);

        // Run the test
        final Booking result = bookingServiceUnderTest.update(booking);

        // Verify the results
        assertNull(result);
    }

    @Test
    void testUpdate_BlockRepositoryExistsByDateFromLessThanEqualAndDateToGreaterThanEqualReturnsTrue() {
        // Setup
        final Booking booking = new Booking();
        booking.setBookingId(0L);
        booking.setDateFrom(LocalDate.of(2020, 1, 1));
        booking.setDateTo(LocalDate.of(2020, 1, 1));
        booking.setGuestName("guestName");
        booking.setState(Booking.State.BOOKED);

        when(mockBlockRepository.existsByDateFromGreaterThanEqualAndDateFromLessThanEqual(LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 1))).thenReturn(false);
        when(mockBlockRepository.existsByDateToGreaterThanEqualAndDateToLessThanEqual(LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 1))).thenReturn(false);
        when(mockBlockRepository.existsByDateFromLessThanEqualAndDateToGreaterThanEqual(LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 1))).thenReturn(true);

        // Run the test
        final Booking result = bookingServiceUnderTest.update(booking);

        // Verify the results
        assertNull(result);
    }

    @Test
    void testPartialUpdate() {
        // Setup
        final Booking booking = new Booking();
        booking.setBookingId(0L);
        booking.setDateFrom(LocalDate.of(2020, 1, 1));
        booking.setDateTo(LocalDate.of(2020, 1, 1));
        booking.setGuestName("guestName");
        booking.setState(Booking.State.BOOKED);

        // Configure BookingRepository.findById(...).
        final Booking booking2 = new Booking();
        booking2.setBookingId(0L);
        booking2.setDateFrom(LocalDate.of(2020, 1, 1));
        booking2.setDateTo(LocalDate.of(2020, 1, 1));
        booking2.setGuestName("guestName");
        booking2.setState(Booking.State.BOOKED);
        final Optional<Booking> booking1 = Optional.of(booking2);
        when(mockBookingRepository.findById(0L)).thenReturn(booking1);

        when(mockBookingRepository.existsByDateFromGreaterThanEqualAndDateFromLessThanEqualAndState(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), Booking.State.BOOKED)).thenReturn(false);
        when(mockBookingRepository.existsByDateToGreaterThanEqualAndDateToLessThanEqualAndState(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), Booking.State.BOOKED)).thenReturn(false);
        when(mockBookingRepository.existsByDateFromLessThanEqualAndDateToGreaterThanEqualAndState(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), Booking.State.BOOKED)).thenReturn(false);
        when(mockBlockRepository.existsByDateFromGreaterThanEqualAndDateFromLessThanEqual(LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 1))).thenReturn(false);
        when(mockBlockRepository.existsByDateToGreaterThanEqualAndDateToLessThanEqual(LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 1))).thenReturn(false);
        when(mockBlockRepository.existsByDateFromLessThanEqualAndDateToGreaterThanEqual(LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 1))).thenReturn(false);

        // Configure BookingRepository.save(...).
        final Booking booking3 = new Booking();
        booking3.setBookingId(0L);
        booking3.setDateFrom(LocalDate.of(2020, 1, 1));
        booking3.setDateTo(LocalDate.of(2020, 1, 1));
        booking3.setGuestName("guestName");
        booking3.setState(Booking.State.BOOKED);
        when(mockBookingRepository.save(any(Booking.class))).thenReturn(booking3);

        // Run the test
        final Optional<Booking> result = bookingServiceUnderTest.partialUpdate(booking);

        // Verify the results
    }

    @Test
    void testPartialUpdate_BookingRepositoryFindByIdReturnsAbsent() {
        // Setup
        final Booking booking = new Booking();
        booking.setBookingId(0L);
        booking.setDateFrom(LocalDate.of(2020, 1, 1));
        booking.setDateTo(LocalDate.of(2020, 1, 1));
        booking.setGuestName("guestName");
        booking.setState(Booking.State.BOOKED);

        when(mockBookingRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        final Optional<Booking> result = bookingServiceUnderTest.partialUpdate(booking);

        // Verify the results
        assertEquals(Optional.empty(), result);
    }

    @Test
    void testPartialUpdate_BookingRepositoryExistsByDateFromLessThanEqualAndDateToGreaterThanEqualAndStateReturnsTrue() {
        // Setup
        final Booking booking = new Booking();
        booking.setBookingId(0L);
        booking.setDateFrom(LocalDate.of(2020, 1, 1));
        booking.setDateTo(LocalDate.of(2020, 1, 1));
        booking.setGuestName("guestName");
        booking.setState(Booking.State.BOOKED);

        // Configure BookingRepository.findById(...).
        final Booking booking2 = new Booking();
        booking2.setBookingId(0L);
        booking2.setDateFrom(LocalDate.of(2020, 1, 1));
        booking2.setDateTo(LocalDate.of(2020, 1, 1));
        booking2.setGuestName("guestName");
        booking2.setState(Booking.State.BOOKED);
        final Optional<Booking> booking1 = Optional.of(booking2);
        when(mockBookingRepository.findById(0L)).thenReturn(booking1);

        when(mockBookingRepository.existsByDateFromGreaterThanEqualAndDateFromLessThanEqualAndState(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), Booking.State.BOOKED)).thenReturn(false);
        when(mockBookingRepository.existsByDateToGreaterThanEqualAndDateToLessThanEqualAndState(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), Booking.State.BOOKED)).thenReturn(false);
        when(mockBookingRepository.existsByDateFromLessThanEqualAndDateToGreaterThanEqualAndState(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), Booking.State.BOOKED)).thenReturn(true);

        // Run the test
        final Optional<Booking> result = bookingServiceUnderTest.partialUpdate(booking);

        // Verify the results
        assertEquals(Optional.empty(), result);
    }

    @Test
    void testPartialUpdate_BlockRepositoryExistsByDateFromLessThanEqualAndDateToGreaterThanEqualReturnsTrue() {
        // Setup
        final Booking booking = new Booking();
        booking.setBookingId(0L);
        booking.setDateFrom(LocalDate.of(2020, 1, 1));
        booking.setDateTo(LocalDate.of(2020, 1, 1));
        booking.setGuestName("guestName");
        booking.setState(Booking.State.BOOKED);

        // Configure BookingRepository.findById(...).
        final Booking booking2 = new Booking();
        booking2.setBookingId(0L);
        booking2.setDateFrom(LocalDate.of(2020, 1, 1));
        booking2.setDateTo(LocalDate.of(2020, 1, 1));
        booking2.setGuestName("guestName");
        booking2.setState(Booking.State.BOOKED);
        final Optional<Booking> booking1 = Optional.of(booking2);
        when(mockBookingRepository.findById(0L)).thenReturn(booking1);

        when(mockBlockRepository.existsByDateFromGreaterThanEqualAndDateFromLessThanEqual(LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 1))).thenReturn(false);
        when(mockBlockRepository.existsByDateToGreaterThanEqualAndDateToLessThanEqual(LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 1))).thenReturn(false);
        when(mockBlockRepository.existsByDateFromLessThanEqualAndDateToGreaterThanEqual(LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 1))).thenReturn(true);

        // Run the test
        final Optional<Booking> result = bookingServiceUnderTest.partialUpdate(booking);

        // Verify the results
        assertEquals(Optional.empty(), result);
    }

    @Test
    void testFindAll() {
        // Setup
        // Configure BookingRepository.findAll(...).
        final Booking booking = new Booking();
        booking.setBookingId(0L);
        booking.setDateFrom(LocalDate.of(2020, 1, 1));
        booking.setDateTo(LocalDate.of(2020, 1, 1));
        booking.setGuestName("guestName");
        booking.setState(Booking.State.BOOKED);
        final List<Booking> bookings = List.of(booking);
        when(mockBookingRepository.findAll()).thenReturn(bookings);

        // Run the test
        final List<Booking> result = bookingServiceUnderTest.findAll();

        // Verify the results
    }

    @Test
    void testFindAll_BookingRepositoryReturnsNoItems() {
        // Setup
        when(mockBookingRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<Booking> result = bookingServiceUnderTest.findAll();

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void testCount() {
        // Setup
        when(mockBookingRepository.count()).thenReturn(0L);

        // Run the test
        final long result = bookingServiceUnderTest.count();

        // Verify the results
        assertEquals(0L, result);
    }

    @Test
    void testFindOne() {
        // Setup
        // Configure BookingRepository.findById(...).
        final Booking booking1 = new Booking();
        booking1.setBookingId(0L);
        booking1.setDateFrom(LocalDate.of(2020, 1, 1));
        booking1.setDateTo(LocalDate.of(2020, 1, 1));
        booking1.setGuestName("guestName");
        booking1.setState(Booking.State.BOOKED);
        final Optional<Booking> booking = Optional.of(booking1);
        when(mockBookingRepository.findById(0L)).thenReturn(booking);

        // Run the test
        final Optional<Booking> result = bookingServiceUnderTest.findOne(0L);

        // Verify the results
    }

    @Test
    void testFindOne_BookingRepositoryReturnsAbsent() {
        // Setup
        when(mockBookingRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        final Optional<Booking> result = bookingServiceUnderTest.findOne(0L);

        // Verify the results
        assertEquals(Optional.empty(), result);
    }

    @Test
    void testDelete() {
        // Setup
        // Run the test
        bookingServiceUnderTest.delete(0L);

        // Verify the results
        verify(mockBookingRepository).deleteById(0L);
    }

    @Test
    void testIsBookingValid() {
        // Setup
        final Booking booking = new Booking();
        booking.setBookingId(0L);
        booking.setDateFrom(LocalDate.of(2020, 1, 1));
        booking.setDateTo(LocalDate.of(2020, 1, 1));
        booking.setGuestName("guestName");
        booking.setState(Booking.State.BOOKED);

        when(mockBookingRepository.existsByDateFromGreaterThanEqualAndDateFromLessThanEqualAndState(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), Booking.State.BOOKED)).thenReturn(false);
        when(mockBookingRepository.existsByDateToGreaterThanEqualAndDateToLessThanEqualAndState(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), Booking.State.BOOKED)).thenReturn(false);
        when(mockBookingRepository.existsByDateFromLessThanEqualAndDateToGreaterThanEqualAndState(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), Booking.State.BOOKED)).thenReturn(false);

        // Run the test
        final boolean result = bookingServiceUnderTest.isBookingValid(booking);

        // Verify the results
        assertTrue(result);
    }

    @Test
    void testIsBookingValid_BookingRepositoryExistsByDateFromLessThanEqualAndDateToGreaterThanEqualAndStateReturnsTrue() {
        // Setup
        final Booking booking = new Booking();
        booking.setBookingId(0L);
        booking.setDateFrom(LocalDate.of(2020, 1, 1));
        booking.setDateTo(LocalDate.of(2020, 1, 1));
        booking.setGuestName("guestName");
        booking.setState(Booking.State.BOOKED);

        when(mockBookingRepository.existsByDateFromGreaterThanEqualAndDateFromLessThanEqualAndState(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), Booking.State.BOOKED)).thenReturn(false);
        when(mockBookingRepository.existsByDateToGreaterThanEqualAndDateToLessThanEqualAndState(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), Booking.State.BOOKED)).thenReturn(false);
        when(mockBookingRepository.existsByDateFromLessThanEqualAndDateToGreaterThanEqualAndState(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), Booking.State.BOOKED)).thenReturn(true);

        // Run the test
        final boolean result = bookingServiceUnderTest.isBookingValid(booking);

        // Verify the results
        assertFalse(result);
    }

    @Test
    void testIsBookingValid_BlockRepositoryExistsByDateFromLessThanEqualAndDateToGreaterThanEqualReturnsTrue() {
        // Setup
        final Booking booking = new Booking();
        booking.setBookingId(0L);
        booking.setDateFrom(LocalDate.of(2020, 1, 1));
        booking.setDateTo(LocalDate.of(2020, 1, 1));
        booking.setGuestName("guestName");
        booking.setState(Booking.State.BOOKED);

        when(mockBlockRepository.existsByDateFromGreaterThanEqualAndDateFromLessThanEqual(LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 1))).thenReturn(false);
        when(mockBlockRepository.existsByDateToGreaterThanEqualAndDateToLessThanEqual(LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 1))).thenReturn(false);
        when(mockBlockRepository.existsByDateFromLessThanEqualAndDateToGreaterThanEqual(LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 1))).thenReturn(true);

        // Run the test
        final boolean result = bookingServiceUnderTest.isBookingValid(booking);

        // Verify the results
        assertFalse(result);
    }

    @Test
    void testHasBookingConflicts() {
        // Setup
        final Booking booking = new Booking();
        booking.setBookingId(0L);
        booking.setDateFrom(LocalDate.of(2020, 1, 1));
        booking.setDateTo(LocalDate.of(2020, 1, 1));
        booking.setGuestName("guestName");
        booking.setState(Booking.State.BOOKED);

        when(mockBookingRepository.existsByDateFromGreaterThanEqualAndDateFromLessThanEqualAndState(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), Booking.State.BOOKED)).thenReturn(false);
        when(mockBookingRepository.existsByDateToGreaterThanEqualAndDateToLessThanEqualAndState(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), Booking.State.BOOKED)).thenReturn(false);
        when(mockBookingRepository.existsByDateFromLessThanEqualAndDateToGreaterThanEqualAndState(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), Booking.State.BOOKED)).thenReturn(false);

        // Run the test
        final boolean result = bookingServiceUnderTest.hasBookingConflicts(booking);

        // Verify the results
        assertFalse(result);
    }

    @Test
    void testHasBookingConflicts_BookingRepositoryExistsByDateFromLessThanEqualAndDateToGreaterThanEqualAndStateReturnsTrue() {
        // Setup
        final Booking booking = new Booking();
        booking.setBookingId(0L);
        booking.setDateFrom(LocalDate.of(2020, 1, 1));
        booking.setDateTo(LocalDate.of(2020, 1, 1));
        booking.setGuestName("guestName");
        booking.setState(Booking.State.BOOKED);

        when(mockBookingRepository.existsByDateFromGreaterThanEqualAndDateFromLessThanEqualAndState(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), Booking.State.BOOKED)).thenReturn(false);
        when(mockBookingRepository.existsByDateToGreaterThanEqualAndDateToLessThanEqualAndState(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), Booking.State.BOOKED)).thenReturn(false);
        when(mockBookingRepository.existsByDateFromLessThanEqualAndDateToGreaterThanEqualAndState(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), Booking.State.BOOKED)).thenReturn(true);

        // Run the test
        final boolean result = bookingServiceUnderTest.hasBookingConflicts(booking);

        // Verify the results
        assertTrue(result);
    }

    @Test
    void testHasBlockConflicts() {
        // Setup
        final Booking booking = new Booking();
        booking.setBookingId(0L);
        booking.setDateFrom(LocalDate.of(2020, 1, 1));
        booking.setDateTo(LocalDate.of(2020, 1, 1));
        booking.setGuestName("guestName");
        booking.setState(Booking.State.BOOKED);

        when(mockBlockRepository.existsByDateFromGreaterThanEqualAndDateFromLessThanEqual(LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 1))).thenReturn(false);
        when(mockBlockRepository.existsByDateToGreaterThanEqualAndDateToLessThanEqual(LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 1))).thenReturn(false);
        when(mockBlockRepository.existsByDateFromLessThanEqualAndDateToGreaterThanEqual(LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 1))).thenReturn(false);

        // Run the test
        final boolean result = bookingServiceUnderTest.hasBlockConflicts(booking);

        // Verify the results
        assertFalse(result);
    }


    @Test
    void testHasBlockConflicts_BlockRepositoryExistsByDateFromLessThanEqualAndDateToGreaterThanEqualReturnsTrue() {
        // Setup
        final Booking booking = new Booking();
        booking.setBookingId(0L);
        booking.setDateFrom(LocalDate.of(2020, 1, 1));
        booking.setDateTo(LocalDate.of(2020, 1, 1));
        booking.setGuestName("guestName");
        booking.setState(Booking.State.BOOKED);

        when(mockBlockRepository.existsByDateFromGreaterThanEqualAndDateFromLessThanEqual(LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 1))).thenReturn(false);
        when(mockBlockRepository.existsByDateToGreaterThanEqualAndDateToLessThanEqual(LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 1))).thenReturn(false);
        when(mockBlockRepository.existsByDateFromLessThanEqualAndDateToGreaterThanEqual(LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 1))).thenReturn(true);

        // Run the test
        final boolean result = bookingServiceUnderTest.hasBlockConflicts(booking);

        // Verify the results
        assertTrue(result);
    }
}
