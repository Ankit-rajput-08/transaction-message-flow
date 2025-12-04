package com.jpmc.midascore.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDTO {
    @NonNull
    private Long senderId;
    @NonNull
    private Long recipientId;
    @NonNull
    private float amount;
    @NonNull
    private String type;
}
