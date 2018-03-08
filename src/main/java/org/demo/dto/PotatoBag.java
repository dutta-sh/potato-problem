package org.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
public class PotatoBag {
    private UUID uuid;
    private Integer potatoCount;
    private Double price;
    private String supplier;
    private Date packDate;
}
