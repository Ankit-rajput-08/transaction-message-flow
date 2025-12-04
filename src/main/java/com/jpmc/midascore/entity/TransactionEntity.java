package com.jpmc.midascore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    private UserEntity sender;

    @NonNull
    @ManyToOne
    private UserEntity recipient;

    @Column(nullable = false)
    private float amount;

    private String type;

    @Column(nullable = false)
    private LocalDateTime timestamp;

}
