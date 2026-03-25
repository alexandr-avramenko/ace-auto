package com.aceguardian.config.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class PropertyUtil {
    private static final Properties PROPERTIES = new Properties();
    private static final String DEFAULT_SOURCE_FILE = "application.properties";
    private static final String DEV_SOURCE_FILE = "dev.properties";
    private static final String PRE_STAGE_SOURCE_FILE = "pre_stage.properties";
    private static final String STAGE_SOURCE_FILE = "stage.properties";

    static {
        loadProperties();
    }

    public static String get(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException(String.format("Key cannot be empty or null: %s", key));
        }
        String value = PROPERTIES.getProperty(key);

        if (value == null) {
            throw new IllegalArgumentException(String.format("Key wasn't found -> key: %s", key));
        }
        return value;
    }

    private static String resolveEnvironment() {
        String env = System.getProperty("env");
        return switch (env) {
            case "dev" -> DEV_SOURCE_FILE;
            case "pre_stage" -> PRE_STAGE_SOURCE_FILE;
            case "stage" -> STAGE_SOURCE_FILE;
            default -> DEFAULT_SOURCE_FILE;
        };
    }

    private static void loadProperties() {
        String sourceFile = resolveEnvironment();

        try (InputStream inputStream = PropertyUtil.class.getClassLoader().getResourceAsStream(sourceFile)) {
            if (inputStream == null) {
                throw new RuntimeException("File application.properties wasn't found " + sourceFile);
            }
            PROPERTIES.load(inputStream);
            log.info("Properties load successfully");
        } catch (IOException ex) {
            log.error("An error occurs during reading properties, {}", ex.getMessage());
        }
    }
}
