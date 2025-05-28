package com.zele.zelcoinsb.mapper;

import com.zele.zelcoinsb.models.dtos.block.BlockViewDTO;
import com.zele.zelcoinsb.models.entities.Block;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BlockMapper {
    public BlockViewDTO toBlockViewDTO(Block block) {
        BlockViewDTO blockViewDTO = new BlockViewDTO();
        blockViewDTO.setId(block.getId());
        blockViewDTO.setHash(block.getHash());
        blockViewDTO.setPreviousHash(block.getPrevHash());
        blockViewDTO.setCreatedAt(block.getTimestamp());
        return blockViewDTO;
    }
}
