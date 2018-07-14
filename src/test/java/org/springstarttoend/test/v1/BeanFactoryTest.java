package org.springstarttoend.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.springstarttoend.beans.BeanDefinition;
import org.springstarttoend.beans.factory.BeanCreationException;
import org.springstarttoend.beans.factory.BeanDefinitionStoreException;
import org.springstarttoend.beans.factory.BeanFactory;
import org.springstarttoend.beans.factory.support.DefaultBeanFactory;

public class BeanFactoryTest {

    @Test
    public void testGetBean(){
        BeanFactory beanFactory = null;
        try {
            beanFactory = new DefaultBeanFactory("petStore-v1.xml");
        } catch (BeanCreationException e) {
            e.printStackTrace();
        }
        BeanDefinition bd = beanFactory.getBeanDefinition("petStore");
        Assert.assertEquals("org.springstarttoend.service.v1.PetStoreService",bd.getBeanClassName());
    }

    @Test
    public void testInvalidBean(){
        BeanFactory beanFactory = null;
        try {
            beanFactory = new DefaultBeanFactory("test-v1.xml");
        } catch (BeanCreationException e) {
            return;
        }

        try {
            beanFactory.getBean("test");
        }catch (BeanDefinitionStoreException e){
            return;
        }
        Assert.fail();
    }

}
