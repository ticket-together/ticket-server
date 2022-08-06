package com.tickettogether.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewSearchCondition {

    private String hallName;
    private String floor;
    private String part;
    private String record;
    private String number;
}

