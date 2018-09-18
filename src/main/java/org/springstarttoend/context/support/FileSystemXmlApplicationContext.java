package org.springstarttoend.context.support;

import org.springstarttoend.core.io.FileSystemResource;
import org.springstarttoend.core.io.Resource;

public class FileSystemXmlApplicationContext extends AbstractApplicationContext {

    public FileSystemXmlApplicationContext(String configFile) {
        super(configFile);
    }

    public Resource getResourceByPath(String path) {
        return new FileSystemResource(path);
    }
}
