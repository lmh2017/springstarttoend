package org.springstarttoend.core.type.classreading;

import org.springstarttoend.core.io.Resource;
import org.springstarttoend.core.type.AnnotationMetadata;
import org.springstarttoend.core.type.ClassMetadata;

/**
 * @author mh_liu
 * @create 2018/9/15 下午5:34
 */
public interface MetadataReader {

    /**
     * Return the resource reference for the class file.
     */
    Resource getResource();

    /**
     * Read basic class metadata for the underlying class.
     */
    ClassMetadata getClassMetadata();

    /**
     * Read full annotation metadata for the underlying class,
     * including metadata for annotated methods.
     */
    AnnotationMetadata getAnnotationMetadata();
}
