package com.jchojdak.jproxima;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BannerTest {

    private static final String BANNER_FILE = "banner.txt";
    private static final String BANNER_CONTENT = "TEST BANNER";
    private static final String APPLICATION_NAME = "Test name";
    private static final String APPLICATION_VERSION = "Test version";

    @Nested
    class Render {

        @Test
        void shouldRenderBannerWithMetadata() {
            Banner banner = createBanner(BANNER_CONTENT);

            String result = banner.render();

            assertTrue(result.contains(BANNER_CONTENT));
            assertTrue(result.contains(APPLICATION_NAME));
            assertTrue(result.contains(APPLICATION_VERSION));
        }

        @Test
        void shouldRemoveTrailingWhitespaceFromBanner() {
            String contentWithTrailingWhitespace = BANNER_CONTENT + " \n\n\t";

            Banner banner = createBanner(contentWithTrailingWhitespace);

            String result = banner.render();

            assertTrue(result.startsWith(BANNER_CONTENT));
            assertTrue(result.contains(
                    ">> " + APPLICATION_NAME + " (v" + APPLICATION_VERSION + ")"
            ));
        }
    }

    @Nested
    class Print {

        @Test
        void shouldPrintRenderedBanner() {
            Banner banner = createBanner(BANNER_CONTENT);

            PrintStream originalOut = System.out;
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            try {
                System.setOut(new PrintStream(output));

                banner.print();

                assertEquals(banner.render(), output.toString());
            } finally {
                System.setOut(originalOut);
            }
        }
    }

    @Nested
    class Loading {

        @Test
        void shouldThrowExceptionWhenBannerResourceDoesNotExist() {
            ResourceLoader resourceLoader = mock(ResourceLoader.class);
            Metadata metadata = mock(Metadata.class);

            when(resourceLoader.load(BANNER_FILE))
                    .thenReturn(null);

            IllegalStateException exception = assertThrows(
                    IllegalStateException.class,
                    () -> new Banner(resourceLoader, metadata)
            );

            assertEquals(
                    "Banner resource not found: " + BANNER_FILE,
                    exception.getMessage()
            );
        }

        @Test
        void shouldThrowExceptionWhenBannerCannotBeRead() throws IOException {
            ResourceLoader resourceLoader = mock(ResourceLoader.class);
            Metadata metadata = mock(Metadata.class);
            InputStream inputStream = mock(InputStream.class);

            when(resourceLoader.load(BANNER_FILE))
                    .thenReturn(inputStream);
            when(inputStream.readAllBytes())
                    .thenThrow(new IOException("Read failed"));

            IllegalStateException exception = assertThrows(
                    IllegalStateException.class,
                    () -> new Banner(resourceLoader, metadata)
            );

            assertEquals("Unable to load JProxima banner", exception.getMessage());
            assertInstanceOf(IOException.class, exception.getCause());
        }
    }

    private static Banner createBanner(String content) {
        ResourceLoader resourceLoader = mock(ResourceLoader.class);
        Metadata metadata = mock(Metadata.class);

        when(resourceLoader.load(BANNER_FILE))
                .thenReturn(inputStream(content));
        when(metadata.name())
                .thenReturn(APPLICATION_NAME);
        when(metadata.version())
                .thenReturn(APPLICATION_VERSION);

        return new Banner(resourceLoader, metadata);
    }

    private static InputStream inputStream(String content) {
        return new ByteArrayInputStream(
                content.getBytes(StandardCharsets.UTF_8)
        );
    }
}
