package org.springstarttoend.core.io.support;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springstarttoend.core.io.FileSystemResource;
import org.springstarttoend.core.io.Resource;
import org.springstarttoend.utils.Assert;
import org.springstarttoend.utils.ClassUtils;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 扫描加载包下的资源文件，存储并返回
 * @author mh_liu
 * @create 2018/9/8 下午2:17
 */
public class PackageResourceLoader {

    private static final Logger logger = Logger.getLogger(PackageResourceLoader.class);

    private final ClassLoader classLoader;

    public PackageResourceLoader(){
        this.classLoader = ClassUtils.getDefaultClassLoader();
    }

    public PackageResourceLoader(ClassLoader classLoader){
        Assert.notNull(classLoader,"ResourceLoader must not be null");
        this.classLoader = classLoader;
    }

    public ClassLoader getClassLoader(){
        return this.classLoader;
    }

    public Resource[] getResources(String basePackage) {
        Assert.notNull(basePackage,"basePackage must not be null");
        String location = ClassUtils.convertClassNameToResourcePath(basePackage);
        ClassLoader cl = getClassLoader();
        URL url = cl.getResource(location);

        File rootDir = new File(url.getFile());

        Set<File> matchingFiles = retrieveMatchingFile(rootDir);

        Resource[] resources = new Resource[matchingFiles.size()];
        int i=0;
        for(File file:matchingFiles){
            resources[i++] = new FileSystemResource(file);
        }
        return resources;
    }

    private Set<File> retrieveMatchingFile(File rootDir) {
        if(!rootDir.exists()){
            //如果目录不存在，跳过
            if(logger.isDebugEnabled()){
                logger.debug("Skipping ["+ rootDir.getAbsolutePath() +"] because it does not denote a directory");
            }
            return Collections.emptySet();
        }
        if(!rootDir.isDirectory()){
            //如果不是个目录，跳过
            if(logger.isEnabledFor(Level.WARN)){
                logger.warn("Skiping [" +rootDir.getAbsolutePath()+ "] besause it does not denote a directory");
            }
            return Collections.emptySet();
        }
        if(!rootDir.canRead()){
            if(logger.isEnabledFor(Level.WARN)){
                logger.warn("Cannot search for matching files underneath directory [" + rootDir.getAbsolutePath() +
                        "] because the application is not allowed to read the directory");
            }
            return Collections.emptySet();
        }

        Set<File> result = new LinkedHashSet<>(8);
        doRetrieveMatchingFiles(rootDir,result);
        return result;

    }

    private void doRetrieveMatchingFiles(File dir, Set<File> result) {
        File[] dirContents = dir.listFiles();
        if (dirContents == null) {
            if (logger.isEnabledFor(Level.WARN)) {
                logger.warn("Could not retrieve contents of directory [" + dir.getAbsolutePath() + "]");
            }
            return;
        }
        for(File content:dirContents){
            if (content.isDirectory() ) {
                if (!content.canRead()) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Skipping subdirectory [" + dir.getAbsolutePath() +
                                "] because the application is not allowed to read the directory");
                    }
                }
                else {
                    doRetrieveMatchingFiles(content, result);
                }
            } else{
                result.add(content);
            }
        }

    }
}
