package com.example.filikov_advanced_server.responses;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

public class UrlResource {
    String description;
    File file;
    String filename;
    InputStream inputStream;
    boolean open;
    boolean readable;
    URI uri;
    URL url;

}