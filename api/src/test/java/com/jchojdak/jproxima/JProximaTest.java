package com.jchojdak.jproxima;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class JProximaTest {

    @Test
    void shouldReturnName() {
        String name = JProxima.name();

        assertNotNull(name);
        assertFalse(name.isBlank());
    }

    @Test
    void shouldReturnVersion() {
        String version = JProxima.version();

        assertNotNull(version);
        assertFalse(version.isBlank());
    }

    @Test
    void shouldReturnBanner() {
        String banner = JProxima.banner();

        assertNotNull(banner);
        assertFalse(banner.isBlank());
    }

    @Test
    void shouldPrintBanner() {
        var originalOut = System.out;
        var output = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(output));

            JProxima.printBanner();

            assertEquals(JProxima.banner(), output.toString());
        } finally {
            System.setOut(originalOut);
        }
    }
}
