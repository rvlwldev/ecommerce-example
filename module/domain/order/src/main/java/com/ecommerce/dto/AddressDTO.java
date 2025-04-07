package com.ecommerce.dto;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private UUID memberId;
    private String address;
    private boolean isDefault;
}
