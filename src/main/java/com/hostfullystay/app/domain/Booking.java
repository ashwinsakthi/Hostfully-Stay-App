package com.hostfullystay.app.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "booking")
public class Booking implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", initialValue = 1000)
    @Column(name = "bookingId")
    private Long bookingId;

    @NotNull
    @Column(name = "dateFrom", nullable = false)
    private LocalDate dateFrom;

    @NotNull
    @Column(name = "dateTo", nullable = false)
    private LocalDate dateTo;

    @NotNull
    @Size(max = 255)
    @Column(name = "guestName", length = 255, nullable = false)
    private String guestName;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State state;

    public enum State {
        BOOKED,
        CANCELED,
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
