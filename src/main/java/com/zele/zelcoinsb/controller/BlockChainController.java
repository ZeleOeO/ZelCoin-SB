package com.zele.zelcoinsb.controller;

import com.zele.zelcoinsb.models.dtos.block.BlockViewDTO;
import com.zele.zelcoinsb.service.BlockChainService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/blockchain")
@AllArgsConstructor
public class BlockChainController {

    private final BlockChainService blockChainService;

    @GetMapping("/all")
    public List<BlockViewDTO> getAllBlocks() {
        return blockChainService.getAllBlocks();
    }

    @GetMapping("/{hash}")
    public ResponseEntity<BlockViewDTO> getBlockByHash(@PathVariable String hash) {
        return blockChainService.getBlocksByHash(hash);
    }

    @PostMapping("/mine")
    public ResponseEntity<BlockViewDTO> mineBlock() {
        return blockChainService.mineBlock();
    }
}
