package org.springstarttoend.core.type.classreading;

import org.springframework.asm.ClassReader;
import org.springstarttoend.core.io.Resource;
import org.springstarttoend.core.type.AnnotationMetadata;
import org.springstarttoend.core.type.ClassMetadata;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author mh_liu
 * @create 2018/9/15 下午5:41
 */
public class SimpleMetadataReader implements MetadataReader {

    private final Resource resource;

    private final ClassMetadata classMetadata;

    private final AnnotationMetadata annotationMetadata;

    public SimpleMetadataReader(Resource resource) throws IOException {
        InputStream inputStream = new BufferedInputStream(resource.getInputStream());

        ClassReader reader;

        try {
            reader = new ClassReader(inputStream);
        }finally {
            inputStream.close();
        }

        AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();
        reader.accept(visitor,ClassReader.SKIP_DEBUG);

        this.resource = resource;
        this.classMetadata = visitor;
        this.annotationMetadata = visitor;
    }


    @Override
    public Resource getResource() {
        return this.resource;
    }

    @Override
    public ClassMetadata getClassMetadata() {
        return this.classMetadata;
    }

    @Override
    public AnnotationMetadata getAnnotationMetadata() {
        return this.annotationMetadata;
    }
}
