package com.aceguardian.config.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

@Slf4j
public class PropertyUtil {
    private static final Properties PROPERTIES = new Properties();
    ;

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

    private static Environment resolveEnvironment() {
        String env = System.getProperty("env");

        return Arrays.stream(Environment.values())
                .filter(e -> e.name().equals(env.toUpperCase()))
                .findFirst()
                .orElse(Environment.DEFAULT);
    }

    private static void loadProperties() {
        String sourceFile = resolveEnvironment().name();

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
