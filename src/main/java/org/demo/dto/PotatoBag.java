package org.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PotatoBag {
    private UUID uuid;
    private Integer potatoCount;
    private Supplier supplier;
    private Date packDate;
    private Double price;
}
