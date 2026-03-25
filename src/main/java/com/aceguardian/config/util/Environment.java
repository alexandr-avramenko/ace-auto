package com.aceguardian.config.util;

public enum Environment {
    DEV("dev.properties"),
    PRE_STAGE("pre_stage.properties"),
    STAGE("stage.properties"),
    DEFAULT("application.properties");

    private final String properties;

    Environment(String properties) {
        this.properties = properties;
    }
}
