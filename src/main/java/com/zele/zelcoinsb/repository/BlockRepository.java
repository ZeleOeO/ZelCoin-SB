package com.zele.zelcoinsb.repository;

import com.zele.zelcoinsb.models.entities.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, Long> {
}
