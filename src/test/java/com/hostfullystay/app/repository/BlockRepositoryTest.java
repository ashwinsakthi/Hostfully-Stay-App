package com.hostfullystay.app.repository;

import com.hostfullystay.app.domain.Block;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class BlockRepositoryTest {

    @Autowired
    private BlockRepository blockRepository;

    @Test
    public void testExistsByDateFromGreaterThanEqualAndDateFromLessThanEqual() {
        // Arrange
        LocalDate dateFrom = LocalDate.now();
        LocalDate dateTo = LocalDate.now().plusDays(5);

        // Act
        boolean exists = blockRepository.existsByDateFromGreaterThanEqualAndDateFromLessThanEqual(dateFrom, dateTo);

        // Assert
        assertFalse(exists);
    }

    @Test
    public void testExistsByDateToGreaterThanEqualAndDateToLessThanEqual() {
        // Arrange
        LocalDate dateFrom = LocalDate.now().minusDays(5);
        LocalDate dateTo = LocalDate.now();

        // Act
        boolean exists = blockRepository.existsByDateToGreaterThanEqualAndDateToLessThanEqual(dateFrom, dateTo);

        // Assert
        assertFalse(exists);
    }

    @Test
    public void testExistsByDateFromLessThanEqualAndDateToGreaterThanEqual() {
        // Arrange
        LocalDate dateFromAndTo1 = LocalDate.now().minusDays(2);
        LocalDate dateFromAndTo2 = LocalDate.now().plusDays(2);

        // Act
        boolean exists = blockRepository.existsByDateFromLessThanEqualAndDateToGreaterThanEqual(dateFromAndTo1, dateFromAndTo2);

        // Assert
        assertFalse(exists);
    }
}