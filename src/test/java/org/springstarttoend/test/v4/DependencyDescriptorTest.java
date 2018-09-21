package org.springstarttoend.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.springstarttoend.beans.factory.config.DependencyDescriptor;
import org.springstarttoend.beans.factory.support.DefaultBeanFactory;
import org.springstarttoend.beans.factory.xml.XmlBeanDefinitionReader;
import org.springstarttoend.core.io.ClassPathResource;
import org.springstarttoend.core.io.Resource;
import org.springstarttoend.dao.v4.AccountDao;
import org.springstarttoend.service.v4.PetService;

import java.lang.reflect.Field;

/**
 * @author mh_liu
 * @create 2018/9/20 下午6:24
 */
public class DependencyDescriptorTest {

    @Test
    public void testResolveDependency() throws NoSuchFieldException {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petStore-v4.xml");
        reader.loadBeanDefinitions(resource);

        Field f = PetService.class.getDeclaredField("accountDao");
        DependencyDescriptor descriptor = new DependencyDescriptor(f,true);

        Object o = factory.resolveDependency(descriptor);

        Assert.assertTrue(o instanceof AccountDao);


    }
}
