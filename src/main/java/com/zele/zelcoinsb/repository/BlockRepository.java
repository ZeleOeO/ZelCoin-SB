package com.zele.zelcoinsb.repository;

import com.zele.zelcoinsb.models.entities.Block;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlockRepository extends JpaRepository<Block, Long> {
    Optional<Block> findByHash(String hash);
}
