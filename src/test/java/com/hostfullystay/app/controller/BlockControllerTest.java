package com.hostfullystay.app.controller;

import com.hostfullystay.app.domain.Block;
import com.hostfullystay.app.repository.BlockRepository;
import com.hostfullystay.app.service.BlockService;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BlockControllerTest {

    @Mock
    private BlockService blockService;

    @Mock
    private BlockRepository blockRepository;

    @InjectMocks
    private BlockController blockController;

    @Test
    void createBlock() throws URISyntaxException {
        Block block = new Block();
        when(blockService.save(Mockito.any(Block.class))).thenReturn(block);

        ResponseEntity<Block> responseEntity = blockController.createBlock(block);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void createBlockWithInvalidId() {
        Block block = new Block();
        block.setBlockId(1L);

        assertThrows(ResponseStatusException.class, () -> blockController.createBlock(block));
    }

    @Test
    void updateBlock() throws URISyntaxException {
        Block block = new Block();
        block.setBlockId(1L);

        when(blockRepository.existsById(1L)).thenReturn(true);
        when(blockService.update(Mockito.any(Block.class))).thenReturn(block);

        ResponseEntity<Block> responseEntity = blockController.updateBlock(1L, block);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void updateBlockWithInvalidId() {
        Block block = new Block();
        block.setBlockId(1L);

        assertThrows(ResponseStatusException.class, () -> blockController.updateBlock(2L, block));
    }

    @Test
    void partialUpdateBlock() throws URISyntaxException {
        Block block = new Block();
        block.setBlockId(1L);

        when(blockRepository.existsById(1L)).thenReturn(true);
        when(blockService.partialUpdate(Mockito.any(Block.class))).thenReturn(Optional.of(block));

        ResponseEntity<Block> responseEntity = blockController.partialUpdateBlock(1L, block);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void partialUpdateBlockWithInvalidId() {
        Block block = new Block();
        block.setBlockId(1L);

        assertThrows(ResponseStatusException.class, () -> blockController.partialUpdateBlock(2L, block));
    }

    @Test
    void getAllBlocks() {
        List<Block> blockList = Arrays.asList(new Block(), new Block());
        when(blockService.findAll()).thenReturn(blockList);

        ResponseEntity<List<Block>> responseEntity = blockController.getAllBlocks();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(blockList, responseEntity.getBody());
    }

    @Test
    void countBlocks() {
        when(blockService.count()).thenReturn(5L);

        ResponseEntity<Long> responseEntity = blockController.countBlocks();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(5L, responseEntity.getBody());
    }

    @Test
    void getBlock() {
        Block block = new Block();
        block.setBlockId(1L);

        when(blockService.findOne(1L)).thenReturn(Optional.of(block));

        ResponseEntity<Block> responseEntity = blockController.getBlock(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void getBlockWithInvalidId() {
        when(blockService.findOne(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> blockController.getBlock(1L));
    }

    @Test
    void deleteBlock() {
        assertDoesNotThrow(() -> blockController.deleteBlock(1L));
    }
}
