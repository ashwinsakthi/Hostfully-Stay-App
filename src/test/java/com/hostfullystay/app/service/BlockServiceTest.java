package com.hostfullystay.app.service;

import com.hostfullystay.app.domain.Block;
import com.hostfullystay.app.repository.BlockRepository;
import com.hostfullystay.app.repository.BookingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static com.hostfullystay.app.domain.Booking.State.BOOKED;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlockServiceTest {

    @Mock
    private BlockRepository blockRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BlockService blockService;

    @Test
    void testSaveValidBlock() {
        Block block = new Block();
        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = LocalDate.now().plusDays(1);

        // Mock the behavior of the bookingRepository.existsByDateFrom... methods
        when(bookingRepository.existsByDateFromGreaterThanEqualAndDateFromLessThanEqualAndState(
                any(LocalDate.class), any(LocalDate.class), any())).thenReturn(false);

        when(blockRepository.save(block)).thenReturn(block);

        // Call the method with valid arguments
        block.setDateFrom(fromDate);
        block.setDateTo(toDate);
        Block savedBlock = blockService.save(block);

        assertNotNull(savedBlock);

        // Verify that the bookingRepository.existsByDateFrom... methods were called with the correct arguments
        verify(bookingRepository, times(1)).existsByDateFromGreaterThanEqualAndDateFromLessThanEqualAndState(
                eq(fromDate), eq(toDate), any());
        verify(blockRepository, times(1)).save(block);
    }
    @Test
    void testSaveInvalidBlock() {
        // Create a block with conflicting dates
        Block block = new Block();
        block.setDateFrom(LocalDate.now().minusDays(1));
        block.setDateTo(LocalDate.now().plusDays(1));

        // Mock the behavior of the bookingRepository.existsByDateFrom... methods
        when(bookingRepository.existsByDateFromGreaterThanEqualAndDateFromLessThanEqualAndState(
                any(LocalDate.class), any(LocalDate.class), any())).thenReturn(true);

        // Call the method with the conflicting block
        Block savedBlock = blockService.save(block);

        // Assert that the block was not saved due to conflicts
        assertNull(savedBlock);

        // Verify that the bookingRepository.existsByDateFrom... methods were called with the correct arguments
        verify(bookingRepository, times(1)).existsByDateFromGreaterThanEqualAndDateFromLessThanEqualAndState(
                eq(block.getDateFrom()), eq(block.getDateTo()), eq(BOOKED));
        verify(blockRepository, never()).save(any());
    }


    @Test
    void testDeleteBlock() {
        Long blockId = 1L;
        doNothing().when(blockRepository).deleteById(blockId);

        blockService.delete(blockId);

        verify(blockRepository, times(1)).deleteById(blockId);
    }

}
