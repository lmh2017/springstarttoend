package org.springstarttoend.context.support;

import org.springstarttoend.core.io.ClassPathResource;
import org.springstarttoend.core.io.Resource;

public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    public ClassPathXmlApplicationContext(String configFile) {
        super(configFile);
    }

    public Resource getResourceByPath(String path) {
        return new ClassPathResource(path,this.getBeanClassLoader());
    }
}
