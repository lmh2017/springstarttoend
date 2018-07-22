package org.springstarttoend.io;

import org.springstarttoend.utils.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileSystemResource implements Resource {
    private final String path;
    private final File file;

    public FileSystemResource(String path){
        Assert.notNull(path,"Path must not be null");
        this.file = new File(path);
        this.path = path;
    }

    public InputStream getInputStream() throws IOException {
        return new FileInputStream(file);
    }

    public String getDescription() {
        return "file["+file.getAbsolutePath()+"]";
    }
}
