package org.springstarttoend.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.asm.ClassReader;
import org.springstarttoend.core.annotation.AnnotationAttributes;
import org.springstarttoend.core.io.ClassPathResource;
import org.springstarttoend.core.type.classreading.AnnotationMetadataReadingVisitor;
import org.springstarttoend.core.type.classreading.ClassMetadataReadingVisitor;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author mh_liu
 * @create 2018/9/10 下午2:41
 */
public class ClassReaderTest {

    @Test
    public void testGetClassMetaData() throws IOException {

        ClassPathResource resource = new ClassPathResource("org/springstarttoend/service/v4/PetService.class");
        InputStream in = resource.getInputStream();
        ClassReader reader = new ClassReader(in);

        ClassMetadataReadingVisitor visitor = new ClassMetadataReadingVisitor();
        reader.accept(visitor,ClassReader.SKIP_DEBUG);

        Assert.assertFalse(visitor.isAbstract());
        Assert.assertFalse(visitor.isInterface());
        Assert.assertFalse(visitor.isFinal());
        Assert.assertEquals("org.springstarttoend.service.v4.PetService", visitor.getClassName());
        Assert.assertEquals("java.lang.Object", visitor.getSuperClassName());
        Assert.assertEquals(0, visitor.getInterfaceNames().length);
    }

    @Test
    public void testGetAnnonation() throws Exception{
        ClassPathResource resource = new ClassPathResource("org/springstarttoend/service/v4/PetService.class");
        ClassReader reader = new ClassReader(resource.getInputStream());

        AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();
        reader.accept(visitor,ClassReader.SKIP_DEBUG);

        String annotation = "org.springstarttoend.stereotype.Component";
        Assert.assertTrue(visitor.hasAnnotation(annotation));

        AnnotationAttributes attributes = visitor.getAnnotationAttributes(annotation);
        Assert.assertEquals("petStore",attributes.get("value"));
    }
}
