package org.springstarttoend.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.springstarttoend.beans.BeanDefinition;
import org.springstarttoend.beans.factory.support.DefaultBeanFactory;
import org.springstarttoend.context.annotation.ClassPathBeanDefinitionScanner;
import org.springstarttoend.context.annotation.ScannedGenericBeanDefinition;
import org.springstarttoend.core.annotation.AnnotationAttributes;
import org.springstarttoend.core.type.AnnotationMetadata;
import org.springstarttoend.stereotype.Component;

/**
 * @author mh_liu
 * @create 2018/9/7 下午4:53
 */
public class ClassPathBeanDefinitionScannerTest {

    @Test
    public void testParseScanedBean(){

        DefaultBeanFactory factory = new DefaultBeanFactory();

        String basePackages = "org.springstarttoend.service.v4,org.springstarttoend.dao.v4";

        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(factory);

        scanner.doScan(basePackages);

        String annotation = Component.class.getName();

        {
            BeanDefinition bd = factory.getBeanDefinition("petService");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition)bd;
            AnnotationMetadata amd = sbd.getMetadata();


            Assert.assertTrue(amd.hasAnnotation(annotation));
            AnnotationAttributes attributes = amd.getAnnotationAttributes(annotation);
            Assert.assertEquals("petService", attributes.get("value"));
        }
        {
            BeanDefinition bd = factory.getBeanDefinition("accountDao");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition)bd;
            AnnotationMetadata amd = sbd.getMetadata();
            Assert.assertTrue(amd.hasAnnotation(annotation));
        }
        {
            BeanDefinition bd = factory.getBeanDefinition("itemDao");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition)bd;
            AnnotationMetadata amd = sbd.getMetadata();
            Assert.assertTrue(amd.hasAnnotation(annotation));
        }
    }

}
