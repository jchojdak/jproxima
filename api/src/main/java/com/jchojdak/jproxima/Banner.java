package com.jchojdak.jproxima;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

final class Banner {

    private static final String BANNER_FILE = "banner.txt";

    private final Metadata metadata;
    private final String content;

    Banner(ResourceLoader resourceLoader, Metadata metadata) {
        this.metadata = metadata;
        this.content = renderContent(resourceLoader);
    }

    String render() {
        return content;
    }

    void print() {
        System.out.print(content);
    }

    private String renderContent(ResourceLoader resourceLoader) {
        return String.join(
                System.lineSeparator(),
                loadBanner(resourceLoader).stripTrailing(),
                "",
                ">> " + metadata.name() + " (v" + metadata.version() + ")",
                "",
                ""
        );
    }

    private String loadBanner(ResourceLoader resourceLoader) {
        try (InputStream inputStream = resourceLoader.load(BANNER_FILE)) {
            if (inputStream == null) {
                throw new IllegalStateException("Banner resource not found: " + BANNER_FILE);
            }

            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load JProxima banner", e);
        }
    }
}
