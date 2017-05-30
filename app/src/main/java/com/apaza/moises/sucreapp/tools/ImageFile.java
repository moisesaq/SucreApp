package com.apaza.moises.sucreapp.tools;

import java.io.IOException;

public interface ImageFile {

    void create(String name, String extension) throws IOException;
    boolean exists();
    void delete();
    String getPath();
}
