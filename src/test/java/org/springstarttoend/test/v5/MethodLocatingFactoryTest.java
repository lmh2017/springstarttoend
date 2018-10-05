package org.springstarttoend.test.v5;

import org.junit.Assert;
import org.junit.Test;
import org.springstarttoend.aop.config.MethodLocatingFactory;
import org.springstarttoend.beans.factory.support.DefaultBeanFactory;
import org.springstarttoend.beans.factory.xml.XmlBeanDefinitionReader;
import org.springstarttoend.core.io.ClassPathResource;
import org.springstarttoend.core.io.Resource;
import org.springstarttoend.tx.TransactionManager;

import java.lang.reflect.Method;

/**
 * @author mh_liu
 * @create 2018/10/4 上午11:17
 */
public class MethodLocatingFactoryTest {

    @Test
    public void testGetMethod() throws Exception {
        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        Resource resource = new ClassPathResource("petStore-v5.xml");
        reader.loadBeanDefinitions(resource);

        MethodLocatingFactory methodLocatingFactory = new MethodLocatingFactory();
        methodLocatingFactory.setTargetBeanName("tx");
        methodLocatingFactory.setMethodName("start");
        methodLocatingFactory.setBeanFactory(beanFactory);

        Method m = methodLocatingFactory.getObject();

        Assert.assertTrue(TransactionManager.class.equals(m.getDeclaringClass()));
        Assert.assertTrue(m.equals(TransactionManager.class.getMethod("start")));

    }


}
