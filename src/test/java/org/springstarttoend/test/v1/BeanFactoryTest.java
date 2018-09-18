package org.springstarttoend.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.springstarttoend.beans.BeanDefinition;
import org.springstarttoend.beans.factory.support.DefaultBeanFactory;
import org.springstarttoend.beans.factory.xml.XmlBeanDefinitionReader;
import org.springstarttoend.core.io.ClassPathResource;

public class BeanFactoryTest {

    @Test
    public void testGetBean(){
        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions(new ClassPathResource("petStore-v1.xml"));
        BeanDefinition bd = beanFactory.getBeanDefinition("petStore");
        Assert.assertEquals("org.springstarttoend.service.v1.PetStoreService",bd.getBeanClassName());
    }

}
