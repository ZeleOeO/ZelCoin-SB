package com.zele.zelcoinsb.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String prevHash;
    private String hash;
    private int nonce;
    private String timestamp;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "block_id")
    private List<Transaction> transactions = new ArrayList<>();
}
