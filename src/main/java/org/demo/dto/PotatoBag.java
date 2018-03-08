package org.demo.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Builder
@Data
public class PotatoBag {
    private UUID uuid;
    private Integer potatoCount;
    private Double price;
    private String supplier;
    private Date packDate;
}
