package com.jchojdak.jproxima;

import java.io.InputStream;

final class ClasspathResourceLoader implements ResourceLoader {

    private final ClassLoader classLoader;

    ClasspathResourceLoader() {
        this(ClasspathResourceLoader.class.getClassLoader());
    }

    ClasspathResourceLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public InputStream load(String resource) {
        return classLoader.getResourceAsStream(resource);
    }
}
