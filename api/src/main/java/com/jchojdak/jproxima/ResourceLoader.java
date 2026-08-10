package com.jchojdak.jproxima;

import java.io.InputStream;

interface ResourceLoader {

    InputStream load(String resource);
}
