package com.zele.zelcoinsb.controller;

import com.zele.zelcoinsb.models.dtos.block.BlockViewDTO;
import com.zele.zelcoinsb.service.BlockChainService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
