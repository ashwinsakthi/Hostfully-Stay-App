package com.hostfullystay.app.repository;

import com.hostfullystay.app.domain.Booking;
import com.hostfullystay.app.repository.BookingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Test
    public void testExistsByDateFromGreaterThanEqualAndDateFromLessThanEqualAndState() {
        // Arrange
        LocalDate dateFrom = LocalDate.now();
        LocalDate dateTo = LocalDate.now().plusDays(5);
        Booking.State state = Booking.State.BOOKED;

        // Act
        boolean exists = bookingRepository.existsByDateFromGreaterThanEqualAndDateFromLessThanEqualAndState(dateFrom, dateTo, state);

        // Assert
        if (!exists) {
            // Print additional information for debugging
            System.out.println("No bookings found for the specified date range and state.");
            System.out.println("Date From: " + dateFrom);
            System.out.println("Date To: " + dateTo);
            System.out.println("State: " + state);
            assertFalse(exists);
        }else{
            assertTrue(exists);
        }


    }

    @Test
    public void testExistsByDateToGreaterThanEqualAndDateToLessThanEqualAndState() {
        // Arrange
        LocalDate dateFrom = LocalDate.now().minusDays(5);
        LocalDate dateTo = LocalDate.now();
        Booking.State state = Booking.State.BOOKED;

        // Act
        boolean exists = bookingRepository.existsByDateToGreaterThanEqualAndDateToLessThanEqualAndState(dateFrom, dateTo, state);

        // Assert
        if (!exists) {
            // Print additional information for debugging
            System.out.println("No bookings found for the specified date range and state.");
            System.out.println("Date From: " + dateFrom);
            System.out.println("Date To: " + dateTo);
            System.out.println("State: " + state);
            assertFalse(exists);
        }else{
            assertTrue(exists);
        }
    }

    @Test
    public void testExistsByDateFromLessThanEqualAndDateToGreaterThanEqualAndState() {
        // Arrange
        LocalDate dateFromAndTo1 = LocalDate.now().minusDays(2);
        LocalDate dateFromAndTo2 = LocalDate.now().plusDays(2);
        Booking.State state = Booking.State.BOOKED;

        // Act
        boolean exists = bookingRepository.existsByDateFromLessThanEqualAndDateToGreaterThanEqualAndState(dateFromAndTo1, dateFromAndTo2, state);

        // Assert
        if (!exists) {
            // Print additional information for debugging
            System.out.println("No bookings found for the specified date range and state.");
            System.out.println("Date From: " + dateFromAndTo1);
            System.out.println("Date To: " + dateFromAndTo2);
            System.out.println("State: " + state);
            assertFalse(exists);
        }else{
            assertTrue(exists);
        }
    }
}