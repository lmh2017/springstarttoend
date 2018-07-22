package org.springstarttoend.io;

import org.springstarttoend.utils.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author mh_liu
 * @create 2018/7/6 上午9:48
 */
public class ClassPathResource implements Resource{

    private String path;

    private ClassLoader classLoader;

    public ClassPathResource(String path){
        this(path,(ClassLoader)null);
    }

    public ClassPathResource(String path, ClassLoader classLoader) {
        this.path = path;
        this.classLoader = (classLoader!=null?classLoader: ClassUtils.getDefaultClassLoader());
    }

    public InputStream getInputStream() throws IOException {
        InputStream inputStream = this.classLoader.getResourceAsStream(this.path);
        if(null == inputStream){
            throw new FileNotFoundException(path+" cannot be opened");
        }
        return inputStream;
    }

    public String getDescription() {
        return this.path;
    }
}
