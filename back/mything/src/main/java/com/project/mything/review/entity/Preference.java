package com.project.mything.review.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Preference {
    LIKE, OK, HATE;

    @JsonCreator
    public static Preference from(String s) {
        return Preference.valueOf(s.toUpperCase());
    }
}
