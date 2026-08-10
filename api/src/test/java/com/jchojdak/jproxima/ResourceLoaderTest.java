package com.jchojdak.jproxima;

import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ResourceLoaderTest {

    @Test
    void shouldLoadExistingResource() {
        ResourceLoader resourceLoader = new ClasspathResourceLoader();

        InputStream inputStream = resourceLoader.load("banner.txt");

        assertNotNull(inputStream);
    }

    @Test
    void shouldReturnNullForMissingResource() {
        ResourceLoader resourceLoader = new ClasspathResourceLoader();

        InputStream inputStream = resourceLoader.load("missing-resource.txt");

        assertNull(inputStream);
    }
}
