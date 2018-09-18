package org.springstarttoend.test.v4;

import org.junit.Test;
import org.springstarttoend.beans.factory.support.DefaultBeanFactory;
import org.springstarttoend.beans.factory.xml.XmlBeanDefinitionReader;
import org.springstarttoend.core.io.ClassPathResource;
import org.springstarttoend.core.io.Resource;
import org.springstarttoend.stereotype.Component;

/**
 * @author mh_liu
 * @create 2018/9/17 下午2:45
 */
public class XmlBeanDefinitionReaderTest {

    @Test
    public void testParseScanedBean(){
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstore-v4.xml");
        reader.loadBeanDefinitions(resource);
        String annotation = Component.class.getName();
    }
}
