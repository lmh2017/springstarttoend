package org.springstarttoend.test.v3;

import org.junit.Assert;
import org.junit.Test;
import org.springstarttoend.beans.BeanDefinition;
import org.springstarttoend.beans.factory.support.ConStructorResolver;
import org.springstarttoend.beans.factory.support.DefaultBeanFactory;
import org.springstarttoend.beans.factory.xml.XmlBeanDefinitionReader;
import org.springstarttoend.io.ClassPathResource;
import org.springstarttoend.io.Resource;
import org.springstarttoend.service.v3.PetStoreService;

public class ConStructorResolverTest {

    @Test
    public void testAutowireConStructor(){
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petStore-v3.xml");
        reader.loadBeanDefinitions(resource);

        BeanDefinition bd = factory.getBeanDefinition("petStore");

        ConStructorResolver resolver = new ConStructorResolver(factory);

        PetStoreService petStoreService = (PetStoreService) resolver.autowireConstructor(bd);

        Assert.assertEquals(1,petStoreService.getVersion());
        Assert.assertNotNull(petStoreService.getAccountDao());
        Assert.assertNotNull(petStoreService.getItemDao());
    }
}
