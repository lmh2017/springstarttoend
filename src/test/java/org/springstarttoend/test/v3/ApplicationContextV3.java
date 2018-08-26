package org.springstarttoend.test.v3;

import org.junit.Assert;
import org.junit.Test;
import org.springstarttoend.context.ApplicationContext;
import org.springstarttoend.context.support.ClassPathXmlApplicationContext;
import org.springstarttoend.service.v3.PetStoreService;

public class ApplicationContextV3 {

    @Test
    public void ApplicationContextV3Test(){
        ApplicationContext apx = new ClassPathXmlApplicationContext("petStore-v3.xml");
        PetStoreService pet = (PetStoreService) apx.getBean("petStore");

        Assert.assertNotNull(pet.getAccountDao());
        Assert.assertNotNull(pet.getItemDao());
        Assert.assertNotNull(pet.getVersion());
    }
}
