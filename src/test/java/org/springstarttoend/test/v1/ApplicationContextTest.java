package org.springstarttoend.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.springstarttoend.context.ApplicationContext;
import org.springstarttoend.context.support.ClassPathXmlApplicationContext;
import org.springstarttoend.service.v1.PetStoreService;

public class ApplicationContextTest {

    @Test
    public void testCreatBean(){
        ApplicationContext apx = new ClassPathXmlApplicationContext("petStore-v1.xml");
        PetStoreService ps = (PetStoreService) apx.getBean("petStore");
        Assert.assertNotNull(ps);
    }
}
