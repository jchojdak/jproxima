package com.jchojdak.jproxima;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

final class Metadata {

    private static final String PROPERTIES_FILE = "jproxima.properties";

    private final Properties properties;

    Metadata(ResourceLoader resourceLoader) {
        this.properties = loadProperties(resourceLoader);
    }

    String name() {
        return properties.getProperty("jproxima.name");
    }

    String version() {
        return properties.getProperty("jproxima.version");
    }

    private static Properties loadProperties(ResourceLoader resourceLoader) {
        Properties properties = new Properties();

        try (InputStream inputStream = resourceLoader.load(PROPERTIES_FILE)) {
            if (inputStream == null) {
                throw new IllegalStateException("Properties file not found: " + PROPERTIES_FILE);
            }

            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load JProxima properties", e);
        }

        return properties;
    }
}
