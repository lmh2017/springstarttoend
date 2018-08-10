package org.springstarttoend.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.springstarttoend.context.ApplicationContext;
import org.springstarttoend.context.support.ClassPathXmlApplicationContext;
import org.springstarttoend.dao.v2.AccountDao;
import org.springstarttoend.dao.v2.ItemDao;
import org.springstarttoend.service.v2.PetStoreService;

/**
 * @author mh_liu
 * @create 2018/8/10 下午3:32
 */
public class ApplicationContextTest {

    @Test
    public void run(){

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("petStore-v2.xml");
        PetStoreService petStoreService = (PetStoreService) applicationContext.getBean("petStore");

        Assert.assertNotNull(petStoreService.getAccountDao());
        Assert.assertNotNull(petStoreService.getItemDao());

        Assert.assertTrue(petStoreService.getAccountDao() instanceof AccountDao);
        Assert.assertTrue(petStoreService.getItemDao() instanceof ItemDao);




    }
}
