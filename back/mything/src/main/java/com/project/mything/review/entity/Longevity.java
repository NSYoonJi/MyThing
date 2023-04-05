package com.project.mything.review.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Longevity {
    LONG, MIDDLE, SHORT;

    @JsonCreator
    public static Longevity from(String s) {
        return Longevity.valueOf(s.toUpperCase());
    }
}
