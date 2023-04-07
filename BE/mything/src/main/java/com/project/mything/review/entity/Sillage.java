package com.project.mything.review.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Sillage {
    STRONG, NORMAL, WEAK;

    @JsonCreator
    public static Sillage from(String s) {
        return Sillage.valueOf(s.toUpperCase());
    }
}
