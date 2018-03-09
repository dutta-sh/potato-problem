package org.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss aa z")
    private Date packDate;
}
