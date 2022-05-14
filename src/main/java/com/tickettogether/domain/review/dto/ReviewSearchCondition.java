package com.tickettogether.domain.review.dto;

import lombok.Data;

@Data
public class ReviewSearchCondition {

    private Long hallId;
    private String floor;
    private String part;
    private String record;
    private String number;
}

