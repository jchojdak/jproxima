package com.jchojdak.jproxima;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MetadataTest {

    private static final String PROPERTIES_FILE = "jproxima.properties";

    private static final String APPLICATION_NAME = "Test name";
    private static final String APPLICATION_VERSION = "Test version";

    @Nested
    class Loading {

        @Test
        void shouldLoadPropertiesFromResource() {
            Metadata metadata = createMetadata();

            assertEquals(APPLICATION_NAME, metadata.name());
            assertEquals(APPLICATION_VERSION, metadata.version());
        }

        @Test
        void shouldThrowExceptionWhenPropertiesResourceDoesNotExist() {
            ResourceLoader resourceLoader = mock(ResourceLoader.class);

            when(resourceLoader.load(PROPERTIES_FILE))
                    .thenReturn(null);

            IllegalStateException exception = assertThrows(
                    IllegalStateException.class,
                    () -> new Metadata(resourceLoader)
            );

            assertEquals(
                    "Properties file not found: " + PROPERTIES_FILE,
                    exception.getMessage()
            );
        }

        @Test
        void shouldThrowExceptionWhenPropertiesCannotBeRead() {
            ResourceLoader resourceLoader = mock(ResourceLoader.class);

            when(resourceLoader.load(PROPERTIES_FILE))
                    .thenReturn(failingInputStream());

            IllegalStateException exception = assertThrows(
                    IllegalStateException.class,
                    () -> new Metadata(resourceLoader)
            );

            assertEquals(
                    "Unable to load JProxima properties",
                    exception.getMessage()
            );
            assertInstanceOf(IOException.class, exception.getCause());
        }
    }

    @Nested
    class Properties {

        @Test
        void shouldReturnConfiguredName() {
            Metadata metadata = createMetadata();

            assertEquals(APPLICATION_NAME, metadata.name());
        }

        @Test
        void shouldReturnConfiguredVersion() {
            Metadata metadata = createMetadata();

            assertEquals(APPLICATION_VERSION, metadata.version());
        }
    }

    private static Metadata createMetadata() {
        ResourceLoader resourceLoader = mock(ResourceLoader.class);

        when(resourceLoader.load(PROPERTIES_FILE))
                .thenReturn(inputStream("""
                        jproxima.name=%s
                        jproxima.version=%s
                        """.formatted(
                        APPLICATION_NAME,
                        APPLICATION_VERSION
                )));

        return new Metadata(resourceLoader);
    }

    private static InputStream inputStream(String content) {
        return new ByteArrayInputStream(
                content.getBytes(StandardCharsets.UTF_8)
        );
    }

    private static InputStream failingInputStream() {
        return new InputStream() {
            @Override
            public int read() throws IOException {
                throw new IOException("Read failed");
            }
        };
    }
}