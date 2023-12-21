package com.hostfullystay.app.service;

import com.hostfullystay.app.domain.Booking;
import com.hostfullystay.app.repository.BlockRepository;
import com.hostfullystay.app.repository.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.hostfullystay.app.domain.Booking.State.BOOKED;

/**
 * Service class for handling business logic related to Booking entities.
 * Manages the interaction between the controllers and the Booking repository.
 *
 * @author Sakthidharan Ashwin
 */
@Service
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BlockRepository blockRepository;

    public BookingService(
            BookingRepository bookingRepository,
            BlockRepository blockRepository
    ) {
        this.bookingRepository = bookingRepository;
        this.blockRepository = blockRepository;
    }

    public Booking save(Booking booking) {
        if (!isBookingValid(booking)) {
            return null;
        }

        if (booking.getState() == null) {
            booking.setState(BOOKED);
        }

        booking = bookingRepository.save(booking);
        return booking;
    }

    public Booking update(Booking booking) {
        if (BOOKED.equals(booking.getState())) {
            if (!isBookingValid(booking)) {
                return null;
            }
        }

        booking = bookingRepository.save(booking);
        return booking;
    }

    public Optional<Booking> partialUpdate(Booking booking) {
        return bookingRepository
                .findById(booking.getBookingId())
                .map(existingBooking -> {
                    if (booking.getDateFrom() != null) {
                        existingBooking.setDateFrom(booking.getDateFrom());
                    }
                    if (booking.getDateTo() != null) {
                        existingBooking.setDateTo(booking.getDateTo());
                    }
                    if (booking.getGuestName() != null) {
                        existingBooking.setGuestName(booking.getGuestName());
                    }
                    if (booking.getState() != null) {
                        existingBooking.setState(booking.getState());
                    }

                    return existingBooking;
                })
                .map(newBooking -> {
                    if (BOOKED.equals(newBooking.getState())) {
                        if (!isBookingValid(newBooking)) {
                            return null;
                        }
                    }
                    return bookingRepository.save(newBooking);
                });
    }

    @Transactional(readOnly = true)
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Transactional(readOnly = true)
    public long count() {
        return bookingRepository.count();
    }

    @Transactional(readOnly = true)
    public Optional<Booking> findOne(Long id) {
        return bookingRepository.findById(id);
    }

    public void delete(Long id) {
        bookingRepository.deleteById(id);
    }

    public boolean isBookingValid(Booking booking) {
        return !hasBookingConflicts(booking) && !hasBlockConflicts(booking);
    }

    public boolean hasBookingConflicts(Booking booking) {
        return bookingRepository.existsByDateFromGreaterThanEqualAndDateFromLessThanEqualAndState(booking.getDateFrom(), booking.getDateTo(), BOOKED)
                || bookingRepository.existsByDateToGreaterThanEqualAndDateToLessThanEqualAndState(booking.getDateFrom(), booking.getDateTo(), BOOKED)
                || bookingRepository.existsByDateFromLessThanEqualAndDateToGreaterThanEqualAndState(booking.getDateTo(), booking.getDateTo(), BOOKED)
                || bookingRepository.existsByDateFromLessThanEqualAndDateToGreaterThanEqualAndState(booking.getDateFrom(), booking.getDateFrom(), BOOKED);
    }

    public boolean hasBlockConflicts(Booking booking) {
        return blockRepository.existsByDateFromGreaterThanEqualAndDateFromLessThanEqual(booking.getDateFrom(), booking.getDateTo())
                || blockRepository.existsByDateToGreaterThanEqualAndDateToLessThanEqual(booking.getDateFrom(), booking.getDateTo())
                || blockRepository.existsByDateFromLessThanEqualAndDateToGreaterThanEqual(booking.getDateTo(), booking.getDateTo())
                || blockRepository.existsByDateFromLessThanEqualAndDateToGreaterThanEqual(booking.getDateFrom(), booking.getDateFrom());
    }
}
