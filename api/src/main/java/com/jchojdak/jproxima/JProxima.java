package com.jchojdak.jproxima;

public final class JProxima {

    private static final ResourceLoader RESOURCE_LOADER = new ClasspathResourceLoader();

    private static final Metadata METADATA = new Metadata(RESOURCE_LOADER);

    private static final Banner BANNER = new Banner(RESOURCE_LOADER, METADATA);

    private JProxima() {
    }

    public static String name() {
        return METADATA.name();
    }

    public static String version() {
        return METADATA.version();
    }

    public static String banner() {
        return BANNER.render();
    }

    public static void printBanner() {
        BANNER.print();
    }
}
