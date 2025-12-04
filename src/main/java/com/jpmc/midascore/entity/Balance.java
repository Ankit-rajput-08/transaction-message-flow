package com.jpmc.midascore.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class Balance {
    @Id
    private Long userId;   // <-- same as UserEntity.id (NOT auto-generated)

    private float amount;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public Balance() {}

    public Balance(UserEntity user, float amount) {
        this.user = user;
        this.userId = user.getId();  // important
        this.amount = amount;
    }
}
