package org.springstarttoend.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.springstarttoend.core.annotation.AnnotationAttributes;
import org.springstarttoend.core.io.ClassPathResource;
import org.springstarttoend.core.type.AnnotationMetadata;
import org.springstarttoend.core.type.classreading.MetadataReader;
import org.springstarttoend.core.type.classreading.SimpleMetadataReader;
import org.springstarttoend.stereotype.Component;

import java.io.IOException;

/**
 * @author mh_liu
 * @create 2018/9/15 下午5:31
 */
public class MetadataReaderTest {

    @Test
    public void testMetadata() throws IOException{
        ClassPathResource resource = new ClassPathResource("org/springstarttoend/service/v4/PetService.class");

        MetadataReader reader = new SimpleMetadataReader(resource);

        AnnotationMetadata metadata = reader.getAnnotationMetadata();

        String annotation = Component.class.getName();

        Assert.assertTrue(metadata.hasAnnotation(annotation));
        AnnotationAttributes attributes = metadata.getAnnotationAttributes(annotation);
        Assert.assertEquals("petService",attributes.get("value"));

        Assert.assertFalse(metadata.isAbstract());
        Assert.assertFalse(metadata.isFinal());
        Assert.assertEquals("org.springstarttoend.service.v4.PetService", metadata.getClassName());


    }
}
