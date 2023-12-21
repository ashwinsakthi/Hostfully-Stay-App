package com.hostfullystay.app.repository;

import com.hostfullystay.app.domain.Block;
import com.hostfullystay.app.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long>, JpaSpecificationExecutor<Block> {

    boolean existsByDateFromGreaterThanEqualAndDateFromLessThanEqual(LocalDate bookingDateFrom, LocalDate bookingDateTo);

    boolean existsByDateToGreaterThanEqualAndDateToLessThanEqual(LocalDate bookingDateFrom, LocalDate bookingDateTo);

    boolean existsByDateFromLessThanEqualAndDateToGreaterThanEqual(LocalDate bookingDateFromAndTo1, LocalDate bookingDateFromAndTo2);
}
